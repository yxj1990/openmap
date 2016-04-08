package com.openmaps.data.shp;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * shxÎÄ¼þÐ´Èë
 * @author yxj
 *
 */
public class ShxWriter {
	private BufferedOutputStream stream;
	public ShxWriter(String fileName,ShapefileHeader header) throws Exception{
		try {
			this.stream=new BufferedOutputStream(new FileOutputStream(fileName));
			ShapefileHeader.writerHeader(header, stream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public ShxWriter(BufferedOutputStream stream,ShapefileHeader header) throws Exception{
		this.stream=stream;
		ShapefileHeader.writerHeader(header, stream);
	}

	public void addRecord(int offset,int recordLength) throws Exception{
		try {
			this.stream.write(LittleEndianReader.putBigEndianInt(offset));
			this.stream.write(LittleEndianReader.putBigEndianInt(recordLength));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
