package com.openmaps.tile.source;

import com.openmaps.tile.MapTile;

public class TDTSource extends OnlineTileSource {
	/**矢量底图 经纬度投影*/
	public static final String TYPE_VEC_C = "vec_c";
	/**中文注记 经纬度投影*/
	public static final String TYPE_CVA_C = "cva_c";
	/**英文注记 经纬度投影*/
	public static final String TYPE_EVA_C = "eva_c";
	
	/**矢量底图 墨卡托投影*/
	public static final String TYPE_VEC_W = "vec_w";
	/**中文注记 墨卡托投影*/
	public static final String TYPE_CVA_W = "cva_w";
	/**英文注记 墨卡托投影*/
	public static final String TYPE_EVA_W = "eva_w";
	
	
	/**影像底图 经纬度投影*/
	public static final String TYPE_IMG_C = "img_c";
	/**影像中文注记 经纬度投影*/
	public static final String TYPE_CIA_C = "cia_c";
	/**影像 英文注记 经纬度投影*/
	public static final String TYPE_EIA_C = "eia_c";
	
	/**影像底图 墨卡托投影*/
	public static final String TYPE_IMG_W = "img_w";
	/**影像中文注记 墨卡托投影*/
	public static final String TYPE_CIA_W = "cia_w";
	/**影像 英文注记 墨卡托投影*/
	public static final String TYPE_EIA_W = "eia_w";
	
	/** 地形  经纬度 */
	public static final String TYPE_TER_C = "ter_c";
	/** 地形注记  经纬度 */
	public static final String TYPE_CTA_C = "cta_c";
	/** 地形  墨卡托 */
	public static final String TYPE_TER_W = "ter_w";
	/** 地形注记  墨卡托 */
	public static final String TYPE_CTA_W = "cta_w";
	

	protected String mMapType;
	public TDTSource(String name, int minZoom, int maxZoom, int tileSize,
			String[] baseURLs,String mapType) {
		super(name, minZoom, maxZoom, tileSize, baseURLs);
		this.mMapType = mapType;
	}
	
	@Override
	public String getURLString(MapTile tile){
		return getBaseURL()+String.format("?T=%s&x=%d&y=%d&l=%d",mMapType,tile.x,tile.y,tile.getZoomLevel()); 
	}

}
