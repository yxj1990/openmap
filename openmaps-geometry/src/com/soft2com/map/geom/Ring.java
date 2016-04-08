package com.soft2com.map.geom;

import java.util.Vector;

import com.soft2com.util.CCollection;
import com.soft2com.util.CIterator;

/**
 *
 * <p>Title: 闭合多边形,构成面的基础</p>
 * <p>Description: 与Polygon不同的是它不能包含洞</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class Ring extends AbstractShape implements Area
{
    public Ring()
    {
        super();
    }

    /**
     * @todo 构建并且初始化一个Ring
     * @param xpoints 包含X座标的数组
     * @param ypoints 包含Y座标的数组
     * @param npoints Ring包含点的数量
     */
    public Ring( int[] xpoints, int[] ypoints, int npoints )
    {
        if ( npoints < 0 )
        {
            throw new NegativeArraySizeException();
        }

        //精确点的个数，防止数组越界
        int minPoints = npoints > xpoints.length ? xpoints.length : npoints;
        minPoints = minPoints > ypoints.length ? ypoints.length : minPoints;
        CPoint point;

        for ( int i = 0; i < minPoints; i++ )
        {
            point = new CPoint( xpoints[i], ypoints[i] );
            collection.add( point );
        }
    }

    /**
     * @todo 构建并且初始化一个Ring
     * @param collection collection 点的集合
     */
    public Ring( CCollection collection )
    {
        this.collection.addAll( collection );
    }

    /**
     * @todo 判断环形是否包含给定点
     * @param point
     * @return
     */
    public boolean contains( CPoint point )
    {
        return contains( point.x, point.y );
    }

    /**
     * @todo 判断环形是否包含给定点
     * @param x
     * @param y
     * @return
     */
    public boolean contains( double x, double y )
    {
        int npoints = this.size();
        if ( (npoints <= 2) || (!super.getExtent().contains( new CPoint( x, y ) )) )
        {
            return false;
        }

        Object[] points = this.toArray();
        double xpoints[] = new double[npoints];
        double ypoints[] = new double[npoints];
        CPoint p;
        for ( int i = 0; i < npoints; i++ )
        {
            p = ( CPoint ) points[i];
            xpoints[i] = p.x;
            ypoints[i] = p.y;
        }

        //开始计算，算法取自java.awt.Polygon
        int hits = 0;

        double lastx = xpoints[npoints - 1];
        double lasty = ypoints[npoints - 1];
        double curx, cury;

        // Walk the edges of the polygon
        for ( int i = 0; i < npoints; lastx = curx, lasty = cury, i++ )
        {
            curx = xpoints[i];
            cury = ypoints[i];

            if ( cury == lasty )
            {
                continue;
            }

            double leftx;
            if ( curx < lastx )
            {
                if ( x >= lastx )
                {
                    continue;
                }
                leftx = curx;
            }
            else
            {
                if ( x >= curx )
                {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if ( cury < lasty )
            {
                if ( y < cury || y >= lasty )
                {
                    continue;
                }
                if ( x < leftx )
                {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            }
            else
            {
                if ( y < lasty || y >= cury )
                {
                    continue;
                }
                if ( x < leftx )
                {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if ( test1 < ( test2 / ( lasty - cury ) * ( lastx - curx ) ) )
            {
                hits++;
            }
        }

        return ( ( hits & 1 ) != 0 );
    }

    /**
     * @todo 判断Ring是否与extent相交
     * @param ext Extent
     * @return boolean true or false
     */
    public boolean intersects( Extent ext )
    {
        //如果两个extent不相交，返回false
        if ( !super.getExtent().intersected( ext ) )
        {
            return false;
        }

        double  minx = ext.getMinX();
        double  miny = ext.getMinY();
        double  maxx = ext.getMaxX();
        double  maxy = ext.getMaxY();

        //如果extent中包含的图形是点，则用contains()判断
        if ( minx == maxx && miny == maxy )
        {
            CPoint point = new CPoint( minx, miny );
            if ( this.contains( point ) )
            {
                return true;
            }
        }
        return this.intersects( minx, miny, maxx, maxy );
    }

    /**
     * @todo 判断Ring是否与参数中的四个点构成的矩形相交
     * @param minx
     * @param miny
     * @param maxx
     * @param maxy
     * @return
     */
    public boolean intersects( double minx, double miny, double maxx,double maxy )
    {
        //算法取自java.awt.Polygon
        Crossings cross = getCrossings( minx, miny, maxx, maxy );
        return ( cross == null || !cross.isEmpty() );
    }

    /**
     *
     * @param minx
     * @param miny
     * @param maxx
     * @param maxy
     * @return
     */
    private Crossings getCrossings( double minx, double miny,
                                    double maxx, double maxy )
    {
        Crossings cross = new Crossings( minx, miny, maxx, maxy );
        int size = this.getPoints().size();
        Object[] points = this.getPoints().toArray();
        double lastx = ( ( CPoint ) points[size - 1] ).getX();
        double lasty = ( ( CPoint ) points[size - 1] ).getY();
        double curx, cury;

        // Walk the edges of the polygon
        for ( int i = 0; i < size; i++ )
        {
            curx = ( ( CPoint ) points[i] ).getX();
            cury = ( ( CPoint ) points[i] ).getY();
            if ( cross.accumulateLine( lastx, lasty, curx, cury ) )
            {
                return null;
            }
            lastx = curx;
            lasty = cury;
        }
        return cross;
    }

    /**
     * 覆盖Object的toString()方法,把本对象字符串化.
     */
    public String toString()
    {
        CIterator it = collection.iterator();
        CPoint pt;
        String s = "Ring include " + collection.size() + " points:\n";
        while ( it.hasNext() )
        {
            pt = ( CPoint ) it.next();
            s += "x=" + pt.getX() + "\ty=" + pt.getY() + "\n";
        }
        return s;
    }

    public boolean equals( Object obj )
    {
        if ( obj instanceof Ring )
        {
            return ( ( Ring ) obj ).getPoints().equals( this.getPoints() );
        }
        else
            return false;
    }

    public boolean hitTest( double x, double y, double offset )
    {
        return this.contains( x, y );
    }

    /**
     * @todo 求单多边形的面积
     * @return
     */
    public double getAreaOfRing()
    {
        double douArea = 0.0;
        CPoint firstPoint = ( CPoint )this.getPoints().iterator().next();
        CPoint secondPoint = ( CPoint )this.getPoints().iterator().next();
        for ( int i = 0; i < this.getPoints().size() - 1; i++ )
        {
            douArea = douArea +
                ( firstPoint.getX() - secondPoint.getX() ) *
                ( firstPoint.getY() + secondPoint.getY() ) / 2;
            firstPoint = secondPoint;
            secondPoint = ( CPoint )this.getPoints().iterator().next();
        }
        return douArea;
    }

    /**
     * 求单多边形与相邻单多边形合并后的多边形
     * @param robj
     * @return
     */
    public CShape unites( Ring robj )
    {
        CShape r = null;
        if ( robj == null )
        {
            return this;
        }

        Object[] obj = this.toArray();
        CPoint[] p1 = new CPoint[obj.length];
        for ( int i = 0; i < obj.length; i++ )
        {
            p1[i] = ( CPoint ) obj[i];
        }

        int m1 = p1.length;
        Object[] obj2 = robj.toArray();
        CPoint[] p2 = new CPoint[obj2.length];
        for ( int i = 0; i < obj2.length; i++ )
        {
            p2[i] = ( CPoint ) obj2[i];
        }

        int m2 = p2.length;
        int direction = this.direction( p1, p2 );
        if ( direction == -2 || direction == 0 )
        {
            MultiPolygon pg = new MultiPolygon();
            r = pg;
            ( ( MultiPolygon ) r ).add( this );
            ( ( MultiPolygon ) r ).add( robj );
        }
        else
        {
            if ( direction == -1 )
            {
                CPoint[] p3 = new CPoint[m2];
                for ( int i = 0; i < m2; i++ )
                {
                    p3[i] = p2[m2 - ( i + 1 )];
                }
                p2 = p3;
            }
            Ring ring = new Ring();
            r = ring;
            CPoint p = null;
            Vector v = new Vector( 10, 10 );
            int pm1 = -1;
            for ( int i = 0; i < p1.length; i++ )
            {
                if ( this.isInsert( p1[i], p2 ) == -1 )
                {
                    v.addElement( p1[i] ); //jdk1.1
                    p = p1[i];
                    pm1 = i + 1;
                    break;
                }
            }
            this.addPoint( v, p1, p2, pm1, p );
            for ( int k = 0; k < v.size(); k++ )
            {
                ( ( Ring ) r ).add( ( CPoint ) v.elementAt( k ) ); //jdk1.1
            }
        }
        return r;
    }

    //釆用递归算法，将两个多边形的点加入到Vector中去。
    private void addPoint( Vector v, CPoint p1[], CPoint p2[], int m1, CPoint p )
    {
        int i = m1;
        for ( int j = 0; j < p1.length; j++ )
        {
            if ( i >= p1.length )
            {
                i = i - p1.length + 1;
            }
            if ( p1[i].equals( p ) )
            {
                v.addElement( p1[i] ); //jdk1.1s
                return;
            }
            int num = this.isInsert( p1[i], p2 );
            if ( num == -1 )
            {
                v.addElement( p1[i] );//jdk1.1
            }
            else
            {
                v.addElement( p1[i] ); //jdk1.1
                this.addPoint( v, p2, p1, num + 1, p );
                break;
            }
            i++;
        }
        return;
    }

    //判断一个点是否在多边形线上
    //-1代表不在，int代表多边形p2的点的下标
    private int isInsert( CPoint p, CPoint[] p2 )
    {
        int b = -1;
        for ( int i = 0; i < p2.length; i++ )
        {
            if ( p.equals( p2[i] ) )
            {
                b = i;
            }
        }
        return b;
    }

    //求两ring中点的排序方向，1代表同时相，－1代表反时相，
    //    0代表只有一个交点，没有同边，－2代表没有相交。
    private int direction( CPoint[] p1, CPoint[] p2 )
    {
        int dir = -2;
        int m1 = p1.length;
        int m2 = p2.length;
        boolean b = false;
        for ( int i = 0; i < m1; i++ )
        {
            for ( int j = 0; j < m2; j++ )
            {
                if ( p1[i].equals( p2[j] ) )
                {
                    if ( i == 0 )
                    {
                        if ( j == 0 )
                        {
                            if ( p1[i + 1].equals( p2[j + 1] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[m1 - 1].equals( p2[j + 1] ) ||
                                      p1[i + 1].equals( p2[m2 - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                        else if ( j == m2 - 1 )
                        {
                            if ( p1[i + 1].equals( p2[0] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[m1 - 1].equals( p2[0] ) ||
                                      p1[i + 1].equals( p2[j - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                        else
                        {
                            if ( p1[i + 1].equals( p2[j + 1] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[m1 - 1].equals( p2[j + 1] ) ||
                                      p1[i + 1].equals( p2[j - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                    }
                    else if ( i == m1 - 1 )
                    {
                        if ( j == 0 )
                        {
                            if ( p1[0].equals( p2[j + 1] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[i - 1].equals( p2[j + 1] ) ||
                                      p1[0].equals( p2[m2 - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                        else if ( j == m2 - 1 )
                        {
                            if ( p1[0].equals( p2[0] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[i - 1].equals( p2[0] ) ||
                                      p1[0].equals( p2[j - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                        else
                        {
                            if ( p1[0].equals( p2[j + 1] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[i - 1].equals( p2[j + 1] ) ||
                                      p1[0].equals( p2[j - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                    }
                    else
                    {
                        if ( j == 0 )
                        {
                            if ( p1[i + 1].equals( p2[j + 1] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[i - 1].equals( p2[j + 1] ) ||
                                      p1[i + 1].equals( p2[m2 - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                        else if ( j == m2 - 1 )
                        {
                            if ( p1[i + 1].equals( p2[0] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[i - 1].equals( p2[0] ) ||
                                      p1[i + 1].equals( p2[j - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                        else
                        {
                            if ( p1[i + 1].equals( p2[j + 1] ) )
                            { //方向相反，需要将数组倒置
                                dir = -1;
                                b = true;
                                break;
                            }
                            else if ( p1[i - 1].equals( p2[j + 1] ) ||
                                      p1[i + 1].equals( p2[j - 1] ) )
                            {
                                dir = 1;
                                b = true;
                                break;
                            }
                            else
                            {
                                dir = 0;
                            }
                        }
                    }
                }
            }
            if ( b )
            {
                break;
            }
        }
        return dir;
    }
}