package com.openmaps.style.symbolize;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;

public class PointSymbolize extends Symbolize {
	protected Marker mMarker;
	protected Bitmap mBitmap;
	protected Paint mPaint;
	
	
	
	public Paint getPaint(){
		return this.mPaint;
	}
	
	public PointSymbolize(){
		mPaint = new Paint();
	}
	
	/**
	 * 填充的图标
	 * @param fillColor
	 */
	public PointSymbolize(int fillColor){
		this.mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(fillColor);
	}
	public PointSymbolize(int fillColor,int alpha){
		this.mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(fillColor);
		this.mPaint.setAlpha(alpha);
	}
	/**
	 * 空心的图标
	 * @param strokeCokor
	 * @param width
	 */
	public PointSymbolize(int strokeColor,float strokeWidth){
		this.mPaint =  new Paint();
		mPaint.setStrokeWidth(strokeWidth);
		this.mPaint.setColor(strokeColor);
		this.mPaint.setStyle(Style.STROKE);
	}
	/**
	 * 空心的图标
	 * @param strokeCokor
	 * @param width
	 */
	public PointSymbolize(int strokeColor,float strokeWidth,int alpha){
		this.mPaint =  new Paint();
		mPaint.setStrokeWidth(strokeWidth);
		this.mPaint.setColor(strokeColor);
		this.mPaint.setStyle(Style.STROKE);
		this.mPaint.setAlpha(alpha);
		
		
	}
	
	public void setMarker(Marker mMarker) {
		this.mMarker = mMarker;
	}
	
	public void setBitMap(Bitmap bitmap){
		this.mBitmap = bitmap;
	}


	
	public void draw(Canvas canvas,Point p){
		canvas.drawPath(mMarker.getPath(p.x,p.y),mPaint);
		if(mBitmap!=null)
			canvas.drawBitmap(mBitmap, p.x, p.y,mPaint);
	}
	
}
