package com.openmaps.style.fill;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.openmaps.R;
import com.openmaps.util.Util;

public class BitmapFill extends Fill {
	private Bitmap mBitMap;
	public BitmapFill(Bitmap bitmap){
		this.mBitMap = bitmap;
	}
	
	public BitmapFill(String bitmapPath){
		File f = new File(bitmapPath);
		if(!f.exists())return;
		try {
			FileInputStream fis  = new FileInputStream(f);
			byte[] bytes = new byte[(int) f.length()];
			fis.read(bytes);
			this.mBitMap = BitmapFactory.decodeByteArray(bytes,0, (int) f.length());
			fis.close();
			fis = null;
			f = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BitmapFill(int id){
		BitmapDrawable drawable = (BitmapDrawable) Util.getResources().getDrawable(id);
		mBitMap = drawable.getBitmap();
	}
	
	@Override
	public void draw(Canvas canvas, Point[] points) {
		if(this.mBitMap==null) {
			Log.e("openmaps", "使用空的bitmap填充多边形");
			return;
		}
		BitmapShader shader = new BitmapShader(this.mBitMap, TileMode.REPEAT, TileMode.REPEAT);
		Paint paint = new Paint();
		paint.setShader(shader);
		Path path = new Path();
		path.moveTo(points[0].x,points[0].y);
		for(int  i=1; i < points.length; i++){
			path.lineTo(points[i].x,points[i].y);
		}
		canvas.drawPath(path, paint);
	}
	
	
	

}
