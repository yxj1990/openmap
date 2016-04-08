package com.openmaps.controls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;

import com.openmaps.Map;

public class MapAnimationControl {
	public static final int ANIMATION_DURATION_SHORT = 500;
	public static final int ANIMATION_DURATION_DEFAULT = 1000;
	public static final int ANIMATION_DURATION_LONG = 2000;



	protected final Map mMap;


	private ValueAnimator mZoomInAnimation;
	private ValueAnimator mZoomOutAnimation;
	private ScaleAnimation mZoomInAnimationOld;
	private ScaleAnimation mZoomOutAnimationOld;

	private Animator mCurrentAnimator;
	@SuppressLint("NewApi")
	public MapAnimationControl(Map map){
		mMap = map;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			mZoomInAnimation = ValueAnimator.ofFloat(1f,2f);
			mZoomInAnimation.addListener(new MyZoomAnimatorListener());
			mZoomInAnimation.addUpdateListener(new MyZoomAnimatorUpdateListener());
			mZoomInAnimation.setDuration(ANIMATION_DURATION_SHORT);

			mZoomOutAnimation = ValueAnimator.ofFloat(1f,0.5f);
			mZoomOutAnimation.addListener(new MyZoomAnimatorListener());
			mZoomOutAnimation.addUpdateListener(new MyZoomAnimatorUpdateListener());
			mZoomOutAnimation.setDuration(ANIMATION_DURATION_SHORT);
		} else {
			mZoomInAnimationOld = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			mZoomOutAnimationOld = new ScaleAnimation(1, 0.5f, 1, 0.5f, Animation.RELATIVE_TO_SELF,
					0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			mZoomInAnimationOld.setDuration(ANIMATION_DURATION_SHORT);
			mZoomOutAnimationOld.setDuration(ANIMATION_DURATION_SHORT);
			mZoomInAnimationOld.setAnimationListener(new MyZoomAnimationListener());
			mZoomOutAnimationOld.setAnimationListener(new MyZoomAnimationListener());
		}
	}




	public boolean zoomIn() {
		return zoomInFixing(mMap.getWidth() / 2, mMap.getHeight() / 2);
	}

	public boolean zoomInFixing(final int xPixel, final int yPixel) {
		mMap.mMultiTouchScalePoint.set(xPixel, yPixel);
		if (mMap.canZoomIn()) {
			if (mMap.mIsAnimating.getAndSet(true)) {
				// TODO extend zoom (and return true)
				return false;
			} else {
				mMap.mTargetZoomLevel.set(mMap.getZoomLevel(false) + 1);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					mCurrentAnimator = mZoomInAnimation;
					mZoomInAnimation.start();
				} else {
					mMap.startAnimation(mZoomInAnimationOld);
				}
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean zoomOut() {
		return zoomOutFixing(mMap.getWidth() / 2, mMap.getHeight() / 2);
	}


	public boolean zoomOutFixing(final int xPixel, final int yPixel) {
		mMap.mMultiTouchScalePoint.set(xPixel, yPixel);
		if (mMap.canZoomOut()) {
			if (mMap.mIsAnimating.getAndSet(true)) {
				// TODO extend zoom (and return true)
				return false;
			} else {
				mMap.mTargetZoomLevel.set(mMap.getZoomLevel(false) - 1);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
					mCurrentAnimator = mZoomOutAnimation;
					mZoomOutAnimation.start();
				} else {
					mMap.startAnimation(mZoomOutAnimationOld);
				}
				return true;
			}
		} else {
			return false;
		}
	}




	protected void onAnimationEnd() {
		mMap.mIsAnimating.set(false);
		mMap.setZoom(mMap.mTargetZoomLevel.get());
		mMap.mMultiTouchScale = 1;
		mMap.invalidate();
		Log.d("动画播放完成","onAnimationEnd ");
	}
	protected void onAnimationStart() {
		mMap.mIsAnimating.set(true);
	}

	protected class MyZoomAnimatorListener extends AnimatorListenerAdapter {
		@Override
		public void onAnimationStart(Animator animation) {
			MapAnimationControl.this.onAnimationStart();
			super.onAnimationStart(animation);
		}

		@Override
		public void onAnimationEnd(Animator animation) {
			MapAnimationControl.this.onAnimationEnd();
			super.onAnimationEnd(animation);
		}
	}
	protected class MyZoomAnimatorUpdateListener implements AnimatorUpdateListener {
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			mMap.mMultiTouchScale = (Float) animation.getAnimatedValue();
			mMap.invalidate();
		}
	}
	protected class MyZoomAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
			MapAnimationControl.this.onAnimationStart();
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			MapAnimationControl.this.onAnimationEnd();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// Nothing to do here...
		}
	}


	}







