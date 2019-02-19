package piratehat.HttpClient;

/**
 *
 * Created by PirateHat on 2019/2/19.
 */

public class RetryInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Response response = chain.proceed(request);


        return response;
    }
}
