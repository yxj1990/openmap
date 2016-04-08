package com.openmaps.geometry;

import com.openmaps.geometry.basetypes.Location;

/**
 * Bounds �Ե�������ϵ��������Ļ����ϵ���������ϵ��������ֵ�෴
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
	 * �ж��Ƿ������һ��Bounds
	 * @param bounds  ��������Bounds
	 * @return  ����ture������false������Bounds��Сһ��Ҳ����ture��
	 */
	public Boolean contain(Bounds bounds){
		return (this.bottom<=bounds.bottom&&this.top>=bounds.top&&
				this.left<=bounds.left&&this.right>=bounds.right);
	}
	
	/**
	 *  ��ȡ����Bounds���ص�����
	 * @param b1
	 * @param b2
	 * @return �ص������Bounds
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
	 * ��ȡ����Bounds�Ĳ���
	 * @param b1
	 * @param b2
	 * @return����Bounds�Ĳ���
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
	 * ��ȡ����Bounds�Ĳ���
	 * @param b1
	 * @param b2
	 * @return����Bounds�Ĳ���
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
