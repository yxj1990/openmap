package com.openmaps.tile.provider;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import android.util.SparseArray;

import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventDispatcher;
import com.openmaps.events.IEventListener;
import com.openmaps.tile.ITileProvider;
import com.openmaps.tile.MapTile;
import com.openmaps.tile.source.FileTileSource;
import com.openmaps.tile.source.OfflineTileSource;
import com.openmaps.tile.source.OnlineTileSource;
import com.openmaps.tile.source.TileSource;
import com.openmaps.util.Util;

public class MapTileProvider implements ITileProvider,IEventDispatcher,IEventListener {
	protected Vector<TileSource> mTileSources = new Vector<TileSource>();
	/**缓存图片*/
	protected LruCache<MapTile, Drawable> mTileCache;
	/**记录正在从网络获取的图片*/
	protected HashMap<MapTile, MapTileRequestState> mWorking = new HashMap<MapTile, MapTileRequestState>();
	/**最大瓦片缓存数量*/
	protected int mMaxCacheSize = 180;  //根据实际情况设定 。Android 给Bitmap分配8M内存
	

	/**记录当前的下载任务*/
	protected HashMap<MapTile, Future<?>> mTasks = new HashMap<MapTile, Future<?>>();
	protected ExecutorService executor = Executors.newFixedThreadPool(32); 
	
	/**事件监听者*/
	protected SparseArray<Vector<IEventListener>> mEventListeners = new SparseArray<Vector<IEventListener>>();
	
	/**瓦片资源位置*/
	protected OnlineTileSource mOnlineTileSource = null;
	
	protected FileTileSource mFileTileSource = null;
	protected OfflineTileSource mOfflineTileSource = null;
	
	public void addTileSource(TileSource source){
		this.mTileSources.add(source);
	}
	

	public MapTileProvider(){
		mTileCache = new LruCache<MapTile, Drawable>(mMaxCacheSize);
	}
	
	public MapTileProvider(int maxCacheSize){
		this.mMaxCacheSize = maxCacheSize;
		mTileCache = new LruCache<MapTile, Drawable>(maxCacheSize);
	}
	
	public int getMaxCacheSize() {
		return mMaxCacheSize;
	}


	public void setMaxCacheSize(int mMaxCacheSize) {
		this.mMaxCacheSize = mMaxCacheSize;
	}
	
	/**
	 * 根据瓦片的x，y, zoom获取Drawable
	 * <br/>获取顺序 内存>文件系统>离线地图包>网络
	 * @param tile 要获取数据的瓦片
	 */
	@Override
	public Drawable getTile(final MapTile tile) {
		Drawable drawable =  mTileCache.get(tile);
		//如果缓冲中有该图片
		if(drawable!=null){ 
			return drawable;
		}
		//文件缓存中有该瓦片
		if(mFileTileSource!=null){
			File tileFile = new File(mFileTileSource.getTileFileDirectroy());
			if(!tileFile.exists()){
				tileFile.mkdirs();
			}else{
				drawable = mFileTileSource.getTileFromFileCache(tile);
				if(drawable!=null) {
	//				Log.e("openmaps", "get drawable from map file");
					mTileCache.put(tile, drawable);
					return drawable;
				}
			}
		}
		
		// 从离线地图中读取
		if(mOfflineTileSource!=null){
			if(mOfflineTileSource.exists()){
				drawable = mOfflineTileSource.getDrawable(tile);
				if(drawable!=null) {
					mTileCache.put(tile,drawable);
//					Log.e("openmaps", "get drawable from map file");
					return drawable;
				}
			}
		}
		
		if(mOnlineTileSource!=null){
			if(mWorking.containsKey(tile)) { //如果已经开始获取
				return null;
			}else{
				synchronized (mWorking) {
					MapTileRequestState state = new MapTileRequestState(tile);
					mWorking.put(tile, state);
				}
				loadTileAsync(tile);
			}
		}
		
		return null;
	}
	
//	protected Drawable getTransparentDrawable(){
//		//Bitmap bitmap = BitmapFactory.
//	}

