package com.openmaps.tile.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.openmaps.tile.MapTile;
import com.openmaps.util.Util;

public class FileTileSource extends TileSource {
	protected String mTileFileDirectroy;

	/**
	 * 
	 * @param name
	 * @param minZoom
	 * @param maxZoom
	 * @param tileSize
	 * @param ditectory 瓦片文件夹地址
	 */
	public FileTileSource(String name, int minZoom, int maxZoom, int tileSize,String ditectory) {
		super(name, minZoom, maxZoom, tileSize);
		this.mTileFileDirectroy = ditectory;
	}
	
	/**
	 * @return 存储瓦片文件的文件夹地址
	 */
	public String getTileFileDirectroy() {
		return mTileFileDirectroy;
	}
	
	/**
	 * 设置存储瓦片文件的地址
	 * @param mTileFileDirectroy
	 */
	public void setTileFileDirectroy(String mTileFileDirectroy) {
		this.mTileFileDirectroy = mTileFileDirectroy;
	}
	
	/**
	 * @return 从文件中获取的Drawable，如果未找到返回null
	 */
	public Drawable getTileFromFileCache(MapTile tile){
		File f = new File(this.mTileFileDirectroy); 
		if(!f.exists())return null;
		f = null;
		f = new File(this.mTileFileDirectroy+"/"+tile.getZoomLevel()+"_"+tile.x+"_"+tile.y+".png");
		if(!f.exists())return null;
		try {
			FileInputStream fis = new FileInputStream(f);
			byte[] b = new byte[(int) f.length()];
			fis.read(b,0,b.length);
			Bitmap bitmap  = BitmapFactory.decodeByteArray(b, 0, b.length);
			Drawable drawable = new BitmapDrawable(Util.getResources(),bitmap);
			fis.close();
			return drawable;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError error){
			Log.e("get tile from file cache", "out of memory");
		}
		return null;
	}
}
