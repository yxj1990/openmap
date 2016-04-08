package com.openmaps.data.shp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.layer.VectorLayer;


/**
 * shp文件写出类
 * @author yxj
 *
 */
public class ShapeFileWriter {



	private VectorLayer layer;
	private ShpWriter _shpWriter;
	private ShxWriter _shxWriter;
	private DBFWriter _dbfWriter;

	public ShapeFileWriter(String Filepath,String fileName,VectorLayer layer) throws Exception{
		this.layer=layer;

		JDBField[]  jdbField=new JDBField[layer.getAttributeKeys().size()];
		for(int i=0;i<layer.getAttributeKeys().size();i++){
			jdbField[i]=new JDBField(layer.getAttributeKeys().get(i),'C',100,0);
		}

		String shxFile = new StringBuffer( Filepath ).append( "/" ).append( fileName ).append(".shx" ).toString();
		String shpFile = new StringBuffer( Filepath ).append( "/" ).append( fileName ).append(".shp" ).toString();
		String dbfFile = new StringBuffer( Filepath ).append( "/" ).append( fileName ).append(".dbf" ).toString();

		_shpWriter=new ShpWriter(shpFile, getShapeFileHeader());
		_shxWriter=new ShxWriter(shxFile, getShapeFileHeader());
		_dbfWriter=new DBFWriter(dbfFile, jdbField);

		writerFile();
	}

	/**
	 * 写入shpFile文件
	 * @throws Exception 
	 */
	private void writerFile() throws Exception{
		int offset=50;
		try {
			for(int i=0;i<layer.getFeatures().size();i++){
				Geometry geo=layer.getFeatures().get(i).getGeometry() ;
				int len;

				if(geo instanceof GeoPolygon){
					len=(((GeoPolygon)geo).getComponents().size()*16+32+12+4)/2;
					_shpWriter.addWriterGeomertry(i+1,len,layer.getFeatures().get(i).getGeometry());
					_dbfWriter.addRecord(layer.getFeatures().get(i).getAttributes().values().toArray());
					_shxWriter.addRecord(offset, len);
					offset+=len+4;
				}else if(geo instanceof GeoPolyline){
					len=(((GeoPolyline)geo).getComponents().size()*16+32+12+4)/2;
					_shpWriter.addWriterGeomertry(i+1,len,layer.getFeatures().get(i).getGeometry());
					_dbfWriter.addRecord(layer.getFeatures().get(i).getAttributes().values().toArray());
					_shxWriter.addRecord(offset, len);
					offset+=len+4;
				}else if(geo instanceof GeoPoint){
					len=10;
					_shpWriter.addWriterGeomertry(i+1,len,layer.getFeatures().get(i).getGeometry());
					_dbfWriter.addRecord(layer.getFeatures().get(i).getAttributes().values().toArray());
					_shxWriter.addRecord(offset, len);
					offset+=len+4;
				}
			} 
			_dbfWriter.close();
			_shpWriter.close();
			_shxWriter.close();
		}catch (JDBFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 生成头文件
	 * @return
	 */
	private ShapefileHeader getShapeFileHeader(){
		ShapefileHeader header=new ShapefileHeader();
		int offset=50;
		Bounds bounds=null; 
		for(int i=0;i<layer.getFeatures().size();i++){
			Geometry geo=layer.getFeatures().get(i).getGeometry() ;
			int len;

			if(geo instanceof GeoPolygon){
				len=(((GeoPolygon)geo).getComponents().size()*16+32+12+4)/2;
				offset+=len+4;
				bounds=Bounds.getUnionBounds(bounds, ((GeoPolygon)geo).getBounds());
				header.setShpType(ShapeType.POLYGON);
			}else if(geo instanceof GeoPolyline){
				len=(((GeoPolyline)geo).getComponents().size()*16+32+12+4)/2;
				offset+=len+4;
				bounds=Bounds.getUnionBounds(bounds, ((GeoPolyline)geo).getBounds());
				header.setShpType(ShapeType.POLYLINE);
			}else if(geo instanceof GeoPoint){
				len=10;
				offset+=len+4;
				bounds=Bounds.getUnionBounds(bounds, ((GeoPoint)geo).getBounds());
				header.setShpType(ShapeType.POINT);
			}
		} 

		
		header.setFileLength(offset);
		header.setBox(bounds);
		return header;
	}
}
