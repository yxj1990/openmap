package com.soft2com.map.geom;

import com.soft2com.util.CCollection;
import com.soft2com.util.CIterator;
import com.soft2com.util.VectorCollection;


/**
 *
 * <p>Title: 可以包含洞的闭合多边形</p>
 * <p>Description: 本类从AbstractShape继承来的方法主要用于管理多边形的外廓</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class CPolygon extends Ring
{
    /*new added begin*/
    CBox box = null;
    int numParts;
    int numPoints;
    int[] partIndex;
    CPoint[] pts = null;

    int recordNum = 0;
    int contentLength = 0;
    /*new added end*/

    /**
     * 多边形内包含的洞
     */
    protected CCollection holes = new VectorCollection();

    /**
     * 构建并且初始化一个Polygon
     * @param xpoints 包含X座标的数组
     * @param ypoints 包含Y座标的数组
     * @param npoints Polygon包含点的数量
     */
    public CPolygon( int[] xpoints, int[] ypoints, int npoints )
    {
        if ( npoints < 0 )
        {
            throw new NegativeArraySizeException();
        }

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
     * 构建并且初始化一个Polygon
     * @param collection collection 点的集合
     */
    public CPolygon( CCollection collection )
    {
        this.collection.addAll( collection );
    }

    public CPolygon()
    {
        //super();
    }

    /** new added begin*/
    /**
     * @param box
     * @param numParts
     * @param numPoints
     * @param partIndex
     * @param pts
     */
    public CPolygon( CBox box, int numParts, int numPoints, int[] partIndex,
                    CPoint[] pts )
    {
        this.box = box;
        this.numParts = numParts;
        this.numPoints = numPoints;
        this.partIndex = partIndex;
        this.pts = pts;
    }

    public CBox getBox()
    {
        return box;
    }

    public int getContentLength()
    {
        return contentLength;
    }

    public int getNumParts()
    {
        return numParts;
    }

    public int getNumPoints()
    {
        return numPoints;
    }

    public int[] getPartIndex()
    {
        return partIndex;
    }

    public CPoint[] getPts()
    {
        return pts;
    }

    public int getRecordNum()
    {
        return recordNum;
    }

    public void setBox( CBox box )
    {
        this.box = box;
    }

    public void setContentLength( int contentLength )
    {
        this.contentLength = contentLength;
    }

    public void setNumParts( int numParts )
    {
        this.numParts = numParts;
    }

    public void setNumPoints( int numPoints )
    {
        this.numPoints = numPoints;
    }

    public void setPartIndex( int[] partIndex )
    {
        this.partIndex = partIndex;
    }

    public void setPts( CPoint[] pts )
    {
        this.pts = pts;
    }

    public void setRecordNum( int recordNum )
    {
        this.recordNum = recordNum;
    }

    /**
     * 取得从当前索引开始的点的集合
     * @param index 当前部分的开始点索引
     * @return
     */
    public CPoint[] getPoints( int index )
    {
        int partIndex = this.partIndex[index];
        int nextPartPointIndex = 0;
        if( index == this.partIndex.length-1 )
        {
            nextPartPointIndex = this.numPoints;
        }
        else
        {
            nextPartPointIndex = this.partIndex[index+1];
        }

        int count = nextPartPointIndex - this.partIndex[index]; //点个数
        CPoint[] points = new CPoint[count];
        int j = 0;
        for( int i = this.partIndex[index]; i < nextPartPointIndex;i++ )
        {
            points[j++] = this.pts[i];
        }
        return points;
    }

    /** new added end*/

    /**
     * 获取多边形所包含的洞
     * @return
     */
    public CCollection getHoles()
    {
        return holes;
    }

    /**
     * 向多边形中添加洞
     * @param hole
     */
    public void addHole( Ring hole )
    {
        holes.add( hole );
    }

    /**
     * 移除洞
     */
    public void removeHole( Ring hole )
    {
        holes.remove( hole );
    }

    /**
     * 删除所有的Holes
     */
    public void removeAllHoles()
    {
        holes.clear();
    }

    public boolean contains( CPoint point )
    {
        return contains( point.x, point.y );
    }

    public boolean contains( double x, double y )
    {
        Ring ring;
        ring = new Ring( this.getPoints() );
        if ( ring.contains( x, y ) )
        {
            CIterator it = holes.iterator();
            while ( it.hasNext() )
            {
                ring = ( Ring ) it.next();
                if ( ring.contains( x, y ) )
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public String toString()
    {
        CIterator it = collection.iterator();
        CPoint pt;
        String s = "Polygon include " + collection.size() + " points:\n";
        while ( it.hasNext() )
        {
            pt = ( CPoint ) it.next();
            s += "\tx=" + pt.getX() + "\ty=" + pt.getY() + "\n";
        }
        if ( holes.size() > 0 )
        {
            s += "include " + holes.size() + " holes:\n";
            it = holes.iterator();
            Ring ring;
            int i = 1;
            while ( it.hasNext() )
            {
                s += "\tHoles " + i + "\n:";
                ring = ( Ring ) it.next();
                CIterator it2 = ( ring.getPoints() ).iterator();
                while ( it2.hasNext() )
                {
                    pt = ( CPoint ) it2.next();
                    s += "x=" + pt.getX() + "\ty=" + pt.getY() + "\n";
                }
                i++;
            }
        }
        else
        {
            s += "\tinclude 0 holes:\n";
        }
        return s;
    }

    public boolean equals( Object obj )
    {
        boolean bequals = false;
        if ( obj instanceof CPolygon )
        {
            CPolygon plg = (CPolygon ) obj;
            bequals = this.getPoints() == plg.getPoints()
                && this.getHoles() == plg.getHoles();
        }
        else if ( obj instanceof Ring )
        {
            if ( this.getHoles().size() == 0 )
            {
                bequals = this.getPoints() == ( ( Ring ) obj ).getPoints();
            }
        }
        return bequals;
    }
}