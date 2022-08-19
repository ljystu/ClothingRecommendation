package dhu.rs.common;

/**
 * @apiNote 分类级别
 */
public enum RSCategoryNameEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "一级分类");

    private int isRight;
    private String name;

    RSCategoryNameEnum(int isRight, String name) {
        this.isRight = isRight;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsRight() {
        return isRight;
    }

    public void setIsRight(int isRight) {
        this.isRight = isRight;
    }
}
