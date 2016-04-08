package com.soft2com.util;

import java.io.Serializable;

/**
 * 集合工具类
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
     * 把一个对象加入集合中
     */
    void add( Object object );

    /**
     * 从集合中移除指定对象
     */
    void remove( Object object );

    /**
     * 返回一个反复器以遍历集合中的成员
     */
    CIterator iterator();

    /**
     * 由于数据大部分是接近有序的，故采用二分冒泡排序法（BinaryBubbleSort）
     */
    void sort();

    /**
     * 判断集合中是否包含指定对象
     */
    boolean contains( Object object );

    /**
     * 把集合转化为一个对象数组
     */
    Object[] toArray();

    /**
     * i don't what exacty this method mean,but i have some other use
     * so i defined here
     */
    Object[] toArray( Object[] object );

    /**
     * 清除集合中所有对象
     */
    void clear();

    /**
     * 清除参数集合与本集合的交集中的对象
     */
    void removeAll( CCollection collection );

    /**
     * 把参数集合中的所有成员加入本集合中
     */
    void addAll( CCollection collection );

    /**
     * 获取集合中成员数目
     */
    int size();

    /**
     * 判断集合是否为空
     */
    boolean isEmpty();

    int hashCode();

    public boolean equals( Object obj );
}
