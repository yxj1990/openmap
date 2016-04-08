package com.soft2com.map.geom;

import android.graphics.Point;


/**
 * LineSegment类的awt版本，所用数据为整形
 * @see LineSegment
 */
public class LineSegmentPixel
{
    public Point startPoint=new Point(0,0);
    public Point endPoint=new Point(0,0);

    public LineSegmentPixel(Point startPoint,Point endPoint)
    {
      this.startPoint=startPoint;
      this.endPoint=endPoint;
    }

    public double length()
    {
        double dx=startPoint.x-endPoint.x;
        double dy=startPoint.y-endPoint.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
    public LineSegmentPixel getParallelLineSegment(int width)
    {
        Point newStartPoint=new Point(0,0);
        Point newEndPoint=new Point(0,0);

        double dx=endPoint.x-startPoint.x;
        double dy=endPoint.y-startPoint.y;
        double len=this.length();

        if(len>0)
        {
            dy*=width/len;

            newStartPoint.x=startPoint.x-(int)dy;
            newEndPoint.x=endPoint.x-(int)dy;

            dx*=width/len;
            newStartPoint.y=startPoint.y+(int)dx;
            newEndPoint.y=endPoint.y+(int)dx;
            return new LineSegmentPixel(newStartPoint,newEndPoint);
        }
        else
        {
          return this;
        }
    }

    public Point getStartPoint()
    {
        return startPoint;
    }

    public Point getEndPoint()
    {
        return endPoint;
    }

    public String toString()
    {
      StringBuffer sb=new StringBuffer();
      sb.append("LineSegmentPixel:\n");
      sb.append("\tStartPoint:x="+startPoint.x+"\ty="+startPoint.y+"\n");
      sb.append("\tEndPoint:x="+endPoint.x+"\ty="+endPoint.y+"\n");
      return sb.toString();
    }
}