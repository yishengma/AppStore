package piratehat.httpClient;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 *
 * Created by PirateHat on 2019/2/18.
 */

public class HttpClient {

    Dispatcher dispatcher; //任务分发器
    final List<Interceptor> interceptors;//拦截器链
    List<Protocol> protocols;  //支持的协议
    SocketFactory socketFactory; //socket 工厂
    SocketFactory sslSocketFactory;
    boolean followRedirects; //重定向
    int connectTimeout;
    int writeTimeout;
    int readTimeout;


    public HttpClient() {
        this(new Builder());
    }

    private HttpClient(Builder builder){
        this.dispatcher = builder.dispatcher;
        this.interceptors = Util.immutableList(builder.interceptors);
        protocols = builder.protocols;
        socketFactory = builder.socketFactory;
        if (builder.sslSocketFactory!=null){
            sslSocketFactory = builder.sslSocketFactory;
        }else {
            sslSocketFactory = SSLSocketFactory.getDefault();
        }
        followRedirects = builder.followRedirects;
        connectTimeout = builder.connectTimeout;
        writeTimeout = builder.writeTimeout;
        readTimeout = builder.readTimeout;


    }


    public Call newCall(Request request){
        return new RealCall(this,request);
    }

    public static final class Builder {
        Dispatcher dispatcher; //任务分发器
        final List<Interceptor> interceptors = new ArrayList<>(); //拦截器链
        List<Protocol> protocols;  //支持的协议
        SocketFactory socketFactory; //socket 工厂
        @Nullable
        SocketFactory sslSocketFactory; // 支持 ssl 的 socket 的工厂
        boolean followRedirects; //重定向
        int connectTimeout;
        int writeTimeout;
        int readTimeout;

        public Builder() {
            dispatcher = new Dispatcher();
            protocols = Util.immutableList(Protocol.HTTP, Protocol.HTTPS);
            socketFactory = SocketFactory.getDefault();
            followRedirects = false;
            connectTimeout = 600_000;
            writeTimeout = 600_000;
            readTimeout = 600_000;
        }

        public Builder socketFactory(SocketFactory socketFactory) {
            if (socketFactory == null) {
                throw new NullPointerException("SocketFactory == null!");
            }
            this.socketFactory = socketFactory;
            return this;
        }

        public Builder sslSocketFactory(SocketFactory sslSocketFactory) {
            if (sslSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null!");
            }
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public Builder followRedirects(boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor == null) {
                throw new NullPointerException("interceptor == null");
            }
            interceptors.add(interceptor);
            return this;
        }

        public Builder connectTimeout(long connectTimeout, TimeUnit unit) {
            this.connectTimeout = checkDuration("connect", connectTimeout, unit);
            return this;
        }

        public Builder writeTimeout(long writeTimeout, TimeUnit unit) {
            this.writeTimeout = checkDuration("write", writeTimeout, unit);
            return this;
        }

        public Builder readTimeout(long readTimeout, TimeUnit unit) {
            this.readTimeout = checkDuration("read", readTimeout, unit);
            return this;
        }

        private int checkDuration(String name, long timeout, TimeUnit unit) {
            if (timeout < 0) throw new IllegalArgumentException(name + " < 0");
            if (unit == null) throw new NullPointerException("TimeUnit == null!");
            long millis = unit.toMillis(timeout);
            if (millis > Integer.MAX_VALUE)
                throw new IllegalArgumentException(name + " too large!");
            if (millis == 0 && timeout == 0)
                throw new IllegalArgumentException(name + " too small!");
            return (int) millis;


        }

        public HttpClient build() {
            return new HttpClient(this);
        }

    }
}
