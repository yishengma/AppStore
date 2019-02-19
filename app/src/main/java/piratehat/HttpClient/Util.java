package piratehat.HttpClient;

import android.support.annotation.NonNull;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadFactory;

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
}
