package piratehat.httpClient;

/**
 * Created by PirateHat on 2019/2/19.
 */

public class ConnectInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Response response = null;
        RealConnection realConnection = new RealConnection(request.protocol(), chain.httpClient(), request.host());
        response = realConnection.connect(request);
        return response;
    }
}
