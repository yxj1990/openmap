package com.openmaps.projection;

import android.graphics.Point;

import com.openmaps.Map;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.basetypes.Location;

public abstract class Projection {
	public Map    mMap;
	public Bounds mGeoBounds; //本投影的最大地理显示范围
	
	public Projection(Map map){
		this.mMap = map;
	}
	
	public Bounds getGeoBounds() {
		return mGeoBounds;
	}

	public void setGeoBounds(Bounds mGeoBounds) {
		this.mGeoBounds = mGeoBounds;
	}

	/**
	 * 地理坐标转屏幕坐标
	 * @param location
	 * @return
	 */
	public abstract Point transformMapPointToPixel(Location location);
	
	/**
	 * 屏幕坐标转地理坐标
	 * @param point
	 * @return
	 */
	public abstract Location transformPixelToMapPoint(Point point);

}
