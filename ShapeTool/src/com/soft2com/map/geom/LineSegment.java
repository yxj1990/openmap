package com.soft2com.map.geom;

import com.soft2com.toolkit.Log;
/**
 *
 * <p>Title: �߶���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class LineSegment
{
    /**
     * �߶����
     */
    public CPoint startPoint;

    /**
     * �߶��յ�
     */
    public CPoint  endPoint;

    /**
     * ���캯������ʼ���߶�
     * @param startPoint �߶����
     * @param endPoint �߶��յ�
     */
    public LineSegment( CPoint startPoint, CPoint endPoint )
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * ���߶γ���
     * @return
     */
    public double length()
    {
        double dx = startPoint.x - endPoint.x;
        double dy = startPoint.y - endPoint.y;
        return Math.sqrt( dx * dx + dy * dy );
    }

    /**
     * ��ƽ���߶Σ��㷨ȡ���Ժ���
     * @param h Ҫ���ƽ���߶��뱾�߶�֮���ֱ�߾��룬��h>0���Ϸ���h<0���·�
     */
    public LineSegment getParallelLineSegment( double h )
    {
        CPoint newStartPoint;
        CPoint  newEndPoint;
        double dx = endPoint.x - startPoint.x;
        double dy = endPoint.y - startPoint.y;
        double len = this.length();
        if ( len > 0 )
        {
            newStartPoint = new CPoint( startPoint );
            newEndPoint = new CPoint( endPoint );

            dy *= h / len;
            newStartPoint.x -= dy;
            newEndPoint.x -= dy;

            dx *= h / len;
            newStartPoint.y += dx;
            newEndPoint.y += dx;
            return new LineSegment( newStartPoint, newEndPoint );
        }
        else
        {
            return null;
        }
    }

    /**
     * ���߶�����ֱ�ߵķ���
     */
    public LineEquation getLineEquation()
    {
        return new LineEquation( startPoint, endPoint );
    }

    /**
     * ���߶ν���
     */
    public CPoint getIntersectionPoint( LineSegment ls )
    {
        double EPS = 0.0001;
        CPoint p = new CPoint( 0.0, 0.0 )
            , p1 = new CPoint( this.startPoint )
            , p2 = new CPoint( this.endPoint )
            , p3 = new CPoint( ls.startPoint )
            , p4 = new CPoint( ls.endPoint );

        double e = p2.x - p1.x;
        double f = p4.x - p3.x;
        double g = p2.y - p1.y;
        double h = p4.y - p3.y;
        double t = g * f - h * e;
        if ( t != 0 )
        {
            p.y = ( p3.y * g * f - p1.y * h * e + ( p1.x - p3.x ) * g * h ) / t;
            p.x = ( Math.abs( g ) < EPS ) ? ( p3.x + f * ( p.y - p3.y ) / h ) :
                ( p1.x + e * ( p.y - p1.y ) / g );

            //�ж��Ƿ��ڵ�һ���߶���
            if ( ! ( ( ( p.x > p1.x ) ^ ( p.x > p2.x ) ) ||
                     ( ( p.y > p1.y ) ^ ( p.y > p2.y ) ) ) )
                return null;

            //�ж��Ƿ��ڵڶ����߶���
            if ( ! ( ( ( p.x > p3.x ) ^ ( p.x > p4.x ) ) ||
                     ( ( p.y > p3.y ) ^ ( p.y > p4.y ) ) ) )
            {
                return null;
            }
            else
            {
                return p;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * @todo ������չ����Polygon
     * @param offset ��չ�Ŀ��,����·�ߵĿ��
     * @param startPoint �ߵ���ʼ
     * @param endPoint �ߵ��յ�
     * @return �����
     */
//    public Polygon getOffset( int offset, CPoint startPoint,
//                                       CPoint endPoint )
//    {
//        Polygon polygon = new Polygon();
//        LineEquation line = new LineEquation( startPoint, endPoint );
//
//        //�õ����ߴ�ֱ�ߵ�б��
//        double verticalSlope = line.getVerticalSlope();
//
//        //�õ����polygon��������
//        LineEquation line1 = line.getLineEquationByDistance( offset / 2 );
//        LineEquation line2 = line.getLineEquationByDistance( -offset / 2 );
//        LineEquation line3 = new LineEquation( verticalSlope, startPoint );
//        LineEquation line4 = new LineEquation( verticalSlope, endPoint );
//
//        //�õ����polygon�������ߵ��ĸ�����
//        CPoint point1 = line1.getIntersection( line3 );
//        CPoint point2 = line1.getIntersection( line4 );
//        CPoint point3 = line2.getIntersection( line4 );
//        CPoint point4 = line2.getIntersection( line3 );
//
//        //�����ĸ����㹹��һ��polygon
//        polygon.addPoint( ( int ) point1.x, ( int ) point1.y );
//        polygon.addPoint( ( int ) point2.x, ( int ) point2.y );
//        polygon.addPoint( ( int ) point3.x, ( int ) point3.y );
//        polygon.addPoint( ( int ) point4.x, ( int ) point4.y );
//
//        return polygon;
//    }

    public String toString()
    {
        return "s=" + this.startPoint.toString() + "e=" +
            this.endPoint.toString();
    }

    /**
     * ����б���ϵ�һ�ξ���õ�б���ϵ�һ��
     */
    public CPoint getPointByDistance( double distance )
    {
        if ( distance > 0 )
        {
            double dx = endPoint.x - startPoint.x;
            double dy = endPoint.y - startPoint.y;
            double x = startPoint.x + distance * dx / this.length();
            double y = startPoint.y + distance * dy / this.length();
            return new CPoint( x, y );
        }
        else
        {
            return startPoint;
        }
    }
}