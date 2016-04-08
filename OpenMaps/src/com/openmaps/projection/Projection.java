package com.openmaps.projection;

import android.graphics.Point;

import com.openmaps.Map;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.basetypes.Location;

public abstract class Projection {
	public Map    mMap;
	public Bounds mGeoBounds; //��ͶӰ����������ʾ��Χ
	
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
	 * ��������ת��Ļ����
	 * @param location
	 * @return
	 */
	public abstract Point transformMapPointToPixel(Location location);
	
	/**
	 * ��Ļ����ת��������
	 * @param point
	 * @return
	 */
	public abstract Location transformPixelToMapPoint(Point point);

}
