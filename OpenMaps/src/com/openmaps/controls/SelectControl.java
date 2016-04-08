package com.openmaps.controls;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import android.R.bool;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.FeatureEvent;
import com.openmaps.events.IEventListener;
import com.openmaps.events.MapTouchEvent;
import com.openmaps.feature.Feature;
import com.openmaps.feature.PolygonFeature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.layer.Layer;
import com.openmaps.layer.VectorLayer;
import com.openmaps.style.Style;
import com.openmaps.style.stroke.Stroke;
import com.openmaps.style.symbolize.LineSymbolize;

public class SelectControl extends Control  implements IEventListener {
	protected Location startMotionLocation;
	protected Location endMotionLocation;
	protected VectorLayer mLayer;
	protected PolygonFeature mSelctetRect;
	protected Bounds mSelectBounds;
	protected Boolean isIntersected = false; //�ཻ������Ƿ��������

	protected Boolean isRectSelected=false;//Ĭ���ǵ�ѡ����������Ϊ��ѡ

	protected int buffer=50;//��ѡ�����û�����
	
	protected Point startMotionPoint;//��갴��ʱ���ص��λ��
	protected Point endMotionPoint;//��굯��ʱ���ص�λ��
	

	public void setBuffer(int value){
		buffer=value;
	}

	public int getBuffer(){
		return buffer;
	}

	public void setIsRectSelected(Boolean isRectselected){
		isRectSelected=isRectselected;
	}

	public Boolean getIsRectSelected(){
		return isRectSelected;
	}

	public Boolean getIsIntersected() {
		return isIntersected;
	}

	public void setIsIntersected(Boolean isIntersected) {
		this.isIntersected = isIntersected;
	}


	protected HashMap<VectorLayer, Vector<Feature>> mSelectedFeatures;
	public SelectControl(Map map) {
		super(map);
		mLayer = new VectorLayer(map,"��ʱͼ��");
	}

	@Override
	public void activate() {
		this.isActivated = true;
		this.mMap.addEventListener(EventUtil.MAP_TOUCHED, this);
		if(!this.mMap.contain(mLayer))
		{
			this.mMap.addLayer(mLayer);
			this.mMap.invalidate();
		}
	}

	@Override
	public void disActivate() {
		this.isActivated = false;
		mLayer.removeAllFeatures();
		this.mMap.removeLayer(mLayer);
		deSelectFeatures();
		this.mMap.removeEventListener(EventUtil.MAP_TOUCHED, this);
	}


	/**
	 * ����ѡ����ڵ�Ҫ��
	 * @param bounds
	 */
	protected void selectFeatures(Bounds bounds){
		Vector<Layer>  layers = this.mMap.getAllLayers();
		if(this.mSelectedFeatures==null) mSelectedFeatures = new HashMap<VectorLayer, Vector<Feature>>();
		mSelectedFeatures.clear(); 
		int len = layers.size();
		if(len==0)return;
		//Log.e("����ʱ��","start");
		for(int i=0; i<len; i++){
			if(!(layers.get(i) instanceof VectorLayer)) continue;
			Vector<Feature> features = ((VectorLayer) layers.get(i)).getFeaturesByBounds(bounds, this.isIntersected);
			if(features==null) continue;
			if(features.size()<1) continue;
			for(Feature f:features){
				f.isSelected=true;
			}
			layers.get(i).reDraw();
			this.mSelectedFeatures.put((VectorLayer) layers.get(i), features);
			Log.e("featureselect", features.size()+"");
		}
		//Log.e("����ʱ��","end");
		FeatureEvent event = new FeatureEvent(EventUtil.FEATURE_SELECTED, mSelectedFeatures);
		
		this.mMap.disPatchEvent(event);
	}

	protected void deSelectFeatures(){
		if(mSelectedFeatures==null) return;
		Iterator<VectorLayer> it = mSelectedFeatures.keySet().iterator();
		while(it.hasNext()){
			VectorLayer layer =  it.next();
			layer.deSelectAll();
			layer.reDraw();
		}
		FeatureEvent event = new FeatureEvent(EventUtil.FEATURE_UNSELECTED, mSelectedFeatures);
		this.mMap.disPatchEvent(event);
	}

	@Override
	public void respond(BaseEvent pEvent) {
		MotionEvent event =((MapTouchEvent)pEvent).getMotionEvent();	
		switch(event.getAction()&MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:{
			this.mLayer.removeAllFeatures();
			deSelectFeatures();
			if(isRectSelected){
				mSelctetRect = new PolygonFeature(this.mMap);
				mSelctetRect.setStyle(getSelectBoundsStyle());
				mSelctetRect.setMap(this.mMap);
				mLayer.addFeature(mSelctetRect);
			}

			startMotionPoint = new Point((int)event.getX(),(int)event.getY());
			startMotionLocation = this.mMap.transformPixelToMapPoint(startMotionPoint);
			break;
		}
		case MotionEvent.ACTION_MOVE:{
			if(isRectSelected){
				GeoPolygon polygon = new GeoPolygon();
				polygon.getComponents().removeAllElements();
				endMotionLocation = this.mMap.transformPixelToMapPoint(new Point((int)event.getX(),(int)event.getY()));
				Location loc1 = new Location(startMotionLocation.x,endMotionLocation.y);
				Location loc2 = new Location(endMotionLocation.x,startMotionLocation.y);
				polygon.getComponents().add(startMotionLocation);
				polygon.getComponents().add(loc1);
				polygon.getComponents().add(endMotionLocation);
				polygon.getComponents().add(loc2);
				polygon.getComponents().add(startMotionLocation);
				mSelctetRect.setGeometry(polygon);
				mLayer.reDraw();
			}
			break;
		}
		case MotionEvent.ACTION_UP:{
			endMotionPoint=new Point((int)event.getX(),(int)event.getY());
			
			if(isRectSelected){
				endMotionLocation = this.mMap.transformPixelToMapPoint(endMotionPoint);
			}else{
				if(startMotionPoint.equals(endMotionPoint)){
					startMotionLocation =this.mMap.transformPixelToMapPoint(new Point((int)(startMotionPoint.x-buffer/2),(int)(startMotionPoint.y-buffer/2)));
					endMotionLocation = this.mMap.transformPixelToMapPoint(new Point((int)(endMotionPoint.x+buffer/2),(int)(endMotionPoint.y+buffer/2)));
				}else{
					break;
				}
			}
			
			mSelectBounds = new Bounds(startMotionLocation, endMotionLocation);
			mLayer.removeAllFeatures();
			mLayer.reDraw();
			selectFeatures(mSelectBounds);
			break;
		}
		}
	}

	private Style getSelectBoundsStyle(){
		Style style = new Style();
		LineSymbolize sym = new LineSymbolize();
		Stroke stroke  = new Stroke(Color.RED, 2, 1);
		sym.setStroke(stroke);
		style.addSymbolize(sym);
		return style;
	}


	/**
	 * ��������Ҫ�ص����ؾ���
	 * @return
	 */
	public static int pixelDisBetweenPointAndLine(){

		return 0;
	}
}