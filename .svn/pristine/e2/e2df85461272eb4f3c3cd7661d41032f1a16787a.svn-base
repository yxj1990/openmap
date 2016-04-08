package com.openmaps.feature;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Point;

import com.openmaps.Map;
import com.openmaps.exceptions.GeometryTypeMisMatchException;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.style.Style;
import com.openmaps.style.symbolize.LineSymbolize;
import com.openmaps.style.symbolize.PolygonSymbolize;
import com.openmaps.style.symbolize.TextSymbolize;

public class PolygonFeature extends Feature {
	
	public PolygonFeature(Map map) {
		super(map);
	}

	@Override
	public void setGeometry(Geometry geo) throws GeometryTypeMisMatchException {
		// TODO Auto-generated method stub
		if(geo.getClass()!=GeoPolygon.class){
			throw(new GeometryTypeMisMatchException("the geometry does not match LineFeature"));
		}
		this.mGeometry = geo;
	}

	@Override
	public Geometry getGeometry() {
		// TODO Auto-generated method stub
		return this.mGeometry;
	}
	


	@Override
	public void reDraw(Canvas canvas) {
		if(mGeometry==null) return;
		Vector<Location> points = ((GeoPolygon)mGeometry).getComponents();
		if(points.size()<2) return;
		if(mStyle==null) mStyle = Style.getDefaultPloygonStyle();
		
		Style style;
		if(isSelected)
			style = Style.getDefaultPloygonSelectedStyle();
		else
			style = this.mStyle;
		
		if((style.getMinScaleDenominator()!=Double.NaN) && style.getMinScaleDenominator()>mMap.getResolution()) {
			return;
		}
		if((style.getMaxScaleDenominator()!=Double.NaN) && style.getMaxScaleDenominator()<mMap.getResolution()) {
			return;
		}
		Point[] ps = transformMapPointsToPixels(points);
		for(int i=0;i<style.getSymbolize().size();i++){
			if(style.getSymbolize().get(i) instanceof PolygonSymbolize){
				PolygonSymbolize sym = (PolygonSymbolize) style.getSymbolize().get(i);
				if(sym.getMaxLevel()>mMap.getZoom()&&sym.getMinLevel()<mMap.getZoom())
				sym.draw(canvas, ps);
			}else if(style.getSymbolize().get(i) instanceof LineSymbolize){
				LineSymbolize sym = (LineSymbolize) style.getSymbolize().get(i);
				if(sym.getMaxLevel()>mMap.getZoom()&&sym.getMinLevel()<mMap.getZoom())
				sym.draw(canvas, ps);
			}
			
			if(style.getSymbolize().get(i) instanceof TextSymbolize){
				TextSymbolize sym = (TextSymbolize) style.getSymbolize().get(i);
				if(sym.getMaxLevel()>mMap.getZoom()&&sym.getMinLevel()<mMap.getZoom())
				sym.draw(canvas, ps[0], getLabelText());
			}
		}
//		Point p1,p2;
//		Path path = new Path();
//		path.setFillType(FillType.WINDING);
//		
//		p1 = mMap.transformMapPointToPixel(points.get(0));
//		path.moveTo(p1.x, p1.y);
//		for(int i=1;i<points.size();i++){
//			p2 = mMap.transformMapPointToPixel(points.get(i));
//			path.lineTo(p2.x, p2.y);
//		}
//		p2 = mMap.transformMapPointToPixel(points.get(0)); 
//		path.lineTo(p2.x, p2.y);
//		canvas.drawPath(path, paint);
	}

	/**
	 * 先计算好要素的屏幕坐标串，在分辨率不改变，地图中心点不变的情况下，就不在重新计算。
	 * 地图平移是可以采用Location的add方法修改坐标，也无需重新计算。
	 * @param locs
	 * @return
	 */
	private Point[] transformMapPointsToPixels(Vector<Location> locs){
		Point[] points = new Point[locs.size()];
		for(int i=0;i<points.length;i++){
			Point p = mMap.transformMapPointToPixel(locs.get(i));
			points[i] = p;
		}
		return points;
	}
	
}
