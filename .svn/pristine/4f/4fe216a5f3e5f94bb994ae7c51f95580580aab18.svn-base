package com.soft2com.util.shape;

import java.io.DataInputStream;
import java.io.IOException;

import com.soft2com.map.geom.CBox;
import com.soft2com.map.geom.CPoint;
import com.soft2com.map.geom.CPolyLine;
import com.soft2com.map.geom.CPolygon;
import com.soft2com.map.geom.MultiPoint;
import com.soft2com.vjavalib.vutil.*;
import java.io.FileWriter;

/**
 *
 * <p>Title: shape文件阅读器</p>
 * <p>Description:解析shape主文件</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author 
 * @version 1.0
 */
public class ShapeReader
{
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
        header.setBox( new CBox( minx,miny,maxx,maxy ) );

        dis.skip( 32 ); //跳过剩下了32个字节
        return header;
    }


    /**
     * @todo 读取shape文件内容
     * 当前一个shp文件类型只能为一种
     * @param dis shp文件记录体数据流
     * @throws IOException
     */
    public static VList readRecord( DataInputStream dis ) throws IOException
    {
        //记录集合
        VList records = new VArrayList();
        Object obj = null;
        while( dis.available() > 0 )
        {
            obj = readRecordXOffset( 0,dis );
            records.add( obj );
        }

        return records;
    }

    /**
     * @todo 根据offset读取一条记录
     * @param offset 偏移量以word计数
     * @param dis shp文件数据流
     * @throws IOException
     */
    public static Object readRecordXOffset( int offset,DataInputStream dis ) throws IOException
    {
        dis.skip( offset*2 );

        //记录编号
        int recNum = dis.readInt();

        //记录长度
        int recLength = dis.readInt();

        //类型
        byte[] w = new byte[4];
        dis.read( w, 0, 4 );
        int shpType = LittleEndianReader.readInt( w );

        Object rec = null;
        switch ( shpType )
        {
            case ShapeType.POLYGON:
                rec = readPolygon( dis );
                break;
            case ShapeType.POINT:
                rec = readPoint( dis );
                break;
            case ShapeType.POLYLINE:
                rec = readPolyLine( dis );
                break;
            default:
                break;
        }

        return rec;
    }

    /**
     * @todo 读取一条polygon记录
     * @param dis shp记录体数据流
     * @return 多边形数据对象
     * @throws IOException
     */
    public static CPolygon readPolygon( DataInputStream dis ) throws IOException
    {
        //
        CBox box = readBox( dis );

        byte[] w = new byte[4];
        dis.read( w,0,4 );
        int numParts = LittleEndianReader.readInt( w );

        dis.read( w,0,4 );
        int numPoints = LittleEndianReader.readInt( w );

        //
        int[] partIndex = new int[numParts];
        int index = 0;
        for( int i = 0; i < numParts;i++ )
        {
            dis.read( w,0,4 );
            index = LittleEndianReader.readInt( w );
            partIndex[i] = index;
        }

        //
        CPoint pt = null;
        CPoint[] pts = new CPoint[numPoints];
        for( int i = 0; i < numPoints; i++ )
        {
            pt = readPoint( dis );
            pts[i] = pt;
        }

        return new CPolygon( box,numParts,numPoints,partIndex,pts );
    }

    /**
     * @todo 读取一条PolyLine记录
     * @param dis shp记录体数据流
     * @return 多线形对象
     * @throws IOException
     */
    public static CPolyLine readPolyLine( DataInputStream dis ) throws IOException
    {
        CBox box = readBox( dis );

        byte[] w = new byte[4];
        dis.read( w,0,4 );
        int numParts = LittleEndianReader.readInt( w );

        dis.read( w,0,4 );
        int numPoints = LittleEndianReader.readInt( w );

        //
        int[] partIndex = new int[numParts];
        int index = 0;
        for( int i = 0; i < numParts;i++ )
        {
            dis.read( w,0,4 );
            index = LittleEndianReader.readInt( w );
            partIndex[i] = index;
        }

        //
        CPoint pt = null;
        CPoint[] pts = new CPoint[numPoints];
        for( int i = 0; i < numPoints; i++ )
        {
            pt = readPoint( dis );
            pts[i] = pt;
        }

        return new CPolyLine( box,numParts,numPoints,partIndex,pts );
    }


    /**
     * @todo 读取边界
     * @param dis shp记录体数据流
     * @return 边界数据对象
     * @throws IOException
     */
    private static CBox readBox( DataInputStream dis )  throws IOException
    {
        //minx
        byte[] d = new byte[8];
        dis.read( d,0,8 );
        double minx = LittleEndianReader.readDouble( d );

        //miny
        dis.read( d,0,8 );
        double miny = LittleEndianReader.readDouble( d );

        //maxx
        dis.read( d,0,8 );
        double maxx = LittleEndianReader.readDouble( d );

        //maxy
        dis.read( d,0,8 );
        double maxy = LittleEndianReader.readDouble( d );

        return new CBox( minx,miny,maxx,maxy );
    }

    /**
     * @todo 读取一条Point类型记录
     * @param dis shp记录体数据流
     * @return 点数据对象
     * @throws IOException
     */
    public static CPoint readPoint( DataInputStream dis )  throws IOException
    {
        byte[] d = new byte[8];
        dis.read( d,0,8 );
        double x = LittleEndianReader.readDouble( d );

        dis.read( d,0,8 );
        double y = LittleEndianReader.readDouble( d );

        return new CPoint( x,y );
    }

    /**
     * @todo 读取一条MultiPoint记录
     * @param dis shp记录体数据流
     * @return 多点数据对象
     * @throws IOException
     */
    public static MultiPoint readMultiPoint( DataInputStream dis )  throws IOException
    {
        CBox box = readBox( dis );

        byte[] w = new byte[4];
        dis.read( w,0,4 );
        int numPoints = LittleEndianReader.readInt( w );

        CPoint pt = null;
        CPoint[] pts = new CPoint[numPoints];
        for( int i = 0; i < numPoints; i++ )
        {
            pt = readPoint( dis );
            pts[i] = pt;
        }

        return new MultiPoint( box,numPoints,pts );
    }

    /**
     * @todo 用于计算总记录数
     * @param dis
     * @throws IOException
     */
    public static void outputAllTypes( DataInputStream dis ) throws IOException
    {
        boolean flag = true;
        while( flag )
        {
            int recNum = dis.readInt();

            int recLength = dis.readInt();

            byte[] w = new byte[4];
            dis.read( w, 0, 4 );
            int shpType = LittleEndianReader.readInt( w );

            int available = dis.available();
            if( available <= recLength *2 -4 )
            {
                flag = false;
            }
            else
            {
                dis.skip( recLength * 2 - 4 );
            }
        }

    }

    public static void main( String[] args ) throws Exception
    {
//        String filePath = "F:/map_web/webmapdata/data/a_region/a_region.shp";
        String filePath = "C:/mapconverter/a_region.shp";
        java.io.FileInputStream fis = new java.io.FileInputStream( filePath );
        java.io.DataInputStream dis = new java.io.DataInputStream( fis );

        ShapefileHeader header = readerHeader( dis );
        java.io.BufferedWriter fw = new java.io.BufferedWriter(new FileWriter("f:\\cors.txt") );

        VList list = readRecord( dis );
        for( int i = 0; i< list.size(); i++ )
        {
            CPolygon p = (CPolygon)list.get( i );
            CPoint[] pts = p.getPts();
            for( int j = 0; j < pts.length; j++ )
            {
                fw.write( pts[j].getX() + "," + pts[j].getY() + "\r\n" );
            }
        }
        fw.flush();
    }
}
