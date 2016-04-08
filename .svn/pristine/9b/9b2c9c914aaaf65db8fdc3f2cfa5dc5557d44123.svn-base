package com.openmaps.widget;

import com.openmaps.Map;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.location.MyLocation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;


public class LocationButton extends Widget  implements View.OnClickListener{
	MyLocation location;
	public LocationButton(Context context, Map map) {
		super(context, map);
		location = MyLocation.getIntance(context);
	}

	public void setDrawable(Drawable drawable){
		this.setDrawable(drawable);
	}

	@Override
	public void onClick(View view) {
		if(location.isPostionFixed()){
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			mMap.setMapCenter(new Location(lon, lat));
		}
	}	
}
