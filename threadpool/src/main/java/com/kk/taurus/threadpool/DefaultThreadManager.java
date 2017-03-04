package com.kk.taurus.threadpool;

/**
 * Created by Taurus on 2017/3/3.
 */

public class DefaultThreadManager {

    private static DefaultThreadManager instance;
    private DefaultPoolExecutor mDefaultPoolExecutor;

    private DefaultThreadManager(){
        mDefaultPoolExecutor = DefaultPoolExecutor.getInstance();
    }

    private DefaultThreadManager(ExecutorSetting executorSetting){
        mDefaultPoolExecutor = DefaultPoolExecutor.getInstance(
                executorSetting.getCorePoolSize(),
                executorSetting.getMaximumPoolSize(),
                executorSetting.getKeepAliveTime(),
                executorSetting.getUnit());
    }

    public static DefaultThreadManager getInstance(){
        if(null == instance){
            synchronized (DefaultThreadManager.class){
                if(null == instance){
                    instance = new DefaultThreadManager();
                }
            }
        }
        return instance;
    }

    public static DefaultThreadManager getInstance(ExecutorSetting executorSetting){
        if(null == instance){
            synchronized (DefaultThreadManager.class){
                if(null == instance){
                    instance = new DefaultThreadManager(executorSetting);
                }
            }
        }
        return instance;
    }

    public void execute(Runnable runnable){
        mDefaultPoolExecutor.execute(runnable);
    }

}
