package com.research.effect;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

public class EffectActivity extends Activity {
	private static final String TAG = "EffectActivity";
	private boolean longClick;
	private AppIconItem	mScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(featureId)
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER,
				WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
		setContentView(R.layout.main);
		mScrollView = (AppIconItem) findViewById(R.id.text);
		mScrollView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Log.d(TAG, "onLongClick");
				longClick = true;
				//mScrollView.
				/*
				float scale = v.getContext().getResources().getDisplayMetrics().density;				
				float distance = v.getCameraDistance();
				scale += 0.5;
				Log.d(TAG, "scale="+scale+"  distance="+distance);
				v.setCameraDistance(scale*distance);
				v.setRotationY(90*scale);
				//v.invalidate();
				 */
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	private float mX, mY; 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			longClick = false;
			Log.d(TAG, "ACTION_DOWN sin(90)="+Math.sin(90));
			Log.d(TAG, "ACTION_DOWN sin(pi)="+Math.sin(Math.PI/2));
			mX = event.getX();
			mY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			//Log.d(TAG, "ACTION_MOVE");
			if(longClick) {
				//Log.d(TAG, "ACTION_MOVE after long click");
				mScrollView.setRotate(event.getX() - mX, mY - event.getY());
				//mScrollView
				if(event.getPointerCount() == 2) {
					int id0 = event.getPointerId(0);
					int id1 = event.getPointerId(1);
					float x = event.getX(id0) - event.getX(id1);
					float y = event.getY(id0) - event.getY(id1);
					double arg = Math.atan2(y, x);
					Log.d(TAG, ""+arg);
				}
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			if(event.getPointerCount() == 2 && longClick) {
				int id0 = event.getPointerId(0);
				int id1 = event.getPointerId(1);
				float x = event.getX(id0) - event.getX(id1);
				float y = event.getY(id0) - event.getY(id1);
				double arg = Math.atan2(y, x);
				Log.d(TAG, ""+arg);
			}
		case MotionEvent.ACTION_UP:
			Log.d(TAG, "ACTION_UP");
			//mScrollView.canceRotate();
			double from = 2*Math.PI*(event.getX() - mX)/480;
			double to = 0;
			if(from > Math.PI ) {
				to = 2*Math.PI;
			}
			if(from < -Math.PI){
				to = -2*Math.PI;
			}
			Rotate3DAnimation anim = new Rotate3DAnimation(from, to, 240/Math.PI);
			anim.setWidthHeight(mScrollView.getWidth(), mScrollView.getHeight());
			anim.setDuration(500);
			anim.setInterpolator(new AccelerateInterpolator());
			mScrollView.startAnimation(anim);
			longClick = false;
			break;
		}
		return super.onTouchEvent(event);
	}
}
