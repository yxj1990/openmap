package com.soft2com.map.geom;

import com.soft2com.toolkit.Log;
import com.soft2com.util.CCollection;
import com.soft2com.util.CIterator;
import com.soft2com.util.VectorCollection;

/**
 *  AbstractShape类实现自Collection接口的方法主要用于构成图形的点的管理，
 *  它并没有实现hitTest方法，但可以实现getExtent方法
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public abstract class AbstractShape implements CShape, CCollection
{
    CCollection collection = new VectorCollection( 20, 10 );

    /**
     * @todo 图形内的点集合
     * @return
     */
    public CCollection getPoints()
    {
        return collection;
    }

    protected Extent savedExtent = null;

    //注意这个方法只会被调用一次，如果图形被改变之后，必须调用update方法以保证获取正确结果
    public void update()
    {
        savedExtent = null;
    }

    public void setExtent( Extent ex )
    {
        Log.debug( "AbstractShape.setExtent begin..." );
        savedExtent = ex;
    }

    /**
     * @todo 取得外接矩形
     * @return
     */
    public Extent getExtent()
    {
        if ( savedExtent == null )
        {
            CPoint pt = null;
            double minx = Double.MAX_VALUE;
            double miny = Double.MAX_VALUE;
            double maxx = -Double.MAX_VALUE;
            double maxy = -Double.MAX_VALUE;
            double tempX;
            double tempY;

            CIterator it = collection.iterator();
            while ( it.hasNext() )
            {
                pt = ( CPoint ) it.next();
                tempX = pt.x;
                tempY = pt.y;
                minx = minx > tempX ? tempX : minx;
                miny = miny > tempY ? tempY : miny;
                maxx = maxx < tempX ? tempX : maxx;
                maxy = maxy < tempY ? tempY : maxy;
            }
            savedExtent = new Extent( new CPoint( minx, miny ),
                                      new CPoint( maxx, maxy ) );
        }
        return savedExtent;
    }

    public void add( Object object )
    {
        collection.add( object );
        update();
    }

    public void remove( Object object )
    {
        collection.remove( object );
        update();
    }

    public CIterator iterator()
    {
        return collection.iterator();
    }

    public void sort()
    {
    //throw new java.lang.UnsupportedOperationException("Method sort() not yet implemented.");
    }

    public boolean contains( Object object )
    {
        return collection.contains( object );
    }

    public Object[] toArray()
    {
        return collection.toArray();
    }

    public Object[] toArray( Object[] object )
    {
        //throw new java.lang.UnsupportedOperationException("Method toArray() not yet implemented.");
        //throw new java.lang.Exception();
        return null;
    }

    public void clear()
    {
        collection.clear();
    }

    public void removeAll( CCollection collection )
    {
        collection.remove( collection );
        update();
    }

    public void addAll( CCollection collection )
    {
        collection.addAll( collection );
        update();
    }

    public int size()
    {
        return collection.size();
    }

    public boolean isEmpty()
    {
        return collection.isEmpty();
    }

    public boolean hitTest( CPoint point, double offset )
    {
        return hitTest( point.x, point.y, offset );
    }

    /**
     * 这个方法不应在此处定义，
     * 现在定义只是为了方便，
     * 实现具体的类时应当覆盖此方法
     */
    public abstract boolean hitTest( double x, double y, double offset );

    /**
     * @todo 未实现方法
     * @param obj
     * @return
     */
    public boolean equals( Object obj )
    {
        return false;
    }
}
