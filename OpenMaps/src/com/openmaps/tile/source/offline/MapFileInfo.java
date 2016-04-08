package com.openmaps.tile.source.offline;

import java.util.Vector;

import com.openmaps.tile.MapTile;

public class MapFileInfo extends Object{
	public String mapFilePath;
	public int minZoomLevel;
	public int maxZoomLevel;
	public Vector<LevelInfo> levels; //各级瓦片信息
	public String mapType; //地图类型 针对天地图
	
	/**
	 * 判断是否包含该瓦片
	 * @param tile
	 * @return
	 */
	public  Boolean isContained(MapTile tile){
		if(levels==null) return false;
		if(tile.getZoomLevel()>maxZoomLevel||tile.getZoomLevel()<minZoomLevel) return false;
		LevelInfo levelInfo = levels.elementAt(tile.getZoomLevel()-1);
		if(tile.x > levelInfo.maxX || tile.x < levelInfo.minX) return false;
		if(tile.y > levelInfo.maxY || tile.y < levelInfo.minY) return false;
		return true;
	}
	
}
