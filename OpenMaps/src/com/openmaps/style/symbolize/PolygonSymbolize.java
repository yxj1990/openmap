package com.openmaps.style.symbolize;

import android.graphics.Canvas;
import android.graphics.Point;

import com.openmaps.style.fill.Fill;

public class PolygonSymbolize extends Symbolize {
	private Fill mFill;
	
	public Fill getFill() {
		return mFill;
	}

	public void setFill(Fill mFill) {
		this.mFill = mFill;
	}

	private int  mColor;
	
	public void draw(Canvas canvas,Point[] points){
		this.mFill.draw(canvas, points);
	}

}
