package com.research.effect;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Rotate3DAnimation extends Animation {
	private double mFrom;
	private double mTo;
	private Camera mCamera;	
	private double mRadius;
	private int mWidth;
	private int mHeight;
	
	public Rotate3DAnimation(double from, double to, double radius) {
		mFrom = from;
		mTo = to;		
		mRadius = radius;
		Log.d("zdx", "from = "+mFrom+"; to = "+mTo + "; radius = "+mRadius);
	}
	
	public void setWidthHeight(int width, int height) {
		mWidth = width;
		mHeight = height;
	}
	
	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {		
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}
	
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {		
		Matrix matrix = t.getMatrix();
		//Matrix matrix3d = new Matrix();
		double arg = (mTo-mFrom)*interpolatedTime;
		mCamera.save();
		double disX = Math.sin(arg)*mRadius;
		double dis = mRadius*(1-Math.cos(arg));
		Log.d("zdx", "arg="+arg+"; disX="+disX+"; dis="+dis);
		mCamera.translate((float)disX, 0, (float)dis);
		mCamera.rotate(0, (float)Math.toDegrees(arg), 0);		
		mCamera.getMatrix(matrix);
		mCamera.restore();
		matrix.preTranslate(-mWidth/2, -mHeight/2);
		matrix.postTranslate(mWidth/2, mHeight/2);
	}
}
