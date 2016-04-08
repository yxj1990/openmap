package com.openmaps.controls;


import android.graphics.Point;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventListener;
import com.openmaps.events.MapTouchEvent;

public class DragControl extends Control implements IEventListener{
	public static final int MODE_DRAG = 0;
	public static final int MODE_ZOOM = 1;
	public static final int MODE_NONE = -1;
	Point preMotionPoint;
	private int mode=MODE_NONE;
	private float oldDistance;
	private float newDistance;
	private float oldRotation=0;

	//private int currentZoomLevel;

	private float tempScale=0.0f;

	public DragControl(Map map){
		super(map);
		this.activate();
	}


	@Override
	public void activate() {
		this.isActivated = true;
		this.mMap.addEventListener(EventUtil.MAP_TOUCHED, this);
	}

	@Override
	public void disActivate() {
		// TODO Auto-generated method stub
		this.isActivated = false;
		this.mMap.removeEventListener(EventUtil.MAP_TOUCHED, this);
	}

	private float spacing(MotionEvent event) { //

		try{  //以后完善
			float x =event.getX(0) - event.getX(1);   
			float y =event.getY(0) - event.getY(1);   
			return FloatMath.sqrt(x * x + y * y);
		}catch(IllegalArgumentException e){
			return 1;
		}   
	}


	private float rotation(MotionEvent event){
		try{  //以后完善
			double delta_x =event.getX(0) - event.getX(1);   
			double delta_y =event.getY(0) - event.getY(1);   
			double radians=Math.atan2(delta_y, delta_x);
			return (float) Math.toDegrees(radians);
		}catch(IllegalArgumentException e){
			return 0;
		}   
	}

	@Override
	public void respond(BaseEvent pEvent) {
		MotionEvent event =((MapTouchEvent)pEvent).getMotionEvent();
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:{
			mode = MODE_DRAG;
			preMotionPoint = new Point((int)event.getX(),(int)event.getY());
			break;
		}
		case MotionEvent.ACTION_POINTER_DOWN:{
			oldDistance = spacing(event);
			oldRotation=rotation(event);
			mode = MODE_ZOOM;
			//currentZoomLevel=mMap.getZoom();
			break;
		}
		case MotionEvent.ACTION_UP:{	
			mode = MODE_NONE;
			break;
		}
		case MotionEvent.ACTION_POINTER_UP:{
			if(mode==MODE_ZOOM){
				if(newDistance/oldDistance > 1) {
					mMap.setZoom(mMap.getZoom()+1);
					mMap.mMultiTouchScale=1;
					oldDistance = newDistance;
				}else if(oldDistance/newDistance > 1) {
					mMap.setZoom(mMap.getZoom()-1);
					mMap.mMultiTouchScale=1;
					oldDistance = newDistance;
				}
				//mMap.mMultiTouchScale=newDistance/oldDistance;

				mMap.invalidate();
				//mMap.postInvalidate();
			}
			//Log.e("----","ACTION_POINTER_UP 这里变成了-1");
			mode = MODE_NONE;
			break;
		}
		case MotionEvent.ACTION_MOVE:{
			mMap.mMultiTouchScalePoint.x=event.getX();
			mMap.mMultiTouchScalePoint.y=event.getY();
			//Log.e("=====","mode:"+mode);
			if(mode==MODE_DRAG){
				Point p2 = new Point((int)event.getX(),(int)event.getY());
				mMap.move(preMotionPoint.x-p2.x,preMotionPoint.y-p2.y);
				preMotionPoint = p2;
			}else if(mode==MODE_ZOOM){
				newDistance = spacing(event);
				if(newDistance==1) return;
				//mMap.mMultiTouchScale=newDistance/oldDistance;
				tempScale=newDistance/oldDistance;
				//Log.e("缩放前", mMap.mMultiTouchScale+"");
				if( tempScale>1 ){
					mMap.setZoom(mMap.getZoom()+(int)tempScale/2);
					mMap.mMultiTouchScale=tempScale-(int)tempScale/2;
					oldDistance = newDistance/(tempScale-(int)tempScale/2);
//					Log.e("mapzoom+", mMap.getZoom()+";"+mMap.mMultiTouchScale);
				}else if(tempScale<1){
					mMap.setZoom(mMap.getZoom()-(int)(tempScale/0.5));
					mMap.mMultiTouchScale=tempScale-(int)(tempScale/0.5)*0.5f;
					oldDistance = newDistance/(tempScale-(int)(tempScale/0.5)*0.5f);
//					Log.e("mapzoom-", mMap.getZoom()+";"+mMap.mMultiTouchScale);
				}

				//								if( tempScale>1 ){
				//									mMap.setZoom(mMap.getZoom()+(int)(Math.log(tempScale)/Math.log(2)));
				//								}else{
				//									mMap.setZoom(mMap.getZoom()-(int)(Math.log(1/tempScale)/Math.log(2)));
				//								}		
				//				if(newDistance/oldDistance > 2) {
				//					mMap.setZoom(mMap.getZoom()+1);
				//					mMap.mMultiTouchScale=1;
				//					oldDistance = newDistance;
				//				}else if(oldDistance/newDistance > 2) {
				//					mMap.setZoom(mMap.getZoom()-1);
				//					mMap.mMultiTouchScale=1;
				//					oldDistance = newDistance;
				//				}
				//Log.e("手势缩放","newDistance"+newDistance+"oldDistance"+oldDistance+String.valueOf(mMap.mMultiTouchScale)+"等级"+currentZoomLevel);
				mMap.mMultiTouchRotation=rotation(event)-oldRotation;
				mMap.invalidate();
			}
		}
		}

	}  

}