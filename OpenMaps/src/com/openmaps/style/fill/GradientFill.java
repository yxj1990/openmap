package com.openmaps.style.fill;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader.TileMode;

public class GradientFill extends Fill {
	private float mStartX,mStartY,mEndX,mEndY;
	private int mStartColor,mEndColor;
	private TileMode mMode;
	
	/**
	 * 请参照LinearGradient的参数说明
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param color0
	 * @param color1
	 * @param mode
	 */
	public GradientFill(float x0,float y0,float x1,float y1,int color0,int color1,TileMode mode){
		this.mStartX = x0;
		this.mStartY = y0;
		this.mEndX   = x1;
		this.mEndY   = y1;
		this.mStartColor = color0;
		this.mEndColor = color1;
		this.mMode = mode;
	}
	
	
	@Override
	public void draw(Canvas canvas, Point[] points) {
		Paint paint = new Paint();
		LinearGradient shader  = new LinearGradient(mStartX, mStartY,mEndX,mEndY, mStartColor ,mEndColor,mMode);
		paint.setShader(shader);
		Path path = new Path();
		path.moveTo(points[0].x,points[0].y);
		for(int  i=1; i < points.length; i++){
			path.lineTo(points[i].x,points[i].y);
		}
		canvas.drawPath(path, paint);
	}

}
