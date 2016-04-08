package com.openmaps.data.shp;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.basetypes.Location;

/**
 * 
 * @author yxj
 *2015.1.9
 */
public class ShpWriter {
	private BufferedOutputStream stream;

	public ShpWriter(String fileName,ShapefileHeader header) throws Exception{
		try {
			this.stream=new BufferedOutputStream(new FileOutputStream(fileName));
			ShapefileHeader.writerHeader(header, stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ShpWriter(BufferedOutputStream stream,ShapefileHeader header) throws Exception{
		this.stream=stream;
		ShapefileHeader.writerHeader(header, stream);
	}
	
	public void close(){
		try {
			this.stream.flush();
			this.stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void addWriterGeomertry(int i,int len,Geometry geo) throws Exception{
		try {
			//从2*offset开始记录
			//记录编码
			this.stream.write(LittleEndianReader.putBigEndianInt(i));
			//记录长度
			this.stream.write(LittleEndianReader.putBigEndianInt(len));
			if(geo instanceof GeoPoint){
				this.stream.write(LittleEndianReader.putLittleEndianInt(ShapeType.POINT,0));
				writePoint((GeoPoint)geo);
			}else if(geo instanceof GeoPolyline){
				this.stream.write(LittleEndianReader.putLittleEndianInt(ShapeType.POLYLINE,0));
				writerPolyLine((GeoPolyline)geo);
			}else if(geo instanceof GeoPolygon){
				this.stream.write(LittleEndianReader.putLittleEndianInt(ShapeType.POLYGON,0));
				writerPolygon((GeoPolygon)geo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writerPolygon(GeoPolygon geo) throws Exception{
		try {
			//记录要素范围
			writerBox(geo.getBounds());
			//记录有几个部分
			this.stream.write(LittleEndianReader.putLittleEndianInt(1,0));
			//记录节点数目
			this.stream.write(LittleEndianReader.putLittleEndianInt(geo.getComponents().size(),0));
			//记录每部分的指针
			this.stream.write(LittleEndianReader.putLittleEndianInt(0,0));
			for(int i=0;i<geo.getComponents().size();i++){
				writeLocation(geo.getComponents().get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writerPolyLine(GeoPolyline geo) throws Exception{
		try {
			//记录要素范围
			writerBox(geo.getBounds());
			//记录有几个部分
			this.stream.write(LittleEndianReader.putLittleEndianInt(1,0));
			//记录节点数目
			this.stream.write(LittleEndianReader.putLittleEndianInt(geo.getComponents().size(),0));
			//记录每部分的指针
			this.stream.write(LittleEndianReader.putLittleEndianInt(0,0));
			for(int i=0;i<geo.getComponents().size();i++){
				writeLocation(geo.getComponents().get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writePoint(GeoPoint point){
		writeLocation(point.location);
	}

	private void writeLocation(Location point){
		try {
			this.stream.write(LittleEndianReader.putDouble(point.x, 0));
			this.stream.write(LittleEndianReader.putDouble(point.y, 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 写要素的bounds
	 * @param bounds
	 */
	private void writerBox(Bounds bounds){
		try {
			this.stream.write(LittleEndianReader.putDouble(bounds.left, 0));
			this.stream.write(LittleEndianReader.putDouble(bounds.bottom, 0));
			this.stream.write(LittleEndianReader.putDouble(bounds.right, 0));
			this.stream.write(LittleEndianReader.putDouble(bounds.top, 0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
