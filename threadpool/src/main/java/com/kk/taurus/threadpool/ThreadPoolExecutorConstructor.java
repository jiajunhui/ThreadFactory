package com.kk.taurus.threadpool;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Taurus on 2017/3/4.
 */
public class ThreadPoolExecutorConstructor extends ThreadPoolExecutor {

    private static final String TAG = "_ThreadPoolExecutor";

    public ThreadPoolExecutorConstructor(int corePoolSize, int maximumPoolSize, long keepAliveTime){
        super(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new DefaultThreadFactory());
    }

    private ThreadPoolExecutorConstructor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    /*
     *  线程执行结束，顺便看一下有么有什么乱七八糟的异常
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null) {
            Log.w(TAG, "Running task appeared exception! Thread [" + Thread.currentThread().getName() + "], because [" + t.getMessage() + "]\n" + TextUtils.formatStackTrace(t.getStackTrace()));
        }
    }
}
