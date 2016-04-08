package com.soft2com.map.geom;

import java.io.Serializable;

/**
 *
 * <p>Title: ������״����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public interface CShape extends Serializable
{
    /**
     * @todo ��ȡͼ�ε���Ӿ���
     * @return  ��Ӿ���
     */
    public Extent getExtent();

    /**
     * @todo ������ԣ��μ�{@link #hitTest(Point,double)}
     * @param x
     * @param y
     * @param offset
     * @return
     */
    public boolean hitTest( double x, double y, double offset );

    /**
     * @todo �������
     * <pre>
     * ���ݱ�����ͼ�ε����Ͳ�ͬ���в�ͬ��Ϊ
     * �㣺�ж��Ե�Ϊ���ģ���offsetΪ�뾶��Բ�Ƿ����������
     * �ߣ�����Ϊ���ģ���offsetΪ��չ�Ķ�����Ƿ����������
     * �棺���Ƿ������
     * </pre>
     * @param point
     * @param offset
     * @return
     */
    public boolean hitTest( CPoint point, double offset );

    public boolean equals( Object obj );
}
