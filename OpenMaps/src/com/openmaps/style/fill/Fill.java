package com.openmaps.style.fill;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.Point;

public abstract class Fill {
	public Fill(){
		Path path = new Path();
		path.setFillType(FillType.INVERSE_WINDING);
	}
	
	public abstract void draw(Canvas canvas,Point[] points);
	

}
