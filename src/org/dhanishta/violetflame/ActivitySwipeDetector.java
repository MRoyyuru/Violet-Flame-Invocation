package org.dhanishta.violetflame;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ActivitySwipeDetector implements View.OnTouchListener {

	private SwipeInterface activity;
	static final int MIN_DISTANCE = 50;
	private float downX, upX;
	private String logTag = "Violet Flame";

	public ActivitySwipeDetector(SwipeInterface activity) {
		this.activity = activity;
	}

	public void onRightToLeftSwipe(View v) {
		activity.right2left(v);

	}

	public void onLeftToRightSwipe(View v) {
		activity.left2right(v);
	}

	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			downX = event.getX();
			event.getY();
			return true;
		}

		case MotionEvent.ACTION_UP: {
			upX = event.getX();
			event.getY();

			float deltaX = downX - upX;

			// swipe horizontal
			if (Math.abs(deltaX) > MIN_DISTANCE) {
				// left or right
				if (deltaX < 0) {
					this.onLeftToRightSwipe(v);
					return true;
				}
				if (deltaX > 0) {
					this.onRightToLeftSwipe(v);
					return true;
				}
			} else {
				Log.i(logTag, "Swipe was only " + Math.abs(deltaX)
						+ " long, need at least " + MIN_DISTANCE);
			}

		}
		}
		return true;
	}

}
