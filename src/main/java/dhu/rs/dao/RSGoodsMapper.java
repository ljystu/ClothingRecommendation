package dhu.rs.dao;

import dhu.rs.controller.vo.SurveyVO;
import dhu.rs.entity.RSGoods;

import dhu.rs.util.PageQueryUtil;
import dhu.rs.util.PageResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface RSGoodsMapper {

    int deleteByPrimaryKey(Long goodsId);

    int insert(RSGoods record);

    int insertSelective(RSGoods record);

    RSGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(RSGoods record);

    int updateByPrimaryKeyWithBLOBs(RSGoods record);

    int updateByPrimaryKey(RSGoods record);

    List<RSGoods> findRSGoodsList(PageQueryUtil pageUtil);

    int getTotalRSGoods(PageQueryUtil pageUtil);

    List<RSGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<RSGoods> findRSGoodsListBySearch(PageQueryUtil pageUtil);

    int Insertsurvey(SurveyVO surveyVO);

    int getTotalRSGoodsBySearch(PageQueryUtil pageUtil);

//    int batchInsert(@Param("RSGoodsList") List<RSGoods> RSGoodsList);

    int batchUpdateSellStatus(@Param("orderIds") Long[] orderIds, @Param("sellStatus") int sellStatus);

    List<RSGoods> findRSGoodsListByMultipleSearch(PageQueryUtil pageQueryUtil);

    List<RSGoods> findRSGoodsListByMultipleSearchNull(PageQueryUtil pageQueryUtil);


}