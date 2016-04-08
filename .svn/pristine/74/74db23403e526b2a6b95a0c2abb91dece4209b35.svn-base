package com.soft2com.map.geom;

/**
 *
 * <p>Title: 直线方程类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class LineEquation
{
    /**
     * 斜率
     */
    double k;

    /**
     * 截距
     */
    double b;

    /**
     * 用斜率和截距定义线性方程
     * @param k 斜率
     * @param b 截距
     */
    public LineEquation( double k, double b )
    {
        this.k = k;
        this.b = b;
    }

    /**
     * 用两个点定义线性方程
     * @param p1
     * @param p2
     */
    public LineEquation( CPoint p1, CPoint p2 )
    {
        //斜率为0
        if ( p1.getX() == p2.getX() )
        {
            this.k = Double.NaN;
            this.b = p1.getX();
        }
        else
        {
            //斜率为无穷大
            if ( p1.getY() == p2.getY() )
            {
                this.k = 0;
                this.b = p1.getY();
            }
            else
            {
                this.k = ( p1.getY() - p2.getY() ) / ( p1.getX() - p2.getX() );
                this.b = p1.getY() - k * p1.getX();
            }
        }
    }

    /**
     * 用斜率和一个点定义值线方程
     * @param k
     * @param p
     */
    public LineEquation( double k, CPoint p )
    {
        if ( Double.isNaN( k ) )
        {
            this.k = Double.NaN;
            this.b = p.x;
        }
        else
        {
            if ( k == 0 )
            {
                this.k = 0;
                this.b = p.y;
            }
            else
            {
                this.k = k;
                this.b = p.getY() - k * p.getX();
            }
        }
    }

    /**
     * 求两条直线方程的交点
     * @param line2
     * @return
     */
    public CPoint getIntersection( LineEquation line2 )
    {
        //当两条线的斜率相同时
        if ( this.k == line2.k )
        {
            return new CPoint( Double.NaN, Double.NaN );
        }

        if ( Double.isNaN( this.k ) ) //当该线是垂直线时,即斜率为正无穷或者负无穷
        {
            if ( line2.k == 0 ) //当第二条线是水平线时,即斜率为0
            {
                return new CPoint( this.b, line2.b );
            }
            else
            {
                return new CPoint( this.b, line2.getY( this.b ) );
            }
        }
        else if ( this.k == 0 ) //当该线是水平线时,即斜率为0
        {
            if ( Double.isNaN( line2.k ) )
            {
                return new CPoint( line2.b, this.b );
            }
            else //当第二线是垂直线时,即斜率为正无穷或者负无穷
            {
                return new CPoint( line2.getX( this.b ), this.b );
            }
        }
        else
        {
            double x = ( line2.b - b ) / ( k - line2.k );
            double y = getY( x );
            return new CPoint( x, y );
        }
    }

    /**
     * 根据x得到y;
     * @param y
     * @return
     */
    public double getX( double y )
    {
        return ( y - b ) / k;
    }

    /**
     * 根据y得到x
     */
    public double getY( double x )
    {
        return k * x + b;
    }

    /**
     * 根据直线距离求另一条直线方程
     */
    public LineEquation getLineEquationByDistance( double distance )
    {
        if ( Double.isNaN( this.k ) )
        { //当该线是垂直线时,即斜率为正无穷或者负无穷
            return new LineEquation( Double.NaN, this.b + distance );
        }
        else if ( this.k == 0 )
        { //当该线是水平线时,即斜率为0
            return new LineEquation( 0, this.b + distance );
        }
        else
        {
            int factor = 1;
            double offsetB = distance * Math.sqrt( 1 + k * k ) * factor;
            return new LineEquation( k, b + offsetB );
        }
    }

    /**
     * 判断另一条线是否在本线的下方
     */
    public boolean isBelow( LineEquation line2 )
    {
        return b < line2.b;
    }


    /**
     * 得到线方程式的斜率
     * @return
     */
    public double getSlope()
    {
        return this.k;
    }

    /**
     * 得到与该线的垂直线的斜率
     */
    public double getVerticalSlope()
    {
        if ( Double.isNaN( this.k ) )
        {
            return 0;
        }
        else if ( this.k == 0 )
        {
            return Double.NaN;
        }
        else
        {
            double arc = Math.PI / 180;
            return Math.tan( ( Math.atan( k ) / arc + 90 ) * arc );
        }
    }
}