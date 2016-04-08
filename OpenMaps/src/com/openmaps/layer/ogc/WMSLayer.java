package com.openmaps.layer.ogc;

import com.openmaps.Map;
import com.openmaps.layer.Grid;
import com.openmaps.tile.provider.MapTileProvider;

public class WMSLayer extends Grid {

	public WMSLayer(Map map, String name, MapTileProvider provider) {
		super(map, name, provider);
	}

	

}
