package dhu.rs.service.impl;

import dhu.rs.common.ServiceResultEnum;
import dhu.rs.controller.vo.RSSearchGoodsVO;
import dhu.rs.controller.vo.SurveyVO;
import dhu.rs.dao.MallUserMapper;
import dhu.rs.dao.RSGoodsMapper;
import dhu.rs.entity.RSGoods;
import dhu.rs.service.RSGoodsService;
import dhu.rs.util.BeanUtil;
import dhu.rs.util.MybatisUtil;
import dhu.rs.util.PageQueryUtil;
import dhu.rs.util.PageResult;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RSGoodsServiceImpl implements RSGoodsService {

    @Autowired
    private RSGoodsMapper goodsMapper;
    @Autowired
    private MallUserMapper mallUserMapper;

    @Override
    public PageResult getRSGoodsPage(PageQueryUtil pageUtil) {
        List<RSGoods> goodsList = goodsMapper.findRSGoodsList(pageUtil);
        int total = goodsMapper.getTotalRSGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveRSGoods(RSGoods goods) {
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

//    @Override
//    public void batchSaveRSGoods(List<RSGoods> RSGoodsList) {
//        if (!CollectionUtils.isEmpty(RSGoodsList)) {
//            goodsMapper.batchInsert(RSGoodsList);
//        }
//    }

    @Override
    public String updateRSGoods(RSGoods goods) {
        RSGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public RSGoods getRSGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchRSGoods(PageQueryUtil pageUtil) {

        List<RSGoods> goodsList = goodsMapper.findRSGoodsListByMultipleSearch(pageUtil);
        int total = goodsMapper.getTotalRSGoodsBySearch(pageUtil);
        List<RSSearchGoodsVO> RSSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            RSSearchGoodsVOS = BeanUtil.copyList(goodsList, RSSearchGoodsVO.class);
            for (RSSearchGoodsVO RSSearchGoodsVO : RSSearchGoodsVOS) {
                String goodsName = RSSearchGoodsVO.getGoodsName();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 20) {
                    goodsName = goodsName.substring(0, 20) + "...";
                    RSSearchGoodsVO.setGoodsName(goodsName);
                }
            }
        }
        PageResult pageResult = new PageResult(RSSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    @Override
    public PageResult searchNullRSGoods(PageQueryUtil pageUtil) {

        List<RSGoods> goodsList = goodsMapper.findRSGoodsListByMultipleSearchNull(pageUtil);
        int total = goodsMapper.getTotalRSGoodsBySearch(pageUtil);
        List<RSSearchGoodsVO> RSSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            RSSearchGoodsVOS = BeanUtil.copyList(goodsList, RSSearchGoodsVO.class);
            for (RSSearchGoodsVO RSSearchGoodsVO : RSSearchGoodsVOS) {
                String goodsName = RSSearchGoodsVO.getGoodsName();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 20) {
                    goodsName = goodsName.substring(0, 20) + "...";
                    RSSearchGoodsVO.setGoodsName(goodsName);
                }
            }
        }
        PageResult pageResult = new PageResult(RSSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public PageResult survey(PageQueryUtil pageUtil) {
        SurveyVO surveyVO =new SurveyVO();
        int score = Integer.parseInt((String) pageUtil.get("score"));
        Long goodsId = Long.parseLong((String) pageUtil.get("goodsId"));
        RSGoods goods = goodsMapper.selectByPrimaryKey(goodsId);


        String username = (String) pageUtil.get("user");
        surveyVO.setIid(Integer.parseInt((String) pageUtil.get("goodsId")));

        surveyVO.setRatings(score);

        surveyVO.setUserID(username);

        surveyVO.setUid(mallUserMapper.selectUidByUsername(username));

        SqlSession sqlSession = MybatisUtil.openSession();
        RSGoodsMapper mapper = sqlSession.getMapper(RSGoodsMapper.class);
        int result =mapper.Insertsurvey(surveyVO);
        sqlSession.commit(); //注意提交事物

        sqlSession.close();

        if (result != 1)
            return null;


        List<RSGoods> goodsList = goodsMapper.findRSGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalRSGoodsBySearch(pageUtil);
        List<RSSearchGoodsVO> RSSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            RSSearchGoodsVOS = BeanUtil.copyList(goodsList, RSSearchGoodsVO.class);
            for (RSSearchGoodsVO RSSearchGoodsVO : RSSearchGoodsVOS) {
                String goodsName = RSSearchGoodsVO.getGoodsName();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 20) {
                    goodsName = goodsName.substring(0, 20) + "...";
                    RSSearchGoodsVO.setGoodsName(goodsName);
                }
            }
        }
        PageResult pageResult = new PageResult(RSSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public PageResult findRSGoodsListByMultipleSearch(PageQueryUtil pageQueryUtil) {


        return null;
    }


}
