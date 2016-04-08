package com.openmaps.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.location.LocationListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventDispatcher;
import com.openmaps.events.IEventListener;
import com.openmaps.events.LocationEvent;
import com.openmaps.feature.Feature;
import com.openmaps.feature.PointFeature;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.basetypes.Unit;
import com.openmaps.location.MyLocation;
import com.openmaps.style.Style;

public class MyLocationLayer extends Layer implements IEventListener{
	private PointFeature mFeature=null;
	private GeoPoint mPoint=null;
	protected Context mContext=null;
	public MyLocationLayer(Map map,Context context) {
		super(map);
		// TODO Auto-generated constructor stub
		this.setName("MyLocation");
		mContext=context;
		if(!MyLocation.getIntance(context).isSupprotLocation()){
			//不支持定位
			Toast.makeText(context, "设备不支持定位！", 2000);
			return;
		}
		MyLocation.getIntance(context).addEventListener(EventUtil.LOCATION_CHANGE, this);
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		mFeature=new PointFeature(map);
		mPoint=new GeoPoint(MyLocation.getIntance(mContext).getLongitude(), MyLocation.getIntance(mContext).getLatitude());
		mFeature.setGeometry(mPoint);
		DisplayMetrics dm=new DisplayMetrics();
		dm=mContext.getApplicationContext().getResources().getDisplayMetrics();
		double meter= MyLocation.getIntance(mContext).getAccuracy()/Unit.getScaleFromResolutionToMeter(map.getResolution(), dm.xdpi);
		// Log.e("GPS精度", ""+MyLocation.getIntance(mContext).getAccuracy()+"_"+meter);
		mFeature.setStyle(Style.getGPSLocationStyle((int)meter,MyLocation.getIntance(mContext).getBearing()));
		mFeature.reDraw(canvas);
	}
	public void reDraw(){
		this.invalidate();
	}

	@Override
	public void respond(BaseEvent event) {
		// TODO Auto-generated method stub
		switch(event.getType()){
		case EventUtil.LOCATION_CHANGE:
			this.invalidate();
			break;
	
		}
	}



	
	
}
