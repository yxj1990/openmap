package com.openmaps.geometry;

import com.openmaps.geometry.basetypes.Location;

public class GeometryUtils {
	/*
	 * 计算点与点之间的最小距离
	 */
	public static double getDistance(GeoPoint geom1,Location location){
		double distance=0.0;
		double dx=Math.pow(geom1.location.x-location.x,2 );
		double dy=Math.pow(geom1.location.y-location.y,2 );
		distance=Math.sqrt(dx+dy);
		return distance;
	}
	/*
	 * 计算点和线要素之间的最小距离
	 */
	public static double getDistance(GeoPoint geom1,GeoPolyline geom2){
		double distance=0.0;
		for(int i=0;i<geom2.getComponents().size();i+=2){
			if(distance>pointToLineMinDistance(geom2.getComponents().get(i).x,geom2.getComponents().get(i).y,geom2.getComponents().get(i+1).x,geom2.getComponents().get(i+1).y,geom1.location.x,geom1.location.y)){
				distance=pointToLineMinDistance(geom2.getComponents().get(i).x,geom2.getComponents().get(i).y,geom2.getComponents().get(i+1).x,geom2.getComponents().get(i+1).y,geom1.location.x,geom1.location.y);
			}
		}
		return distance;
	}

	/*
	 * 计算点和面要素之间的最小距离
	 */
	public static double getDistance(GeoPoint geom1,GeoPolygon geom2){
		double distance=0.0;
		if(!intersects(geom1, geom2)){
			for(int i=0;i<geom2.getComponents().size();i+=2){
				if(distance>pointToLineMinDistance(geom2.getComponents().get(i).x,geom2.getComponents().get(i).y,geom2.getComponents().get(i+1).x,geom2.getComponents().get(i+1).y,geom1.location.x,geom1.location.y)){
					distance=pointToLineMinDistance(geom2.getComponents().get(i).x,geom2.getComponents().get(i).y,geom2.getComponents().get(i+1).x,geom2.getComponents().get(i+1).y,geom1.location.x,geom1.location.y);
				}
			}
		}
		return distance;
	}

	/*
	 * 计算线与线之间的最小距离
	 */
	public static double getDistance(GeoPolyline geom1,GeoPolyline geom2){
		double distance=0.0;
		if(!intersects(geom1, geom2)){
			distance=getDistance(geom2.getComponents().get(0),geom1);
			for(int i=0;i<geom2.getComponents().size();i++){
				if(distance>=getDistance(geom2.getComponents().get(i),geom1)){
					distance=getDistance(geom2.getComponents().get(i),geom1);
				}
			}
		}
		return distance;
	}
	private static double getDistance(Location location, GeoPolyline geom1) {
		// TODO Auto-generated method stub
		GeoPoint p=new GeoPoint();
		p.location=location;
		return getDistance(p,geom1);
	}
	/*
	 * 计算面与面之间的最小距离
	 */
	public static double getDistance(GeoPolygon geom1,GeoPolygon geom2){
		double distance=0.0;
		if(!intersects(geom1, geom2)){
			distance=getDistance(geom2.getComponents().get(0),geom1);
			for(int i=0;i<geom2.getComponents().size();i++){
				if(distance>=getDistance(geom2.getComponents().get(i),geom1)){
					distance=getDistance(geom2.getComponents().get(i),geom1);
				}
			}
		}
		return distance;
	}


	private static double getDistance(Location location, GeoPolygon geom1) {
		// TODO Auto-generated method stub
		GeoPoint p=new GeoPoint();
		p.location=location;
		return getDistance(p,geom1);
	}
	/*
	 * 计算线和面要素之间的最小距离
	 */
	public static double getDistance(GeoPolyline geom1,GeoPolygon geom2){
		double distance=0.0;
		if(!intersects(geom1, geom2)){
			distance=getDistance(geom2.getComponents().get(0),geom1);
			for(int i=0;i<geom2.getComponents().size();i++){
				if(distance>=getDistance(geom2.getComponents().get(i),geom1)){
					distance=getDistance(geom2.getComponents().get(i),geom1);
				}
			}
		}
		return distance;
	}




