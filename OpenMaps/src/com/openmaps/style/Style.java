/** 
*@author  Wsj 2015.03.11
* 参照OpenScales的Style设计实现
*/

package com.openmaps.style;


import java.util.Vector;

import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Shader.TileMode;

import com.openmaps.style.fill.GradientFill;
import com.openmaps.style.fill.SolidFill;
import com.openmaps.style.stroke.Stroke;
import com.openmaps.style.symbolize.LineSymbolize;
import com.openmaps.style.symbolize.Marker;
import com.openmaps.style.symbolize.Marker.MarkerType;
import com.openmaps.style.symbolize.PointSymbolize;
import com.openmaps.style.symbolize.PolygonSymbolize;
import com.openmaps.style.symbolize.Symbolize;
import com.openmaps.style.symbolize.TextSymbolize;


	public class Style {
	
	protected Vector<Symbolize> mSymbolizes;
	protected double _maxScaleDenominator=Double.NaN;
	protected double _minScaleDenominator=Double.NaN;
	public Style(){}
	
	/**
	 *  获取默认的点样式
	 * @return
	 */
	public static com.openmaps.style.Style getDefaultPointStyle(){
		com.openmaps.style.Style pointStyle = new com.openmaps.style.Style();
		PointSymbolize sym = new PointSymbolize(Color.BLUE,(float)2);
		Marker marker = new Marker(10, MarkerType.CIRCLE);
		sym.setMarker(marker);
		pointStyle.addSymbolize(sym);
		return pointStyle;
	}
	
	/**
	 *  获取点的选中样式
	 * @return
	 */
	public static com.openmaps.style.Style getDefaultPointSelectedStyle(){
		com.openmaps.style.Style pointStyle = new com.openmaps.style.Style();
		PointSymbolize sym = new PointSymbolize(Color.RED);
		Marker marker = new Marker(10, MarkerType.SQUARE);
		sym.setMarker(marker);
		pointStyle.addSymbolize(sym);
		return pointStyle;
	}
	
	/**
	 * @return  默认的线样式
	 */
	public static com.openmaps.style.Style getDefaultLineStyle(){
		com.openmaps.style.Style lineStyle = new com.openmaps.style.Style();
		LineSymbolize sym = new LineSymbolize();
		float[] array = {5,5,5,5};
		Stroke stroke = new Stroke(Color.BLUE, 3, 100, Cap.ROUND, Join.ROUND,array,2,45);
		sym.setStroke(stroke);
		lineStyle.addSymbolize(sym);
		return lineStyle;
	}
	
	
	/**
	 * @return  默认的线选中样式
	 */
	public static com.openmaps.style.Style getDefaultLineSelectedStyle(){
		com.openmaps.style.Style lineStyle = new com.openmaps.style.Style();
		LineSymbolize sym = new LineSymbolize();
		Stroke stroke = new Stroke(Color.RED, 5, 255);
		sym.setStroke(stroke);
		lineStyle.addSymbolize(sym);
		return lineStyle;
	}
	
	/**
	 * @return  默认的多边形样式
	 */
	public static com.openmaps.style.Style getDefaultPloygonStyle(){
		com.openmaps.style.Style style = new com.openmaps.style.Style();
		PolygonSymbolize sym = new PolygonSymbolize();
		GradientFill fill = new GradientFill(0,0,1000,0,Color.RED,Color.BLUE,TileMode.MIRROR);
		sym.setFill(fill);
		style.addSymbolize(sym);
		return style;
	}
	
	/**
	 * @return  默认的多边形选中样式
	 */
	public static com.openmaps.style.Style getDefaultPloygonSelectedStyle(){
		com.openmaps.style.Style style = new com.openmaps.style.Style();
		PolygonSymbolize sym = new PolygonSymbolize();
		SolidFill fill = new SolidFill(Color.RED);
		sym.setFill(fill);
		style.addSymbolize(sym);
		return style;
	}
	
	
	public static com.openmaps.style.Style getGPSLocationStyle(int accuracy,double bearing){
		com.openmaps.style.Style pointStyle = new com.openmaps.style.Style();
		PointSymbolize sym0 = new PointSymbolize(Color.rgb(34, 141, 240),20);
		Marker marker0 = new Marker(accuracy, MarkerType.CIRCLE);
		sym0.setMarker(marker0);
		pointStyle.addSymbolize(sym0);
		
		PointSymbolize sym = new PointSymbolize(Color.WHITE);
		Marker marker = new Marker(12, MarkerType.CIRCLE);
		sym.setMarker(marker);
		pointStyle.addSymbolize(sym);
		
		PointSymbolize sym1 = new PointSymbolize(Color.rgb(34, 141, 240));
		Marker marker1 = new Marker(10, MarkerType.CIRCLE);
		sym1.setMarker(marker1);
		pointStyle.addSymbolize(sym1);
		
//		PointSymbolize sym2 = new PointSymbolize(Color.BLUE);
//		Marker marker2 = new Marker(20, MarkerType.TRIANGLE);
//		marker2.setRotate(250, 10, 10);
//		sym2.setMarker(marker2);
//		pointStyle.addSymbolize(sym2);
		return pointStyle;
	}
	
	public double getMaxScaleDenominator(){
		return _maxScaleDenominator;
	}
	public void setMaxScaleDenominator(double value){
		_maxScaleDenominator=value;
	}
	public double getMinScaleDenominator(){
		return _minScaleDenominator;
	}
	public void setMinScaleDenominator(double value){
		_minScaleDenominator=value;
	}
	
	/**
	 *  增加一个symbolize
	 * @param symbolize
	 */
	public void addSymbolize(Symbolize symbolize){
		if(this.mSymbolizes==null) this.mSymbolizes = new Vector<Symbolize>();
		this.mSymbolizes.add(symbolize);
	}
	
	/**
	 * 返回所有的symbolize
	 */
	public Vector<Symbolize> getSymbolize(){
		return this.mSymbolizes;
	}
	
		
}
