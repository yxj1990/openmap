package com.openmaps;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.openmaps.controls.Control;
import com.openmaps.controls.MapAnimationControl;
import com.openmaps.controls.MapTouchListener;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventDispatcher;
import com.openmaps.events.IEventListener;
import com.openmaps.events.MapEvent;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.basetypes.Location;
import com.openmaps.layer.Layer;
import com.openmaps.projection.ProjLatLon;
import com.openmaps.projection.Projection;
import com.openmaps.widget.Widget;

public class Map extends ViewGroup  implements IEventDispatcher{
	
	public final Matrix mRotateScaleMatrix = new Matrix();
	public final Point mRotateScalePoint = new Point();
	public float mMultiTouchScale = 1.0f;
	public float mMultiTouchRotation=0.0f;
	public PointF mMultiTouchScalePoint = new PointF();
	public final AtomicInteger mTargetZoomLevel = new AtomicInteger();
	public final AtomicBoolean mIsAnimating = new AtomicBoolean(false);
	private final MapAnimationControl mMapAnimationControl;//���ڿ��Ʋ��Ŷ���Ч��
	
	private boolean mLayoutOccurred = false;

	protected int mZoom=1;
	public  int maxZoom = 18;
	public  int minZoom = 1;
	
	protected Projection mProjection = new ProjLatLon(this);
	
	/**��ͼ�ɼ���Χ*/
	protected Bounds mViewBounds = new Bounds(-180,-90,180,90);
	/**��ͼ�������� **/
	public Location mMapCenter = new Location(0,0);


	/**�ֱ��ʣ���λ����/���أ� **/
	protected double mResolution;
	protected Vector<Layer> mLayers = new Vector<Layer>();
	protected Vector<Control> mHandlers = new Vector<Control>();
	/** ��ͼ�Ŵ����*/
	private float mapZoomRatio = 2.0f;
	protected int minMapWidth  = (int) (512*mapZoomRatio);
	protected int minMapHeight = (int) (256*mapZoomRatio);
	protected int mapWidth = minMapWidth*mZoom;
	protected int mapHeight = minMapHeight*mZoom;
	protected SparseArray<Vector<IEventListener>> mEventListeners = new SparseArray<Vector<IEventListener>>();
	private  DisplayMetrics mMetrics;
	private  boolean readyForDraw = false;
	private Boolean neesReLayout = true; //�Ƿ���Ҫ���²���
	
	public Map(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mMapAnimationControl = new MapAnimationControl(this);
		this.setOnTouchListener(new MapTouchListener(this));
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if(!neesReLayout)return;
		for(int i=0;i<mLayers.size();i++){
			mLayers.get(i).layout(l, t, r, b);
		}
		this.readyForDraw = true;
		this.reDraw();
		this.neesReLayout = false;
	}

	public boolean isReadyForDraw() {
		return readyForDraw;
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		this.reDraw();
	}
	
	/**
	 * ����ͼ��
	 * @param layer
	 */
	public void addLayer(Layer layer){
		this.mLayers.add(layer);
		layer.setMap(this);
		this.addView(layer);
		this.neesReLayout = true;
	}
	
	/**
	 * �Ƴ�һ��ͼ��
	 * @param layer
	 */
	public void removeLayer(Layer layer){
		this.mLayers.remove(layer);
		this.removeView(layer);
		this.neesReLayout = true;
	}
	
	public Boolean contain(Layer layer){
		return this.mLayers.contains(layer);
	}
	
	/**
	 * ����һ��ͼ��
	 * @param layers
	 */
	public void addLayers(Vector<Layer> layers){
		if(layers==null) return;
		for(int i=0;i<layers.size();i++){
			this.mLayers.add(layers.get(i));
			this.addView(layers.get(i));
		}
	}
	
	/**
	 * @return ȫ����ͼ��
	 */
	public Vector<Layer> getAllLayers(){
		return mLayers;
	}
	
	public void addHandler(Control control){
		control.mMap = this;
		this.mHandlers.add(control);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		
	}

	
	public double getResolution() {
		return mResolution;
	}


	public void setResolution(double mResolution) {
		this.mapWidth = (int) (360/mResolution);
		this.mapHeight = (int)(180/mResolution);
		this.mResolution = mResolution;
		this.reDraw();
		
		MapEvent event=new MapEvent(this, EventUtil.MAP_RESOLUTION_CHANGED, this);
		this.disPatchEvent(event);
	}
	

	
	public int getZoom(){
		return mZoom;
	}
	
