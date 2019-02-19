package piratehat.HttpClient;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.util.zip.GZIPInputStream;

import javax.net.SocketFactory;

/**
 *
 * Created by PirateHat on 2019/2/19.
 */

public class RealConnection {
    private Socket mSocket;
    private HttpClient mHttpClient;
    private String mHost;
    private final boolean DEBUG = true;

    public RealConnection(@NonNull Protocol protocol, @NonNull HttpClient httpClient, @NonNull String host) throws IOException{
        mHttpClient = httpClient;
        mHost = host;
        if (protocol.equals(Protocol.HTTP)){
            mSocket = mHttpClient.socketFactory.createSocket(mHost,80);

        }
        if (protocol.equals(Protocol.HTTPS)){
            mSocket = mHttpClient.sslSocketFactory.createSocket(mHost,443);
        }
    }

    public Response connect(Request request) throws IOException{
        if (DEBUG){
            System.out.println("connect");
        }
        PrintWriter pw = new PrintWriter(mSocket.getOutputStream());
        for (String header:request.headers()) {
            pw.println(header);
        }
        pw.println("");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(/*new GZIPInputStream(*/mSocket.getInputStream(),"UTF-8"));
        String t;
        StringBuilder builder = new StringBuilder();
        while((t = br.readLine()) != null){
            builder.append(t).append("\n");
        }
        br.close();

        Util.closeQuietly(pw);




        return new Response(builder.toString());
    }


}
