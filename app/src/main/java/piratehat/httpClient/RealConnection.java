package piratehat.httpClient;

import android.support.annotation.NonNull;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;


/**
 *
 * Created by PirateHat on 2019/2/19.
 */

public class RealConnection {
    private Socket mSocket;
    private HttpClient mHttpClient;
    private String mHost;
    private final boolean DEBUG = true;
    private Response mResponse;

    public RealConnection(@NonNull Protocol protocol, @NonNull HttpClient httpClient, @NonNull String host)  {
        mHttpClient = httpClient;
        mResponse = new Response();
        mHost = host;
        try {
            if (protocol.equals(Protocol.HTTP)) {
                mSocket = mHttpClient.socketFactory.createSocket(InetAddress.getByName(mHost), 80);

            }
            if (protocol.equals(Protocol.HTTPS)) {
                mSocket = mHttpClient.sslSocketFactory.createSocket(InetAddress.getByName(mHost), 443);
            }
        }catch (IOException ignore){
            mResponse.setException(ignore);
            mResponse.setCanceled(true);
        }
    }

    public Response connect(Request request) {
        if (DEBUG) {
            System.out.println("connect");
        }
        if (mResponse.isCanceled()){
            return mResponse;
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(mSocket.getOutputStream());
        } catch (IOException ignore) {
            mResponse.setException(ignore);
            mResponse.setCanceled(true);
            return mResponse;
        }

        for (String header : request.headers()) {
            pw.println(header);
        }
        pw.println("");
        pw.flush();
        if (mResponse.isCanceled()){
            return mResponse;
        }
        try {
            mSocket.setSoTimeout(mHttpClient.readTimeout);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        SegmentInputStream stream = null;
        try {
            stream = new SegmentInputStream(mSocket.getInputStream());
        } catch (IOException ignore) {
            mResponse.setException(ignore);
            mResponse.setCanceled(true);
            return mResponse;
        }

        mResponse.setHeaders(stream.getHeaders());
        if (request.isHeader()){
            Util.closeQuietly(pw);
            Util.closeQuietly(stream);
            Util.closeQuietly(mSocket);
            //返回成功才压缩，返回失败没有压缩
            return mResponse;
        }
        if (mResponse.mStatus == Response.OK){
            mResponse.setByteArray(Util.uncompressToByte(stream));
        }else {
            mResponse.setByteArray(Util.streamToByte(stream));
        }
        Util.closeQuietly(pw);
        Util.closeQuietly(stream);
        Util.closeQuietly(mSocket);
        //返回成功才压缩，返回失败没有压缩
        return mResponse;
    }




}
