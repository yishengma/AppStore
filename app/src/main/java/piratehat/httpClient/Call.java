package piratehat.httpClient;

import java.io.IOException;

/**
 *
 * Created by PirateHat on 2019/2/18.
 */

public interface Call {

    Request request();

    /**
     * 同步的请求
     * @return Response
     */
    Response execute() throws IOException;


    void enqueue(Callback callback);


}
