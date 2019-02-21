package piratehat.httpClient;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by PirateHat on 2019/2/18.
 */

public class Response {
    private String mResultString;
    private HashMap<String,String> mHeaders;
    public int mStatus;
    private boolean mCanceled ;
    private IOException mException;
    private byte[] mByteArray;
    private long mContentLength;


    @Override
    public String toString() {
        return "Response{" +
                "mResultString='" + mResultString + '\'' +
                '}';
    }



    public void setResultString(String resultString) {
        mResultString = resultString;
    }

    public void setHeaders(HashMap<String, String> headers) {
        mHeaders = headers;
        if (mHeaders!=null){
            String title = mHeaders.get("");
            String[] parts = title.split(" ");
            mStatus = Integer.valueOf(parts[1]);
            if (mHeaders.get("Content-Length")!=null){
                mContentLength = Long.valueOf(mHeaders.get("Content-Length"));
            }else {
                mContentLength =0;
            }
        }
    }

    public byte[] getByteArray() {
        return mByteArray;
    }

    public void setByteArray(byte[] byteArray) {
        mByteArray = byteArray;
    }

    public boolean isCanceled() {
        return mCanceled;
    }

    public void setCanceled(boolean canceled) {
        mCanceled = canceled;
    }

    public void setException(IOException exception) {
        mException = exception;
    }

    public IOException getException() {
        return mException;
    }

    public HashMap<String, String> getHeaders() {
        return mHeaders;
    }

    public long getContentLength() {
        return mContentLength;
    }

    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int FORRBIDDEN = 404;
    public static final int MOVED_TEMPORARILY = 302;

}
