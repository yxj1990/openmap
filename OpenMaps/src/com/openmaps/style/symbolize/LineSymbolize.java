package com.openmaps.style.symbolize;

import android.graphics.Canvas;
import android.graphics.Point;

import com.openmaps.style.stroke.Stroke;

public class LineSymbolize extends Symbolize {
	private Stroke mStroke;
	public Stroke getStroke() {
		return mStroke;
	}
	public void setStroke(Stroke mStroke) {
		this.mStroke = mStroke;
	}
	public void draw(Canvas canvas,Point[] points){
		if(this.mStroke==null)return;
		this.mStroke.draw(canvas, points);
	}
}
