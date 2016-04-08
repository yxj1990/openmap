package com.soft2com.map.geom;

/**
 *
 * <p>Title: 地图几何形状边界类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author not attributable
 * @version 1.0
 */
public class CBox
{
    private double minx;
    private double miny;
    private double maxx;
    private double maxy;

    public CBox( double minx, double miny, double maxx, double maxy )
    {
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    public double getMaxx()
    {
        return maxx;
    }

    public void setMaxx( double maxx )
    {
        this.maxx = maxx;
    }

    public void setMaxy( double maxy )
    {
        this.maxy = maxy;
    }

    public double getMaxy()
    {
        return maxy;
    }

    public double getMinx()
    {
        return minx;
    }

    public void setMinx( double minx )
    {
        this.minx = minx;
    }

    public double getMiny()
    {
        return miny;
    }

    public void setMiny( double miny )
    {
        this.miny = miny;
    }

    public String toString()
    {
        return new StringBuffer().append( "minx=").append(minx).append(",miny=").append(miny).append(",maxx=").append(maxx).append(",maxy=").append(maxy ).toString();
    }
}