	/**
	 * ���������ʾ�ȼ�
	 * @param zoom
	 */
	public void setMaxZoom(int zoom){
		maxZoom = zoom;	
	}
	
	/**
	 * ������С��ʾ�ȼ�
	 * @param zoom
	 */
	public void setMinZoom(int zoom){
		minZoom = zoom;
	}
	
	public void setZoom(int zoom){
		if(zoom > this.maxZoom || zoom < this.minZoom) return;
		if(this.mZoom == zoom)return;
		this.mZoom = zoom;
		this.mapWidth = (int) (minMapWidth*Math.pow(2,mZoom-1));
		setResolution((double)360/mapWidth);
		// Log.e("scale", ""+this.getScale());
//		 Log.e("level", ""+getZoomLevel(false));
	}
	
	
	
	public Bounds getViewBounds(){
		return getExtentForResolution(this.mResolution);
	}
	public Bounds getBounds(){
		return this.mProjection.getGeoBounds();
	}
	
	//----------------start-----------------------------------------
	public Location getMapCenter() {
		return mMapCenter;
	}


	public void setMapCenter(Location mMapCenter) {
		this.mMapCenter = mMapCenter;
		this.reDraw();
		MapEvent event=new MapEvent(this, EventUtil.MAP_CENTER_CHANGED, this);
		this.disPatchEvent(event);
		//mController.animateTo(mMapCenter);
		
		
	}
	public void zoomIn(){
		if(this.mZoom<this.maxZoom);
			//this.setZoom(mZoom+1);
		mMapAnimationControl.zoomIn();
	}
	
	public void zoomOut(){
		if(this.mZoom>this.minZoom);
			//this.setZoom(mZoom-1);
		mMapAnimationControl.zoomOut();
	}
	

	
	
	
	@Override
	protected void dispatchDraw(final Canvas c) {
		c.save();
		mRotateScaleMatrix.reset();
		c.translate(getScrollX(), getScrollY());
		mRotateScaleMatrix.preScale(mMultiTouchScale, mMultiTouchScale,
				mMultiTouchScalePoint.x, mMultiTouchScalePoint.y);
		//mRotateScaleMatrix.preRotate(mMultiTouchRotation, getWidth() / 2, getHeight() / 2);
		mRotateScaleMatrix.preRotate(0, getWidth() / 2, getHeight() / 2);
		//mRotateScaleMatrix.preSkew(0.0f, 0.5f);
		c.concat(mRotateScaleMatrix);
		// Restore the canvas matrix
		//c.restore();
		
		super.dispatchDraw(c);
	
		//�ַ��¼�
		MapEvent event=new MapEvent(this, EventUtil.MAP_SCALE_CHANGGE, new Object());
		this.disPatchEvent(event);
	}
	
	
	public boolean isAnimating() {
		return mIsAnimating.get();
	}
	
	public boolean canZoomIn() {
		if ((isAnimating() ? mTargetZoomLevel.get() : mZoom) >= maxZoom) {
			return false;
		}
		return true;
	}

	public boolean canZoomOut() {
		if ((isAnimating() ? mTargetZoomLevel.get() : mZoom) <= minZoom) {
			return false;
		}
		return true;
	}
	/**
	 * ��ȡ��ͼ�ȼ�
	 * @param aPending �Ƿ��ڵȴ���ͼ����
	 * @return
	 */
	public int getZoomLevel(final boolean aPending) {
		if (aPending && isAnimating()) {
			return mTargetZoomLevel.get();
		} else {
			return mZoom;
		}
	}
	

	public boolean isLayoutOccurred() {
		return mLayoutOccurred;
	}
	

	//-----------------------------end-----------------------------------
	

	/**
	 * �ƶ���ͼ
	 * @param deltaX ˮƽ�����ƶ����루��Ļ���꣩
	 * @param deltaY ��ֱ�����ƶ����루��Ļ���꣩
	 */
	public void move(int deltaX,int deltaY){
		this.mMapCenter.add(this.mResolution*deltaX, -this.mResolution*deltaY);
		this.setMapCenter(mMapCenter);
	}

	
	/**
	 * @return ����ֵ���ǵ�ͼView�Ŀ��ȣ��ǵ�ͼ�Ŀ��ȣ�������Ļ��Χ��Ĳ���
	 */
	public int getMapWidth(){
		return mapWidth;
	}
	
