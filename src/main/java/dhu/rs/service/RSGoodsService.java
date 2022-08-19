package dhu.rs.service;

import dhu.rs.entity.RSGoods;
import dhu.rs.util.PageQueryUtil;
import dhu.rs.util.PageResult;

import java.util.List;

public interface RSGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getRSGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveRSGoods(RSGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param RSGoodsList
     * @return
     */
//    void batchSaveRSGoods(List<RSGoods> RSGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateRSGoods(RSGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    RSGoods getRSGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchRSGoods(PageQueryUtil pageUtil);

    PageResult survey(PageQueryUtil pageUtil);

    PageResult findRSGoodsListByMultipleSearch(PageQueryUtil pageQueryUtil);

    PageResult searchNullRSGoods(PageQueryUtil pageUtil);
}
