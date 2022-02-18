package com.anythink.unitybridge.utils;


import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager {

    private static TaskManager sSelf = null;

    private ExecutorService mSinglePool = null;

    private ThreadPoolExecutor mAPKDownPool = null;

    protected TaskManager() {
        mSinglePool = Executors.newSingleThreadExecutor();
    }

    static public TaskManager getInstance() {
        if (sSelf == null) {
            sSelf = new TaskManager();
        }
        return sSelf;
    }

    private void run(Worker worker) {
        mSinglePool.execute(worker);
    }

    public void run_proxy(final Runnable runnable) {
        run_proxyDelayed(runnable, 0);
    }

    public void run_proxyDelayed(final Runnable runnable, final long delayed) {
        if (runnable != null) {
            Worker worker = new Worker() {
                @Override
                public void work() {
                    try {
                        Thread.sleep(delayed);
                    } catch (InterruptedException e) {
                        if (Const.DEBUG) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("t", "thread-" + this.getID());
                    runnable.run();

                }
            };
            worker.setID(new Long(System.currentTimeMillis() / 1000).intValue());
            run(worker);
        }
    }

    public void release() {
        mSinglePool.shutdown();
        mAPKDownPool.shutdown();
    }

}
