package com.openmaps.layer;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventListener;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.tile.MapTile;
import com.openmaps.tile.provider.MapTileProvider;

public class Grid extends Layer implements IEventListener{
	protected Vector<MapTile> mMapTiles = new Vector<MapTile>();
	protected int mColNum;
	protected int mRowNum;
	protected int tileWidth = 256;
	protected int tileHeight = 256;
	protected MapTileProvider mTileProvider;

	public void setTileProvider(MapTileProvider mTileProvider) {
		this.mTileProvider = mTileProvider;
	}

	public Grid(Map map, String name,MapTileProvider provider) {
		super(map, name);
		this.map = map;
		this.mTileProvider = provider;
		mTileProvider.addEventListener(EventUtil.TILE_UPDATED, this);
		tileWidth = (int) (this.map.getMapZoomRatio()*256);
		tileHeight = (int) (this.map.getMapZoomRatio()*256);
		
	
		this.map.addEventListener(EventUtil.MAP_SCALE_CHANGGE, this);
	}
	
	
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		drawTiles(canvas);
	}
	

	/**
	 * 缩放时设置瓦片的缩放比例
	 * @param scale
	 */
	protected void setTileScale(Canvas canvas,float scale){
		Matrix matrix = new Matrix(); 
		matrix.postScale(scale, scale);
		canvas.concat(matrix);
	}
	
	/**
	 * 绘制 mMapTiles 中的全部
	 */
	protected void drawTiles(Canvas canvas){
		for(int i=0;i < mMapTiles.size();i++){
			MapTile tile = mMapTiles.get(i);
			drawTile(tile, canvas);
		}
	}
	
	/**
	 * 单个绘制
	 * @param tile 
	 */
	protected void drawTile(MapTile tile,Canvas canvas){
		if(canvas==null) return;
		Bounds bounds = tile.getBounds();
		Point p0 = this.map.transformMapPointToPixel(new Location(
				bounds.left, bounds.top));
		Point p1 = this.map.transformMapPointToPixel(new Location(
				bounds.right, bounds.bottom));
		Drawable drawable = mTileProvider.getTile(tile);
		if(drawable==null)return;
		drawable.setBounds(new Rect(p0.x,p0.y,p1.x,p1.y));
		drawable.draw(canvas);
	}
	

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (r == 0)
			return;
	}


	protected void addTiles() {
		Bounds viewbounds = this.map.getExtentForResolution(this.map
				.getResolution());
		Bounds mapBounds = this.map.getBounds();
		Bounds bounds = Bounds.getIntersectBounds(viewbounds, mapBounds); // 瓦片的显示范围
		if(bounds ==null)return;
		double coef = tileWidth * this.map.getResolution();
		int level = this.map.getZoom();
		int minX = (int) Math.floor((bounds.left   - mapBounds.left)/ coef);
		int minY = (int) Math.floor((mapBounds.top - bounds.top)/ coef);
		int maxX = (int) Math.floor((bounds.right  - mapBounds.left)/ coef);
		int maxY = (int) Math.floor((mapBounds.top - bounds.bottom)/ coef);
		
		int mapMaxX = (int)((mapBounds.right-mapBounds.left)/coef);
		int mapMaxY = (int)((mapBounds.top - mapBounds.bottom)/ coef);
		maxX = maxX < mapMaxX?maxX+1:mapMaxX;
		maxY = maxY < mapMaxY?maxY+1:mapMaxY;
		for(int y = minY; y < maxY;y++){
			for(int x = minX;x < maxX;x++){
				MapTile tile = new MapTile(level,x,y);
				tile.computeBounds();
				mTileProvider.getTile(tile);
				this.mMapTiles.add(tile);
			}
		}
	}


	@Override
	public void reDraw() {
		this.invalidate();
		mTileProvider.reset();
		this.mMapTiles.clear();
		addTiles();
	}
	/**
	 * 移动图层
	 * 
	 * @param x
	 *            水平方向移动距离（屏幕坐标）
	 * @param y
	 *            垂直方向移动距离（屏幕坐标）
	 */
	@Override
	public void move(int x, int y) {

	}

	/**
	 * 从分辨率计算等级
	 * 
	 * @return
	 */
	public int getZoom() {
		int colNum = this.map.getMapWidth() / tileWidth;
		int zoom = (int) (Math.log(colNum) / Math.log(2));
		return zoom;
	}

	@Override
	public void respond(final BaseEvent event) {
		this.postInvalidate();
		if(event.getType()==EventUtil.MAP_SCALE_CHANGGE){
			//Log.e("地图比例变化",java.lang.String.valueOf(this.map.mMultiTouchScale));
			
		}
	}


}
