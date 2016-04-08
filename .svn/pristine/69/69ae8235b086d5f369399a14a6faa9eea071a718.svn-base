package com.openmaps.controls;

import com.openmaps.Map;
import com.openmaps.events.EventUtil;
import com.openmaps.events.MapTouchEvent;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class MapTouchListener implements OnTouchListener {
	private Map mMap;
	
	public MapTouchListener(Map map){
		this.mMap = map;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		MapTouchEvent evt = new MapTouchEvent(mMap,EventUtil.MAP_TOUCHED,event);
		mMap.disPatchEvent(evt);
		return true;
	}

}
