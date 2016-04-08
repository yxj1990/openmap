package com.openmaps.tile;

import com.openmaps.geometry.Bounds;

public class MapTile {
	public  int x;
	public  int y;
	public  int zoomLevel;
	public  Bounds bounds;
/**
 * @param zoomLevel 等级
 * @param tileX   x
 * @param tileY   y
 */
	public MapTile(final int zoomLevel, final int tileX, final int tileY) {
		this.zoomLevel = zoomLevel;
		this.x = tileX;
		this.y = tileY;
	}

	public int getZoomLevel() {
		return zoomLevel;
	}

	

	@Override
	public String toString() {
		return "/" + zoomLevel + "/" + x + "/" + y;
	}
	
	   /**
     * 计算瓦片的范围
     */
    public void computeBounds(){
    		Double w = 360/Math.pow(2,zoomLevel);
    		double left = -180+x*w;
    		double top  = 90-y*w;
    		this.bounds = new Bounds(left, top-w, left+w, top);
    }
    
    /**
     * 返回瓦片的bounds
     * @return
     */
    public Bounds getBounds(){
    		return this.bounds;
    }

	@Override
	public boolean equals(final Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof MapTile))
			return false;
		final MapTile rhs = (MapTile) obj;
		return zoomLevel == rhs.zoomLevel && x == rhs.x && y == rhs.y;
	}

	@Override
	public int hashCode() {
		int code = 17;
		code *= 37 + zoomLevel;
		code *= 37 + x;
		code *= 37 + y;
		return code;
	}
}
