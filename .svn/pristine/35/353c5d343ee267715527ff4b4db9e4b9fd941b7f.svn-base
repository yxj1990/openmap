package com.openmaps.format;

import java.util.Locale;

import com.openmaps.feature.Feature;
import com.openmaps.geometry.GeoPoint;

public class WKTFormat {
	public static void read(String wkt){
		String[] array = wkt.split("\\(",2);
		if(array.length>1){
			String type = array[0].toLowerCase(Locale.ENGLISH);
			parse(type, array[1]);
		}
	}
	
	/**
	 *  将字符串转为Feature
	 * @param type
	 * @param data
	 */
	public static void parse(String type,String data){
		data = data.replaceAll("\\s{2,}+", " ");
		if(type.equals("point")){
			data = data.replaceAll("\\(|\\)", "");
			String[] array = data.trim().split("\\s");
			GeoPoint p = new GeoPoint(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
		}else if(type.equals("multipoint")){
			data = data.replaceAll("\\(|\\)", "");
			String[] points = data.split(",");
			for(int i=0;i < points.length;i++){
				String[] array = points[i].trim().split("\\s+");
				GeoPoint p = new GeoPoint(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
			}
		}else if(type.equals("linestring")){
			data = data.replaceAll("\\(|\\)", "");
			String[] points = data.split(",");
			for(int i=0;i < points.length;i++){
				String[] array = points[i].trim().split("\\s+");
				GeoPoint p = new GeoPoint(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
			}
			
		}else if(type.equals("polygon")){
			String[] lines = data.split("\\)\\s*,\\s*\\(");
			for(int i=0;i<lines.length;i++){
				parse("linestring", lines[i]);
			}
		}else if(type.equals("multilinestring")){
			String[] lines = data.split("\\)\\s*,\\s*\\(");
			for(int i=0;i<lines.length;i++){
				parse("linestring", lines[i]);
			}
		}else if(type.equals("multipolygon")){
			String[] polygons = data.split("(\\)\\s*){2}\\s*,\\s*(\\(\\s*){2}");
			for(int i=0;i<polygons.length;i++){
				parse("polygon", polygons[i]);
			}
		}else if(type.equals("geometrycollection")){
			data = data.replaceAll(",\\s*([A-Za-z])", "|");
			String[] geometrys = data.split("\\|");
			read(geometrys[0]);
		}else{
			return;
		}
	}
	
	/**
	 *  将要素转为wkt文本
	 * @param feature
	 */
	public static String write(Feature feature){
		return feature.getGeometry().toWKTString();
	}
}
