package com.research.effect;

import android.view.View;

public class CheckLongPressHelper {
	private View mView;
	private boolean mHasPerformedLongPress;
	private CheckForLongPress mPendingCheckForLongPress;
	private static int sLongPressTimeout = 400;
	
	class CheckForLongPress implements Runnable {
		public void run() {
			if ((mView.getParent() != null) && mView.hasWindowFocus()
					&& !mHasPerformedLongPress) {
				if (mView.performLongClick()) {
					mView.setPressed(false);
					mHasPerformedLongPress = true;
				}
			}
		}
	}

	public CheckLongPressHelper(View v) {
		mView = v;
	}

	public void postCheckForLongPress() {
		mHasPerformedLongPress = false;

		if (mPendingCheckForLongPress == null) {
			mPendingCheckForLongPress = new CheckForLongPress();
		}
		mView.postDelayed(mPendingCheckForLongPress, sLongPressTimeout);
	}

	public void cancelLongPress() {
		mHasPerformedLongPress = false;
		if (mPendingCheckForLongPress != null) {
			mView.removeCallbacks(mPendingCheckForLongPress);
			mPendingCheckForLongPress = null;
		}
	}

	public boolean hasPerformedLongPress() {
		return mHasPerformedLongPress;
	}

}
