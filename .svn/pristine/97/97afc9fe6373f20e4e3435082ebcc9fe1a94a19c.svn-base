package com.openmaps.style.fill;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;

public class SolidFill extends Fill {
	private int mColor;
	public SolidFill(int color){
		this.mColor = color;
	}
	@Override
	public void draw(Canvas canvas, Point[] points) {
		Paint paint = new Paint();
		paint.setStyle(Style.FILL);
		paint.setColor(mColor);
		Path path = new Path();
		path.moveTo(points[0].x,points[0].y);
		for(int  i=1; i < points.length; i++){
			path.lineTo(points[i].x,points[i].y);
		}
		path.lineTo(points[0].x,points[0].y);
		canvas.drawPath(path,paint);
	}

}
