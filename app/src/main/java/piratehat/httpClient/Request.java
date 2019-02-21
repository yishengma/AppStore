package piratehat.httpClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by PirateHat on 2019/2/18.
 */

public class Request {
    private final String mUrl;
    private final String mPath;
    private final String mHost;
    private final ArrayList<String> mHeaders;
    private final Protocol mProtocol;
    private URI mURI;
    volatile boolean mCanceled;
    private boolean mIsHeader;


    public Request(String url) {
        System.out.println(url);
        mUrl = url;
        mHeaders = new ArrayList<>();
        try {
            mURI = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mPath = mURI.getPath();
        mHost = mURI.getHost();
        mProtocol = mURI.getScheme()==null?Protocol.HTTPS:mURI.getScheme().equals(Protocol.HTTP.toString()) ? Protocol.HTTP : Protocol.HTTPS;
        addHeader(this.method() + " " + this.path() + " HTTP/1.1");
    }

    public boolean isHeader() {
        return mIsHeader;
    }

    public void setHeader(boolean header) {
        mIsHeader = header;
    }

    public Request addHeader(String header) {
        mHeaders.add(header);
        return this;
    }

    public List<String> headers() {
        return mHeaders;
    }

    public String url() {
        return mUrl;
    }

    public String method() {
        return "GET";
    }

    public String path() {
        return mPath;
    }

    public String host() {
        return mHost;
    }

    public Protocol protocol() {
        return mProtocol;
    }


}
