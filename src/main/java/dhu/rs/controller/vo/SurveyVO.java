package dhu.rs.controller.vo;

import java.io.Serializable;

public class SurveyVO implements Serializable {

    private String userID;
    private int uid;
    private int iid;
    private int ratings;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    @Override
    public String toString() {
        return "SurveyVO{" +
                "userID='" + userID + '\'' +
                ", uid=" + uid +
                ", iid=" + iid +
                ", ratings=" + ratings +
                '}';
    }
}
