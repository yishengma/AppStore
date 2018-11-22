package piratehat.appstore.Bean;

/**
 * Created by PirateHat on 2018/11/22.
 */

public class AppDownloadInfo {
    private String mName;
    private String mIcon;
    private int mDownloadProgress;
    private int mState;

    public String getName() {
        return mName == null ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIcon() {
        return mIcon == null ? "" : mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getDownloadProgress() {
        return mDownloadProgress;
    }

    public void setDownloadProgress(int downloadProgress) {
        mDownloadProgress = downloadProgress;
    }

    public int getState() {
        return mState;
    }

    public void setState(int state) {
        mState = state;
    }
}
