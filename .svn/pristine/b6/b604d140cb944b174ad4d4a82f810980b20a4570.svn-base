package com.openmaps.tile.source;

import java.io.File;
import java.util.Vector;

import android.graphics.drawable.Drawable;

import com.openmaps.tile.MapTile;
import com.openmaps.tile.source.offline.MapFileInfo;
import com.openmaps.tile.source.offline.MapFileReader;
import com.openmaps.util.FileFilterFactory;

public class OfflineTileSource extends TileSource {
	protected Vector<MapFileInfo> mTileFileVector;
	protected String mOfflineMapDirectory;
	protected Boolean isExistsed = false;
	public OfflineTileSource(String name, int minZoom, int maxZoom, int tileSize,String mapDirectory) {
		super(name, minZoom, maxZoom, tileSize);
		this.mOfflineMapDirectory = mapDirectory;
		initMapFileInfo();
	}
	/**
	 * 初始化离线地图文件的信息
	 */
	protected void initMapFileInfo(){
		File mapFolder = new File(mOfflineMapDirectory);
		mTileFileVector = new Vector<MapFileInfo>();
		if(mapFolder.exists()&&mapFolder.isDirectory()){
			isExistsed = true;
			File[] mapFiles =  mapFolder.listFiles(FileFilterFactory.getFileTypeFileFilter("map"));
			for(int i=0;i<mapFiles.length;i++){
				MapFileInfo info = MapFileReader.readHead(mapFiles[i].getAbsolutePath());
				if(info!=null)
					mTileFileVector.add(info);
			}
		}
		return;
	}
	
	/**
	 * @return 离线地图文件夹是否存在
	 */
	public Boolean exists(){
		return isExistsed;
	}
	
	/**
	 * 从离线文件中获取瓦片
	 * @param tile
	 * @return
	 */
	public Drawable getDrawable(MapTile tile){
		return null;
	}

}
