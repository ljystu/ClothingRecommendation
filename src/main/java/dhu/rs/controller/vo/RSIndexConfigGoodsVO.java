package dhu.rs.controller.vo;

import java.io.Serializable;

/**
 * 首页配置商品VO
 */
public class RSIndexConfigGoodsVO implements Serializable {

    private Long goodsId;

    private String goodsName;

    private String link;

    private String goodsCoverImg;

    private String sellingPrice;
    private int reviewnums;

    public int getReviewnums() {
        return reviewnums;
    }

    public void setReviewnums(int reviewnums) {
        this.reviewnums = reviewnums;
    }

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    private Double stars;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

}
