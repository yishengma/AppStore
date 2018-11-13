package piratehat.appstore.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayDeque;
import java.util.List;

import piratehat.appstore.Bean.AppBean;
import piratehat.appstore.utils.UnitUtil;

/**
 *
 *
 * Created by PirateHat on 2018/10/28.
 */

public class AppDto implements Mapper<AppBean> , Parcelable{

    private String apkUrl;
    private long appDownCount;
    private String appName;
    private String iconUrl;
    private String pkgName;
    private double averageRating;
    private String editorIntro;
    private long fileSize;
    private List<String> images;
    private String categoryName;

    private AppDto(Parcel in) {

        apkUrl = in.readString();
        appDownCount = in.readLong();
        appName = in.readString();
        iconUrl = in.readString();
        pkgName = in.readString();
        averageRating = in.readDouble();
        editorIntro = in.readString();
        fileSize = in.readLong();
        images = in.createStringArrayList();
        categoryName = in.readString();
    }

    public static final Creator<AppDto> CREATOR = new Creator<AppDto>() {
        @Override
        public AppDto createFromParcel(Parcel in) {
            return new AppDto(in);
        }

        @Override
        public AppDto[] newArray(int size) {
            return new AppDto[size];
        }
    };

    @Override
    public AppBean transform() {
        AppBean bean = new AppBean();
        bean.setName(appName)
                .setDownloadUrl(apkUrl)
                .setIconUrl(iconUrl)
                .setHot(UnitUtil.converse(appDownCount))
                .setAppSize(UnitUtil.convertFileSize(fileSize))
                .setDetailUrl(pkgName)
                .setCategory(categoryName)
                .setIntro(editorIntro)
                .setMpkgName(pkgName);

        return bean;
    }

    public String getCategoryName() {
        return categoryName == null ? "" : categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getApkUrl() {
        return apkUrl == null ? "" : apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }


    public String getAppName() {
        return appName == null ? "" : appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIconUrl() {
        return iconUrl == null ? "" : iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getPkgName() {
        return pkgName == null ? "" : pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public void setAppDownCount(long appDownCount) {
        this.appDownCount = appDownCount;
    }

    public void setAverageRating(long averageRating) {
        this.averageRating = averageRating;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getAppDownCount() {
        return appDownCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setAppDownCount(int appDownCount) {
        this.appDownCount = appDownCount;
    }



    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }


    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public String getEditorIntro() {
        return editorIntro == null ? "" : editorIntro;
    }

    public void setEditorIntro(String editorIntro) {
        this.editorIntro = editorIntro;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apkUrl);
        dest.writeLong(appDownCount);
        dest.writeString(appName);
        dest.writeString(iconUrl);
        dest.writeString(pkgName);
        dest.writeDouble(averageRating);
        dest.writeString(editorIntro);
        dest.writeLong(fileSize);
        dest.writeStringList(images);
        dest.writeString(categoryName);
    }
}