	private static double pointToLineMinDistance(double x, double y, double x2, double y2, double x3, 
			double y3) { 
		double space = 0; 
		double a, b, c; 
		a = lineSpace(x, y, x2, y2);// 线段的长度 
		b = lineSpace(x, y, x3, y3);// (x1,y1)到点的距离 
		c = lineSpace(x2, y2, x3, y3);// (x2,y2)到点的距离 
		if (c <= 0.000001 || b <= 0.000001) { 
			space = 0; 
			return space; 
		} 
		if (a <= 0.000001) { 
			space = b; 
			return space; 
		} 
		if (c * c >= a * a + b * b) { 
			space = b; 
			return space; 
		} 
		if (b * b >= a * a + c * c) { 
			space = c; 
			return space; 
		} 
		double p = (a + b + c) / 2;// 半周长 
		double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积 
		space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高） 
		return space; 
	} 

	// 计算两点之间的距离 
	private static double lineSpace(double x, double y, double x2, double y2) { 
		double lineLength = 0; 
		lineLength = Math.sqrt((x - x2) * (x - x2) + (y - y2) 
				* (y - y2)); 
		return lineLength; 

	}




	/*
	 * 计算两个要素是否相交
	 */
	public static  boolean intersects(GeoPoint geom1,GeoPoint geom2){
		if(geom1.location.x==geom2.location.x&&geom1.location.y==geom2.location.y){
			return true;
		}
		return false;
	}
	/*
	 * 计算两个要素是否相交
	 */
	public static  boolean intersects(GeoPoint geom1,GeoPolyline geom2){
		if(getDistance(geom1, geom2)==0.0){
			return true;
		}
		return false;
	}
	/*
	 * 计算两个要素是否相交
	 */
	public static  boolean intersects(GeoPoint geom1,GeoPolygon geom2){
		Location l0;//第一个节点
		Location l1;//第二个节点
		boolean result=false;
		for(int i=0;i<geom2.getComponents().size();i++){
			l0=geom2.getComponents().get(i);
			l1=geom2.getComponents().get(i+1);
			if(i+1==geom2.getComponents().size()){
				l1=geom2.getComponents().get(0);
			}

			if((l0.y<geom1.location.y&&l1.y>=geom1.location.y||l1.y<geom1.location.y&&l0.y>=geom1.location.y)&&l0.x+(geom1.location.y-l0.y)/(l1.y-l0.y)*(l1.x-l0.x)<geom1.location.x){
				result=!result;
			}

		}
		return result;
	}
	/*
	 * 计算两个要素是否相交
	 */
	public static  boolean intersects(GeoPolyline geom1,GeoPolyline geom2){
		boolean result=false;
		for(int i=0;i<geom1.getComponents().size()-1;i++){
			if(result) break;
			Location l0=geom1.getComponents().get(i);
			Location l1=geom1.getComponents().get(i+1);
			for(int j=0;j<geom2.getComponents().size()-1;j++){
				if(intersectLineLine(l0,l1,geom2.getComponents().get(j),geom2.getComponents().get(j+1))){
					result= true;
				}
			}
		}

		return result;
	}

	public static boolean intersectLineLine(Location a1,Location a2, Location b1,Location b2)
	{
		double _loc_9;
		double _loc_10;
		double _loc_6 = (b2.x - b1.x) * (a1.y - b1.y) - (b2.y - b1.y) * (a1.x - b1.x);
		double _loc_7 = (a2.x - a1.x) * (a1.y - b1.y) - (a2.y - a1.y) * (a1.x - b1.x);
		double _loc_8= (b2.y - b1.y) * (a2.x - a1.x) - (b2.x - b1.x) * (a2.y - a1.y);
		if (_loc_8 != 0)
		{
			_loc_9 = _loc_6 / _loc_8;
			_loc_10 = _loc_7 / _loc_8;
			if (_loc_9 >= 0)
			{
				if (_loc_9 <= 1)
				{
					if (_loc_10 >= 0)
					{
						if (_loc_10 <= 1)
						{
							return true;
							//result = new Point(a1.x + _loc_9 * (a2.x - a1.x), a1.y + _loc_9 * (a2.y - a1.y));
						}
						else
						{
							//result = "No Intersection";
							return false;
						}
					}
				}
			}
		}
		return false;
	}


