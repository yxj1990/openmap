package com.soft2com.util;
/**
 * 遍历器
 */
public interface CIterator{
    /**
     * 遍历器中是否还有成员
     */
	public boolean hasNext();
    /**
     * 获取遍历器中的下一个成员
     */
	public Object next();
    /**
     * 把遍历器指针放在初始位置
     */
    public void reset();
}
/* END CLASS DEFINITION Iterator */
