package com.openmaps.handler;

import android.graphics.Canvas;
import android.view.ViewGroup;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.IEventDispatcher;
import com.openmaps.events.IEventListener;

public class Layer extends ViewGroup implements IEventDispatcher{
	private String mName;
	protected Map map;
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
	}
	
	public Layer(Map map,String name) {
		super(map.getContext());
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

}
