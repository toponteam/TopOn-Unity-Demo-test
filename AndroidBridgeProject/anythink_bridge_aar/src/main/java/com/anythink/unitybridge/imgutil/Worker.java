package com.anythink.unitybridge.imgutil;

/**
 * Created by chenzhf on 2014/12/23.
 */
public abstract class Worker implements Runnable  {

    public final static int TYPE_NORMAL = 1;
    public final static int TYPE_PHOTO = 2;
    public final static int TYPE_PRECLICK = 3;

    protected boolean mRunning = true;
    protected WorkerListener mWorkerStatus;
    protected int mType = TYPE_NORMAL;
    private int mWorkID = 0;

    void setID(int id){
        mWorkID = id;
    }

    public int getID(){
        return mWorkID;
    }

    public void setStatusListener(WorkerListener listener){
        mWorkerStatus = listener;
    }

    @Override
    public void run() {

        if(mWorkerStatus != null){
            mWorkerStatus.onWorkStart(this);
        }

        //这里，就是run()
        work();

        if(mWorkerStatus != null){
            mWorkerStatus.onWorkFinished(this);
        }
//        mRunning = false;
    }

    /**
     *  具体任务内容，在此方法中实现，相当于Runnable的run
     *  建议在长时间的任务中（例如下载），增加中断判断，形如：
     *  if(!mRunning){
     *      return;
     *  }
     *  以便中途结束任务
     *
     */
    abstract public void work();


}
