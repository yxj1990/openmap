package com.openmaps.style.symbolize;

import android.graphics.Path;

public class Marker{
	public static enum MarkerType{
		CIRCLE,SQUARE,TRIANGLE,STAR,CROSS,X
	};
	
	protected int mSize; //外接圆的半径
	
	/**形状*/
	protected MarkerType mType = MarkerType.CIRCLE;
	
	public Marker(int size){
		this.mSize  = size;
	}
	
	public Path getPath(float x,float y){
		Path path = new Path();
		
		
		
		switch(mType){
		case CIRCLE:
			path.addCircle(x, y, this.mSize,Path.Direction.CW);
			break;
		case SQUARE:
			path.addRect(x-mSize, y-mSize, x+mSize, y+mSize, Path.Direction.CW);
			break;
		case TRIANGLE:
			path.moveTo(x,y-mSize);
			path.lineTo(x-0.866f*mSize,y+0.5f*mSize);
			path.lineTo(x+0.866f*mSize,y+0.5f*mSize);
			path.lineTo(x, y-mSize);
			break;
		case STAR:
			float R = mSize;
			path.moveTo(x,y-R);
			path.lineTo(x-0.2245140f*R,y-0.3090170f*R);
			path.lineTo(x-0.9510565f*R,y-0.3090170f*R);
			path.lineTo(x-0.3632713f*R,y+0.1180340f*R);
			path.lineTo(x-0.5877852f*R,y+0.8090170f*R);
			path.lineTo(x-0.0f,y+0.3819660f*R);
			path.lineTo(x+0.5877852f*R,y+0.8090170f*R);
			path.lineTo(x+0.3632713f*R,y+0.1180340f*R);
			path.lineTo(x+0.9510565f*R,y-0.3090170f*R);
			path.lineTo(x+0.2245140f*R,y-0.3090170f*R);
			path.lineTo(x+0,y-R);
			break;
		case CROSS:
			path.moveTo(x-mSize,y);
			path.lineTo(x+mSize,y);
			path.moveTo(x,y-mSize);
			path.lineTo(x,y+mSize);
			break;
		case X:
			path.moveTo(x-0.707f*mSize, y-0.707f*mSize);
			path.lineTo(x+0.707f*mSize, y+0.707f*mSize);
			path.moveTo(x-0.707f*mSize, y+0.707f*mSize);
			path.lineTo(x+0.707f*mSize, y-0.707f*mSize);
			break;
		}
		
		return path;
	}
	
	public Marker(int size,MarkerType type){
		this.mSize = size;
		this.mType = type;
		
	}
	
	
}

//绘制五角星的源代码
//float r = (float) (size*(Math.sin(Math.PI/10))/Math.cos(Math.PI/5));
//this.moveTo(0,(float) -R);
//this.lineTo((float)-Math.abs(r*Math.cos(Math.PI*126/180)),(float)-Math.abs(r*Math.sin(Math.PI*126/180)));
//this.lineTo((float)-Math.abs(R*Math.cos(Math.PI*162/180)),(float)-Math.abs(R*Math.sin(Math.PI*162/180)));
//this.lineTo((float)-Math.abs(r*Math.cos(Math.PI*198/180)),(float)Math.abs(r*Math.sin(Math.PI*198/180)));
//this.lineTo((float)-Math.abs(R*Math.cos(Math.PI*234/180)),(float)Math.abs(R*Math.sin(Math.PI*234/180)));
//this.lineTo((float)-Math.abs(r*Math.cos(Math.PI*270/180)),(float)Math.abs(r*Math.sin(Math.PI*270/180)));
//this.lineTo((float)Math.abs(R*Math.cos(Math.PI*306/180)),(float)Math.abs(R*Math.sin(Math.PI*306/180)));
//this.lineTo((float)Math.abs(r*Math.cos(Math.PI*342/180)),(float)Math.abs(r*Math.sin(Math.PI*342/180)));
//this.lineTo((float)Math.abs(R*Math.cos(Math.PI*18/180)),(float)-Math.abs(R*Math.sin(Math.PI*18/180)));
//this.lineTo((float)Math.abs(r*Math.cos(Math.PI*54/180)),(float)-Math.abs(r*Math.sin(Math.PI*54/180)));
//this.lineTo(0,(float)-R);
