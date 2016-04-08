package com.openmaps.controls;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventListener;
import com.openmaps.geometry.basetypes.Unit;

public class ScaleLine extends View implements IEventListener {

	protected Map mMap=null;
	
	protected String scaleSize="";
	protected double scaleWidth=0;
	
	protected int mFontSize=20;
	protected int mTextColor=Color.BLACK;
	protected String mFontFamily="宋体";
	protected Point point=new Point(20,500);
	
	@SuppressLint("NewApi")
	public ScaleLine(Map map){
		super(map.getContext());
		point.y=map.getContext().getResources().getDisplayMetrics().heightPixels-150;
		mMap=map;
		mMap.addEventListener( EventUtil.MAP_SCALE_CHANGGE, this);
		mMap.addEventListener(EventUtil.MAP_CENTER_CHANGED, this);
	}

	@Override
	public void respond(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getType()) {
		case EventUtil.MAP_SCALE_CHANGGE:
			DrawScaleLine();
			break;
		case EventUtil.MAP_CENTER_CHANGED:
			DrawScaleLine();
			break;
		default:
			break;
		}
	}
	
	public void setFontSize(int fontSize){
		mFontSize=fontSize;
	}
	public void setFontFamily(String fontFamily){
		mFontFamily=fontFamily;
	}
	
	public void setLocation(int x,int y){
		point=new Point(x, y);
	}
	
	protected void DrawScaleLine(){
	scaleWidth=100*Unit.getResolutionOnCenter(mMap.getResolution(), mMap.getMapCenter());
	if(scaleWidth>100000){
		scaleWidth=scaleWidth/1000;	
		scaleSize=String.valueOf(getBarLen(scaleWidth))+" km";
		scaleWidth=getBarLen(scaleWidth)/Unit.getResolutionOnCenter(mMap.getResolution(), mMap.getMapCenter())*1000;
	}else{
		scaleSize=String.valueOf(getBarLen(scaleWidth))+" m";
		scaleWidth=getBarLen(scaleWidth)/Unit.getResolutionOnCenter(mMap.getResolution(), mMap.getMapCenter());
	}
	this.invalidate();
	}
	
	/*
	 * 设置比例尺显示控制
	 */
	private int getBarLen(double maxLen){
		int barLen=1;
		int digits = (int)(Math.log(maxLen) / Math.log(10));
		double pow10 = Math.pow(10, digits);
		
		int firstChar = (int)(maxLen / pow10);
		if(firstChar > 5) {
			barLen = 5;
		} else if(firstChar > 2) {
			barLen = 2;
		} else {
			barLen = 1;
		}
		return (int)(barLen* pow10);
	}


	
	
	@SuppressLint("DrawAllocation")
	protected void onDraw(Canvas canvas){
		
		Paint mPaint=new Paint();
		
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setColor(mTextColor);
		mPaint.setTextSize(mFontSize);
		mPaint.setTypeface(Typeface.create(mFontFamily,Typeface.NORMAL));
		canvas.drawText(scaleSize,point.x,point.y,mPaint);
		
		Path path=new Path();
		path.moveTo(point.x,point.y+ 10);
		path.lineTo(point.x,point.y+ 20);
		path.lineTo(point.x+(int)scaleWidth,point.y+ 20);
		path.lineTo(point.x+(int)scaleWidth,point.y+ 10);
		//path.close();
		
		canvas.drawPath(path, mPaint);
		Log.e("Scales",String.valueOf(scaleWidth)+"--"+scaleSize);
	}

}
