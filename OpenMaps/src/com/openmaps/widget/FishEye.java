package com.openmaps.widget;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventListener;
import com.openmaps.events.MapTouchEvent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class FishEye extends Widget  implements IEventListener{
	private int corssSize;
	private Bitmap mBitMap; //显示鱼眼
	static public final  int  DEFAULT_VIEW_WIDTH = 124;	//鱼眼的大小
	static public final  int  DEFAULT_VIEW_HEIGHT= 124;
	private  int  imageWidth;
	private  int  imageHeight;
	private int mScale = 2;
	private  Point mPosition; //corss的相对位置
	private Paint mPaint;	
	private Boolean isActivite = false;
	public FishEye(Context context, Map map) {
		super(context, map);
		corssSize = dip2px(context, 5);
		mPaint = new Paint();
		mPaint.setStyle(Style.STROKE);
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(3);
		mPosition = new Point(0, 0);
		imageHeight = DEFAULT_VIEW_HEIGHT;
		imageWidth  = DEFAULT_VIEW_WIDTH;
		this.mMap.addEventListener(EventUtil.MAP_TOUCHED,this);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(mBitMap==null)return;
		int radius = imageWidth/2;
		mPaint.setStyle(Style.FILL_AND_STROKE);	//填充效果，即画面状地物
		mPaint.setColor(Color.argb(255, 200, 200, 200));
		Bitmap circleBitmap = getCircleBitmap(mBitMap, radius); 	//<circle>
		canvas.drawBitmap(circleBitmap, 0, 0, null);
		mPaint.setStyle(Style.STROKE);	//画线，不填充
		mPaint.setStrokeWidth(3);
		mPaint.setARGB(255, 0, 204, 0);
		canvas.drawCircle(radius, radius, radius-2, mPaint);
		
		mPaint.setColor(Color.BLUE);
		canvas.drawLine(mPosition.x*mScale-corssSize, mPosition.y*mScale, mPosition.x*mScale+corssSize, mPosition.y*mScale, mPaint);
		canvas.drawLine(mPosition.x*mScale,mPosition.y*mScale-corssSize, mPosition.x*mScale, mPosition.y*mScale+corssSize, mPaint);
		super.onDraw(canvas);
	}
	

	private int  dip2px(Context context,float dip) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm); 
		float d = dm.density;
		return (int) (0.5F + dip * d);
	}

	private Bitmap getCircleBitmap(Bitmap bmp, int radius){
		Bitmap sbmp;  
	    if(bmp.getWidth() != 2*radius || bmp.getHeight() != 2*radius)  
	        sbmp = Bitmap.createScaledBitmap(bmp, 2*radius, 2*radius, false);  
	    else  
	        sbmp = bmp;  
	    Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),  
	            sbmp.getHeight(), Config.ARGB_8888);  
	    Canvas canvas = new Canvas(output);    
	    final Paint paint = new Paint();  
	    final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());  
	  
	    paint.setAntiAlias(true);  
	    paint.setFilterBitmap(true);  
	    paint.setDither(true);  
	    canvas.drawARGB(0, 0, 0, 0);  
	    paint.setColor(Color.parseColor("#BAB399"));  
	    canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,  
	            sbmp.getWidth() / 2+0.1f, paint);  
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
	    canvas.drawBitmap(sbmp, rect, rect, paint);    
	    return output;  
	}
	
	public  Bitmap loadBitmapFromView(View v) {  
        if (v == null) {  
            return null;  
        }  
        Bitmap screenshot;  
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);  
        Canvas c = new Canvas(screenshot);  
        v.draw(c);  
        return screenshot;  
    }  

	private void onMapTouched(MotionEvent event){
		switch (MotionEvent.ACTION_MASK&event.getAction()) {
		case MotionEvent.ACTION_DOWN:		
			this.setVisibility(View.VISIBLE);
			break;
		case MotionEvent.ACTION_UP:
			this.setVisibility(View.GONE);
			break;
		default:
			break;
		}
			int clipImageWidth = imageWidth/mScale;
			int clipImageHeight = imageHeight/mScale;
		Bitmap bitmap = loadBitmapFromView(mMap);
		int x= (int) event.getX();
		int y = (int) event.getY();
		int left = x - clipImageWidth/2;
		int top  = y - clipImageHeight/2;
		left = left <  0?0:left;
		top  = top  <  0?0:top;
		if(left+clipImageWidth > mMap.getWidth()) left = mMap.getWidth()  - clipImageWidth;
		if(top +clipImageHeight> mMap.getHeight()) top = mMap.getHeight() - clipImageHeight; //不考虑 view比imageView还小的情况
		mPosition.x = x - left;
		mPosition.y = y - top;
		if(bitmap!=null){
			mBitMap =  Bitmap.createBitmap(bitmap,left,top,clipImageWidth,clipImageHeight);
			this.invalidate();
		}
	}
	
	@Override
	public void respond(BaseEvent event) {
		MotionEvent evt = ((MapTouchEvent)event).getMotionEvent();
		onMapTouched(evt);
	}

}
