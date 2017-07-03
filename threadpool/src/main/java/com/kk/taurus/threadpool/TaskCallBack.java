package com.kk.taurus.threadpool;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Taurus on 2017/5/19.
 */

public abstract class TaskCallBack<Params,Progress,Result> implements RunnableCallBack<Params,Progress,Result>{

    private final int MSG_CODE_PROGRESS = 1002;
    private final int MSG_CODE_END = 1003;

    private DefaultPoolExecutor mDefaultPoolExecutor;

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CODE_PROGRESS:
                    Progress progress = (Progress) msg.obj;
                    onProgressUpdate(progress);
                    break;
                case MSG_CODE_END:
                    Result result = (Result) msg.obj;
                    onPostExecute(result);
                    break;
            }
        }
    };

    public TaskCallBack(){
        if(mDefaultPoolExecutor==null){
            mDefaultPoolExecutor = DefaultPoolExecutor.getInstance();
        }
    }

    @Override
    public void execute(final Params... params) {
        onPreExecute();
        mDefaultPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = doInBackground(params);
                Message message = Message.obtain();
                message.what = MSG_CODE_END;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    public void execute(ThreadPoolExecutor executor,final Params... params) {
        onPreExecute();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = doInBackground(params);
                Message message = Message.obtain();
                message.what = MSG_CODE_END;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    public void publishProgress(Progress progress) {
        Message message = Message.obtain();
        message.what = MSG_CODE_PROGRESS;
        message.obj = progress;
        mHandler.sendMessage(message);
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onProgressUpdate(Progress progress) {

    }

    @Override
    public void onPostExecute(Result result) {

    }
}