	/**
	 * @return ����ֵ���ǵ�ͼView�Ŀ��ȣ��ǵ�ͼ�ĸ߶ȣ�������Ļ��Χ��Ĳ���
	 */
	public int getMapHeight(){
		return mapHeight;
	}
	
	/**
	 * ���»���ͼ��
	 */
	public void reDraw(){
		if(mLayers==null) return;
		if(!readyForDraw) return;
		for(int i=0;i<mLayers.size();i++){
			mLayers.get(i).reDraw();
		}
	}
	
	/**
	 * ��������ת��Ļ����
	 * @param geoPoint
	 * @return
	 */
	public  Point transformMapPointToPixel(Location location){
		return this.mProjection.transformMapPointToPixel(location);
//		Location loc = location;
//		Point px = new Point();
//		Bounds b = this.getExtentForResolution(this.mResolution);
//		px.x = (int) ((loc.x - b.left)/mResolution);
//		px.y = (int) ((b.top - loc.y) /mResolution);
//		return px;
	}
	
	/**
	 * ��Ļ����ת��������
	 * @param point
	 * @return
	 */
	public Location transformPixelToMapPoint(Point point){
		return this.mProjection.transformPixelToMapPoint(point);
//		Bounds b = this.getExtentForResolution(this.mResolution);
//		double x = b.left + point.x*mResolution;
//		double y = b.top  - point.y*mResolution;
//		Location location = new Location(x,y);
//		return location;
	}
	
	/**
	 * 
	 * @param resolution
	 * @return ʹ�õ��������ʾ�Ŀɼ���Χ
	 */
	public Bounds getExtentForResolution(double resolution)
	{
			double w_deg = getWidth() * resolution;
			double h_deg = getHeight() * resolution;
			Bounds extent = new Bounds(mMapCenter.x - w_deg / 2,
					mMapCenter.y - h_deg / 2,
					mMapCenter.x + w_deg / 2,
					mMapCenter.y + h_deg / 2
					);
		return extent;
	}
	
	/**
	 * �����ͼ���ĵ㸽���ĵ���ֱ���
	 * @return ��ͼ�ĵ���ֱ��� ��λ M/cm
	 */
	public double getScale(){
		if(this.mMetrics==null){
			Log.e("openmaps","���ܻ�ȡ�豸DPI����,�����Map��setMetrics������");
			return 0.0;
		}
		float dpi  = this.mMetrics.xdpi;//ÿӢ����ٸ�����
		float dpcm  = dpi/2.54f; //ÿ���׶��ٸ����أ�1 Ӣ�磽0.0254��
		double res = this.mResolution;//ÿ�����ش������ٶ�
		double scale = dpcm*res*111000;  //ÿ��γ��ԼΪ111000m��
		return scale;
	}
	
	/**
	 * @param metric
	 */
	public void setMetrics(DisplayMetrics metric){
		this.mMetrics = metric;
	}
	
	/**
	 * �����¼�����
	 */
	public void addEventListener(int type,IEventListener listener){
		Vector<IEventListener> vector = this.mEventListeners.get(type);
		if(vector==null)
			vector = new Vector<IEventListener>();
		if(vector.contains(listener)) return;
		vector.add(listener);
		this.mEventListeners.put(type, vector);
	}
	
	/**
	 * �Ƴ��¼�����
	 */
	public void removeEventListener(int type,IEventListener listener){
		Vector<IEventListener> vector = this.mEventListeners.get(type);
		if(vector==null)
			return;
		vector.remove(listener);
	}
	
	/**
	 * �ɷ��¼�
	 * @param event
	 */
	public void disPatchEvent(BaseEvent event){
		Vector<IEventListener> vector = this.mEventListeners.get(event.getType());
		if(vector==null)
			return;
		for(int i=0;i<vector.size();i++){
			vector.get(i).respond(event);
		}
	}


	public float getMapZoomRatio() {
		return mapZoomRatio;
	}


	public void setMapZoomRatio(float ratio) {
		this.mapZoomRatio = ratio;
		this.minMapWidth  = (int) (512*mapZoomRatio);
		this.minMapHeight = (int) (256*mapZoomRatio);
	}

}