package piratehat.httpClient;

import java.util.HashMap;

/**
 *
 * Created by PirateHat on 2019/2/19.
 */

public class RetryInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Response response = chain.proceed(request);
         while (!response.isCanceled() && response.mStatus == Response.MOVED_TEMPORARILY){
               HashMap<String,String> headers = response.getHeaders();
             for (String key:headers.keySet()) {
                 if (key.equals("Location")){
                     request = new Request(headers.get(key));
                     break;
                 }
             }
             response = chain.proceed(request);
         }

        return response;
    }
}
