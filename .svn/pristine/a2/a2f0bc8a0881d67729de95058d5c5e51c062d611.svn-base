package com.openmaps.tile.source;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.openmaps.tile.MapTile;
import com.openmaps.tile.source.offline.MapFileInfo;
import com.openmaps.tile.source.offline.MapFileReader;
import com.openmaps.util.Util;

public class TDTOfflineTileSource extends OfflineTileSource {
	protected String mType;//区分注记还是底图
	public TDTOfflineTileSource(String name, int minZoom, int maxZoom,
			int tileSize, String mapDirectory,String type) {
		super(name, minZoom, maxZoom, tileSize, mapDirectory);
		this.mType = type;
	}
	
	
	/**
	 * 从离线文件中获取瓦片
	 * @param tile
	 * @return
	 */
	public Drawable getDrawable(MapTile tile){
		int length = mTileFileVector.size();
		MapFileInfo info = null;
		for(int i=0;i<length;i++){
			if(mTileFileVector.get(i).isContained(tile)){
				info = mTileFileVector.get(i);
				break;
			}
		}
		if(info!=null)
		{
			Bitmap bitmap = MapFileReader.getImage(tile, info, this.mType);
			if(bitmap==null) return null;
			Drawable drawable =  new BitmapDrawable(Util.getResources(), bitmap);
			return drawable;
		}
		
		return null;
	}

}
