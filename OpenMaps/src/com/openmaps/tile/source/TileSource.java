package com.openmaps.tile.source;

import java.io.InputStream;
import android.graphics.drawable.Drawable;
import com.openmaps.tile.MapTile;

public class TileSource implements ITileSource{
	public String mName;
	public int mMinZoom;
	public int mMaxZoom;
	public int mTileSize;
	public TileSource(String name,int minZoom,int maxZoom,int tileSize){
		this.mName = name;
		this.mMinZoom = minZoom;
		this.mMaxZoom = maxZoom;
		this.mTileSize = tileSize;
	}
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return mName;
	}

	@Override
	public String getTileRelativeFilenameString(MapTile aTile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable getDrawable(String aFilePath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Drawable getDrawable(InputStream aTileInputStream) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getMinimumZoomLevel() {
		// TODO Auto-generated method stub
		return mMinZoom;
	}

	@Override
	public int getMaximumZoomLevel() {
		// TODO Auto-generated method stub
		return mMaxZoom;
	}

	@Override
	public int getTileSizePixels() {
		// TODO Auto-generated method stub
		return mTileSize;
	}



	@Override
	public int ordinal() {
		// TODO Auto-generated method stub
		return 0;
	}
}
