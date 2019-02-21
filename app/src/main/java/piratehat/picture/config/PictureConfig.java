package piratehat.picture.config;

import piratehat.picture.cache.DoubleCache;
import piratehat.picture.cache.IBitmapCache;
import piratehat.picture.loader.policy.ILoadPolicy;
import piratehat.picture.loader.policy.ReverseLoaderPolicy;

/**
 * Created by PirateHat on 2019/2/21.
 */

public class PictureConfig {

    private IBitmapCache mBitmapCache = new DoubleCache();
    private ILoadPolicy mLoadPolicy = new ReverseLoaderPolicy();
    private DisplayConfig mDisplayConfig = new DisplayConfig();

    public IBitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public void setBitmapCache(IBitmapCache bitmapCache) {
        mBitmapCache = bitmapCache;
    }

    public ILoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public void setLoadPolicy(ILoadPolicy loadPolicy) {
        mLoadPolicy = loadPolicy;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public void setDisplayConfig(DisplayConfig displayConfig) {
        mDisplayConfig = displayConfig;
    }

    public static class Builder{
        private PictureConfig mConfig;

        public Builder() {
            mConfig = new PictureConfig();
        }
        public Builder setBitmapCache(IBitmapCache bitmapCache) {
            mConfig.mBitmapCache = bitmapCache;
            return this;
        }
        public Builder setLoadPolicy(ILoadPolicy loadPolicy) {
            mConfig.mLoadPolicy = loadPolicy;
            return this;
        }
        public Builder setPlaceHolder(int id) {
            mConfig.mDisplayConfig.PLACE_HOLDRE = id;
            return this;
        }
        public Builder setError(int id) {
            mConfig.mDisplayConfig.ERROR_HOLDER = id;
            return this;
        }
        public PictureConfig build() {
            return mConfig;
        }

    }
}
