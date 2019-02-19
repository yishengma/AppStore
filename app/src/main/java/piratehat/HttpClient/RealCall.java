package piratehat.HttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PirateHat on 2019/2/18.
 */

public class RealCall implements Call {
    private Request mRequest;

    private HttpClient mHttpClient;

    public RealCall(HttpClient httpClient, Request request) {
        mHttpClient = httpClient;
        mRequest = request;
    }

    @Override
    public Request request() {
        return mRequest;
    }


    /**
     * 同步请求
     *
     * @return Response
     */
    @Override
    public Response execute() throws IOException {
        mHttpClient.dispatcher.execute(this);
        Response response = getResponseWithInterceptorChain();
        if (response == null) {
            throw new IOException("Cancel!");
        }
        mHttpClient.dispatcher.finish(this);
        return response;
    }

    /**
     * 异步的请求
     *
     * @param callback 回调
     */
    @Override
    public void enqueue(Callback callback) {
        mHttpClient.dispatcher.enqueue(new AsyncCall(callback));
    }

    final class AsyncCall extends NameRunnable {
        private Callback mCallback;

        public AsyncCall(Callback callback) {
            super(mRequest.url());
            mCallback = callback;
        }

        @Override
        public void execute() {
            Response response = getResponseWithInterceptorChain();
            if (mRequest.mCanceled){
                mCallback.onFailure(RealCall.this,new IOException(""));
            }else {
                mCallback.onResponse(RealCall.this,response);
            }
            mHttpClient.dispatcher.finish(this);
        }
    }

    private Response getResponseWithInterceptorChain() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(mHttpClient.interceptors);
        interceptors.add(new RetryInterceptor());
        interceptors.add(new BridgeInterceptor());
        interceptors.add(new ConnectInterceptor());
        Interceptor.Chain chain = new RealInterceptorChain(mHttpClient,mRequest,interceptors,0);
        return chain.proceed(mRequest);

    }
}
