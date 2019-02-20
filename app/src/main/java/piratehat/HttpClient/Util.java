package piratehat.HttpClient;

import android.support.annotation.NonNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.zip.GZIPInputStream;

/**
 *
 *
 * Created by PirateHat on 2019/2/18.
 */

public class Util {


    public static void closeQuietly(Closeable closeable){
        if (closeable==null){
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void closeQuietly(Socket socket){
        if (socket == null){
            return;
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static<T> List<T> immutableList(T...elements){
        return Collections.unmodifiableList(Arrays.asList(elements.clone()));
    }

    public static<T> List<T> immutableList(List<T> list){
        return Collections.unmodifiableList(new ArrayList<T>(list));
    }

    public static ThreadFactory threadFactory(final String name, final boolean daemon){
        return new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread(r);
                thread.setName(name);
                thread.setDaemon(daemon);
                return thread;

            }
        };
    }

    public static byte[] uncompressToByte(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] streamToByte(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[256];
            int n;
            while ((n = in.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
