package com.openmaps.tile;

import java.util.Stack;

public class TilePool {
	private Stack<MapTile> pool;
	public TilePool(int count){
		this.pool = new Stack<MapTile>();
	}
	
	public void pushTile(MapTile tile){	
		pool.push(tile);
	}
	
	public MapTile popTile(){
		if(pool.isEmpty())return null;
		return pool.pop();
	}
}
