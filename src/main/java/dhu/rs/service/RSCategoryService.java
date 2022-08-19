package dhu.rs.service;

import dhu.rs.controller.vo.SearchPageCategoryVO;
import dhu.rs.entity.GoodsCategory;
import dhu.rs.util.PageQueryUtil;
import dhu.rs.util.PageResult;

import java.util.List;

public interface RSCategoryService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Integer[] ids);


    /**
     * 返回分类数据(搜索页调用)
     *
     * @param categoryId
     * @return
     */
    SearchPageCategoryVO getCategoriesForSearch(Long categoryId);

    /**
     * 根据parentId和level获取分类列表
     *

     * @return
     */
    List<GoodsCategory> selectCategories(String categoryName, int i);
}