	/*
	 * 计算两个要素是否相交
	 */
	public static  boolean intersects(GeoPolygon geom1,GeoPolygon geom2){
		boolean result=false;
		for(int i=0;i<geom1.getComponents().size();i++){
			if(result) break;
			Location l0=geom1.getComponents().get(i);
			Location l1;
			if(i+1==geom1.getComponents().size()){
				l1=geom1.getComponents().get(0);
			}else{
				l1=geom1.getComponents().get(i+1);
			}
			
			for(int j=0;j<geom2.getComponents().size()-1;j++){
				if(intersectLineLine(l0,l1,geom2.getComponents().get(j),geom2.getComponents().get(j+1))){
					result= true;
					break;
				}
			}
			if(intersectLineLine(l0,l1,geom2.getComponents().get(geom2.getComponents().size()-1),geom2.getComponents().get(0))){
				result= true;
			}
		}
		return result;
	}

	public static boolean intersects(Location location, GeoPolygon geom2) {
		// TODO Auto-generated method stub
		GeoPoint p=new GeoPoint();
		p.location=location;
		return intersects(p,geom2);
	}
	/*
	 * 计算两个要素是否相交
	 */
	public static  boolean intersects(GeoPolyline geom2,GeoPolygon geom1){
		boolean result=false;
		
		for(int i=0;i<geom1.getComponents().size();i++){
			if(result) break;
			Location l0=geom1.getComponents().get(i);
			Location l1;
			if(i+1==geom1.getComponents().size()){
				l1=geom1.getComponents().get(0);
			}else{
				l1=geom1.getComponents().get(i+1);
			}
			
			for(int j=0;j<geom2.getComponents().size()-1;j++){
				if(intersectLineLine(l0,l1,geom2.getComponents().get(j),geom2.getComponents().get(j+1))){
					result= true;
					break;
				}
			}
		}
		return result;
	}
	public static boolean intersects(Geometry geom1, Geometry geom2) {
		// TODO Auto-generated method stub
		if(geom1 instanceof GeoPoint){
			if(geom2 instanceof GeoPoint){
			return	intersects((GeoPoint)geom1,(GeoPoint)geom2);
			}
			if(geom2 instanceof GeoPolyline){
				return	intersects((GeoPoint)geom1,(GeoPolyline)geom2);
			}
			if(geom2 instanceof GeoPolygon){
				return	intersects((GeoPoint)geom1,(GeoPolygon)geom2);
			}
		}
		if(geom1 instanceof GeoPolyline){
			if(geom2 instanceof GeoPoint){
				return	intersects((GeoPolyline)geom1,(GeoPoint)geom2);
			}
			if(geom2 instanceof GeoPolyline){
				return	intersects((GeoPolyline)geom1,(GeoPolyline)geom2);
			}
			if(geom2 instanceof GeoPolygon){
				return	intersects((GeoPolyline)geom1,(GeoPolygon)geom2);
			}
		}
		if(geom1 instanceof GeoPolygon){
			if(geom2 instanceof GeoPoint){
				return	intersects((GeoPolygon)geom1,(GeoPoint)geom2);
			}
			if(geom2 instanceof GeoPolyline){
				return	intersects((GeoPolyline)geom2,(GeoPolygon)geom1);
			}
			if(geom2 instanceof GeoPolygon){
				return	intersects((GeoPolygon)geom1,(GeoPolygon)geom2);
			}
		}

		return false;
	}

}
