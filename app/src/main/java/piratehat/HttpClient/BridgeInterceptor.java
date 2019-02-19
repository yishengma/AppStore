package piratehat.HttpClient;

/**
 * Created by PirateHat on 2019/2/19.
 */

public class BridgeInterceptor implements Interceptor {
    private final boolean DEBUG = true;

    @Override
    public Response intercept(Chain chain) {

        Request request = chain.request();
        request.addHeader(request.method() + " " + request.path() + " HTTP/1.1")
                .addHeader("Host: " + request.host())
                .addHeader("Connection: keep-alive")
//        .addHeader("Cache-Control: max-age=0")
//        .addHeader("Upgrade-Insecure-Requests: 1")
        .addHeader("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36")
//        .addHeader("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Accept-Encoding: gzip");

//        .addHeader("Accept-Language: zh-CN,zh;q=0.9");
        if (DEBUG) {
            System.out.println(request.headers().toString());
        }
        Response response = chain.proceed(request);


        return response;
    }


}
//Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36
//Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36


