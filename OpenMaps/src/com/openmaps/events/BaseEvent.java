package com.openmaps.events;

import java.util.EventObject;

/**
 * @author wusj
 */
public class BaseEvent extends EventObject {
	protected int mType;
	public BaseEvent(Object source) {
		super(source);
	}
	
	public BaseEvent(int type,Object source){
		super(source);
		this.mType = type;
	}
	
	public int getType(){
		return mType;
	}

	
	private static final long serialVersionUID = 927410143534937421L;

}
