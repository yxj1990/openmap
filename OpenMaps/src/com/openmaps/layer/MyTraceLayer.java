package com.openmaps.layer;

import android.content.Context;
import android.graphics.Canvas;

import com.openmaps.Map;
import com.openmaps.feature.LineFeature;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.location.MyLocation;
import com.openmaps.style.Style;

public class MyTraceLayer extends MyLocationLayer {

	private GeoPolyline mLine=null;
	private LineFeature mLineFeature=null;
	public MyTraceLayer(Map map, Context context) {
		super(map, context);
		// TODO Auto-generated constructor stub
		this.setName("MyTrace");
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		if(mLine==null||mLineFeature==null){
			mLineFeature=new LineFeature(map);
			mLine=new GeoPolyline();
			mLineFeature.setGeometry(mLine);
			mLineFeature.setStyle(Style.getDefaultLineSelectedStyle());
		}
		Location loc=new Location(MyLocation.getIntance(mContext).getLongitude(), MyLocation.getIntance(mContext).getLatitude());
		if(loc.x==0.0||loc.y==0.0) return;
		mLine.appendPoint(loc);
		
		mLineFeature.reDraw(canvas);
	}
}
