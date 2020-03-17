package com.anythink.unitybridge.imgutil;

import android.annotation.SuppressLint;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程管理器
 * 
 * @author TonyZhou
 * 
 */
public class CommonTaskLoader {
	ExecutorService mThreadPool;
	HashMap<Long, CommonTask> mapTask;
	WeakReference<Context> weakRef;

	@SuppressLint("UseSparseArrays")
	public CommonTaskLoader(Context context, int num) {
		if (num == 0) {
			mThreadPool = Executors.newSingleThreadExecutor();
		} else {
			mThreadPool = Executors.newFixedThreadPool(num);
		}
		mapTask = new HashMap<Long, CommonTask>();
		weakRef = new WeakReference<Context>(context);

	}

	@SuppressLint("UseSparseArrays")
	public CommonTaskLoader(Context context) {
		mThreadPool = Executors.newCachedThreadPool();
		mapTask = new HashMap<Long, CommonTask>();
		weakRef = new WeakReference<Context>(context);

	}

	public synchronized void removeTask(CommonTask task) {
		if (mapTask.containsKey(task.getId())) {
			if(mapTask.get(task.getId())!= null){
				mapTask.get(task.getId()).cancel();
			}
			mapTask.remove(task.getId());
		}
	}

	public synchronized void removeTask(long id) {
		if (mapTask.containsKey(id)) {
			if(mapTask.get(id)!=null){
				mapTask.get(id).cancel();
			}
			mapTask.remove(id);
		}
	}

//	public synchronized void addTask(final CommonTask task) {
//		addTask(task, null);
//	}

	private synchronized void addTask(final CommonTask task,
			final CommonTask.onStateChangeListener listener) {
		mapTask.put(task.getId(), task);
		CommonTask.onStateChangeListener changedListener = new CommonTask.onStateChangeListener() {

			@Override
			public void onstateChanged(CommonTask.State state) {
				if (state == CommonTask.State.CANCEL) {
					mapTask.remove(task.getId());
				} else if (state == CommonTask.State.FINISH) {
					mapTask.remove(task.getId());
				} else if (state == CommonTask.State.RUNNING) {
					Context context = weakRef.get();
					if (context == null) {
						stopAll();
					}
				}
				if (listener != null) {
					listener.onstateChanged(state);
				}
			}
		};
		task.setonStateChangeListener(changedListener);
	}

	public synchronized void stopAll() {
		try {
			Set<Entry<Long, CommonTask>> set = mapTask.entrySet();
			for (Entry<Long, CommonTask> entry : set) {
				entry.getValue().cancel();
			}
			mapTask.clear();
		} catch (Exception e) {
		}
	}

//	public void runAllTask() {
//		Set<Entry<Long, CommonTask>> set = mapTask.entrySet();
//		for (Entry<Long, CommonTask> entry : set) {
//			CommonTask task = entry.getValue();
//			if (task.getState() == CommonTask.State.PAUSE) {
//				task.setPause(false);
//			} else if (task.getState() == CommonTask.State.READY) {
//				mThreadPool.execute(task);
//			}
//		}
//	}

	public void run(CommonTask task) {
		addTask(task, null);
		mThreadPool.execute(task);
	}

	public void run(CommonTask task, CommonTask.onStateChangeListener listener) {
		addTask(task, listener);
		mThreadPool.execute(task);
	}

}
