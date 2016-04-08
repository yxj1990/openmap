/**
 * @author wusj  2015.03.13
 */
package com.openmaps.controls;

import java.util.Date;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventListener;
import com.openmaps.events.MapTouchEvent;
import com.openmaps.feature.Feature;
import com.openmaps.feature.LineFeature;
import com.openmaps.feature.PointFeature;
import com.openmaps.feature.PolygonFeature;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.layer.VectorLayer;
import com.openmaps.style.Style;

public class DrawControl extends Control implements IEventListener{
	public static final short TASK_POINT    = 0;
	public static final short TASK_POLYLINE = 1;
	public static final short TASK_POLYGON  = 2;
	
	protected VectorLayer mLayer = null;
	protected VectorLayer mTempLayer; //���ƹ����У�Ҫ�ش洢����ʱͼ�㣬���ƽ������Ƴ�
	protected short mTask; //����Ҫ�صļ�������
	protected Geometry mGeometry;
	protected Feature mTempFeature;
	public DrawControl(Map map) {
		super(map);
		mTempLayer = new VectorLayer(map, "temp_drawing_layer"+new Date().getTime());
	}
	
	/**
	 * ���û���Ҫ�صļ�������  
	 * @param task  DrawControl.TASK_POINT DrawControl.TASK_POLYLINE DrawControl.TASK_POLYGON
	 */
	public void setTask(short task){
		this.mTask = task;
		mGeometry = null;
		mTempFeature = null;
	}
	
	/**
	 * ����Ҫ����Ҫ�ص�ͼ��
	 * @param layer
	 */
	public  void setDrawLayer(VectorLayer layer){
		this.mLayer = layer;
	}
	
	/**
	 * @return ���Ƶ�ͼ��
	 */
	public  VectorLayer getDrawLayer(){
		return this.mLayer;
	}

	@Override
	public void activate() {
		super.activate();
		this.mMap.addEventListener(EventUtil.MAP_TOUCHED, this);
		if(!this.mMap.contain(mTempLayer))
			this.mMap.addLayer(mTempLayer);
	}

	@Override
	public void disActivate() {
		super.disActivate();
		finishDrawing();
		this.mMap.removeLayer(mTempLayer);
		this.mMap.removeEventListener(EventUtil.MAP_TOUCHED, this);
		mGeometry = null;
		mTempFeature = null;
	}
	

	@Override
	public void respond(BaseEvent pEvent) {
		MotionEvent event =((MapTouchEvent)pEvent).getMotionEvent();	
		Point p = new Point((int)event.getX(),(int)event.getY());
		Location loc = this.mMap.transformPixelToMapPoint(p);
		switch(event.getAction()&MotionEvent.ACTION_MASK){
			case MotionEvent.ACTION_DOWN:{
				//drawGeometry(loc, true);
				break;
			}
			case MotionEvent.ACTION_MOVE:{
				//drawGeometry(loc, true);
				break;
			}
			case MotionEvent.ACTION_UP:{
				if(mTask==TASK_POINT)
					drawGeometry(loc, false);
				else 
					drawGeometry(loc, true);  //�����ߺ��� ����������ʽ����
				break;
			}
			default:
				break;
		}
	}
	
	/**
	 * 
	 * @param loc  �����ĵ� ��������
	 * @param isTemp  TouchDown��TouchMove ΪTrue��TouchUp Ϊ false
	 */
	protected void drawGeometry(Location loc,Boolean isTemp){
		switch(mTask){
			case TASK_POINT:{drawPoint(loc, isTemp);break;}
			case TASK_POLYLINE:{drawPolyline(loc, isTemp);break;}
			case TASK_POLYGON:{drawPolygon(loc, isTemp);break;}
			default:break;
		}
	}
	
	protected void drawPoint(Location loc,Boolean isTemp){
		if(isTemp){
			if(mGeometry==null) mGeometry = new GeoPoint();
			((GeoPoint)mGeometry).location = new Location(loc.x, loc.y);
			if(mTempFeature==null){
				mTempFeature = new PointFeature(this.mMap);
				this.mTempLayer.addFeature(mTempFeature);
			}
			mTempFeature.setGeometry(mGeometry);
			mTempLayer.reDraw();
		}else{
			GeoPoint p = new GeoPoint();
			p.location = new Location(loc.x,loc.y);
			PointFeature pointFeature = new PointFeature(this.mMap);
			pointFeature.setGeometry(p);
			mLayer.addFeature(pointFeature);
			mLayer.reDraw();
		}
	}
	
	
	protected void drawPolyline(Location loc,Boolean isTemp){
		if(isTemp){
			if(mGeometry==null) mGeometry = new GeoPolyline();
			((GeoPolyline)mGeometry).appendPoint(loc);
			if(mTempFeature==null){
				mTempFeature = new LineFeature(this.mMap);
				mTempFeature.setStyle(Style.getDefaultLineStyle());
				this.mTempLayer.addFeature(mTempFeature);
			}
			mTempFeature.setGeometry(mGeometry);
			Log.e("lenght",String.valueOf(((GeoPolyline)mGeometry).getLength()));
			mTempLayer.reDraw();
		}
	}
	
	protected void drawPolygon(Location loc,Boolean isTemp){
		if(isTemp){
			if(mGeometry==null) mGeometry = new GeoPolygon();
			((GeoPolygon)mGeometry).appendPoint(loc);
			if(mTempFeature==null){
				mTempFeature = new PolygonFeature(this.mMap);
				this.mTempLayer.addFeature(mTempFeature);
				mTempFeature.setStyle(Style.getDefaultPloygonStyle());
			}
			mTempFeature.setGeometry(mGeometry);
			mTempLayer.reDraw();
		}
	}
	
	/**
	 * ���������ߺ���
	 */
	protected void finishDrawing(){
		if(mGeometry==null)return;
		if(mTask==TASK_POLYLINE){
			LineFeature feature = new LineFeature(mMap);
			feature.setGeometry(mGeometry.clone());
			feature.setStyle(Style.getDefaultLineStyle());
			mLayer.addFeature(feature);
			mLayer.reDraw();
		}else if(mTask==TASK_POLYGON){
			PolygonFeature feature = new PolygonFeature(mMap);
			feature.setGeometry(mGeometry.clone());
			feature.setStyle(Style.getDefaultPloygonStyle());
			mLayer.addFeature(feature);
			mLayer.reDraw();
		}
	}
	
}
