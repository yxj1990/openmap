package com.openmaps.events;


public interface IEventDispatcher {

	/**
	 * 添加事件监听
	 */
	public void addEventListener(int type,IEventListener listener);
	
	/**
	 * 移除事件监听
	 */
	public void removeEventListener(int type,IEventListener listener);
	
	/**
	 * 派发事件
	 * @param event
	 */
	public void disPatchEvent(BaseEvent event);
}
