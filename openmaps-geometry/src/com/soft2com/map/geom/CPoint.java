package com.soft2com.map.geom;

/**
 *
 * <p>Title: ����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class CPoint implements CShape
{
    /**
     * ��ĺ�����
     */
    public double x;

    /**
     * ���������
     */
    public double y;

    /**
     * ���캯������ʼ�������
     * @param x ��ĺ�����
     * @param y ���������
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
     * @todo ���㴫��ĵ��������ľ����Ƿ�С��offset�����뾶��
     * ���������Բ�����һ��
     * @param point
     * @param offset
     * @return
     */
    public boolean hitTest( CPoint point, double offset )
    {
        return (this.getDistance( point ) < offset);
    }

    /**
     * @todo ���㴫��ĵ��������ľ����Ƿ�С��offset�����뾶��
     * ���������Բ�����һ��
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
     * @todo ������һ������һ��֮���ֱ�߾���
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
