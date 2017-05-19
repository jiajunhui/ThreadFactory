package com.kk.taurus.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Taurus on 2017/5/19.
 */

public interface RunnableCallBack<Params,Progress,Result> {
    void onPreExecute();
    Result doInBackground(Params...params);
    void publishProgress(Progress progress);
    void onProgressUpdate(Progress progress);
    void onPostExecute(Result result);
    void execute(Params...params);
    void execute(ThreadPoolExecutor executor, Params...params);
}
