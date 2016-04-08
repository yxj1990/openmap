package com.openmaps.geometry;

import com.openmaps.geometry.basetypes.Location;

/**
 * Bounds 以地理坐标系基础。屏幕坐标系与地理坐标系的纵向数值相反
 * @author wusj
 */
public class Bounds {
	public double left;
	public double bottom;
	public double right;
	public double top;

	public Bounds(double left,double bottom,double right,double top){
		this.left = left;
		this.bottom = bottom;
		this.right = right;
		this.top = top;
	}
	
	public Bounds(Location loc1,Location loc2){
		this.left   = loc1.x < loc2.x ? loc1.x:loc2.x;
		this.right  = loc1.x > loc2.x ? loc1.x:loc2.x;
		this.top    = loc1.y > loc2.y ? loc1.y:loc2.y;
		this.bottom = loc1.y < loc2.y ? loc1.y:loc2.y;
	}
	
	
	/**
	 * 判断是否包含另一个Bounds
	 * @param bounds  被包含的Bounds
	 * @return  包含ture，否则false。两个Bounds大小一致也返回ture。
	 */
	public Boolean contain(Bounds bounds){
		return (this.bottom<=bounds.bottom&&this.top>=bounds.top&&
				this.left<=bounds.left&&this.right>=bounds.right);
	}
	
	/**
	 *  获取两个Bounds的重叠部分
	 * @param b1
	 * @param b2
	 * @return 重叠区域的Bounds
	 */
	public static Bounds getIntersectBounds(Bounds b1,Bounds b2){
		if(b1.left >= b2.right  || b2.left >= b1.right) return null;
		if(b1.top  <= b2.bottom || b2.top  <= b1.bottom) return null;
		double l = Math.max(b1.left, b2.left);
		double r = Math.min(b1.right, b2.right);
		double b = Math.max(b1.bottom, b2.bottom);
		double t = Math.min(b1.top, b2.top);
		return new Bounds(l,b,r,t);
	}
	/**
	 * 获取两个Bounds的并集
	 * @param b1
	 * @param b2
	 * @return两个Bounds的并集
	 */
	public static Bounds getUnionBounds(Bounds b1,Bounds b2){
		if(b1==null) return b2;
		if(b2==null) return b1;
		double l = Math.min(b1.left, b2.left);
		double r = Math.max(b1.right, b2.right);
		double b = Math.min(b1.bottom, b2.bottom);
		double t = Math.max(b1.top, b2.top);
		return new Bounds(l,b,r,t);
	}
	/**
	 * 获取两个Bounds的并集
	 * @param b1
	 * @param b2
	 * @return两个Bounds的并集
	 */
	public static Bounds getMaxBounds(Bounds b1,Bounds b2){
		if(b1==null) return b2;
		if(b2==null) return b1;
		double l = Math.min(b1.left, b2.left);
		double r = Math.max(b1.right, b2.right);
		double b = Math.min(b1.bottom, b2.bottom);
		double t = Math.max(b1.top, b2.top);
		return new Bounds(l,b,r,t);
	}
	@Override
	protected Bounds clone(){
		Bounds bounds = new Bounds(this.left,this.bottom,this.right,this.top);
		return bounds;
	}
	
	public static Geometry toGeometry(Bounds b1){
		GeoPolygon p=new GeoPolygon();
		p.appendPoint(new Location(b1.left, b1.top));
		p.appendPoint(new Location(b1.right, b1.top));
		p.appendPoint(new Location(b1.right, b1.bottom));
		p.appendPoint(new Location(b1.left, b1.bottom));
		return p;
	}
	

}
