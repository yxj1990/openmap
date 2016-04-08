package com.openmaps.layer.ogc;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.IEventDispatcher;
import com.openmaps.events.IEventListener;

public class Layer extends SurfaceView implements IEventDispatcher,SurfaceHolder.Callback{
	private String mName;
	protected Map map;
	protected SurfaceHolder mHolder;
	protected Boolean isReadyForDraw = false;
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	
	public Layer(Map map) {
		super(map.getContext());
		this.map = map;
		mHolder = this.getHolder();
		mHolder.addCallback(this);
		mHolder.setFormat(PixelFormat.TRANSPARENT);
		
	}
	
	public Layer(Map map,String name) {
		this(map);
		this.map = map;
		this.mName = name;
	}
	

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
        super.onDraw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 重新绘制图层
	 */
	public void reDraw(){
		
	}
	
	/**
	 * 移动图层
	 * @param x 水平方向移动距离（屏幕坐标）
	 * @param y 垂直方向移动距离（屏幕坐标）
	 */
	public void move(int x,int y){
		
	}

	@Override
	public void addEventListener(int type, IEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeEventListener(int type, IEventListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disPatchEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
				
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		isReadyForDraw = true;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
