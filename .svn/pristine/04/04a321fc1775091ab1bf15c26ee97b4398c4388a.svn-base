package com.openmaps.feature;

import java.util.HashMap;

import android.graphics.Canvas;

import com.openmaps.Map;
import com.openmaps.exceptions.GeometryTypeMisMatchException;
import com.openmaps.geometry.Geometry;
import com.openmaps.layer.VectorLayer;
import com.openmaps.style.Style;

public abstract class Feature  {

	protected Geometry mGeometry;
	protected Style mStyle;
	protected final HashMap<String,String> mAttributes  = new HashMap<String,String>();   //属性
	protected VectorLayer mLayer;
	protected String mLabelField;
	protected String mLabelText;
	public Boolean isSelected = false;//是否选中
	
	public VectorLayer getLayer() {
		return mLayer;
	}
	
	public void setLayer(VectorLayer mLayer) {
		this.mLayer = mLayer;
	}

	
	public Feature(Map map){
		this.mMap = map;
	}
	
	/**
	 * 添加属性
	 * @param key  
	 * @param value
	 */
	public void putAttribute(String key,String value){
		this.mAttributes.put(key, value);
	}
	
	
	/**
	 * @param key
	 * @return  属性值
	 */ 
	public String getAttribute(String key){
		return mAttributes.get(key);
	}
	
	/**
	 * @return  属性哈希表
	 */
	public  HashMap<String ,String> getAttributes(){
		return this.mAttributes;
	}
	/**
	 *  获取要素的样式
	 * @return
	 */
	public Style getStyle() {
		return mStyle;
	}
	/**
	 * 设置要素的样式
	 * @param mStyle
	 */
	public void setStyle(Style mStyle) {
		this.mStyle = mStyle;
	}

	protected Map mMap;
	public Map getMap() {
		return mMap;
	}

	public void setMap(Map mMap) {
		this.mMap = mMap;
	}

	protected void onDraw(Canvas canvas){
		
	}
	
	/**
	 * 设置地图上显示的属性字段名称
	 * @param filedName
	 */
	public  void setLabelField(String filedName){
		this.mLabelField = filedName;
	}
	
	
	abstract public void setGeometry(Geometry geo) throws GeometryTypeMisMatchException;
	abstract public Geometry getGeometry();
	abstract public void reDraw(Canvas canvas);

	/**
	 * 获取要素的注记文字
	 * @return
	 */
	public String getLabelText() {
		if(this.mLabelText!=null)
			return mLabelText;
		if(this.mLabelField!=null)
			return this.mAttributes.get(mLabelField);
		return null;
	}

	/**
	 * 设置要素的注记文字
	 * @param text
	 */
	public void setLabelText(String text) {
		this.mLabelText = text;
	}
}
