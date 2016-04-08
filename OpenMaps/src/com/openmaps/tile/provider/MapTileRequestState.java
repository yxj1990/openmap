package com.openmaps.tile.provider;

import com.openmaps.tile.MapTile;


public class MapTileRequestState {

	private final MapTile mMapTile;

	public MapTileRequestState(final MapTile mapTile) {
		mMapTile = mapTile;
	}

	public MapTile getMapTile() {
		return mMapTile;
	}
}
