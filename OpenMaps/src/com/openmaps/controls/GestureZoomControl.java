package com.openmaps.controls;


import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.openmaps.Map;

public class GestureZoomControl extends Control implements OnGestureListener,OnTouchListener{
	GestureDetector detector;
	public GestureZoomControl(Map map){
		super(map);
		detector = new GestureDetector(mMap.getContext(), this);
		detector.setOnDoubleTapListener(new MapViewDoubleClickListener());
		
		map.setOnTouchListener(this);
	}
	
	private class MapViewDoubleClickListener implements GestureDetector.OnDoubleTapListener {
		@Override
		public boolean onDoubleTap(final MotionEvent e) {
				return true;
		}

		@Override
		public boolean onDoubleTapEvent(final MotionEvent e) {
				return true;

		}

		@Override
		public boolean onSingleTapConfirmed(final MotionEvent e) {
				return true;
		}
	}
	
	
	
	
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		detector.onTouchEvent(event);
		return true;
	}
	

}
