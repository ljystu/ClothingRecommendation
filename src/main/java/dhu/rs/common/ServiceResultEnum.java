package dhu.rs.common;

public enum ServiceResultEnum {
    ERROR("error"),

    SUCCESS("success"),

    NEED_INFORMATION("需要调查记录"),

    DATA_NOT_EXIST("未查询到记录！"),

    SAME_CATEGORY_EXIST("有同级同名的分类！"),

    SAME_LOGIN_NAME_EXIST("用户名已存在！"),

    LOGIN_NAME_NULL("请输入登录名！"),

    LOGIN_PASSWORD_NULL("请输入密码！"),

    LOGIN_VERIFY_CODE_NULL("请输入验证码！"),

    LOGIN_VERIFY_CODE_ERROR("验证码错误！"),

    GOODS_NOT_EXIST("商品不存在！"),

    GOODS_PUT_DOWN("商品已下架！"),

    LOGIN_ERROR("登录失败！"),

    LOGIN_USER_LOCKED("用户已被禁止登录！"),

    OPERATE_ERROR("操作失败！"),


    DB_ERROR("database error");

    private String result;

    ServiceResultEnum(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
