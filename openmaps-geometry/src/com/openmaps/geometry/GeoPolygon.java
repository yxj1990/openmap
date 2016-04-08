package com.openmaps.geometry;

import java.util.Vector;

import com.openmaps.geometry.basetypes.Location;
/**
 * @author wusj
 */
public class GeoPolygon extends Geometry {
	protected Vector<Location> mComponents;

	/**
	 * 获取地图节点数组
	 * @return
	 */
	public Vector<Location> getComponents() {
		if(mComponents==null) mComponents = new Vector<Location>();
		return mComponents;
	}

	/**
	 * 设置节点数组
	 * @param mComponents
	 */
	public void setComponents(Vector<Location> mComponents) {
		this.mComponents = mComponents;
	}

	@Override
	protected void computeBounds() {
		int length = mComponents.size();
		Double minX = 10000000000.0;
		Double maxX = -10000000000.0;
		Double minY = 10000000000.0;
		Double maxY = -10000000000.0;
		for(int i=0; i < length; i++){
			Location loc = mComponents.get(i);
			minX = minX > loc.x?loc.x:minX;
			maxX = maxX < loc.x?loc.x:maxX;
			minY = minY > loc.y?loc.y:minY;
			maxY = maxY < loc.y?loc.y:maxY;
		}
		this.mBounds = new Bounds(minX, minY, maxX, maxY);
	}
	
	/**
	 * 在线的末尾增加一个点
	 * @param loc
	 */
	public void appendPoint(Location loc){
		if(this.mComponents==null)
			this.mComponents = new Vector<Location>();
		mComponents.add(loc);
	}



	@Override
	public String toWKTString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("PLOYGON(");
		int len = this.mComponents.size();
		for(int i=0; i < len-1;i++){
			strBuilder.append(mComponents.get(i).x + " " + mComponents.get(i).y+",");
		}
		strBuilder.append(mComponents.get(len-1).x + " " + mComponents.get(len-1).y);
		return strBuilder.toString();
	}
}
