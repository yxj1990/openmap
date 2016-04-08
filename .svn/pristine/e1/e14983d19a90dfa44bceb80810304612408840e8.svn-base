package com.openmaps.tile;

import java.util.Stack;

public class MapTilePool {
	private Stack<MapTile> pool;
	public MapTilePool(int count){
		this.pool = new Stack<MapTile>();
	}
	
	public void pushTile(MapTile tile){	
		pool.push(tile);
	}
	
	public MapTile popTile(){
		if(pool.isEmpty())return new MapTile(0, 0, 0);
		return pool.pop();
	}
}
