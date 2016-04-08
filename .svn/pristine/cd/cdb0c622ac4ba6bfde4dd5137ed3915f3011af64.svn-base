package com.openmaps.tile.source;

import java.util.Random;

import com.openmaps.tile.MapTile;

public class OnlineTileSource extends TileSource {
	protected String[] mBaseURLs;
	public OnlineTileSource(String name, int minZoom, int maxZoom, int tileSize,String[] baseURLs) {
		super(name, minZoom, maxZoom, tileSize);
		// TODO Auto-generated constructor stub
		this.mBaseURLs = baseURLs;
	}
	/**
	 * 获取瓦片的网络获取地址 
	 * @return
	 */
	public  String getURLString(MapTile tile){return "";}
	
	
	public String getBaseURL(){
		Random random = new Random();
		return 	mBaseURLs[random.nextInt(mBaseURLs.length)];
	}
	
}
