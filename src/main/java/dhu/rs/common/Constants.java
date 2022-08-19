package dhu.rs.common;

import java.util.Arrays;
import java.util.List;

public class Constants {
//    public final static int INDEX_CATEGORY_NUMBER = 10;//首页一级分类的最大数量
//
//    public final static int SEARCH_CATEGORY_NUMBER = 8;//搜索页一级分类的最大数量

    public final static int INDEX_GOODS_HOT_NUMBER = 10;//首页热卖商品数量

    public final static int INDEX_GOODS_NEW_NUMBER = 5;//首页新品数量

    public final static String MALL_VERIFY_CODE_KEY = "mallVerifyCode";//验证码key

    public final static String MALL_USER_SESSION_KEY = "RSUser";//session中user的key

    public final static int GOODS_SEARCH_PAGE_LIMIT = 8;//搜索分页的默认条数(每页10条)

    public final static int GOODS_LOGIN_SURVEY = 3;

    public final static int GOODS_RECOMMEND_LIMIT = 3;

    public final static int SELL_STATUS_UP = 0;//商品上架状态

    public final static int SELL_STATUS_DOWN = 1;//商品下架状态

    public final static List<String> MALE = Arrays.asList("男装", "男人", "男孩", "男", "男子", "男士", "男式", "男性");

    public final static List<String> FEMALE = Arrays.asList("女装", "女人", "女孩", "女", "女子", "女士", "女式", "男性");

    public final static List<String> SPORT = Arrays.asList("运动服","运动衫","运动衣", "运动裤", "运动装", "运动", "打球", "篮球", "足球", "球类", "跑步", "排球", "日常", "娱乐",
            "旅行", "旅游", "居家", "居家服", "自由", "上学", "学校", "长跑", "慢跑");

    public final static List<String> CASUAL = Arrays.asList("休闲服", "休闲裤", "休闲装", "休闲", "日常", "娱乐", "活泼", "时尚", "舒适", "家用", "社交", "party", "派对",
            "旅行", "家居", "居家", "居家服", "室内", "自由", "出街", "逛街", "约会", "舒适", "约会", "出行", "旅游", "生活", "生日", "庆祝", "上学", "舒服",
            "学校", "通勤", "随意");

    public final static List<String> OUTDOORS = Arrays.asList("户外", "登山服", "登山", "徒步", "越野", "定向", "野外", "爬山");

    public final static List<String> FORMAL_OCCASION = Arrays.asList("正式", "宴会", "晚宴", "正式", "金融", "正装", "地产", "国企",
            "央企", "西装", "晚礼服", "礼服", "套装", "商务", "职场", "严肃", "礼仪", "午宴", "访问", "会议", "招待会", "婚礼", "丧礼", "舞会", "婚宴"
            , "歌剧", "音乐会", "酒会", "招聘", "银行");

    public final static List<String> WORK = Arrays.asList("通勤", "上班", "工作", "互联网", "衬衫", "年会", "聚餐", "出差", "面试", "整洁",
            "外企", "公关", "四大", "快消", "工装");

    public final static List<String> FORMAL_WORK = Arrays.asList();

    public final static int[][] CATEGORY = {{67, 107}, {73, 112}, {68, 107}, {111, 15}, {74, 107}};


}
