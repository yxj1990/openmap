package com.openmaps.location;

import java.util.Date;
import java.util.Vector;

import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventDispatcher;
import com.openmaps.events.IEventListener;
import com.openmaps.events.LocationEvent;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.SparseArray;

public class MyLocation implements LocationListener,IEventDispatcher {
	protected SparseArray<Vector<IEventListener>> mEventListeners = new SparseArray<Vector<IEventListener>>();
	private double lon=0.0;
	private double lat=0.0;
	private float  arruracy = 0.0f;
	private long   latestLocationTime=0;
	private float bearing=0;
	private String latestProvider;
	private Boolean isFixedPostion = false;
	private LocationManager mLocationManager=null;
	private static MyLocation mMyLocation=null;
	private MyLocation(Context context){
		mLocationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Criteria cri=new Criteria();
		cri.setCostAllowed(false);//要求定位来源免费
		cri.setBearingRequired(true);//要求提供方向
		mLocationManager.getBestProvider(cri, true);
		if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		if(mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		
	}
	
	public static MyLocation getIntance(Context context){
		if(mMyLocation==null)
			mMyLocation=new MyLocation(context);
		return mMyLocation;
	}
	
	
	@Override
	public void addEventListener(int type, IEventListener listener) {
		// TODO Auto-generated method stub
		Vector<IEventListener> vector = this.mEventListeners.get(type);
		if(vector==null)
			vector = new Vector<IEventListener>();
		if(vector.contains(listener)) return;
		vector.add(listener);
		this.mEventListeners.put(type, vector);
	}

	@Override
	public void removeEventListener(int type, IEventListener listener) {
		// TODO Auto-generated method stub
		Vector<IEventListener> vector = this.mEventListeners.get(type);
		if(vector==null)
			return;
		vector.remove(listener);
	}

	@Override
	public void disPatchEvent(BaseEvent event) {
		// TODO Auto-generated method stub
		Vector<IEventListener> vector = this.mEventListeners.get(event.getType());
		if(vector==null)
			return;
		for(int i=0;i<vector.size();i++){
			vector.get(i).respond(event);
		}
	}

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		this.lon = loc.getLongitude();
		this.lat = loc.getLatitude();
		this.arruracy = loc.getAccuracy();
		this.latestLocationTime = new Date().getTime();
		this.latestProvider  = loc.getProvider();
		this.isFixedPostion = true;
		loc.getBearing();
		
		LocationEvent le=new LocationEvent(EventUtil.LOCATION_CHANGE, new Object());
		this.disPatchEvent(le);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	
	public  Boolean isSupprotLocation() {
		boolean gps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean network = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}
	
	public float getBearing(){
		return bearing;
	}
	/**
	 * @return 当前经度
	 */
	public double getLongitude(){
		return lon;
	}
	
	 /**
	  * @return 当前纬度
	  */
	public double getLatitude(){
		return lat;
	}
	/**
	 * @return 定位精度
	 */
	public float getAccuracy(){
		return this.arruracy;
	}
	
	/**
	 * @return   返回坐标提供者
	 */
	public String  getProvider(){
		return this.latestProvider;
	}
	
	/**
	 * @return 最近一次定位的时间
	 */
	public long getLocationTime(){
		return this.latestLocationTime;
	}
	/**
	 * @return  是否已经定位
	 */
	public Boolean isPostionFixed(){
		return this.isFixedPostion;
	}
}
