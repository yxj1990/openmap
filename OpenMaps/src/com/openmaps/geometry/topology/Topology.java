package com.openmaps.geometry.topology;

import java.util.Vector;

import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.basetypes.Location;

/**
 * @author wusj 20150317
 */
public class Topology {
	public static final double EARTH_RAIDUS = 6378137.0;
	/**
	 * @param polygon  ��Χ����ͼ��
	 * @param geo      �������ļ���ͼ��
	 * @return
	 */
	public static boolean contain(final GeoPolygon polygon,final Geometry geo){
		return false;
	}
	
	
	private static double determinant(double v1, double v2, double v3, double v4)  // ����ʽ  
	{  
	    return (v1*v3-v2*v4);  
	} 
	/**
	 * �ж������߶��Ƿ��ཻ
	 * @param s0 ��һ���ߵ����
	 * @param s1 ��һ���ߵ��յ�
	 * @param e0 �ڶ����ߵ����
	 * @param e1 �ڶ����ߵ��յ�
	 * @return
	 */
	public static boolean interect(Location s0,Location s1,Location e0,Location e1){
		double delta = determinant(s1.x-s0.x, e0.x-e1.x, s1.y-s0.y, e0.y-e1.y);  
	    if ( delta<=(1e-6) && delta>=-(1e-6) )  // delta=0����ʾ���߶��غϻ�ƽ��  
	    {  
	        return false;  
	    }  
	    double namenda = determinant(e0.x-s0.x, e0.x-e1.x, e0.y-s0.y, e0.y-e1.y) / delta;  
	    if ( namenda>1 || namenda<0 )  
	    {  
	        return false;  
	    }  
	    double miu = determinant(s1.x-s0.x, e0.x-s0.x, s1.y-s0.y, e0.y-s0.y) / delta;  
	    if ( miu>1 || miu<0 )  
	    {  
	        return false;  
	    }  
	    return true;  
	}
	
	public static boolean interect(Bounds bounds,GeoPolyline line){
		Location loc1 = new Location(bounds.left, bounds.top);
		Location loc2 = new Location(bounds.left, bounds.bottom);
		Location loc3 = new Location(bounds.right, bounds.bottom);
		Location loc4 = new Location(bounds.right, bounds.top);
		Location loc5,loc6;
		Vector<Location> mPoints = line.getComponents();
		int len = mPoints.size();
		for(int i=0; i < len-1;i++){
			loc5 = mPoints.get(i);
			loc6 = mPoints.get(i+1);
			if(interect(loc1, loc2,loc5,loc6)) return true;
			if(interect(loc2, loc3,loc5,loc6)) return true;
			if(interect(loc3, loc4,loc5,loc6)) return true;
			if(interect(loc4, loc1,loc5,loc6)) return true;
		}
		return false;
	}
	
	public static boolean interect(Bounds bounds,GeoPolygon line){
		Location loc1 = new Location(bounds.left, bounds.top);
		Location loc2 = new Location(bounds.left, bounds.bottom);
		Location loc3 = new Location(bounds.right, bounds.bottom);
		Location loc4 = new Location(bounds.right, bounds.top);
		Location loc5,loc6;
		Vector<Location> mPoints = line.getComponents();
		int len = mPoints.size();
		for(int i=0; i < len-1;i++){
			loc5 = mPoints.get(i);
			loc6 = mPoints.get(i+1);
			if(interect(loc1, loc2,loc5,loc6)) return true;
			if(interect(loc2, loc3,loc5,loc6)) return true;
			if(interect(loc3, loc4,loc5,loc6)) return true;
			if(interect(loc4, loc1,loc5,loc6)) return true;
		}
		return false;
	}
	
	/**
	 * �������ε������GDAL���룩
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
	 * ��������������㷨����OpenScales
	 * @param points : ��γ������ĵ㼯��,
	 * @return  �����
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
	 * �Ƕ�ת����
	 * @param degree
	 * @return
	 */
	public static double degree2Rad(double degree){
		return degree*Math.PI/180;
	}
	
	
}
