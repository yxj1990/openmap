package com.soft2com.map.geom;

/**
 *
 * <p>Title: ֱ�߷�����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class LineEquation
{
    /**
     * б��
     */
    double k;

    /**
     * �ؾ�
     */
    double b;

    /**
     * ��б�ʺͽؾඨ�����Է���
     * @param k б��
     * @param b �ؾ�
     */
    public LineEquation( double k, double b )
    {
        this.k = k;
        this.b = b;
    }

    /**
     * �������㶨�����Է���
     * @param p1
     * @param p2
     */
    public LineEquation( CPoint p1, CPoint p2 )
    {
        //б��Ϊ0
        if ( p1.getX() == p2.getX() )
        {
            this.k = Double.NaN;
            this.b = p1.getX();
        }
        else
        {
            //б��Ϊ�����
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
     * ��б�ʺ�һ���㶨��ֵ�߷���
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
     * ������ֱ�߷��̵Ľ���
     * @param line2
     * @return
     */
    public CPoint getIntersection( LineEquation line2 )
    {
        //�������ߵ�б����ͬʱ
        if ( this.k == line2.k )
        {
            return new CPoint( Double.NaN, Double.NaN );
        }

        if ( Double.isNaN( this.k ) ) //�������Ǵ�ֱ��ʱ,��б��Ϊ��������߸�����
        {
            if ( line2.k == 0 ) //���ڶ�������ˮƽ��ʱ,��б��Ϊ0
            {
                return new CPoint( this.b, line2.b );
            }
            else
            {
                return new CPoint( this.b, line2.getY( this.b ) );
            }
        }
        else if ( this.k == 0 ) //��������ˮƽ��ʱ,��б��Ϊ0
        {
            if ( Double.isNaN( line2.k ) )
            {
                return new CPoint( line2.b, this.b );
            }
            else //���ڶ����Ǵ�ֱ��ʱ,��б��Ϊ��������߸�����
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
     * ����x�õ�y;
     * @param y
     * @return
     */
    public double getX( double y )
    {
        return ( y - b ) / k;
    }

    /**
     * ����y�õ�x
     */
    public double getY( double x )
    {
        return k * x + b;
    }

    /**
     * ����ֱ�߾�������һ��ֱ�߷���
     */
    public LineEquation getLineEquationByDistance( double distance )
    {
        if ( Double.isNaN( this.k ) )
        { //�������Ǵ�ֱ��ʱ,��б��Ϊ��������߸�����
            return new LineEquation( Double.NaN, this.b + distance );
        }
        else if ( this.k == 0 )
        { //��������ˮƽ��ʱ,��б��Ϊ0
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
     * �ж���һ�����Ƿ��ڱ��ߵ��·�
     */
    public boolean isBelow( LineEquation line2 )
    {
        return b < line2.b;
    }


    /**
     * �õ��߷���ʽ��б��
     * @return
     */
    public double getSlope()
    {
        return this.k;
    }

    /**
     * �õ�����ߵĴ�ֱ�ߵ�б��
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