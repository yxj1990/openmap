package com.soft2com.map.geom;

import java.io.Serializable;

/**
 *
 * <p>Title: 几何形状基类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public interface CShape extends Serializable
{
    /**
     * @todo 获取图形的外接矩形
     * @return  外接矩形
     */
    public Extent getExtent();

    /**
     * @todo 点击测试，参见{@link #hitTest(Point,double)}
     * @param x
     * @param y
     * @param offset
     * @return
     */
    public boolean hitTest( double x, double y, double offset );

    /**
     * @todo 点击测试
     * <pre>
     * 根据被测试图形的类型不同，有不同行为
     * 点：判断以点为中心，以offset为半径的圆是否包含参数点
     * 线：以线为中心，以offset为扩展的多边形是否包含参数点
     * 面：面是否包含点
     * </pre>
     * @param point
     * @param offset
     * @return
     */
    public boolean hitTest( CPoint point, double offset );

    public boolean equals( Object obj );
}
