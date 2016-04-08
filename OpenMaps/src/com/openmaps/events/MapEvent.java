package com.openmaps.events;
import com.openmaps.Map;


public class MapEvent extends BaseEvent {
	private Map mMap;
	public MapEvent(Object source) {
		super(source);
	}
	
	public MapEvent(Map map,int type,Object source) {
		super(source);
		this.mMap = map;
		this.mType = type;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = EventUtil.MAPEVENTID;

}