	@Override
	public void setUseNoDataTile(Boolean value) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	 * @param tile
	 * @param byteStream
	 */
	protected void saveTileCache(MapTile tile,final byte[] bytes){
		File f = new File(mFileTileSource.getTileFileDirectroy()+"/"); 
		if(!f.exists()){
			f.mkdirs();
		}
		f = null;
		f = new File(mFileTileSource.getTileFileDirectroy()+"/"+tile.getZoomLevel()+"_"+tile.x+"_"+tile.y+".png");
		if(f.exists())return;
		try {
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 开辟线程异步获取瓦片
	 * @param tile
	 */
	public void loadTileAsync(MapTile tile){
		Future<?> f  = executor.submit(new DownloadImageTask(tile));
		this.mTasks.put(tile, f);
	}
	
	/**
	 * 获取图片
	 * @param tile
	 * @return 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	protected void downloadImage(MapTile tile){
//		Log.e("openmaps", "start sownload tile" + tile.getZoomLevel() +"_" +tile.x+"_"+tile.y);
		String url = mOnlineTileSource.getURLString(tile);
		final HttpClient client = new DefaultHttpClient();
		final HttpUriRequest head = new HttpGet(url);
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		InputStream in = null;
		OutputStream out = null;
		try {
			final HttpResponse response = client.execute(head);
			final org.apache.http.StatusLine line = response.getStatusLine();
			if (line.getStatusCode() != 200) {
				return;
			}
			final HttpEntity entity = response.getEntity();
			if (entity == null) {
				return;
			}
			in = entity.getContent();
			//Bitmap bitmap = BitmapFactory.decodeStream(in);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, StreamUtils.IO_BUFFER_SIZE);
			StreamUtils.copy(in, out);
			out.flush();
			final byte[] data = dataStream.toByteArray();
			final ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
			Drawable drawable = new BitmapDrawable(Util.getResources(),byteStream);
			this.mTileCache.put(tile, drawable);//存入缓存
			if(mTileCache.size()>mMaxCacheSize) 
				removeOneThirdCaches();
			this.mWorking.remove(tile); //从当前的下载任务队列中移除
			this.disPatchEvent(new BaseEvent(EventUtil.TILE_UPDATED, tile));
			//在本地文件系统中缓存图片
			saveTileCache(tile,data);
			return;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		//	Log.e("openmaps", "download tile" + tile.getZoomLevel() +"_" +tile.x+"_"+tile.y
		//			+ e.getMessage());
		} catch (IOException e) {
		//	Log.e("openmaps", "download tile" + tile.getZoomLevel() +"_" +tile.x+"_"+tile.y
		//			+ e.getMessage());
			e.printStackTrace();
		} finally{
			this.mTasks.remove(tile); //下载成功和失败都要将任务移除
			StreamUtils.closeStream(in);
			StreamUtils.closeStream(out);
		}
		return;      
	}
	
	/**下载图片线程*/
	class DownloadImageTask implements Runnable {
		private MapTile mTile;
		public DownloadImageTask(MapTile tile){
			this.mTile = tile;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			downloadImage(this.mTile);
		}
	}
	
	/**
	 * 移除不属于当前显示区域的缓存
	 */
	protected void removeOneThirdCaches(){
//		Iterator<MapTile> it= mTileCache.keySet().iterator();
//		int count = 0;
//		MapTile [] deleteTiles = new MapTile[mMaxCacheSize/3];
//		synchronized (mTileCache) {
//			while(it.hasNext()){
//				MapTile key  = it.next();
//				deleteTiles[count] = key;
//				count ++;
//				if(count > mMaxCacheSize/3-1) {
//					it = null;
//					break;
//				}
//			}
//			
//			for(int i=0;i<deleteTiles.length;i++){
//				BitmapDrawable d = (BitmapDrawable)mTileCache.remove(deleteTiles[i]);
//				d.getBitmap().recycle();
//			}
//		}
//		
//		Log.e("clear cache","内存清理");
//		System.gc();
	}
	
	 
	
	/**
	 * 清除当前的下载任务,为新任务腾出线程
	 */
	public void reset(){
		synchronized (mTasks) {
			Vector<MapTile> cancelTasks = new Vector<MapTile>() ;
			Iterator<MapTile> it = mTasks.keySet().iterator();
			while(it.hasNext()){
				MapTile key = it.next();
				cancelTasks.add(key);
				mTasks.get(key).cancel(true);
			}
			for(int i=0;i<cancelTasks.size();i++){
				mTasks.get(cancelTasks.get(i)).cancel(true);
			}
			mTasks.clear();
		}
		
		//int threadCount = ((ThreadPoolExecutor)executor).getActiveCount();
	}
	
	/**
	 * 清除缓存
	 */
	public void removeCaches(){
		synchronized (mTileCache) {
			this.mTileCache.evictAll();
		}
	}
	
	@Override
	public void addEventListener(int type, IEventListener listener) {
		Vector<IEventListener> vector = this.mEventListeners.get(type);
		if(vector==null)
			vector = new Vector<IEventListener>();
		vector.add(listener);
		this.mEventListeners.put(type, vector);
	}

	@Override
	public void removeEventListener(int type, IEventListener listener) {
		Vector<IEventListener> vector = this.mEventListeners.get(type);
		if(vector==null)
			return;
		vector.remove(listener);
		
	}

	@Override
	public void disPatchEvent(BaseEvent event) {
		Vector<IEventListener> vector = this.mEventListeners.get(event.getType());
		if(vector==null)
			return;
		for(int i=0;i<vector.size();i++){
			vector.get(i).respond(event);
		}
	}

	@Override
	public void respond(BaseEvent event) {
		
	};
	
	public void setOnlineTileSource(OnlineTileSource mOnlineTileSource) {
		this.mOnlineTileSource = mOnlineTileSource;
	}

	public void setFileTileSource(FileTileSource mFileTileSource) {
		this.mFileTileSource = mFileTileSource;
	}

	public void setOfflineTileSource(OfflineTileSource mOfflineTileSource) {
		this.mOfflineTileSource = mOfflineTileSource;
	}
}
