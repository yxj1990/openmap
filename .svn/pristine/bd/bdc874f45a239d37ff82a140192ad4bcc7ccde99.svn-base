package com.openmaps.tile;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.openmaps.geometry.Bounds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageView;


public class Tile extends ImageView {
	private Handler handler;
	public int mX,mY,mLevel;
	private Bounds bounds;
	protected String urlFormat="http://t0.tianditu.com/DataServer?T=vec_c&x=%d&y=%d&l=%d";
	Thread thread;
	protected boolean  isStop = false;
	protected BitmapDrawable drawable;
	public Tile(Context context,int x,int y,int level) {
		super(context);
		this.mX = x;
		this.mY = y;
		this.mLevel = level;
		this.computeBounds();
		handler = new Handler();
	        //得到可用的图片
		new Thread(){
			  @Override
			  public void run(){
				  if(!isStop){
					 String url = String.format(urlFormat,mX,mY,mLevel);
				  	Bitmap bitmap = getHttpBitmap(url);
			      	handler.post(new MyRunable(bitmap));
			      }
				  return;
			  }
		  }.start();
	        
	}
	
	public class MyRunable implements Runnable {
		protected Bitmap mBitmap;
		public MyRunable(Bitmap data){
			this.mBitmap = data;
		}
		public void run() {
			if(!Tile.this.isStop){
				//Tile.this.setImageBitmap(mBitmap);
				 BitmapDrawable drawble = new BitmapDrawable(getResources(),MyRunable.this.mBitmap);
				 Tile.this.drawable = drawble;
				 Tile.this.invalidate();
			}
			return;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		if(this.drawable!=null){
			this.drawable.setBounds(new Rect(0, 0, 256, 256));
			this.drawable.draw(canvas);
		}
	}
	
	/**
	 * 获取网络瓦片地址
	 * @param bounds  瓦片范围
	 * @return
	 */
	public  String getUrl(Bounds bounds){
		
		return null;
	}
	
    public  Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 计算瓦片的范围
     */
    public void computeBounds(){
    		Double w = 360/Math.pow(2,mLevel);
    		double left = -180+mX*w;
    		double top  = 90-mY*w;
    		this.bounds = new Bounds(left, top-w, left+w, top);
    }
    
    /**
     * 返回瓦片的bounds
     * @return
     */
    public Bounds getBounds(){
    		return this.bounds;
    }
    
    /**
     * 停止获取瓦片
     */
    public void stop(){
    		this.isStop = true;
    }
}
