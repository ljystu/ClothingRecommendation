'''
BiasSvd Model
'''

import pandas as pd
import numpy as np
import pymysql
from sklearn.model_selection import KFold
import time
from sqlalchemy import create_engine
import logging


class BiasSvd(object):

    def __init__(self, alpha, reg, max_uid, number_LatentFactors=10, number_epochs=10,
                 columns=["uid", "iid", "ratings"]):
        self.alpha = alpha  #
        self.reg = reg
        self.number_LatentFactors = number_LatentFactors  #
        self.number_epochs = number_epochs
        self.columns = columns
        self.max_uid = max_uid
        self.user_index = [i for i in range(1, max_uid + 1)]
        self.item_index = [i for i in range(1, 62802)]

    def fit(self, dataset):
        '''
        fit dataset
        :param dataset: uid, iid, rating
        :return:
        '''

        self.dataset = pd.DataFrame(dataset)

        self.globalMean = self.dataset[self.columns[2]].mean()
        print(self.globalMean)

        self.P, self.Q, self.bu, self.bi, time = self.sgd()

        return time

    def sgd(self):

        P = {i: np.random.rand(self.number_LatentFactors).astype(np.float32) for i in range(1, max_uid + 1)}
        Q = {i: np.random.rand(self.number_LatentFactors).astype(np.float32) for i in range(1, 62802)}

        bu = dict(zip(self.user_index, np.zeros(len(self.user_index))))
        bi = dict(zip(self.item_index, np.zeros(len(self.item_index))))
        i = 1
        a_0 = 0.03
        a_1 = 1.5
        time_start = time.time()
        for_mae = 0
        alpha_list = []
        while True:
            print("iter%d" % i)
            error_list = []
            mae_list = []
            if i == 1:
                alpha = (a_0 / (a_1 ** i)) + 0.007
            else:
                alpha = (a_0 / a_1 ** (i * 0.8)) + (0.05 / (i ** 3))

            for uid, iid, r_ui in self.dataset.itertuples(index=False):
                v_pu = P[uid]
                # print(v_pu)
                v_qi = Q[iid]
                p_r = self.globalMean + bu[uid] + bi[iid] + np.dot(v_pu, v_qi)

                err = np.float32(r_ui - p_r)

                v_pu += alpha * (err * v_qi - self.reg * v_pu)
                v_qi += alpha * (err * v_pu - self.reg * v_qi)

                P[uid] = v_pu
                Q[iid] = v_qi

                bu[uid] += alpha * (err - self.reg * bu[uid])
                bi[iid] += alpha * (err - self.reg * bi[iid])

                error_list.append(err ** 2)
                mae_list.append(abs(err))
            alpha_list.append(alpha)

            print("rmse:", np.sqrt(np.mean(error_list)))
            print("mae:", np.mean(mae_list))
            print("alpha:", alpha)
            if (for_mae - np.mean(mae_list) < 0.0001 and i != 1):
                print(i)
                m = np.array(alpha_list)
                np.save('my.npy', m)
                break
            for_mae = np.mean(mae_list)
            i += 1

        time_end = time.time()
        print('totally cost', time_end - time_start)
        return P, Q, bu, bi, time_end - time_start

    def predict(self, uid, iid):
        p_u = self.P[uid]
        q_i = self.Q[iid]
        pre_score = self.globalMean + self.bu[uid] + self.bi[iid] + np.dot(p_u, q_i)
        if (pre_score) > 5:
            pre_score = 5
        if (pre_score) < 1:
            pre_score = 1
        return pre_score


if __name__ == '__main__':
    conn = pymysql.connect(host='rm-uf624y772dsn4pf1tuo.mysql.rds.aliyuncs.com', user="ljy", passwd="Ljy1104678937!",
                           db="mall", port=3306)

    sql = "select uid,iid,ratings from quanbushuju;"
    cur = conn.cursor()
    cur.execute(sql)
    data = cur.fetchall()
    sql = "select max(uid) from hashusers;"
    cur = conn.cursor()
    cur.execute(sql)
    max_returns = cur.fetchall()
    print(max_returns[0][0])
    max_uid = max_returns[0][0]
    mydata = pd.DataFrame(list(data), columns=['uid', 'iid', 'ratings'])
    mydata.drop_duplicates(keep='first', inplace=True)
    dataset = mydata.loc[:, ['uid', 'iid', 'ratings']]
    score_dic = []

    kf = KFold(n_splits=5, shuffle=True, random_state=1)
    plt_RMSE = []
    plt_mae = []
    times = []
    for train_index, test_index in kf.split(dataset):
        trainset = dataset.iloc[train_index]
        testset = dataset.iloc[test_index]

        bsvd = BiasSvd(0.01, 0.01, max_uid, 20, 10)
        print(bsvd.max_uid)
        duration = bsvd.fit(trainset)
        for j in range(1019989, max_uid + 1):
            for i in range(1, 62802):
                score_dic.append((bsvd.predict(j, i), j, i))
                print((bsvd.predict(j, i), j, i))
        break

    connection = pymysql.connect(host="rm-uf624y772dsn4pf1tuo.mysql.rds.aliyuncs.com", port=3306, user='ljy',
                                 password='Ljy1104678937!',
                                 db='mall', cursorclass=pymysql.cursors.DictCursor)
    cursor = connection.cursor()

    with connection.cursor() as cursor:
        try:
            delete = "delete from score;"
            cursor.execute(delete)
            sql = "insert into  score(score,uid,iid)  values(%s,%s,%s);"
            cursor.executemany(sql, score_dic)
            overall = "update score,test set overall = score.score*(test.review+test.brand)/2 where score.iid=test.iid;"
            cursor.execute(overall)
            connection.commit()
        except:
            logging.exception("exception")
            connection.rollback()

    connection.close()
