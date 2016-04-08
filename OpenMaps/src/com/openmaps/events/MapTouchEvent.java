package com.openmaps.events;

import android.view.MotionEvent;

import com.openmaps.Map;

public class MapTouchEvent extends MapEvent {
	private static final long serialVersionUID = -1756619602390593385L;
	private MotionEvent mMotionEvent;
	public MapTouchEvent(Map map,int type,MotionEvent event){
		super(new Object());
		this.mType = type;
		this.mMotionEvent = event;
	}

	
	public MotionEvent getMotionEvent(){
		return mMotionEvent;
	}
	

}
