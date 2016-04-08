package com.openmaps.data.shp;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.openmaps.geometry.Bounds;



/**
 * @author 
 * @version 1.0
 */
public class ShapefileHeader
{
	public static final int HEADER_START_CODE =0x0000270A;
	public static final int VERSION_CODE = 1000;
   
	private int fileCode = HEADER_START_CODE;
    private int version = VERSION_CODE;
    private int fileLength = 0;
    private int shpType = 0;

    private Bounds box = null;

    public int getFileCode()
    {
        return fileCode;
    }

    public void setFileCode( int fileCode )
    {
        this.fileCode = fileCode;
    }

    public int getFileLength()
    {
        return fileLength;
    }

    public int getShpType()
    {
        return shpType;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion( int version )
    {
        this.version = version;
    }

    public void setShpType( int shpType )
    {
        this.shpType = shpType;
    }


    public void setFileLength( int fileLength )
    {
        this.fileLength = fileLength;
    }

    public Bounds getBox()
    {
        return box;
    }

    public void setBox( Bounds box )
    {
        this.box = box;
    }
    /**
     * @todo 读取文件头
     * @param dis
     * @return
     * @throws IOException
     */
    public static ShapefileHeader readerHeader( DataInputStream dis ) throws IOException
    {
        //代码
        int fileCode = dis.readInt();

        //长度
        dis.skipBytes( 20 );
        int length = dis.readInt();

        //版本
        byte[] w = new byte[4];
        dis.read( w,0,4 );
        int version = LittleEndianReader.readInt( w );

        //类型
        dis.read( w,0,4 );
        int shpType = LittleEndianReader.readInt( w );
        System.out.println("shapte_type:" + shpType);

        //minx
        byte[] d = new byte[8];
        dis.read( d,0,8 );
        double minx = LittleEndianReader.readDouble( d );
        System.out.println("minx:" + minx);

        //miny
        dis.read( d,0,8 );
        double miny = LittleEndianReader.readDouble( d );
        System.out.println("miny:" + miny);

        //maxx
        dis.read( d,0,8 );
        double maxx = LittleEndianReader.readDouble( d );
        System.out.println("maxx:" + maxx);

        //maxy
        dis.read( d,0,8 );
        double maxy = LittleEndianReader.readDouble( d );
        System.out.println("maxy:" + maxy);

        //文件头对象
        ShapefileHeader header = new ShapefileHeader();
        header.setFileCode( fileCode );
        header.setFileLength( length );
        header.setVersion( version );
        header.setShpType( shpType );
        header.setBox( new Bounds( minx,miny,maxx,maxy ) );

        dis.skip( 32 ); //跳过剩下了32个字节
        return header;
    }
    /**
     * 写shp文件头
     * @param header
     * @param stream
     * @throws Exception 
     */
	public static  void writerHeader(ShapefileHeader header,BufferedOutputStream stream) throws Exception{
		int fileCode=header.getFileCode();
		int length=header.getFileLength();
		int version=header.getVersion();
		int shpType=header.getShpType();
		Bounds bounds=header.getBox();

		try {
			byte[] bb=new byte[20];
			for(int i=0;i<bb.length;i++){
				bb[i]=0;
			}
			stream.write(LittleEndianReader.putBigEndianInt(fileCode));
			stream.write(bb);
			stream.write(LittleEndianReader.putBigEndianInt(length));
			stream.write(LittleEndianReader.putLittleEndianInt(version, 0), 0, 4);
			stream.write(LittleEndianReader.putLittleEndianInt(shpType, 0), 0, 4);
			stream.write(LittleEndianReader.putDouble(bounds.left, 0));
			stream.write(LittleEndianReader.putDouble(bounds.bottom, 0));
			stream.write(LittleEndianReader.putDouble(bounds.right, 0));
			stream.write(LittleEndianReader.putDouble(bounds.top, 0));
			
			bb=new byte[32];
			for(int i=0;i<bb.length;i++){
				bb[i]=0;
			}
			stream.write(bb);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}