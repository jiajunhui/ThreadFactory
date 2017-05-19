package com.taurus.threadfactory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kk.taurus.threadpool.DefaultThreadManager;
import com.kk.taurus.threadpool.TaskCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DefaultThreadManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "over", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        new TaskCallBack<Integer,Integer,String>(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(MainActivity.this, "start", Toast.LENGTH_SHORT).show();
                System.out.println("start ......");
            }

            @Override
            public String doInBackground(Integer... params) {
                System.out.println("doInBackground ......");
                return String.valueOf(params[0]);
            }

            @Override
            public void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, "result : " + s, Toast.LENGTH_SHORT).show();
            }
        }.execute(5);

    }
}
