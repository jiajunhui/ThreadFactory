package com.kk.taurus.threadpool;

/**
 * Created by Taurus on 2017/3/3.
 */

public class ThreadManager {

    private static ThreadManager instance;
    private DefaultPoolExecutor mDefaultPoolExecutor;

    private ThreadManager(){
        mDefaultPoolExecutor = DefaultPoolExecutor.getInstance();
    }

    private ThreadManager(ExecutorSetting executorSetting){
        mDefaultPoolExecutor = DefaultPoolExecutor.getInstance(
                executorSetting.getCorePoolSize(),
                executorSetting.getMaximumPoolSize(),
                executorSetting.getKeepAliveTime(),
                executorSetting.getUnit());
    }

    public static ThreadManager getInstance(){
        if(null == instance){
            synchronized (ThreadManager.class){
                if(null == instance){
                    instance = new ThreadManager();
                }
            }
        }
        return instance;
    }

    public static ThreadManager getInstance(ExecutorSetting executorSetting){
        if(null == instance){
            synchronized (ThreadManager.class){
                if(null == instance){
                    instance = new ThreadManager(executorSetting);
                }
            }
        }
        return instance;
    }

    public void execute(Runnable runnable){
        mDefaultPoolExecutor.execute(runnable);
    }

}
