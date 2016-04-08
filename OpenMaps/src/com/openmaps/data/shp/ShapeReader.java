package com.openmaps.data.shp;

import java.io.DataInputStream;
import java.io.IOException;

import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.basetypes.Location;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author 
 * @version 1.0
 */
public class ShapeReader
{


    /**
     * @todo ��ȡshape�ļ�����(���ص��ǵ㡢�ߡ������)
     * ��ǰһ��shp�ļ�����ֻ��Ϊһ��
     * @param dis shp�ļ���¼��������
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList readRecord( DataInputStream dis ) throws IOException
    {
        //��¼����
    	ArrayList records = new ArrayList();
        Object obj = null;
        while( dis.available() > 0 )
        {
            obj = readRecordXOffset( 0,dis );
            records.add( obj );
        }

        return records;
    }

    /**
     * @todo ����offset��ȡһ����¼(���ص��ǵ㡢�ߡ������)
     * @param offset ƫ������word����
     * @param dis shp�ļ�������
     * @throws IOException
     */
    public static Object readRecordXOffset( int offset,DataInputStream dis ) throws IOException
    {
    	dis.skip( offset*2 );

        //��¼���
        int recNum = dis.readInt();

        //��¼����
        int recLength = dis.readInt();

        //����
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
     * @todo ��ȡһ��polygon��¼
     * @param dis shp��¼��������
     * @return ��������ݶ���
     * @throws IOException
     */
    private static GeoPolygon readPolygon( DataInputStream dis ) throws IOException
    {
        //
    	Bounds box = readBox( dis );

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
        Location pt = null;
       Vector<Location> pts = new  Vector<Location>();
        for( int i = 0; i < numPoints; i++ )
        {
            pt = readLocation( dis );
            pts.add(pt);
        } 
        GeoPolygon polygon=new GeoPolygon();
        polygon.setComponents(pts);
        return polygon;
    }

    /**
     * @todo ��ȡһ��PolyLine��¼
     * @param dis shp��¼��������
     * @return �����ζ���
     * @throws IOException
     */
    private static GeoPolyline readPolyLine( DataInputStream dis ) throws IOException
    {
    	Bounds box = readBox( dis );

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
        Location pt = null;
        Vector<Location> pts = new Vector<Location>();
        for( int i = 0; i < numPoints; i++ )
        {
            pt = readLocation( dis );
            pts.add(pt);
        }
        GeoPolyline line=new GeoPolyline();
        line.setComponents(pts);
        
        return line;
    }


    /**
     * @todo ��ȡ�߽�
     * @param dis shp��¼��������
     * @return �߽����ݶ���
     * @throws IOException
     */
    private static Bounds readBox( DataInputStream dis )  throws IOException
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

        return new Bounds( minx,miny,maxx,maxy );
    }

    /**
     * @todo ��ȡһ��Point���ͼ�¼,���ص�������GeoPoint
     * @param dis shp��¼��������
     * @return �����ݶ���
     * @throws IOException
     */
    private static GeoPoint readPoint( DataInputStream dis )  throws IOException
    {
        byte[] d = new byte[8];
        dis.read( d,0,8 );
        double x = LittleEndianReader.readDouble( d );

        dis.read( d,0,8 );
        double y = LittleEndianReader.readDouble( d );

       
        return  new GeoPoint(x, y);
    }
    
    
    /**
     * @todo ��ȡһ��Point���ͼ�¼,���ص���Location����
     * @param dis shp��¼��������
     * @return �����ݶ���
     * @throws IOException
     */
    private static Location readLocation( DataInputStream dis )  throws IOException
    {
        byte[] d = new byte[8];
        dis.read( d,0,8 );
        double x = LittleEndianReader.readDouble( d );

        dis.read( d,0,8 );
        double y = LittleEndianReader.readDouble( d );

        return new Location( x,y );
    }

    /**
     * @todo ��ȡһ��MultiPoint��¼
     * @param dis shp��¼��������
     * @return ������ݶ���
     * @throws IOException
     */
//    public static MultiPoint readMultiPoint( DataInputStream dis )  throws IOException
//    {
//    	Bounds box = readBox( dis );
//
//        byte[] w = new byte[4];
//        dis.read( w,0,4 );
//        int numPoints = LittleEndianReader.readInt( w );
//
//        GeoPoint pt = null;
//        GeoPoint[] pts = new GeoPoint[numPoints];
//        for( int i = 0; i < numPoints; i++ )
//        {
//            pt = readPoint( dis );
//            pts[i] = pt;
//        }
//
//        return new MultiPoint( box,numPoints,pts );
//    }

    /**
     * @todo ���ڼ����ܼ�¼��
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

        ShapefileHeader header = ShapefileHeader.readerHeader( dis );
        java.io.BufferedWriter fw = new java.io.BufferedWriter(new FileWriter("f:\\cors.txt") );

       // VList list = readRecord( dis );
//        for( int i = 0; i< list.size(); i++ )
//        {
//            GeoPolygon p = (GeoPolygon)list.get( i );
//            GeoPoint[] pts = p.getPts();
//            for( int j = 0; j < pts.length; j++ )
//            {
//                fw.write( pts[j].getX() + "," + pts[j].getY() + "\r\n" );
//            }
//        }
        fw.flush();
    }
}
