package com.openmaps.geometry.topology;

import java.util.Vector;

import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.basetypes.Location;

/**
 * @author wusj 20150317
 */
public class Topology {
	public static final double EARTH_RAIDUS = 6378137.0;
	/**
	 * @param polygon  外围几何图形
	 * @param geo      被包含的几何图形
	 * @return
	 */
	public static boolean contain(final GeoPolygon polygon,final Geometry geo){
		return false;
	}
	
	public static boolean interect(){
		return false;
	}
	
	/**
	 * 计算多边形的面积（GDAL翻译）
	 * @param points   
	 */
	public static double calaArea(final Vector<Location> points){
		double areaSum = 0.0;
	    int i;
	    int pointCount = points.size();
	    if( pointCount < 2 )
	        return 0;
	    areaSum = points.get(0).x * (points.get(1).y - points.get(pointCount-1).y);
	    for( i = 1; i < pointCount-1; i++ )
	    {
	        areaSum += points.get(i).x * (points.get(i+1).y - points.get(i-1).y);
	    }

	    areaSum += points.get(pointCount-1).x * (points.get(0).y - points.get(pointCount-2).y);
	    return 0.5 * Math.abs(areaSum);
	}

	/**
	 * 计算球面面积，算法来自OpenScales
	 * @param points : 经纬度坐标的点集合,
	 * @return  球面积
	 */
	public static double getGeodesicArea(final Vector<Location> points){
		double areaSum = 0;
		int pointCount = points.size();
		if(pointCount<3) return 0; 
		Location p1,p2;
		for(int i=0;i<pointCount-1;i++){
			p1 = points.get(i);
			p2 = points.get(i+1);
			areaSum+=degree2Rad(p2.x-p1.x)*(2+Math.sin(degree2Rad(p1.y))+
					Math.sin(degree2Rad(p2.y)));
		}
		p1 = points.get(pointCount-1);
		p2 = points.get(0);
		areaSum = areaSum+=degree2Rad(p2.x-p1.x)*(2+Math.sin(degree2Rad(p1.y))+
				Math.sin(degree2Rad(p2.y)));
		areaSum = areaSum*EARTH_RAIDUS*EARTH_RAIDUS/2.0;
		return areaSum;
	}
	
	/**
	 * 角度转弧度
	 * @param degree
	 * @return
	 */
	public static double degree2Rad(double degree){
		return degree*Math.PI/180;
	}
	
	
	
}
