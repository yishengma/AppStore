package piratehat.appstore.Bean;

import android.support.v7.util.DiffUtil;

import piratehat.appstore.app.App;

/**
 *
 * Created by PirateHat on 2018/10/28.
 */

public class AppBean {


    private String mIconUrl;
    private String mDownloadUrl;
    private String mName;
    private String mDetailUrl;
    private String mAppSize;
    private String mHot;
    private String mpkgName;
    private String category;
    private String mIntro;



    public String getCategory() {
        return category == null ? "" : category;
    }

    public AppBean setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getIntro() {
        return mIntro == null ? "" : mIntro;
    }

    public AppBean setIntro(String intro) {
        mIntro = intro;
        return this;
    }

    public String getMpkgName() {
        return mpkgName == null ? "" : mpkgName;
    }

    public AppBean setMpkgName(String mpkgName) {
        this.mpkgName = mpkgName;
        return this;
    }

    public String getIconUrl() {
        return mIconUrl == null ? "" : mIconUrl;
    }

    public AppBean setIconUrl(String iconUrl) {
        mIconUrl = iconUrl;
        return this;
    }

    public String getDownloadUrl() {
        return mDownloadUrl == null ? "" : mDownloadUrl;
    }

    public AppBean setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
        return this;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public AppBean setName(String name) {
        mName = name;
        return this;
    }

    public String getDetailUrl() {
        return mDetailUrl == null ? "" : mDetailUrl;
    }

    public AppBean setDetailUrl(String detailUrl) {
        mDetailUrl = detailUrl;
        return this;
    }

    public String getAppSize() {
        return mAppSize == null ? "" : mAppSize;
    }

    public AppBean setAppSize(String appSize) {
        mAppSize = appSize;
        return this;
    }

    public String getHot() {
        return mHot == null ? "" : mHot;
    }

    public AppBean setHot(String hot) {
        mHot = hot;
        return this;
    }

    @Override
    public String toString() {
        return "AppBean{" +
                "mIconUrl='" + mIconUrl + '\'' +
                ", mDownloadUrl='" + mDownloadUrl + '\'' +
                ", mName='" + mName + '\'' +
                ", mDetailUrl='" + mDetailUrl + '\'' +
                ", mAppSize='" + mAppSize + '\'' +
                ", mHot='" + mHot + '\'' +
                '}';
    }
}
