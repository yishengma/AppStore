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

    public BannerBean setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        return this;
    }

    public String getDetailUrl() {
        return mDetailUrl == null ? "" : mDetailUrl;
    }

    public BannerBean setDetailUrl(String detailUrl) {
        mDetailUrl = detailUrl;
        return this;
    }

    public String getName() {
        return mName == null ? "" : mName;
    }

    public BannerBean setName(String name) {
        mName = name;
        return this;
    }
}
