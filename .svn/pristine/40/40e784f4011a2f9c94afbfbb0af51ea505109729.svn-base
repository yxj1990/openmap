package com.openmaps.geometry;

import java.util.Vector;

import com.openmaps.geometry.basetypes.Location;
import com.openmaps.geometry.basetypes.Unit;
import com.openmaps.geometry.basetypes.VincentyDistance;
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
		if(mComponents==null){
			this.mBounds = new Bounds(0,0,0,0);
			return;
		}
		int length = mComponents.size();
		Double minX = 10000000000.0;
		Double maxX = -10000000000.0;
		Double minY = 10000000000.0;
		Double maxY = -10000000000.0;
		for(int i=0; i < length; i++){
			Location loc = mComponents.get(i);
			if(loc==null) continue;
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
	
	@Override
	public GeoPolygon clone(){
		GeoPolygon polygon = new GeoPolygon();
		polygon.mComponents = new Vector<Location>();
		int len = this.mComponents.size();
		for(int i=0; i < len;i++){
			polygon.mComponents.add(this.mComponents.get(i).clone());
		}
		if(this.mBounds!=null) polygon.mBounds = this.mBounds.clone();
		return polygon;
	}

	/*
	 * 计算面要素的实际面积
	 */
	public double getArea(){
		if(mComponents.size()<3){
			return 0.0;
		}
		double area=0.0;
		Location loc1;
		Location loc2; 
		for(int i=0;i<mComponents.size()-1;i++){
			loc1=mComponents.get(i);
			loc2=mComponents.get(i+1);
			area+=VincentyDistance.degtoRad(loc2.x-loc1.x)*(2+Math.sin(VincentyDistance.degtoRad(loc1.y))+Math.sin(VincentyDistance.degtoRad(loc2.y)));
		}
		loc1=mComponents.get(mComponents.size()-1);
		loc2=mComponents.get(0);
		area+=VincentyDistance.degtoRad(loc2.x-loc1.x)*(2+Math.sin(VincentyDistance.degtoRad(loc1.y))+Math.sin(VincentyDistance.degtoRad(loc2.y)));
		area = area * 6378137.0 * 6378137.0 / 2.0;
		return area;
	}

}
