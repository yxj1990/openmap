package com.openmaps.style.symbolize;

import android.graphics.Canvas;

import com.openmaps.geometry.Geometry;

/**
 * Ҫ��ʹ�õķ���
 * @author wusj 20150318
 */
public  class Symbolize {
	protected Geometry mGeometry;
	protected int mMaxLevel=25;
	protected int mMinLevel=0;

	/**
	 * �ɷ��Ÿ���Ҫ�صĻ���
	 * @param canvas ����
	 */
	public void draw(Canvas canvas){
		
	}

	public void setMaxLevel(int maxLevel){
		mMaxLevel=maxLevel;
	}
	public void setMinLevel(int minLevel){
		mMinLevel=minLevel;
	}
	public int getMaxLevel(){
		return mMaxLevel;
	}
	public int getMinLevel(){
		return mMinLevel;
	}
	
}