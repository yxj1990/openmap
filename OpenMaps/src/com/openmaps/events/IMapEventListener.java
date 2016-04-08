package com.openmaps.events;

import java.util.EventListener;

public interface IMapEventListener extends EventListener {
	public void respond(BaseEvent event);
}
