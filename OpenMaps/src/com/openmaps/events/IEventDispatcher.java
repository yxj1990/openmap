package com.openmaps.events;


public interface IEventDispatcher {

	/**
	 * ����¼�����
	 */
	public void addEventListener(int type,IEventListener listener);
	
	/**
	 * �Ƴ��¼�����
	 */
	public void removeEventListener(int type,IEventListener listener);
	
	/**
	 * �ɷ��¼�
	 * @param event
	 */
	public void disPatchEvent(BaseEvent event);
}
