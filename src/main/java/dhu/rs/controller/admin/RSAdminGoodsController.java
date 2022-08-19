package dhu.rs.controller.admin;

import dhu.rs.common.Constants;
import dhu.rs.common.RSCategoryNameEnum;
import dhu.rs.common.ServiceResultEnum;
import dhu.rs.entity.GoodsCategory;
import dhu.rs.entity.RSGoods;
import dhu.rs.service.RSCategoryService;
import dhu.rs.service.RSGoodsService;
import dhu.rs.util.PageQueryUtil;
import dhu.rs.util.Result;
import dhu.rs.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class RSAdminGoodsController {

    @Resource
    private RSGoodsService RSGoodsService;
    @Resource
    private RSCategoryService RSCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "RS_goods");
        return "admin/RS_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = RSCategoryService.selectCategories(RSCategoryNameEnum.LEVEL_ONE.getName(), 0);

        request.setAttribute("firstLevelCategories", firstLevelCategories);
        request.setAttribute("path", "goods-edit");
        return "admin/RS_goods_edit";


    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        RSGoods RSGoods = RSGoodsService.getRSGoodsById(goodsId);
        if (RSGoods == null) {
            return "error/error_400";
        }

        List<GoodsCategory> firstLevelCategories = RSCategoryService.selectCategories(RSCategoryNameEnum.LEVEL_ONE.getName(), 0);
        request.setAttribute("firstLevelCategories", firstLevelCategories);
        request.setAttribute("firstLevelCategoryId",RSGoods.getGoodsCategoryId());
        request.setAttribute("goods", RSGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/RS_goods_edit";
    }


    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(RSGoodsService.getRSGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody RSGoods RSGoods) {
        if (StringUtils.isEmpty(RSGoods.getGoodsName())
                || Objects.isNull(RSGoods.getGoodsCategoryId())
                || Objects.isNull(RSGoods.getSellingPrice())
                || Objects.isNull(RSGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(RSGoods.getGoodsCoverImg())
                ) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = RSGoodsService.saveRSGoods(RSGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody RSGoods RSGoods) {
        if (Objects.isNull(RSGoods.getGoodsId())
                || StringUtils.isEmpty(RSGoods.getGoodsName())
                || Objects.isNull(RSGoods.getSellingPrice())
                || Objects.isNull(RSGoods.getGoodsCategoryId())
                || Objects.isNull(RSGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(RSGoods.getGoodsCoverImg())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = RSGoodsService.updateRSGoods(RSGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        RSGoods goods = RSGoodsService.getRSGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (RSGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}