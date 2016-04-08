package com.soft2com.map.geom;

/**
 *
 * <p>Title: 点类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class CPoint implements CShape
{
    /**
     * 点的横坐标
     */
    public double x;

    /**
     * 点的纵坐标
     */
    public double y;

    /**
     * 构造函数，初始化点对象
     * @param x 点的横坐标
     * @param y 点的纵坐标
     */
    public CPoint( double x, double y )
    {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param point
     */
    public CPoint( CPoint point )
    {
        this.x = point.x;
        this.y = point.y;
    }

    public double getX()
    {
        return this.x;
    }

    public void setX( double x )
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY( double y )
    {
        this.y = y;
    }

    /**
     * @todo 计算传入的点与这个点的距离是否小于offset（即半径）
     * 如果是则可以捕获这一点
     * @param point
     * @param offset
     * @return
     */
    public boolean hitTest( CPoint point, double offset )
    {
        return (this.getDistance( point ) < offset);
    }

    /**
     * @todo 计算传入的点与这个点的距离是否小于offset（即半径）
     * 如果是则可以捕获这一点
     * @param x
     * @param y
     * @param offset
     * @return
     */
    public boolean hitTest( double x, double y, double offset )
    {
        return this.hitTest( new CPoint( x, y ), offset );
    }

    /**
     *
     * @return
     */
    public Extent getExtent()
    {
        return new Extent( this, this );
    }

    /**
     * @todo 计算这一点与另一点之间的直线距离
     * @param point
     * @return
     */
    public double getDistance( CPoint point )
    {
        double dx = this.x - point.x;
        double dy = this.y - point.y;
        return Math.sqrt( dx * dx + dy * dy );
    }

    /**
     *
     * @param obj
     * @return
     */
    public boolean equals( Object obj )
    {
        if ( obj instanceof CPoint )
        {
            CPoint p = ( CPoint ) obj;
            return ( (this.x == p.x) && (this.y == p.y) );
        }
        else
        {
            return false;
        }
    }

    public String toString()
    {
        return "point(" + this.getX() + "," + this.getY() + ")";
    }
}
