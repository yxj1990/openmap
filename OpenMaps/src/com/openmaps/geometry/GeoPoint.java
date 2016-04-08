package com.openmaps.geometry;

import com.openmaps.geometry.basetypes.Location;

public class GeoPoint extends Geometry {
	public Location location = new Location();
	public GeoPoint(double x,double y){
		location.x = x;
		location.y = y;
	}
	
	public GeoPoint(){
	}

	public GeoPoint(String wkt){
		
	}

	@Override
	protected void computeBounds() {
		mBounds = new Bounds(location.x, location.y,location.x,location.y);
	}
	
	public String toWKTString(){
		StringBuilder strWKT = new StringBuilder();
		strWKT.append("POINT(");
		strWKT.append(this.location.x);
		strWKT.append(" ");
		strWKT.append(this.location.y);
		strWKT.append(")");
		return strWKT.toString();
	}
	
	@Override
	public GeoPoint clone() {
		GeoPoint p = new GeoPoint(this.location.x,this.location.y);
		if(mBounds!=null) p.mBounds = this.mBounds.clone();
		return p;
	}



}
