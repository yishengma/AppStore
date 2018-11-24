package piratehat.appstore.Bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * Created by PirateHat on 2018/11/20.
 */

public class AppDetailBean implements Serializable{

    private String mIcon;
    private String mName;
    private String mScore;
    private String mBaseInfo;
    private String mSize;

    private ArrayList<String> mImageList;

    private String mInfo;
    private String mDetailInfo;

    private String mDownloadUrl;

    public AppDetailBean() {
    }

    public String getSize() {
        return mSize == null ? "" : mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getIcon() {
        return mIcon == null ? "" : mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getScore() {
        return mScore == null ? "" : mScore;
    }

    public void setScore(String score) {
        mScore = score;
    }

    public String getBaseInfo() {
        return mBaseInfo == null ? "" : mBaseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        mBaseInfo = baseInfo;
    }

    public ArrayList<String> getImageList() {
        return mImageList;
    }

    public void setImageList(ArrayList<String> imageList) {
        mImageList = imageList;
    }

    public String getInfo() {
        return mInfo == null ? "" : mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public String getDetailInfo() {
        return mDetailInfo == null ? "" : mDetailInfo;
    }

    public void setDetailInfo(String detailInfo) {
        mDetailInfo = detailInfo;
    }

    public String getDownloadUrl() {
        return mDownloadUrl == null ? "" : mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "AppDetailBean{" +
                "mIcon='" + mIcon + '\'' +
                ", mName='" + mName + '\'' +
                ", mScore='" + mScore + '\'' +
                ", mBaseInfo='" + mBaseInfo + '\'' +
                ", mImageList=" + mImageList +
                ", mInfo='" + mInfo + '\'' +
                ", mDetailInfo='" + mDetailInfo + '\'' +
                '}';
    }
}
