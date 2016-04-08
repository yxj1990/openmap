package com.openmaps.layer.ogc;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;

import com.openmaps.Map;
import com.openmaps.feature.Feature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.Geometry;
import com.openmaps.style.Style;

/**
 * ʸ��ͼ��
 * @author wusj  2015.03.11
 */
public class VectorLayer extends Layer{
	protected Style mStyle;
	protected Boolean editable = false;
	protected Boolean selectable = true;
	protected final Vector<String> mAttributesKeys = new Vector<String>();  //�����ֶ�
	
	public VectorLayer(Map map) {
		super(map);
	}

	
	
	public VectorLayer(Map map,String name){
		super(map,name);
	}
	/**
	 * �㣬�ߣ��� ���ֻ�������
	 * NORMAL ������ʸ��ͼ������ 
	 */
	public static enum VectorType{
		POINT,LINE,POLYGON,NORMAL
	};

	
	/**
	 * @return  ͼ���ϵ�Ҫ���Ƿ��ѡ
	 */
	public Boolean getSelectable() {
		return selectable;
	}
	/**
	 * @param selectable  ͼ���ϵ�Ҫ���Ƿ��ѡ
	 */
	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	protected VectorType mVectorType = VectorType.NORMAL;  
	/**
	 * ��ȡͼ���Ĭ����ʽ
	 * @return
	 */
	public Style getStyle() {
		return mStyle;
	}
	
	/**
	 * ����ͼ�����ʽ
	 * @param mStyle
	 */
	public void setStyle(Style mStyle) {
		this.mStyle = mStyle;
	}

	protected Vector<Feature> mFeatures = new Vector<Feature>();

	
	public Vector<Feature> getFeatures() {
		return mFeatures;
	}
	
	/**
	 * ���Ҫ��
	 * @param feature
	 */
	public void addFeature(Feature feature){
		if(feature.getStyle()==null) feature.setStyle(this.mStyle);
		mFeatures.add(feature);
	}
	
	/**
	 * ���ͼ��
	 */
	public void clear(){
		 mFeatures.clear();
	}
	
	/**
	 * �Ƴ�Ҫ��
	 * @param feature
	 */
	public void removeFeature(Feature feature){
		this.mFeatures.remove(feature);
	}
	
	/**
	 * 
	 * @param features
	 */
	public void removeFeatures(Vector<Feature> features){
		for(int i=0;i<features.size();i++){
			mFeatures.remove(features.get(i));
		}
	}
	
	/**
	 * �Ƴ�ȫ��Ҫ��
	 */
	public void removeAllFeatures(){
		mFeatures.removeAllElements();
	}
	
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}
	
	protected void drawFeatures(Canvas canvas){
		for(int i=0;i<mFeatures.size();i++){
			mFeatures.get(i).reDraw(canvas);
		}
	}
	
	/**
	 *�ػ� 
	 */
	public void reDraw(){
		this.invalidate();
		if(!isReadyForDraw)return;
		Canvas canvas = mHolder.lockCanvas(null);
		canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
		drawFeatures(canvas);
		mHolder.unlockCanvasAndPost(canvas);
	}
	
	/**
	 * ��ȡһ�����������ڵ�Ҫ�ؼ�
	 * @param bounds
	 * @return
	 */
	public Vector<Feature> getFeaturesByBounds(Bounds bounds){
		if(!selectable)return null;
		if(mFeatures==null) return null;
		int len = mFeatures.size();
		if(len==0) return null;
		Vector<Feature> vector = new Vector<Feature>();
		for(int i=0; i<len;i++){
			Geometry geo = mFeatures.get(i).getGeometry();
			if(bounds.contain(geo.getBounds())){
				mFeatures.get(i).isSelected = true;
				vector.add(mFeatures.get(i));
			}
		}
		return vector;
	}
	
	public void deSelectAll(){
		int len = mFeatures.size();
		if(len==0) return;
		for(int i=0; i<len;i++){
			mFeatures.get(i).isSelected = false;
		}
	}
	
	/**
	 * ����һ��������
	 * @param key
	 */
	public void addAttributeKey(String key){
		if(this.mAttributesKeys.contains(key)) return;
		this.mAttributesKeys.addElement(key);
	}
	
	/**
	 * �Ƴ�һ��������Ŀ
	 * @param key
	 */
	public void removeAttributeKey(String key){
		this.mAttributesKeys.remove(key);
	}
	
	/**
	 * @return  ͼ���ȫ��������Ŀ
	 */
	public Vector<String> getAttributeKeys(){
		return mAttributesKeys;
	}
	
 }
