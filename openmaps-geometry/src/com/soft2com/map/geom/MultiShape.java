package com.soft2com.map.geom;

import com.soft2com.toolkit.Log;
import com.soft2com.util.CIterator;
import com.soft2com.util.VectorCollection;

//this class shouldn't be public.
abstract class MultiShape extends VectorCollection implements CShape
{
    protected Extent savedExtent = null;

    public MultiShape()
    {
    }

    //注意这个方法只会被调用一次，如果图形被改变之后，必须调用update方法以保证获取正确结果
    public void update()
    {
        savedExtent = null;
    }

    public void setExtent( Extent ex )
    {
        Log.debug( "MultiShaper.setExtent..." );
        savedExtent = ex;
    }

    public Extent getExtent()
    {
        if ( savedExtent == null )
        {
            CIterator it = super.iterator();
            Object obj = null;
            CShape shape = null;
            Extent ext = null;
            while ( it.hasNext() )
            {
                obj = it.next();
                if ( obj instanceof CShape )
                {
                    shape = ( CShape ) obj;
                    ext = shape.getExtent().getMaxExtent( ext );
                }
            }
            savedExtent = ext;
        }
        return savedExtent;
    }

    public boolean hitTest( CPoint point, double offset )
    {
        return hitTest( point.x, point.y, offset );
    }

    public abstract boolean hitTest( double x, double y, double offset );
}