package com.openmaps.projection;

import android.graphics.Point;

import com.openmaps.Map;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.basetypes.Location;

public class ProjMercator extends Projection {
	
	public ProjMercator(Map map){
		super(map);
		this.mGeoBounds = new Bounds(-180,-90,180,90);
	}

	@Override
	public Point transformMapPointToPixel(Location location) {
		Location loc = location;
		Point px = new Point();
		Bounds b = this.mMap.getViewBounds();
		px.x = (int) ((loc.x - b.left)/this.mMap.getResolution());
		px.y = (int) ((b.top - loc.y) /this.mMap.getResolution());
		return px;
	}

	@Override
	public Location transformPixelToMapPoint(Point point) {
		Bounds b = this.mMap.getViewBounds();
		double x = b.left + point.x*this.mMap.getResolution();
		double y = b.top  - point.y*this.mMap.getResolution();
		Location location = new Location(x,y);
		return location;
	}

}
