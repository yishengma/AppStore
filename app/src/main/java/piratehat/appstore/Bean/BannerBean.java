package piratehat.appstore.Bean;

/**
 * Created by PirateHat on 2018/10/27.
 */

public class BannerBean {

    private String mImageUrl;
    private String mDetailUrl;
    private String mName;

    public String getImageUrl() {
        return mImageUrl == null ? "" : mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getDetailUrl() {
        return mDetailUrl == null ? "" : mDetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        mDetailUrl = detailUrl;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
