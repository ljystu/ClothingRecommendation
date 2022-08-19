package dhu.rs.controller.mall;

import dhu.rs.common.Constants;
import dhu.rs.controller.AuthService;
import dhu.rs.controller.vo.SearchPageCategoryVO;
import dhu.rs.dao.MallUserMapper;
import dhu.rs.entity.MallUser;
import dhu.rs.entity.RSGoods;
import dhu.rs.service.RSCategoryService;
import dhu.rs.service.RSGoodsService;
import dhu.rs.service.RSUserService;
import dhu.rs.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController {

    @Resource
    private RSGoodsService RSGoodsService;
    @Resource
    private RSCategoryService RSCategoryService;
    @Resource
    private RSUserService RSUserService;


    @GetMapping({"/searchall", "/searchall.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = RSCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        if (params.containsKey("RSuser") && !StringUtils.isEmpty(params.get("RSuser") + "")) {
            Long uid = (long) RSUserService.selectHashuser((String) params.get("RSuser"));
            params.put("uid", uid);
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
            AuthService authService = new AuthService();
            Map<String, Object> categories = authService.words(keyword);
            if (categories.containsKey("goodsCategoryId")) {
                params.put("goodsCategoryId", categories.get("goodsCategoryId"));
                params.put("subCategoryId", categories.get("subCategoryId"));
            }
            if (categories.containsKey("gender"))
                params.put("gender", categories.get("gender"));
        }


        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);

        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        if (params.containsKey("RSuser") && !StringUtils.isEmpty(params.get("RSuser") + "")) {
            if (RSUserService.uidExist((Long) pageUtil.get("uid")) == 0) {
                request.setAttribute("pageResult", RSGoodsService.searchNullRSGoods(pageUtil));
            } else
                request.setAttribute("pageResult", RSGoodsService.searchRSGoods(pageUtil));
        } else
            request.setAttribute("pageResult", RSGoodsService.searchNullRSGoods(pageUtil));
        return "mall/searchall";
    }

    @GetMapping({"/search", "/search.html"})
    public String search(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_RECOMMEND_LIMIT);
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        if (params.containsKey("RSuser") && !StringUtils.isEmpty(params.get("RSuser") + "")) {
            Long uid = (long) RSUserService.selectHashuser((String) params.get("RSuser"));
            params.put("uid", uid);
        }

        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
            AuthService authService = new AuthService();
            Map<String, Object> categories = authService.words(keyword);

            params.put("goodsCategoryId", categories.get("goodsCategoryId"));
            params.put("subCategoryId", categories.get("subCategoryId"));
            params.put("gender", categories.get("gender"));
        }

        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        if (params.containsKey("RSuser") && !StringUtils.isEmpty(params.get("RSuser") + "")) {
            if (RSUserService.uidExist((Long) pageUtil.get("uid")) == 0) {
                request.setAttribute("pageResult", RSGoodsService.searchNullRSGoods(pageUtil));
            } else
                request.setAttribute("pageResult", RSGoodsService.searchRSGoods(pageUtil));
        } else
            request.setAttribute("pageResult", RSGoodsService.searchNullRSGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping({"/recommend", "/recommend.html"})
    public String recommend(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }

        String username = (String) params.get("RSuser");
        if (username != null) {
            Long uid = RSUserService.getUserId(username);
            int i = RSUserService.updateHasLoggin(uid);
        }
        params.put("limit", Constants.GOODS_LOGIN_SURVEY);

        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";

        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }

        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", RSGoodsService.searchRSGoods(pageUtil));
        return "mall/recommend";
    }

    @GetMapping({"/survey"})
    public String survey(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_LOGIN_SURVEY);

        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);

        request.setAttribute("pageResult", RSGoodsService.survey(pageUtil));
        return "mall/recommend";
    }
}
