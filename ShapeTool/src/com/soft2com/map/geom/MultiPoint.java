package com.soft2com.map.geom;

import com.soft2com.util.CIterator;
import com.soft2com.toolkit.Log;

/**
 *
 * <p>Title: </p>
 * <p>Description: MultiPoint可以包括许多Point，也可以包括MultiPoint</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class MultiPoint extends MultiShape
{

    public MultiPoint()
    {
    }

    public boolean hitTest( double x, double y, double offset )
    {
        return false;
    }

    public boolean equals( Object obj )
    {
        return false;
    }

    /**
     * 递归将MultiPoint对象字符串
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
            if ( shape instanceof CPoint )
            {
                i++;
                sb1.append( "\t" + ( ( CPoint ) shape ).toString() + "\n" );
            }
            else if ( shape instanceof MultiPoint )
            {
                j++;
                sb2.append( "\t" + ( ( MultiPoint ) shape ).toString() + "\n" );
            }
        }
        sb.append( "MultiPoint include " + i + " points ," + j +
                   " MultiPoint\n" );
        sb.append( sb1.toString() + "\n" );
        sb.append( sb2.toString() + "\n" );
        return sb.toString();
    }

    /**new added begin**/
    private CBox box = null;
    private int numPoints;
    private CPoint[] pts = null;

    /**
     *
     * @param box 边界
     * @param numPoints 点个数
     * @param pts 点数组
     */
    public MultiPoint( CBox box, int numPoints, CPoint[] pts )
    {
        this.box = box;
        this.numPoints = numPoints;
        this.pts = pts;
    }

    /**
     * @todo 取得边界
     * @return 边界
     */
    public CBox getBox()
    {
        return box;
    }

    /**
     * @todo 取得点个数
     * @return 点个数
     */
    public int getNumPoints()
    {
        return numPoints;
    }

    /**
     * @todo 取得点数组
     * @return 点数组
     */
    public CPoint[] getPts()
    {
        return pts;
    }

    public void setBox(CBox box)
    {
        this.box = box;
    }
    public void setNumPoints(int numPoints)
    {
        this.numPoints = numPoints;
    }
    public void setPts(CPoint[] pts)
    {
        this.pts = pts;
    }
/**new added end**/
}