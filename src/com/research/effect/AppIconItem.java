package com.research.effect;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class AppIconItem extends ImageView {
	private static final String TAG = "AppIconItem";
	private CheckLongPressHelper mLongCheck;
	private Camera mCamera;
	private Matrix mMatrix;
	private boolean mInCamera;
	private double mRadius;
	
	public AppIconItem(Context context) {
		super(context);
		init();
	}

	public AppIconItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AppIconItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mLongCheck = new CheckLongPressHelper(this);
		mCamera = new Camera();
		mMatrix = new Matrix();
		mRadius = 240/Math.PI;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean ret = super.onTouchEvent(event);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d(TAG, "ACTION_DOWN");
			mLongCheck.postCheckForLongPress();
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "ACTION_MOVE");
			break;
		case MotionEvent.ACTION_UP:
			mLongCheck.cancelLongPress();
			Log.d(TAG, "ACTION_UP");
			break;
		}
		return false;
	}
	
	public void setRadius(double radius) {
		mRadius = radius;
	}
	
	public void setRotate(float x, float y) {
		mInCamera = true;
		mCamera.save();
		double arg = 2*Math.PI*x/480;
		double disX = Math.sin(arg)*mRadius;
		double dis = mRadius*(1-Math.cos(arg));
		Log.d("zdx", "arg="+arg+"; disX="+disX+"; dis="+dis);
		mCamera.translate((float)disX, 0, (float)dis);
		mCamera.rotate(0, (float)Math.toDegrees(arg), 0);
		mCamera.getMatrix(mMatrix);
		mCamera.restore();
		mMatrix.preTranslate(-getWidth()/2, -y);
		mMatrix.postTranslate(getWidth()/2, y);
		invalidate();
	}
	
	public void canceRotate() {
		mInCamera = false;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(mInCamera) {
			canvas.save();
			canvas.concat(mMatrix);
			super.onDraw(canvas);
			canvas.restore();
		} else {
			super.onDraw(canvas);
		}
	}
}
