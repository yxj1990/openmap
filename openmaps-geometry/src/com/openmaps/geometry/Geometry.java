package com.openmaps.geometry;

public abstract class Geometry {
	protected Bounds mBounds;
	
	/**
	 * @return 几何图形的最小包围矩形
	 */
	public  Bounds getBounds(){
		if(mBounds==null)computeBounds();
		return mBounds;
	}
	
	public abstract String toWKTString();
	
	protected abstract void computeBounds();

}
