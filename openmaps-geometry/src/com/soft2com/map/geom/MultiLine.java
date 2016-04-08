package com.soft2com.map.geom;

import com.soft2com.util.CCollection;
import com.soft2com.util.CIterator;
import com.soft2com.toolkit.Log;

/**
 * MultiLine可以包括许多Line，这些Line可以是不连续。
 */
public class MultiLine extends MultiShape
{
    public MultiLine()
    {}

    public MultiLine( CCollection collection )
    {
        this.addAll( collection );
    }

    public boolean hitTest( double x, double y, double offset )
    {
        CIterator it = this.iterator();
        Line l = null;
        Object obj = null;
        MultiLine ml = null;
        while ( it.hasNext() )
        {
            obj = it.next();
            if ( obj instanceof MultiLine )
            {
                ml = ( MultiLine ) obj;
                if ( ml.hitTest( x, y, offset ) )
                    return true;
            }
            else if ( obj instanceof Line )
            {
                l = ( Line ) obj;
                if ( l.hitTest( x, y, offset ) )
                    return true;
            }
        }
        return false;
    }

    /*
     * 递归算法，得到MultiLine包括的所有的点
     */
    /*
         private void getPoints(CIterator it,CCollection points)
         {
         Shape shape=null;
         while(it.hasNext())
         {
         shape=(Shape)it.next();
         if(shape instanceof Line)
         {
         points.addAll(((Line)shape).getPoints());
         }
         else
         if(shape instanceof MultiLine)
         {
         getAllPoint(((MultiLine)shape).iterator(),points);
         }
         }
         }
     */

    /**
     * 递归将MultiLine对象字符串
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb2 = new StringBuffer();
        int i = 0, j = 0;
        CIterator it = this.iterator();
        CShape shape = null;
        while ( it.hasNext() )
        {
            shape = ( CShape ) it.next();
            if ( shape instanceof Line )
            {
                i++;
                sb1.append( "\t" + ( ( Line ) shape ).toString() + "\n" );
            }
            else if ( shape instanceof MultiLine )
            {
                j++;
                sb2.append( "\t" + ( ( MultiLine ) shape ).toString() + "\n" );
            }
        }
        sb.append( "MultiLine include " + i + " lines ," + j + " MultiLine\n" );
        sb.append( sb1.toString() + "\n" );
        sb.append( sb2.toString() + "\n" );
        return sb.toString();
    }
}