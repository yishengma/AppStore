package piratehat.httpClient;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 任务分发器
 * Created by PirateHat on 2019/2/18.
 */

public class Dispatcher {
    private static final boolean DEBUG = true;
    private int maxRequest = 5;

    private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>();


    private final Deque<RealCall.AsyncCall> runningAsyncCalls = new ArrayDeque<>();

    private final Deque<RealCall.AsyncCall> readyAsyncCalls = new ArrayDeque<>();

    private ExecutorService mExecutorService ;

    public Dispatcher() {
        mExecutorService = new ThreadPoolExecutor(0,Integer.MAX_VALUE, 60,
                TimeUnit.SECONDS,new SynchronousQueue<Runnable>(),Util.threadFactory("Dispatcher",false));

    }


     void execute(RealCall realCall) {
        runningSyncCalls.add(realCall);
        if (DEBUG){
            System.out.println(runningSyncCalls.size());
        }
    }

    //因为多线程 添加
    //所以需要 synchronized
    synchronized void enqueue(RealCall.AsyncCall asyncCall){
        if (runningAsyncCalls.size() < maxRequest){
            runningAsyncCalls.add(asyncCall);
            mExecutorService.execute(asyncCall);
        }else {
            readyAsyncCalls.add(asyncCall);
        }

         if (DEBUG){
             System.out.println("async"+runningAsyncCalls.size());
             System.out.println("ready"+readyAsyncCalls.size());
         }
    }

     void finish(RealCall realCall) {
        finish(runningSyncCalls, realCall, false);
         if (DEBUG){
             System.out.println("sync"+runningSyncCalls.size());

         }
    }

     void finish(RealCall.AsyncCall asyncCall) {
        finish(runningAsyncCalls, asyncCall, true);
         if (DEBUG){
             System.out.println("async"+runningAsyncCalls.size());
             System.out.println("ready"+readyAsyncCalls.size());
         }
    }

    //因为对异步的情况可能是多线程删除
    //所以需要 synchronized
    synchronized  <T> void finish(Deque<T> calls, T call, boolean async) {
        calls.remove(call);
        if (async) {
            promoteCall();
        }

    }


    private  void promoteCall() {
        if (runningAsyncCalls.size() >= maxRequest) {
            return;
        }

        if (readyAsyncCalls.isEmpty()) {
            return;
        }

        //边删除编判断，使用迭代器
        for (Iterator<RealCall.AsyncCall> i = readyAsyncCalls.iterator(); i.hasNext(); ) {
            RealCall.AsyncCall call = i.next();
            i.remove();
            runningAsyncCalls.add(call);
            mExecutorService.execute(call);
            if (runningAsyncCalls.size() >= maxRequest) {
                return;
            }
        }

    }


}
