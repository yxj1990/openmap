package com.soft2com.map.geom;

/**
 *
 * <p>Title: </p>
 * <p>Description: ����һ����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */

public interface Area extends CShape
{
    /**
     * @todo �ж�һ�����Ƿ�����Area֮��
     * @param point ��
     * @return true or false
     */
    public boolean contains(CPoint point);

    /**
     * @todo �ж�һ�����Ƿ�����Area֮��
     * @param x �������
     * @param y ��������
     * @return true or false
     */
    public boolean contains(double x,double y);
}