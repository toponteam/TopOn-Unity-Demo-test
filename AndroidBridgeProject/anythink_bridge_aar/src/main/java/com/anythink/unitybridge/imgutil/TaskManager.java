package com.anythink.unitybridge.imgutil;



import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TaskManager {

    public final static int TYPE_SINGLE = 1;
    public final static int TYPE_NORMAL = 2;
    public final static int TYPE_FIXED = 3;
    public final static int TYPE_APK = 4;
    //图片池默认大小，图片请求比较集中，建议3-5比较合适，不要太大
    private final int IMAGE_POOL_SIZE = 2;
    private final int APK_POOL_SIZE = 3;

    private static TaskManager sSelf = null;

    //各种池子
    private ExecutorService mFixedPool = null;
    private ExecutorService mNormalPool = null;
    private ExecutorService mSinglePool = null;

    private ThreadPoolExecutor mAPKDownPool = null;

    protected TaskManager() {
        //初始化各种池子
        mFixedPool = Executors.newFixedThreadPool(IMAGE_POOL_SIZE);
        mNormalPool = Executors.newCachedThreadPool();
        mSinglePool = Executors.newSingleThreadExecutor();

        mAPKDownPool = new ThreadPoolExecutor(
                //线程大小核心线程
                APK_POOL_SIZE,
                //线程池最大小 最大线程
                100,
                //3秒回收空闲线程
                3,
                //前面的时间单位
                TimeUnit.SECONDS,
                //线程队列
                new LinkedBlockingQueue<Runnable>(APK_POOL_SIZE * 3),
                //创建线程池的工厂
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable runnable) {
                        Thread thread = new Thread(runnable);
                        thread.setName("apk_thread_"+System.currentTimeMillis());
                        return thread;
                    }
                },
                //任务超过时候 抛出RejectedExecutionException
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    static public TaskManager getInstance() {
        if (sSelf == null) {
            sSelf = new TaskManager();
        }
        return sSelf;
    }

    static protected  void setInstance(TaskManager taskManager){
        sSelf = taskManager;
    }



    public void run(Worker worker, int type) {

        switch(type){

            case TYPE_SINGLE:
                mSinglePool.execute(worker);
                break;

            case TYPE_NORMAL:
                mNormalPool.execute(worker);
                break;

            case TYPE_FIXED:
                mFixedPool.execute(worker);
                break;
            case TYPE_APK:
                mAPKDownPool.execute(worker);
                break;
            default:
                mNormalPool.execute(worker);
        }

    }

    public void run(Worker worker) {
        run(worker, TYPE_NORMAL);
    }


    public void run_proxy_apkThreadPool(final Runnable runnable){
        if(runnable!=null){
            Worker worker = new Worker() {
                @Override
                public void work() {

                    Log.d("t","thread_name"+this.getID());
                    runnable.run();

                }
            };
            worker.setID(new Long(System.currentTimeMillis()/1000).intValue());
            run(worker,TYPE_APK);
        }
    }
    public void run_proxy(final Runnable runnable){
        run_proxyDelayed(runnable,0);
    }

    public void run_proxyDelayed(final Runnable runnable,final long delayed){
        if(runnable!=null){
            Worker worker = new Worker() {
                @Override
                public void work() {
                    try {
                        Thread.sleep(delayed);
                    } catch (InterruptedException e) {
                        if(Const.DEBUG){e.printStackTrace();}
                    }
                    Log.d("t","thread-"+this.getID());
                    runnable.run();

                }
            };
            worker.setID(new Long(System.currentTimeMillis()/1000).intValue());
            run(worker);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        mSinglePool.shutdown();
        mNormalPool.shutdown();
        mFixedPool.shutdown();
        mAPKDownPool.shutdown();
    }



}
