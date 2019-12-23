package com.anythink.unitybridge.imgutil;

/**
 * 线程实现类
 * @author TonyZhou
 *
 */
public abstract class CommonTask implements Runnable {

	public static long id;

	public State mState = State.READY;
	public onStateChangeListener mListener;

	public abstract void runTask();

	public abstract void cancelTask();
	public abstract void pauseTask(boolean pause);

	@Override
	public final void run() {
		if (mState == State.READY) {
			setState(State.RUNNING);
			runTask();
			setState(State.FINISH);
		}

	}

	public CommonTask() {
		id++;
	}

	public final long getId() {
		return id;
	}

	public static enum State {
		READY, RUNNING, PAUSE, CANCEL,FINISH
	};

	public final void cancel() {
		if (mState != State.CANCEL) {
			setState(State.CANCEL);
			cancelTask();
		}
	}

	public final void setPause(boolean pause) {
		if (mState == State.PAUSE || mState == State.CANCEL || mState == State.FINISH ) {
			return;
		}
		if (pause) {
			setState(State.PAUSE);
		} else {
			setState(State.RUNNING);
		}
		pauseTask(pause);
	}

	public State getState() {
		return mState;
	}

	private void setState(State state) {
		mState = state;
		if (mListener != null) {
			mListener.onstateChanged(state);
		}
	}

	public void setonStateChangeListener(onStateChangeListener listener) {
		mListener = listener;
	}

	public interface onStateChangeListener {
		void onstateChanged(State state);
	}

}
