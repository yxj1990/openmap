package com.soft2com.util;

import java.io.Serializable;

/**
 * �ɱȽ϶���
 */
public interface Comparable extends Serializable
{
   /**
     * ����һ��Comparable����Ƚ�
     * @return > 0 ���������ڲ����� =0 ������ȣ�< 0 С�ڲ���
     */
    public int compareTo(Comparable comparableObject);
}

