package com.openmaps.style.stroke;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;

public class Stroke {
	private Paint mPaint = new Paint();
	public Stroke(){
		
	}
	/**
	 * @param color ɫֵ
	 * @param width  ���
	 * @param opacity  ��͸����[0-255]
	 */
	public Stroke(int color,float width,int opacity){
		this.mPaint.setAlpha(opacity);
		this.mPaint.setColor(color);
		this.mPaint.setStrokeWidth(width);
		this.mPaint.setStyle(Style.STROKE);
	}
	
	/**
	 * 
	 * @param color ɫֵ
	 * @param width �߿�
	 * @param opacity ��͸���� ��0-255��
	 * @param lineCap  �߶˵����ʽ���ο�{@link mPaint.setStrokeCap(cap);}�� Paint.Cap.BUTT;Paint.Cap.ROUND;Paint.Cap.SQUARE
	 * @param lineJion �۵㴦����ʽ (�ο�{@link mPaint.setStrokeJoin(join);}) Join.BEVEL;Join.MITER;Join.ROUND
	 */
	public Stroke(int color,float width,int opacity,Cap lineCap,Join lineJoin){
		this(color,width,opacity);
		this.mPaint.setStrokeCap(lineCap);
		this.mPaint.setStrokeJoin(lineJoin);
	}
	
	/**
	 * 
	 * @param color ɫֵ
	 * @param width �߿�
	 * @param opacity ��͸���� ��0-255��
	 * @param lineCap  �߶˵����ʽ���ο�{@link mPaint.setStrokeCap(cap);}�� Paint.Cap.BUTT;Paint.Cap.ROUND;Paint.Cap.SQUARE
	 * @param lineJion �۵㴦����ʽ (�ο�{@link mPaint.setStrokeJoin(join);}) Join.BEVEL;Join.MITER;Join.ROUND
	 */
	public Stroke(int color,float width,int opacity,Cap lineCap,Join lineJoin,float[] dashArray,float dashOffset,float miter){
		this(color,width,opacity,lineCap,lineJoin);
		PathEffect effect = new DashPathEffect(dashArray, dashOffset);
		this.mPaint.setPathEffect(effect);
	}
	
	public Path getPath(Point[] points){
		Path path = new Path();
		if(points.length<1)return path;
		path.moveTo(points[0].x,points[0].y);
		for(int  i=1; i < points.length; i++){
			path.lineTo(points[i].x,points[i].y);
		}
		return path;
	}
	
	/**
	 * @param canvas   ����
	 * @param points   �ߵĽڵ㼯��
	 */
	public void draw(Canvas canvas,Point[] points){
		Path path = new Path();
		path.moveTo(points[0].x,points[0].y);
		for(int  i=1; i < points.length; i++){
			path.lineTo(points[i].x,points[i].y);
		}
		canvas.drawPath(path, mPaint);
	}
}
