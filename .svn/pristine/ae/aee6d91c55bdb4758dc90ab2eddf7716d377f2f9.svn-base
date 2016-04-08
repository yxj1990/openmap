package com.openmaps.tile.source.offline;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.openmaps.tile.MapTile;
import com.openmaps.tile.source.TDTSource;



public class MapFileReader {
	protected static final int minFileSize  = 16384;
	
	/**
	 * 读取文件头
	 * @throws IOException 
	 */
	public static MapFileInfo readHead(String mapFilePath){
		File mapFile = new File(mapFilePath);
		MapFileInfo info = new MapFileInfo();
		if(!mapFile.exists()){
			Log.e("openmaps", "离线地图文件"+mapFilePath+"不存在");
			return null;
		}
		if(mapFile.length() < minFileSize){
			Log.e("openmaps", "离线地图文件"+mapFilePath+"格式错误");
			return null;
		} 
		try {
			info.mapFilePath = mapFilePath;
			FileInputStream fis = new FileInputStream(mapFile);
			byte[] b = new byte[minFileSize+4096];
			fis.read(b); 
			int offset = 4;
			byte[] tmp = new byte[4];
	//		tmp[0] = b[offset++];
	//		tmp[1] = b[offset++];
	//		tmp[2] = b[offset++];
	//		tmp[3] = b[offset++];
	//		tmp[0] = b[offset++];
	//		tmp[1] = b[offset++];
	//		tmp[2] = b[offset++];
	//		tmp[3] = b[offset++];
	//		//地图名称
	//		String name = "";
	//		for(int i=0;i<128;i++){
	//			tmp[0] = b[offset++];
	//			tmp[1] = b[offset++];
	//			name += (char) byte2Short(tmp);
	//		}
			
			offset = 268;
			//比例尺
			tmp[0] = b[offset++];
			tmp[1] = b[offset++];
			info.minZoomLevel =  byte2Short(tmp);
			tmp[0] = b[offset++];
			tmp[1] = b[offset++];
			info.maxZoomLevel = byte2Short(tmp);
	//		String centerLonStr="";
	//		for(int i=0;i<32;i++)  //272
	//		{//经度
	//			centerLonStr+=(char)b[offset++];
	//		}
	//		String centerLatStr="";
	//		for(int i=0;i<32;i++) //304
	//		{//经度
	//			centerLatStr+=(char)b[offset++];
	//		}
	//		
	//		String versionStr="";
	//		for(int i=0;i<8;i++)  //336
	//		{
	//			versionStr+=(char)b[offset++];
	//		}
		   	offset = 344;
			Vector<LevelInfo> levels = new Vector<LevelInfo>();
			for(int  i=0; i < 20; i++){
				LevelInfo level = new LevelInfo();
				tmp[0] = b[offset++]; tmp[1] = b[offset++]; 
				tmp[2] = b[offset++]; tmp[3] = b[offset++];
				level.minX  = byte2Int(tmp);
				tmp[0] = b[offset++]; tmp[1] = b[offset++]; 
				tmp[2] = b[offset++]; tmp[3] = b[offset++];
				level.maxX = byte2Int(tmp);
				tmp[0] = b[offset++]; tmp[1] = b[offset++]; 
				tmp[2] = b[offset++]; tmp[3] = b[offset++];
				level.minY = byte2Int(tmp);
				tmp[0] = b[offset++]; tmp[1] = b[offset++]; 
				tmp[2] = b[offset++]; tmp[3] = b[offset++];
				level.maxY = byte2Int(tmp);
				tmp[0] = b[offset++]; tmp[1] = b[offset++]; 
				tmp[2] = b[offset++]; tmp[3] = b[offset++];
				level.offset = byte2Int(tmp);
				levels.add(level);
			}
			info.levels = levels;
			fis.close();
			return info;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从文件中读取瓦片
	 * @param x
	 * @param y
	 * @param level
	 * @throws IOException
	 */
	public static  Bitmap getImage(MapTile tile,MapFileInfo fileInfo,String type){
		int x = tile.x;
		int y = tile.y;
		int level = tile.getZoomLevel();
		File mapFile = new File(fileInfo.mapFilePath);
		RandomAccessFile reader;
		try {
			reader = new RandomAccessFile(mapFile,"r");
			LevelInfo levelInfo = fileInfo.levels.get(level-1);
			int offset = levelInfo.offset							//索引表头
					+ ( (x - levelInfo.minX +
						(y-levelInfo.minY)*(levelInfo.maxX-levelInfo.minX+1) )  //相对索引表头的位置
						*16);	
			reader.seek(offset);
			byte[] b = new byte[16];
			reader.read(b);
			byte[] tmp = new byte[4];
			tmp[0] = b[0];
			tmp[1] = b[1];
			tmp[2] = b[2];
			tmp[3] = b[3];
			int mapOffset = byte2Int(tmp);
			tmp[0] = b[4];
			tmp[1] = b[5];
			tmp[2] = b[6];
			tmp[3] = b[7];
			int mapSize = byte2Int(tmp);
			
			tmp[0] = b[8];
			tmp[1] = b[9];
			tmp[2] = b[10];
			tmp[3] = b[11];
			int labelOffset = byte2Int(tmp);
			
			tmp[0] = b[12];
			tmp[1] = b[13];
			tmp[2] = b[14];
			tmp[3] = b[15];
			int labelSize = byte2Int(tmp);
			
			int seeker = mapOffset;
			int dataSize = mapSize;
			//注记层数据
			if(type.equals(TDTSource.TYPE_CIA_C)||type.equals(TDTSource.TYPE_CVA_C)
					||type.equals(TDTSource.TYPE_CIA_W)||type.equals(TDTSource.TYPE_CVA_W)){
				seeker = labelOffset;
				dataSize = labelSize;
			}
			
			
			reader.seek(seeker);
			byte[] imgByte = new byte[dataSize];
			reader.read(imgByte,0,dataSize);
			reader.close();
			
			Bitmap bitmap =  BitmapFactory.decodeByteArray(imgByte,0,imgByte.length);
			return bitmap;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfMemoryError error){  //捕获BitmapFactory.decodeByteArray的内存不足错误
			
		}
		return null;
	}

	  public static int byte2Int(byte[] paramArrayOfByte)
	    {
	       short[] arrayOfShort = new short[4];
	       for (int i = 0; i < 4; ++i){
	    	   	arrayOfShort[i] = ((paramArrayOfByte[i] >= 0) ? (short)paramArrayOfByte[i] : (short)(256 + paramArrayOfByte[i]));
	       }
	       return arrayOfShort[0] + (arrayOfShort[1] << 8) + (arrayOfShort[2] << 16) + (arrayOfShort[3] << 24);
	    }
	   
	  public static short byte2Short(byte[] paramArrayOfByte){
	       short[] arrayOfShort;
	       (arrayOfShort = new short[2])[
	         0] = ((paramArrayOfByte[0] >= 0) ? (short)paramArrayOfByte[0] : (short)(256 + paramArrayOfByte[0]));
	      arrayOfShort[1] = ((paramArrayOfByte[1] >= 0) ? (short)paramArrayOfByte[1] : (short)(256 + paramArrayOfByte[1]));
	       return (short)(arrayOfShort[0] + (arrayOfShort[1] << 8));
	  }
}
