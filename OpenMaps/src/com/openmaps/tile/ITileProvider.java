package com.openmaps.tile;

import android.graphics.drawable.Drawable;


public interface ITileProvider {
	public Drawable getTile(MapTile tile);
	
	public void setUseNoDataTile(Boolean value);
}
