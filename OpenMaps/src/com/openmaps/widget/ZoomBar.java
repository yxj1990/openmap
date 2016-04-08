package com.openmaps.widget;

import com.openmaps.Map;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ZoomBar extends ViewGroup implements OnClickListener{
	private Map mMap;
	private Button btnZoomin;
	private Button btnZoomout;
	private int buttonSize;
	public ZoomBar(Context context, Map map) {
		super(context);
		this.mMap = map;
		btnZoomin  = new Button(context);
		btnZoomout = new Button(context);
		btnZoomin.setOnClickListener(this);
		btnZoomout.setOnClickListener(this);
		buttonSize = this.dip2px(context, 40);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		btnZoomin.layout(l, t, l+buttonSize, b);
		btnZoomout.layout(l+buttonSize, t, r+buttonSize*2, b);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnZoomin)){
			mMap.zoomIn();
		}else{
			mMap.zoomOut();
		}
	}
	
	private int  dip2px(Context context,float dip) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm); 
		float d = dm.density;
		return (int) (0.5F + dip * d);
	}
	
	public Button getZoomInButton(){
		return btnZoomin;
	}
	
	public Button getZoomOutButton(){
		return btnZoomout;
	}

}
