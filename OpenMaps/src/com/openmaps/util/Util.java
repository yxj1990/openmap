package com.openmaps.util;

import android.content.res.Resources;

public class Util {
	public static Resources mResources;
	
	public static void setResources(Resources resources){
		mResources = resources;
	}
	
	public static Resources getResources(){
		return mResources;
	}
}
