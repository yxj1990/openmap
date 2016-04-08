package com.openmaps.geometry.basetypes;

public class Unit {
	public static final String SEXAGESIMAL = "dms";
	public static final String DEGREE = "degrees";
	public static final String METER = "m";
	public static final String KILOMETER = "km";
	public static final String CENTIMETER = "cm";
	public static final String FOOT = "ft";
	public static final String MILE = "mi";
	public static final String INCH = "inch";
	public static final String RADIAN = "rad";
	public static final String NAUTIC_MILE = "nmi";
	public static double PIXEL_SIZE =0.00026488;//0.00028;  0.00026488;


	public static double getScaleDenominatorFromResolutionToMeter(double resolution) {

		return (resolution / Unit.PIXEL_SIZE);
	}
	public static double getScaleDenominatorFromResolutionToDegree(double resolution) {

		return (resolution*111319.4908 / Unit.PIXEL_SIZE);
	}

	/*
	 * 根据以米为单位的比例尺获取地图的分辨率
	 * @scale  地图比例尺
	 * @dpi 屏幕分辨率
	 */
	public static double getResolutionFromScaleToMeter(double scale, double dpi) {
		double normScale = (scale > 1.0) ? (1.0 / scale): scale;
		double resolution = 1 / (normScale * 39.3700787* dpi);
		return resolution;
	}
	/*
	 * 根据以度为单位的比例尺获取地图的分辨率
	 * @scale  地图比例尺
	 * @dpi 屏幕分辨率
	 */
	public static double getResolutionFromScaleToDegree(double scale, double dpi) {

		double normScale = (scale > 1.0) ? (1.0 / scale): scale;
		double resolution = 1 / (normScale * 4374754* dpi);
		return resolution;
	}

	/*
	 * 根据地图分辨率获取到以米为单位的比例尺
	 * @scale  地图比例尺
	 * @dpi 屏幕分辨率
	 */
	public static double getScaleFromResolutionToMeter( double resolution, double dpi) {
		return resolution * 39.3700787 * dpi;
	}
	/*
	 * 根据地图的分辨率获取地图比例尺（单位为度）
	 * @scale  地图比例尺
	 * @dpi 屏幕分辨率
	 */
	public static double getScaleFromResolutionToDegree( double resolution, double dpi) {
		return resolution * 4374754 * dpi;
	}
	
	
	public static double getResolutionOnCenter(double resolution,Location center)
	{
		double res = resolution;
		res*= 111319.490793273573*Math.cos(center.y*0.0174532925199432958);
		return res;
	}
}
