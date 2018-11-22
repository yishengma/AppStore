package piratehat.appstore.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 *
 * Created by PirateHat on 2018/11/22.
 */

public class CloseQuicklyUtil {

    public static void close(Closeable closeable){
       if (closeable!=null){
           try {
               closeable.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
}
