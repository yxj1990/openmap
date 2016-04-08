package com.soft2com.map.geom;

import java.util.Vector;

import com.soft2com.util.CCollection;
import com.soft2com.util.CIterator;
import com.soft2com.toolkit.Log;

/**
 *
 * <p>Title: 线类型</p>
 * <p>Description: 可以是折线</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class Line extends AbstractShape
{
    public Line()
    {
    }

    public Line( Line line )
    {
        this( line.getPoints() );
    }

    public Line( CCollection points )
    {
        CIterator it = points.iterator();
        while ( it.hasNext() )
        {
            super.add( new CPoint( ( CPoint ) it.next() ) );
        }
    }

    /**
     * @todo 把直线上相邻且相同的两个点合并成一个点
     */
    public Line mergeSamePoint()
    {
        if ( super.size() <= 1 )
        {
            return this;
        }
        CIterator it = this.iterator();
        Line line = new Line();
        CPoint prept = null,
            pt = null;
        prept = pt = ( CPoint ) it.next();
        line.add( pt );
        while ( it.hasNext() )
        {
            pt = ( CPoint ) it.next();
            if ( !prept.equals( pt ) )
            {
                prept = pt;
                line.add( pt );
            }
        }
        return line;
    }

    /**
     * @todo 得到线的包裹多边形，如果线是自相交的，描绘时会有问题
     * @param lineWidth
     * @return
     */
    public Ring getWrapRing( double lineWidth )
    {
        //本算法把线的外接多边形分成上下两部分，最后把上下两部分合并成一个完整的多边形
        Line l = this.mergeSamePoint(); //合并重复的点，避免推平行线时出错
        if ( l.size() == 0 )
        {
            return null;
        }

        if ( l.size() == 1 )
        { //如果线上只有一个点，那它的外界多边形是一个矩形
            CPoint pt = ( CPoint ) l.iterator().next();
            Extent ext = new Extent( new CPoint( pt.x - lineWidth,
                                                pt.y - lineWidth ),
                                     new CPoint( pt.x + lineWidth,
                                                pt.y + lineWidth ) );
            return ext.toRing();
        }
        Object[] points = l.toArray();

        Vector points1 = new Vector( this.size() );
        Ring ring = new Ring();

        LineSegment
            cls = null //currentLineSegment
            , cals = null //currentAboveLineSegment
            , cbls = null //currentBelowLineSegment
            , pls = null //nextLineSegment
            , pals = null //nextAboveLineSegment
            , pbls = null; //nextBelowLineSegment

        CPoint aip = null //aboveIntersetionPoint
            , bip = null; //belowIntersetionPoint

        cls = new LineSegment( ( CPoint ) points[0], ( CPoint ) points[1] );
        cals = cls.getParallelLineSegment( lineWidth );
        cbls = cls.getParallelLineSegment( -lineWidth );
        ring.add( cals.startPoint );
        points1.addElement( cbls.startPoint );

        for ( int i = 1; i < points.length - 1; i++ )
        {
            pls = cls;
            pals = cals;
            pbls = cbls;

            cls = new LineSegment( ( CPoint ) points[i], ( CPoint ) points[i + 1] );
            cals = cls.getParallelLineSegment( lineWidth );
            cbls = cls.getParallelLineSegment( -lineWidth );

            aip = cals.getIntersectionPoint( pals ); //判断当前线段是否与前一线段相交
            if ( aip == null )
            { //如果不相交，则把两个当前线段的起点
                ring.add( pals.endPoint ); // 和前一线段的终点都加入多边形中
                ring.add( cals.startPoint );
            }
            else
            { //否则只把交点加入多边形
                ring.add( aip );
            }

            bip = cbls.getIntersectionPoint( pbls ); //思路与上边代码类似
            if ( bip == null )
            {
                points1.addElement( pbls.endPoint );
                points1.addElement( cbls.startPoint );
            }
            else
            {
                points1.addElement( bip );
            }
        }
        ring.add( cals.endPoint ); //把结尾的一点加入多边形
        ring.add( cbls.endPoint );

        for ( int i = points1.size() - 1; i >= 0; i-- )
        { //合并多边形的上下两部分
            if ( points1.elementAt( i ) != null )
            {
                ring.add( points1.elementAt( i ) );
            }
        }
        return ring;
    }

    /**
     * @todo 点击测试
     * @param x
     * @param y
     * @param offset
     * @return
     */
    public boolean hitTest( double x, double y, double offset )
    {
        return this.getWrapRing( offset ).contains( x, y );
    }

    /**
     * @todo 比较参数形状是否与当前形状相同
     * @param obj
     * @return
     */
    public boolean equals( Object obj )
    {
        if ( obj instanceof Line )
        {
            return ( ( Line ) obj ).getPoints().equals( this.getPoints() );
        }
        else
        {
            return false;
        }
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "Line include " + collection.size() + " point\n" );
        CIterator it = collection.iterator();
        int i = 0;
        CPoint point = null;
        while ( it.hasNext() )
        {
            i++;
            point = ( CPoint ) it.next();
            sb.append( "\tpoint " + i + " x=" + point.x + "\ty=" + point.y +
                       "\n" );
        }
        return sb.toString();
    }

    /**
     * @todo 计算线长
     * @return
     */
    public double getLength()
    {
        CIterator it = collection.iterator();
        CPoint cpoint = null;
        CPoint nextPoint = null;
        double length = 0.0;
        if( it.hasNext() )
        {
            cpoint = (CPoint)it.next();
        }

        while( it.hasNext() )
        {
            nextPoint = (CPoint)it.next();
            length += cpoint.getDistance( nextPoint );
            cpoint = nextPoint;
        }
        return length;
    }
}