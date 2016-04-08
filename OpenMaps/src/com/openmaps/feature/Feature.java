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
	protected final HashMap<String,String> mAttributes  = new HashMap<String,String>();   //����
	protected VectorLayer mLayer;
	protected String mLabelField;
	protected String mLabelText;
	public Boolean isSelected = false;//�Ƿ�ѡ��
	
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
	 * �������
	 * @param key  
	 * @param value
	 */
	public void putAttribute(String key,String value){
		this.mAttributes.put(key, value);
	}
	
	
	/**
	 * @param key
	 * @return  ����ֵ
	 */ 
	public String getAttribute(String key){
		return mAttributes.get(key);
	}
	
	/**
	 * @return  ���Թ�ϣ��
	 */
	public  HashMap<String ,String> getAttributes(){
		return this.mAttributes;
	}
	/**
	 *  ��ȡҪ�ص���ʽ
	 * @return
	 */
	public Style getStyle() {
		return mStyle;
	}
	/**
	 * ����Ҫ�ص���ʽ
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
	 * ���õ�ͼ����ʾ�������ֶ�����
	 * @param filedName
	 */
	public  void setLabelField(String filedName){
		this.mLabelField = filedName;
	}
	
	
	abstract public void setGeometry(Geometry geo) throws GeometryTypeMisMatchException;
	abstract public Geometry getGeometry();
	abstract public void reDraw(Canvas canvas);

	/**
	 * ��ȡҪ�ص�ע������
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
	 * ����Ҫ�ص�ע������
	 * @param text
	 */
	public void setLabelText(String text) {
		this.mLabelText = text;
	}
}
