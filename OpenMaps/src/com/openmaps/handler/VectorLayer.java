package com.openmaps.handler;

import java.util.Vector;

import android.graphics.Canvas;
import android.util.Log;

import com.openmaps.Map;
import com.openmaps.feature.Feature;
import com.openmaps.feature.LineFeature;
import com.openmaps.feature.PointFeature;
import com.openmaps.feature.PolygonFeature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.Geometry;
import com.openmaps.style.Style;

/**
 * 矢量图层
 * @author wusj  2015.03.11
 */
public class VectorLayer extends Layer{
	protected Style mPointStyle,mLineStyle,mPolygonStyle;
	protected Boolean editable = false;
	protected Boolean selectable = true;
	protected final Vector<String> mAttributesKeys = new Vector<String>();  //属性字段
	/**
	 * 点，线，面 三种基本类型
	 * NORMAL 不区分矢量图形类型 
	 */
	public static enum VectorType{
		POINT,LINE,POLYGON,NORMAL
	};
	protected VectorType mVectorType = VectorType.NORMAL;  
	public VectorLayer(Map map) {
		super(map);
		this.mPointStyle = Style.getDefaultPointStyle();
		this.mLineStyle  = Style.getDefaultLineStyle();
		this.mPolygonStyle = Style.getDefaultPloygonStyle();
	}

	
	
	public VectorLayer(Map map,String name){
		super(map,name);
		this.mPointStyle = Style.getDefaultPointStyle();
		this.mLineStyle  = Style.getDefaultLineStyle();
		this.mPolygonStyle = Style.getDefaultPloygonStyle();
	}
	

	
	/**
	 * @return  图层上的要素是否可选
	 */
	public Boolean getSelectable() {
		return selectable;
	}
	/**
	 * @param selectable  图层上的要素是否可选
	 */
	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}

	/**
	 * 获取图层的默认样式
	 * @return
	 */
	public Style getStyle(VectorType type) {
		switch(type){
			case POINT:
				return this.mPointStyle;
			case LINE:
				return this.mLineStyle;
			case POLYGON:
				return this.mPolygonStyle;
			default:
				return null;
		}
	}
	
	/**
	 * 设置图层的样式
	 * @param mStyle
	 */
	public void setStyle(Style style,VectorType type) {
		
		switch(type){
		case POINT:
			this.mPointStyle = style;
			break;
		case LINE:
			this.mLineStyle  = style;
			break;
		case POLYGON:
			this.mPolygonStyle = style;
			break;
		default:
			break;
		}
		
	}

	protected Vector<Feature> mFeatures = new Vector<Feature>();

	
	public Vector<Feature> getFeatures() {
		return mFeatures;
	}
	
	/**
	 * 添加要素,如果图层限定了要素的类型，非制定类型的要素将不能添加到该图层
	 * @param feature
	 */
	public void addFeature(Feature feature){
		if(this.mVectorType == VectorType.NORMAL)
		{
			mFeatures.add(feature);
			return;
		}
		
		if(   ((feature instanceof PointFeature)   && this.mVectorType!=VectorType.POINT)
			||((feature instanceof LineFeature)    && this.mVectorType!=VectorType.LINE)
			||((feature instanceof PolygonFeature) && this.mVectorType!=VectorType.POLYGON))
		{
			Log.e("openmaps",feature.getClass().toString()+"cannot be added to this Layer");
			return;
		}else{
			mFeatures.add(feature);
		}
		
	}
	
	/**
	 * 清空图层
	 */
	public void clear(){
		 mFeatures.clear();
	}
	
	/**
	 * 移除要素
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
	 * 移除全部要素
	 */
	public void removeAllFeatures(){
		mFeatures.removeAllElements();
	}
	
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		for(int i=0;i<mFeatures.size();i++){
			mFeatures.get(i).reDraw(canvas);
		}
	}
	
	/**
	 *重绘 
	 */
	public void reDraw(){
		this.invalidate();
	}
	
	/**
	 * 获取一个矩形区域内的要素集
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
	 * 增加一个属性项
	 * @param key
	 */
	public void addAttributeKey(String key){
		if(this.mAttributesKeys.contains(key)) return;
		this.mAttributesKeys.addElement(key);
	}
	
	/**
	 * 移除一个属性项目
	 * @param key
	 */
	public void removeAttributeKey(String key){
		this.mAttributesKeys.remove(key);
	}
	
	/**
	 * @return  图层的全部属性项目
	 */
	public Vector<String> getAttributeKeys(){
		return mAttributesKeys;
	}
	
 }
