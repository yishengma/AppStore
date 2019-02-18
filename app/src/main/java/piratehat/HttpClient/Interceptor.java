package piratehat.HttpClient;

/**
 *
 * Created by PirateHat on 2019/2/18.
 */

public interface Interceptor {

    Response intercept(Chain chain);


    interface Chain{
        Request request();

        Response proceed(Request request);


    }
}
