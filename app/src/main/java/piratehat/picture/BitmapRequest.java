package piratehat.picture;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

import piratehat.picture.config.DisplayConfig;
import piratehat.picture.loader.policy.ILoadPolicy;

/**
 * Created by PirateHat on 2019/2/21.
 */

public class BitmapRequest implements Comparable<BitmapRequest> {
    private String mImageUrl;// 图片的 Url
    private SoftReference<ImageView> mImageViewSoftReference;//ImageView 软引用，内存不足的时候会被回收，即不加载图片
    private String mMD5CacheKey;//图片缓存的 标识
    private int mSerialNo; // 请求的序列号
    private Picture.CallBack mCallBack; //结果的回调
    private DisplayConfig mDisplayConfig; // 显示图片的而外配置，暂位符，错误图等

    private boolean mIsCancel = false;
    private Object mTag;// 请求的 tag???

    private ILoadPolicy mLoadPolicy = Picture.getInstance().getPictureConfig().getLoadPolicy(); // 加载的策略

    public BitmapRequest(String imageUrl,
                         ImageView imageView,
                         Picture.CallBack callBack,
                         DisplayConfig displayConfig) {
        mImageUrl = imageUrl;
        mImageViewSoftReference = new SoftReference<>(imageView);
        mCallBack = callBack;
        mMD5CacheKey = MD5Utils.toMD5(imageUrl);
        if (imageView != null) {
            imageView.setTag(imageUrl);//为ImageView 设置 tag ,防止错位
        }
        if (displayConfig != null) {
            mDisplayConfig = displayConfig;
        } else {

            //默认的显示配置
            mDisplayConfig = Picture.getInstance().getPictureConfig().getDisplayConfig();
        }


    }

    @Override
    public int compareTo(@NonNull BitmapRequest o) {
        return mLoadPolicy.compareTo(o/*req1*/, this);
    }

    @Override
    public int hashCode() {
        int result = mLoadPolicy != null ? mLoadPolicy.hashCode() : 0;
        result = 31 * result + mSerialNo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BitmapRequest request = (BitmapRequest) obj;
        return mSerialNo == request.mSerialNo &&
                (mLoadPolicy != null ?
                        mLoadPolicy.equals(request.mLoadPolicy) :
                        request.mLoadPolicy != null);
    }

    public String getImageUrl() {
        return mImageUrl == null ? "" : mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public ImageView getImageView() {
        return mImageViewSoftReference.get();
    }

    public void setImageViewSoftReference(ImageView imageViewSoftReference) {
        mImageViewSoftReference = new SoftReference<ImageView>(imageViewSoftReference);
    }

    public String getMD5CacheKey() {
        return mMD5CacheKey == null ? "" : mMD5CacheKey;
    }

    public void setMD5CacheKey(String MD5CacheKey) {
        mMD5CacheKey = MD5CacheKey;
    }

    public int getSerialNo() {
        return mSerialNo;
    }

    public void setSerialNo(int serialNo) {
        mSerialNo = serialNo;
    }

    public Picture.CallBack getCallBack() {
        return mCallBack;
    }

    public void setCallBack(Picture.CallBack callBack) {
        mCallBack = callBack;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public void setDisplayConfig(DisplayConfig displayConfig) {
        mDisplayConfig = displayConfig;
    }

    public boolean isCancel() {
        return mIsCancel;
    }

    public void setCancel(boolean cancel) {
        mIsCancel = cancel;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        mTag = tag;
    }

    public ILoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public void setLoadPolicy(ILoadPolicy loadPolicy) {
        mLoadPolicy = loadPolicy;
    }
}
