package com.openmaps.feature;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Point;

import com.openmaps.Map;
import com.openmaps.exceptions.GeometryTypeMisMatchException;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.layer.VectorLayer.VectorType;
import com.openmaps.style.Style;
import com.openmaps.style.symbolize.LineSymbolize;
import com.openmaps.style.symbolize.TextSymbolize;

public class LineFeature extends Feature {

	public LineFeature(Map map) {
		super(map);
	}

	@Override
	public void setGeometry(Geometry geo) throws GeometryTypeMisMatchException {
		// TODO Auto-generated method stub
		if(geo.getClass()!=GeoPolyline.class){
			throw(new GeometryTypeMisMatchException("the geometry does not match LineFeature"));
		}
		this.mGeometry = geo;
	}

	@Override
	public Geometry getGeometry() {
		return this.mGeometry;
	}
	

	@Override
	public void reDraw(Canvas canvas) {
		if(mGeometry==null) return;
		Vector<Location> points = ((GeoPolyline)this.mGeometry).getComponents();
		if(points.size()<2) return;
		
		if(this.mStyle==null) this.mStyle = this.mLayer.getStyle(VectorType.LINE);
		if(this.mStyle==null) Style.getDefaultPointStyle();
		
		Style style;
		if(isSelected)
			style = Style.getDefaultLineSelectedStyle();
		else
			style = this.mStyle;
		
		Point[] ps = transformMapPointsToPixels(points);
		if((style.getMinScaleDenominator()!=Double.NaN) && style.getMinScaleDenominator()>mMap.getResolution()) {
			return;
		}
		if((style.getMaxScaleDenominator()!=Double.NaN) && style.getMaxScaleDenominator()<mMap.getResolution()) {
			return;
		}
		canvas.clipRect(0, 0, this.getMap().getWidth(), this.getMap().getHeight());
		for(int i=0;i<style.getSymbolize().size();i++){
			if(style.getSymbolize().get(i) instanceof LineSymbolize){
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
