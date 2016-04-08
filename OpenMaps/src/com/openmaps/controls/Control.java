package com.openmaps.controls;

import com.openmaps.Map;
import com.openmaps.handler.IHandler;

public class Control implements IHandler{
	protected Boolean isActivated = false;
	
	public Boolean isActivated() {
		return isActivated;
	}

	public Map mMap;
	public Control(Map map){
		this.mMap = map;
	}
	@Override
	public void activate() {
		// TODO Auto-generated method stub
		this.isActivated = true;
	}

	@Override
	public void disActivate() {
		// TODO Auto-generated method stub
		this.isActivated = false;
	}

}
