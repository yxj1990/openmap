package com.openmaps.geometry;

import java.util.Vector;

import com.openmaps.geometry.basetypes.Location;
import com.openmaps.geometry.basetypes.VincentyDistance;

public class GeoPolyline extends Geometry {
	protected Vector<Location> mComponents;

	public Vector<Location> getComponents() {
		if(mComponents==null) mComponents = new Vector<Location>();
		return mComponents;
	}

	public void setComponents(Vector<Location> mComponents) {
		this.mComponents = mComponents;
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
	protected void computeBounds() {
		if(mComponents==null){
			this.mBounds  = new Bounds(0, 0, 0, 0);
			return;
		}
		int length = mComponents.size();
		Double minX = 10000000000.0;
		Double maxX = -10000000000.0;
		Double minY = 10000000000.0;
		Double maxY = -10000000000.0;
		for(int i=0; i < length; i++){
			Location loc = mComponents.get(i);
			if(loc==null)continue;
			minX = minX > loc.x?loc.x:minX;
			maxX = maxX < loc.x?loc.x:maxX;
			minY = minY > loc.y?loc.y:minY;
			maxY = maxY < loc.y?loc.y:maxY;
		}
		
		this.mBounds = new Bounds(minX, minY, maxX, maxY);
	}


	@Override
	public String toWKTString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("LINESTRING(");
		int len = this.mComponents.size();
		for(int i=0; i < len-1;i++){
			strBuilder.append(mComponents.get(i).x + " " + mComponents.get(i).y+",");
		}
		strBuilder.append(mComponents.get(len-1).x + " " + mComponents.get(len-1).y);
		return strBuilder.toString();
	}
	
	
	@Override
	public GeoPolyline clone() {
		GeoPolyline line = new GeoPolyline();
		line.mComponents = new Vector<Location>();
		int len = this.mComponents.size();
		for(int i=0; i < len;i++){
			line.mComponents.add(this.mComponents.get(i).clone());
		}
		if(this.mBounds!=null) line.mBounds = this.mBounds.clone();
		return line;
	}

	/*
	 * 增加计算线的总长度计算
	 */
	public double getLength(){
		double temLength=0.0;
		for(int i=0;i<mComponents.size()-1;i++){
			temLength+=VincentyDistance.calculateDistanceBetweenTwoPoints(mComponents.get(i), mComponents.get(i+1));
		}
		return temLength;
	}

	
	
}
