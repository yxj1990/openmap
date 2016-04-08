package com.openmaps.data.shp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import android.os.Environment;

import com.openmaps.feature.Feature;
import com.openmaps.feature.LineFeature;
import com.openmaps.feature.PointFeature;
import com.openmaps.feature.PolygonFeature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
/**
 * @author ������
 * @version 1.0
 */
public class ShapeFileReader
{
	private String shx = null;
	private String shp = null;
	private String dbf = null;

	@SuppressWarnings("rawtypes")
	private ArrayList dbfRecords = null;
	@SuppressWarnings("rawtypes")
	private ArrayList shpRecords = null;
	@SuppressWarnings("rawtypes")
	private ArrayList fieldList = null;
	private ShapefileHeader header = null;
	private java.io.DataInputStream shpdis = null;
	private java.io.DataInputStream shxdis = null;
	private DBFReader dbfReader = null;


	/**
	 * @todo ��ʼ����ͼ������Ϣ
	 * @param pathName �����ļ�·��, eg. c:\\mapdata
	 * @param datasetName ���ݼ����� eg. res2_4m
	 */
	public ShapeFileReader( String pathName, String datasetName ) throws FileNotFoundException,JDBFException
	{
		this.shx = new StringBuffer( pathName ).append( "/" ).append( datasetName ).append(".shx" ).toString();
		this.shp = new StringBuffer( pathName ).append( "/" ).append( datasetName ).append(".shp" ).toString();
		this.dbf = new StringBuffer( pathName ).append( "/" ).append( datasetName ).append(".dbf" ).toString();

		FileInputStream shpfis = new java.io.FileInputStream(this.shp );
		this.shpdis = new java.io.DataInputStream(shpfis);
		java.io.FileInputStream shxfis = new java.io.FileInputStream( this.shx );
		this.shxdis = new java.io.DataInputStream(shxfis);
		this.dbfReader = new DBFReader( this.dbf );

		
//		try {
//			getShapeRecord(5);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	/**
	 * ��ȡ����ͼ���Ҫ��(����Geomertry������)
	 * @return
	 * @throws IOException 
	 */
	public ArrayList<Feature> getAllFeature() throws IOException{
		ArrayList<Feature> arrFeature=new ArrayList<Feature>();
		try {
			getAllDBFRecords();
			getAllShapeRecords();
			Object obj;
			Feature f=null;
			for(int i=0;i<this.shpRecords.size();i++){
				obj=this.shpRecords.get(i);
				Object[] dbfRecord = (Object[])this.dbfRecords.get(i);
				if(obj instanceof GeoPoint){
					f=new PointFeature(null);
					f.setGeometry((GeoPoint) obj);
				}else if(obj instanceof GeoPolyline){
					f=new LineFeature(null);
					f.setGeometry((GeoPolyline) obj);
				}else if(obj instanceof GeoPolygon){
					f=new PolygonFeature(null);
					f.setGeometry((GeoPolygon) obj);
				}
				for(int j=0;j<fieldList.size();j++){
					f.putAttribute((String)fieldList.get(j), (String)dbfRecord[j]);
				}
				arrFeature.add(f);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return arrFeature;
	}

	
	/**
	 * @todo ��shp�ļ���ȡ��Geometryȫ����¼
	 * ����Ԫ������ΪGeoPolygon,GeoPoint��GeoLine
	 * @return ��¼����
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public ArrayList getAllShapeRecords() throws IOException
	{
		if( null == this.shpRecords ){
			getShapeHeader();
			this.shpRecords = ShapeReader.readRecord( shpdis );
		}
		return this.shpRecords;
	}

	/**
	 * @todo ȡ��ȫ��dbf��¼,��������Ҫ�ص�����
	 * ��¼����ΪObject[],�ֶ�ֵ����
	 * @return ���м�¼�ļ���
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayList getAllDBFRecords()
	{
		if(this.dbfRecords==null){

			try
			{
				dbfRecords=new ArrayList();
				Object[] object = null;
				while ( dbfReader.hasNextRecord() )
				{
					object = dbfReader.nextRecord();
					dbfRecords.add( object );
				}
				int fieldCount = this.dbfReader.getFieldCount();
				this.fieldList = new ArrayList();
				for( int i = 0; i < fieldCount;i++ ){
					this.fieldList.add(this.dbfReader.getField( i ).getName());
				}
			}
			catch ( JDBFException ex )
			{
				ex.printStackTrace();
			}}
		return this.dbfRecords;
	}
	/**
	 * @todo ȡ��shape�ļ�ͷ����
	 * @return
	 * @throws IOException
	 */
	private ShapefileHeader getShapeHeader() throws IOException
	{
		if( null == this.header )
		{
			this.header = ShapefileHeader.readerHeader( shpdis );
		}
		return this.header;
	}
	
	
	
	
	//���·���������ʹ�ã����Ǹо�û�д��ô�������ķ����Ѿ���ʹ����
	/**
	 * ��������ȡ��һ����¼,������0��ʼ(����Geometry�����ԣ�
	 * @param index ��¼������
	 * @return shape��Ϣ����������Ϣ�ͼ�¼��Ϣ
	 */
	public ShapeInfoObject getShapeRecord( int index ) throws IOException
	{
		int[] index2 = ShxIndexReader.getIndex( index, shxdis );

		Object obj = ShapeReader.readRecordXOffset( index2[0], shpdis );
		//ȡdbf����
		Object[] fields = this.getDBFRecord( index );
		return new ShapeInfoObject( obj,fields );
	}

	/**
	 * @todo ��dbf�ļ���ȡ��һ����¼,������0��ʼ,����ĳһ��Ҫ�ص�����
	 * @param index ��¼������
	 * @return ��¼��Ϣ����
	 */
	public Object[] getDBFRecord( int index )
	{
		if( null == this.dbfRecords )
		{
			this.dbfRecords = getAllDBFRecords();
		}
		return (Object[])this.dbfRecords.get( index );
	}

	

//
//	/**
//	 * @todo ȡ��shape�߽�
//	 * @return
//	 * @throws IOException
//	 */
//	public Bounds getBox() throws IOException
//	{
//		if( null == this.header )
//		{
//			this.header = ShapeReader.readerHeader( shpdis );
//		}
//		return this.header.getBox();
//	}
//
//
//	/**
//	 * @todo ȡ�ö�����dbf�ļ��ж�Ӧ���ֶ�ֵ
//	 * @param feature ��ͼ���ζ���
//	 * @param fieldName �ֶ�����
//	 * @return
//	 */
//	public String getFieldValue( Object feature, String fieldName ) throws IOException
//	{
//		if( null == this.shpRecords )
//		{
//			getAllShapeRecords();
//		}
//
//		int index = this.shpRecords.indexOf( feature );
//
//		Object[] dbfRecord = this.getDBFRecord( index );
//		return dbfRecord[this.fieldList.indexOf( fieldName )].toString();
//	}


	public static void main( String[] args ) throws Exception
	{
		ShapeFileReader t = new ShapeFileReader( Environment.getExternalStorageDirectory().getAbsolutePath()+"/shp", "DL_L" );
		t.getAllDBFRecords();
		t.getAllShapeRecords();

	}
}
