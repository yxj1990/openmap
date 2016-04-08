package com.openmaps.layer;

import java.util.Vector;

import android.graphics.Canvas;
import android.location.Location;
import android.util.Log;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.feature.Feature;
import com.openmaps.feature.LineFeature;
import com.openmaps.feature.PointFeature;
import com.openmaps.feature.PolygonFeature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.GeometryUtils;
import com.openmaps.geometry.topology.Topology;
import com.openmaps.style.Style;
import com.openmaps.util.QuadTree;
import com.openmaps.util.QuadTreeNote;

/**
 * 矢量图层
 * @author wusj  2015.03.11
 */
public class VectorLayer extends Layer{
	protected Style mPointStyle,mLineStyle,mPolygonStyle;
	protected Boolean editable = false;
	protected Boolean selectable = true;
	protected final Vector<String> mAttributesKeys = new Vector<String>();  //属性字段
	
	protected QuadTreeNote quadTreeNote;//四叉树索引
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
			feature.setLayer(this);
			//QuadTree.BuildTree(mFeatures);
			
			if(quadTreeNote!=null&&quadTreeNote.dataCount>0){
//				quadTreeNote=QuadTree.BuildTree(mFeatures);
//			}else{
				quadTreeNote=QuadTree.insertGeometry(feature,quadTreeNote);
			}
			System.gc();
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
			feature.setLayer(this);
			if(quadTreeNote!=null&&quadTreeNote.dataCount>0){
//				quadTreeNote=QuadTree.BuildTree(mFeatures);
//			}else{
				quadTreeNote=QuadTree.insertGeometry(feature,quadTreeNote);
			}
			System.gc();
		}
	}
	/***
	 * 添加要素集合，添加完成后创建四叉树索引
	 * @param features
	 */
	public void addFeatures(Vector<Feature> features){
		for(Feature f:features){
			addFeature(f);
		}
		quadTreeNote=QuadTree.BuildTree(mFeatures);
	}
	
	/***
	 * 设置四叉树索引
	 * @param note
	 */
	public void setQuadTreeNote(QuadTreeNote note){
		quadTreeNote=note;
	}
	/***
	 * 获取四叉树索引
	 * @return
	 */
	public QuadTreeNote getQuadTreeNote(){
		return quadTreeNote;
	}
	

	/**
	 * 清空图层
	 */
	public void clear(){
		mFeatures.clear();
		quadTreeNote=null;
	}

	/**
	 * 移除要素
	 * @param feature
	 */
	public void removeFeature(Feature feature){
		this.mFeatures.remove(feature);
		if(quadTreeNote!=null&&quadTreeNote.dataCount>0){
			quadTreeNote=QuadTree.removeGeometry(feature, quadTreeNote);
		}
		
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
		if(quadTreeNote!=null){
			quadTreeNote.clear();
			quadTreeNote.noteData.removeAllElements();
		}
		
	}


	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		
		if(quadTreeNote==null||quadTreeNote.dataCount==0){
			for(int i=0;i<mFeatures.size();i++){
				if(intersection(mFeatures.get(i)))
					mFeatures.get(i).reDraw(canvas);
				Log.d("draw", mFeatures.get(i).getLayer().getVectorType().name());
			}
		}else{//根据四叉树索引计算需要绘制要素
			Log.d("draw", "索引绘制--start");
			Vector<Feature> feas=QuadTree.selectGeometry(this.map.getViewBounds(), quadTreeNote);
			for(Feature f:feas){
				f.reDraw(canvas);
			}
			Log.d("draw", "索引绘制--end");
		}



	}


	private Boolean intersection(Feature fea){
		Geometry geo = fea.getGeometry();
		if(geo==null) return false;
		return this.map.getBounds().contain(geo.getBounds());	
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
	 * @param intersected  相交是否计算在内
	 * @return
	 */
	public Vector<Feature> getFeaturesByBounds(Bounds bounds,Boolean intersected){
		if(!selectable)return null;
		if(mFeatures==null) return null;
		if(bounds == null)return null;
		int len = mFeatures.size();
		if(len==0) return null;
		Vector<Feature> vector = new Vector<Feature>();
		if(quadTreeNote!=null&&quadTreeNote.dataCount>0){
			return  QuadTree.selectGeometry(bounds, quadTreeNote);
		}else{
			
			for(int i=0; i<len;i++){
				Geometry geo = mFeatures.get(i).getGeometry();
				if(geo==null)continue;
				//			if(bounds.contain(geo.getBounds())){
				//				mFeatures.get(i).isSelected = true;
				//				vector.add(mFeatures.get(i));
				//			}
				//测试使用
				//			if(GeometryUtils.intersects(Bounds.toGeometry(bounds), geo)){
				if(bounds.contain(geo.getBounds())){ //判断包含的情况
					//mFeatures.get(i).isSelected = true;
					vector.add(mFeatures.get(i));
					continue;
				}
				if(mFeatures.get(i) instanceof PointFeature) continue; //点类型的不用判断相交
				if(intersected){ //判断相交的情况
					if(geo instanceof GeoPolygon)
						//if(Topology.interect(bounds, (GeoPolygon) geo)){
						if(GeometryUtils.intersects(Bounds.toGeometry(bounds), geo)){
							//mFeatures.get(i).isSelected = true;
							vector.add(mFeatures.get(i));
						}
					if(geo instanceof GeoPolyline)
						//if(Topology.interect(bounds, (GeoPolyline) geo)){
						if(GeometryUtils.intersects(Bounds.toGeometry(bounds), geo)){
							//mFeatures.get(i).isSelected = true;
							vector.add(mFeatures.get(i));
						}
				}
			}
			return vector;
		}
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

	/**
	 * 获取图层的要素类型
	 * @return
	 */
	public VectorType getVectorType(){
		return mVectorType;
	}
	public void setVectorType(VectorType vecType){
		mVectorType=vecType;
	}

}
