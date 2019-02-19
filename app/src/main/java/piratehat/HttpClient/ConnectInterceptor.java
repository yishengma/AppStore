package piratehat.HttpClient;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * Created by PirateHat on 2019/2/19.
 */

public class ConnectInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Response response = null ;
        try {
            RealConnection realConnection = new RealConnection(request.protocol(),chain.httpClient(),request.host());
            response = realConnection.connect(request);
        } catch (IOException ignore) {

        }finally {

        }

        return response;
    }
}
