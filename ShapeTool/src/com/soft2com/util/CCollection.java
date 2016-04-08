package com.soft2com.util;

import java.io.Serializable;

/**
 * ���Ϲ�����
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public interface CCollection extends Serializable
{
    /**
     * ��һ��������뼯����
     */
    void add( Object object );

    /**
     * �Ӽ������Ƴ�ָ������
     */
    void remove( Object object );

    /**
     * ����һ���������Ա��������еĳ�Ա
     */
    CIterator iterator();

    /**
     * �������ݴ󲿷��ǽӽ�����ģ��ʲ��ö���ð�����򷨣�BinaryBubbleSort��
     */
    void sort();

    /**
     * �жϼ������Ƿ����ָ������
     */
    boolean contains( Object object );

    /**
     * �Ѽ���ת��Ϊһ����������
     */
    Object[] toArray();

    /**
     * i don't what exacty this method mean,but i have some other use
     * so i defined here
     */
    Object[] toArray( Object[] object );

    /**
     * ������������ж���
     */
    void clear();

    /**
     * ������������뱾���ϵĽ����еĶ���
     */
    void removeAll( CCollection collection );

    /**
     * �Ѳ��������е����г�Ա���뱾������
     */
    void addAll( CCollection collection );

    /**
     * ��ȡ�����г�Ա��Ŀ
     */
    int size();

    /**
     * �жϼ����Ƿ�Ϊ��
     */
    boolean isEmpty();

    int hashCode();

    public boolean equals( Object obj );
}
