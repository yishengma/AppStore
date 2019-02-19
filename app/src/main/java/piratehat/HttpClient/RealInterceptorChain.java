package piratehat.HttpClient;

import java.util.List;

/**
 * Created by PirateHat on 2019/2/18.
 */

public class RealInterceptorChain implements Interceptor.Chain {

    private Request mRequest;
    private List<Interceptor> mInterceptors;
    private final int mIndex ;
    private HttpClient mHttpClient;

    public RealInterceptorChain(HttpClient httpClient,Request request, List<Interceptor> interceptors,int index) {
        mRequest = request;
        mInterceptors = interceptors;
        mIndex = index;
        mHttpClient = httpClient;
    }

    @Override
    public Request request() {
        return mRequest;
    }

    @Override
    public Response proceed(Request request) {

        RealInterceptorChain next = new RealInterceptorChain(mHttpClient,mRequest,mInterceptors,mIndex+1);
        Interceptor interceptor = mInterceptors.get(mIndex);
        Response response  = interceptor.intercept(next);
        if (response == null){
            throw new NullPointerException("interceptor " + interceptor + "return null!");
        }
        return response;
    }

    @Override
    public HttpClient httpClient() {
        return mHttpClient;
    }
}
