package com.soft2com.map.geom;

/**
 *
 * <p>Title: 矩形区域</p>
 * <p>Description: 由左下角（minx,miny）和右上角(maxx,maxy)的两个点定义的一个矩形区域</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class Extent implements Area
{
    /**
     * 矩形左下角坐标
     */
    protected CPoint mleftdown = null;

    /**
     * 矩形右上角坐标
     */
    protected CPoint mrightup = null;

    /**
     * 构造函数，设置矩形范围
     * @param leftdown 矩形左下角坐标
     * @param rightup 矩形右上角坐标
     */
    public Extent( CPoint leftdown, CPoint rightup )
    {
        this.mleftdown = leftdown;
        this.mrightup = rightup;
    }

    /**
     * 构造函数，设置矩形范围
     * @param minx 矩形左下角横坐标
     * @param miny 矩形左下角纵坐标
     * @param maxx 矩形右上角横坐标
     * @param maxy 矩形右上角纵坐标
     */
    public Extent( double minx, double miny, double maxx, double maxy )
    {
        this( new CPoint( minx, miny ), new CPoint( maxx, maxy ) );
    }

    /**
     * 获取矩形的左下角坐标
     * @return 矩形的左下角坐标
     */
    public CPoint getLeftdown()
    {
        return this.mleftdown;
    }

    public void setLeftdown( CPoint the_mleftdown )
    {
        this.mleftdown = the_mleftdown;
    }

    /**
     * 获取矩形的右上角坐标
     * @return 矩形的右上角坐标
     */
    public CPoint getRightup()
    {
        return this.mrightup;
    }

    public void setRightup( CPoint the_mrightup )
    {
        this.mrightup = the_mrightup;
    }

    /**
     * 获取矩形左上角坐标
     * @return 矩形左上角坐标
     */
    public CPoint getLeftUp()
    {
        return new CPoint( this.mleftdown.x, this.mrightup.y );
    }

    /**
     * 获取矩形右上角坐标
     * @return 矩形右上角坐标
     */
    public CPoint getRightDown()
    {
        return new CPoint( this.mrightup.x, this.mleftdown.y );
    }

    public double getMinX()
    {
        return this.mleftdown.x;
    }

    public double getMaxX()
    {
        return this.mrightup.x;
    }

    public double getMinY()
    {
        return this.mleftdown.y;
    }

    public double getMaxY()
    {
        return this.mrightup.y;
    }

    /**
     * @todo 移动Extent,不改变Extent的大小,用于pan调用
     * @param translatex 横坐标移动
     * @param translatey 纵坐标移动
     * @return 移动后的extent
     */
    public Extent translate( double translatex, double translatey )
    {
        CPoint leftdown = new CPoint( this.getLeftdown().x - translatex,
                                    this.getLeftdown().y - translatey );
        CPoint rightup = new CPoint( this.getRightup().x - translatex,
                                   this.getRightup().y - translatey );
        return new Extent( leftdown, rightup );
    }

    /**
     * @todo 按一个比例因子放大或缩小Extent
     * @param rate 比例因子，当rate>1,放大Extent，当Rate<1缩小Extent
     * @return 改变后的extent
     */
    public Extent changeExtent( double rate )
    {
        //宽度变化
        double dw = this.getWidth() * ( rate - 1 ) / 2; //delta width

        //高度变化
        double dh = this.getHeight() * ( rate - 1 ) / 2; //delta height

        //移动后左下角坐标
        CPoint nld = new CPoint( this.mleftdown );
        nld.x -= dw;
        nld.y -= dh;

        //移动后右上角坐标
        CPoint nru = new CPoint( this.mrightup );
        nru.x += dw;
        nru.y += dh;
        return new Extent( nld, nru );
    }

    /**
     * @todo 把Extent对象转换为一个包含四个点的Ring
     * @return 环形
     */
    public Ring toRing()
    {
        CPoint leftdown = this.getLeftdown();
        CPoint rightup = this.getRightup();
        CPoint rightdown = new CPoint( rightup.x, leftdown.y );
        CPoint leftup = new CPoint( leftdown.x, rightup.y );

        Ring r = new Ring();
        r.add( leftdown );
        r.add( rightdown );
        r.add( rightup );
        r.add( leftup );
        return r;
    }

    /**
     * @todo 获取矩形的宽度
     * @return 矩形的宽度
     */
    public double getWidth()
    {
        return Math.abs( mrightup.x - mleftdown.x );
    }

    /**
     * 获取矩形的高度
     * @return 矩形的高度
     */
    public double getHeight()
    {
        return Math.abs( mrightup.y - mleftdown.y );
    }

    /**
     * 判断矩形中是否包含给定点
     * @param x 给定点横坐标
     * @param y 给定点纵坐标
     * @return true or false
     */
    public boolean contains( double x, double y )
    {
        return contains( new CPoint( x, y ) );
    }

    /**
     * 判断矩形中是否包含给定点
     * @param point 给定点
     * @return true or false
     */
    public boolean contains( CPoint point )
    {
        return ( mleftdown.x <= point.x && point.x <= mrightup.x )
            && ( mleftdown.y <= point.y && point.y <= mrightup.y );
    }

    /**
     *
     * @param x
     * @param y
     * @param offset
     * @return
     */
    public boolean hitTest( double x, double y, double offset )
    {
        return this.contains( x, y );
    }

    /**
     *
     * @param point
     * @param offset
     * @return
     */
    public boolean hitTest( CPoint point, double offset )
    {
        return hitTest( point.x, point.y, offset );
    }

    public Extent getExtent()
    {
        return this;
    }

    /**
     * 判断两个矩(环)形是否重合
     * @param obj 给定矩(环)形
     * @return true or false
     */
    public boolean equals( Object obj )
    {
        if ( obj instanceof Extent )
        {
            Extent ext = ( Extent ) obj;
            return ( ext.getLeftdown().equals( this.getLeftdown() )
                     && ext.getRightup().equals( this.getRightup() ) );
        }
        else if ( obj instanceof Ring )
        {
            return this.toRing().equals( ( Ring ) obj );
        }
        else
        {
            return false;
        }
    }

    public String toString()
    {
        return "Extent :(" + this.mleftdown.x + "," + this.mleftdown.y + "," +
            this.mrightup.x + "," + this.mrightup.y + ")";
    }

    /**
     * @todo 判断另一个矩形是否被本矩形覆盖
     * @param extent 给定矩形
     * @return true or false
     */
    public boolean covers( Extent extent )
    {
        return ( ( this.getMinX() <= extent.getMinX() )
                 && ( this.getMinY() <= extent.getMinY() )
                 && ( this.getMaxX() >= extent.getMaxX() )
                 && ( this.getMaxY() >= extent.getMaxY() ) );
    }

    /**
     * 返回两个矩形相交的交集
     * @param objTemp 给定矩形
     * @return 两个矩形的交集
     */
    public Extent getIntersection( Extent objTemp )
    {
        Extent extObj = null;
        if ( this.intersected( objTemp ) )
        {
            return null;
        }
        if ( this.covers( objTemp ) )
        {
            extObj = objTemp;
        }
        else if ( objTemp.covers( this ) )
        {
            extObj = this;
        }
        else
        {
            CPoint startPoint = null;
            CPoint endPoint = null;
            LineSegment lsOfSelf1 = new LineSegment( new CPoint( this.getMinX(),
                this.getMinY() ), new CPoint( this.getMaxX(), this.getMinY() ) ),
                lsOfSelf2 = new LineSegment( new CPoint( this.getMaxX(),
                this.getMinY() ), new CPoint( this.getMaxX(), this.getMaxY() ) ),
                lsOfSelf3 = new LineSegment( new CPoint( this.getMaxX(),
                this.getMaxY() ), new CPoint( this.getMinX(), this.getMaxY() ) ),
                lsOfSelf4 = new LineSegment( new CPoint( this.getMinX(),
                this.getMaxY() ), new CPoint( this.getMinX(), this.getMinY() ) );


            LineSegment lsOfObj1 = new LineSegment( new CPoint( objTemp.getMinX(),
                this.getMinY() ), new CPoint( this.getMaxX(), this.getMinY() ) ),
                lsOfObj2 = new LineSegment( new CPoint( objTemp.getMaxX(),
                objTemp.getMinY() ),
                                            new CPoint( objTemp.getMaxX(),
                objTemp.getMaxY() ) ),
                lsOfObj3 = new LineSegment( new CPoint( objTemp.getMaxX(),
                objTemp.getMaxY() ),
                                            new CPoint( objTemp.getMinX(),
                objTemp.getMaxY() ) ),
                lsOfObj4 = new LineSegment( new CPoint( objTemp.getMinX(),
                objTemp.getMaxY() ),
                                            new CPoint( objTemp.getMinX(),
                objTemp.getMinY() ) );
        }
        return extObj;
    }

    /**
     * 返回两个矩形相交后自己的余集
     * @param objTemp
     * @return
     */
    public Extent[] getDivisionOfSelf( Extent objTemp )
    {
        return null;
    }

    /**
     * 返回矩形被自包含的矩形分割后所得的矩形集
     * @param objTemp
     * @return
     */
    private Extent[] getDivision( Extent objTemp )
    {
        java.util.Vector vecExtObj = new java.util.Vector( 10, 10 );

        Extent extObj[] = new Extent[vecExtObj.size()];
        return extObj;
    }

    /**
     * 判断另一个矩形是否与本矩形相交
     * @param extent1 给定矩形
     * @return true or false
     */
    public boolean intersected( Extent extent1 )
    {

        boolean notIntersected =
            this.getMaxX() < extent1.getMinX() //left
            || this.getMinX() > extent1.getMaxX() //right
            || this.getMaxY() < extent1.getMinY()
            || this.getMinY() > extent1.getMaxY();
        return!notIntersected || this.covers( extent1 ) || extent1.covers( this );
    }

    /**
     *
     * @param ext
     * @return
     */
    public Extent getMaxExtent( Extent ext )
    {
        if ( ext == null )
        {
            return this;

            //complicated  code
//            return new Extent(
//                this.getMinX()
//                , this.getMinY()
//                , this.getMaxX()
//                , this.getMaxY() );
        }
        else
        {
            return new Extent(
                Math.min( this.getMinX(), ext.getMinX() )
                , Math.min( this.getMinY(), ext.getMinY() )
                , Math.max( this.getMaxX(), ext.getMaxX() )
                , Math.max( this.getMaxY(), ext.getMaxY() )
                );
        }
    }

    /**
     * 取得矩形的面积
     * @return 矩形的面积
     */
    public double getArea()
    {
        return this.getWidth() * this.getHeight();
    }

    /**
     *
     * @return
     */
    public Object clone()
    {
        return new Extent( this.getMinX(), this.getMinY(), this.getMaxX(),
                           this.getMaxY() );
    }
}