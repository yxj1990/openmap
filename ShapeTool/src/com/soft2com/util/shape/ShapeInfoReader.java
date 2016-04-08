package com.soft2com.util.shape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.soft2com.map.geom.CBox;
import com.soft2com.map.geom.CPoint;
import com.soft2com.map.geom.CPolyLine;
import com.soft2com.vjavalib.vutil.VArrayList;
import com.soft2com.vjavalib.vutil.VList;

public class ShapeInfoReader
{
    private String shx = null;
    private String shp = null;
    private String dbf = null;

    private VList dbfRecords = null;
    private VList shpRecords = null;

    private ShapefileHeader header = null;
    private CBox box = null;

    private java.io.DataInputStream shpdis = null;
    private java.io.DataInputStream shxdis = null;

    private DBFReader dbfReader = null;
    private VArrayList fieldList = null;

    /**
     * @todo ��ʼ����ͼ������Ϣ
     * @param pathName �����ļ�·��, eg. c:\\mapdata
     * @param datasetName ���ݼ����� eg. res2_4m
     */
    public ShapeInfoReader( String pathName, String datasetName ) throws FileNotFoundException,JDBFException
    {
        //offset
        this.shx = new StringBuffer( pathName ).append( "\\" ).append( datasetName ).append(
            ".shx" ).toString();
        this.shp = new StringBuffer( pathName ).append( "\\" ).append( datasetName ).append(
            ".shp" ).toString();
        this.dbf = new StringBuffer( pathName ).append( "\\" ).append( datasetName ).append(
            ".dbf" ).toString();

        FileInputStream shpfis = new java.io.FileInputStream(
            this.shp );
        this.shpdis = new java.io.DataInputStream(
            shpfis );

        java.io.FileInputStream shxfis = new java.io.FileInputStream( this.shx );
        this.shxdis = new java.io.DataInputStream(
            shxfis );

        this.dbfReader = new DBFReader( this.dbf );
    }

    /**
     * ��������ͨ��,��ʱδ��
     * ��������ȡ��һ����¼,������0��ʼ
     * @param index ��¼������
     * @return shape��Ϣ����������Ϣ�ͼ�¼��Ϣ
     */
    public ShapeInfoObject getShapeRecord( int index ) throws IOException
    {
        int[] index2 = IndexReader.getIndex( index, shxdis );

        Object obj = ShapeReader.readRecordXOffset( index2[0], shpdis );

        //ȡdbf����
        Object[] fields = this.getDBFRecord( index );
        return new ShapeInfoObject( obj,fields );
    }

    /**
     * @todo ��shp�ļ���ȡ��ȫ����¼
     * ����Ԫ������ΪPolygon,Point��Polygon
     * @return ��¼����
     * @throws IOException
     */
    public VList getAllShapeRecords() throws IOException
    {
        if( null == this.shpRecords )
        {
            //���ļ�ͷ
            getShapeHeader();

            this.shpRecords = ShapeReader.readRecord( shpdis );
        }
        return this.shpRecords;
    }

    /**
     * @todo ��dbf�ļ���ȡ��һ����¼,������0��ʼ
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

    /**
     * @todo ȡ��ȫ��dbf��¼
     * ��¼����ΪObject[],�ֶ�ֵ����
     * @return ���м�¼�ļ���
     */
    public VList getAllDBFRecords()
    {
        if( null == this.dbfRecords )
        {
            try
            {
                Object[] object = null;
                dbfRecords = new VArrayList();
                while ( dbfReader.hasNextRecord() )
                {
                    object = dbfReader.nextRecord();
                    this.dbfRecords.add( object );
                }

                //ȡ���ֶ�����
                int fieldCount = this.dbfReader.getFieldCount();
                this.fieldList = new VArrayList();
                for( int i = 0; i < fieldCount;i++ )
                {
                    this.fieldList.add(this.dbfReader.getField( i ).getName());
                }
            }
            catch ( JDBFException ex )
            {
                ex.printStackTrace();
            }
        }
        return dbfRecords;
    }

    /**
     * @todo ȡ��shape�ļ�ͷ����
     * @return
     * @throws IOException
     */
    public ShapefileHeader getShapeHeader() throws IOException
    {
        if( null == this.header )
        {
            this.header = ShapeReader.readerHeader( shpdis );
        }
        return this.header;
    }

    /**
     * @todo ȡ��shape�߽�
     * @return
     * @throws IOException
     */
    public CBox getBox() throws IOException
    {
        if( null == this.header )
        {
            this.header = ShapeReader.readerHeader( shpdis );
        }
        return this.header.getBox();
    }

    /**
     * @todo �ж�box�Ƿ�����extent��
     * Ӧ����Ѱ���ཻ�㣬����ʵ���Ǵ����
     * @param ext
     * @param box
     * @return
     */
    public static boolean include( CBox ext,CBox box )
    {
        CPoint ldpt = new CPoint(ext.getMinx(),ext.getMiny());
        CPoint rupt = new CPoint(ext.getMaxx(),ext.getMaxy());
        return include( ext,ldpt )&& include( ext,rupt );
    }

    /**
     * @todo �жϵ��Ƿ�����extent��
     * @param ext
     * @param pt
     * @return
     */
    public static boolean include( CBox ext, CPoint pt )
    {
        return ( pt.getX() >= ext.getMinx() ) && ( pt.getY() >= ext.getMiny() ) &&
            ( pt.getX() <= ext.getMaxx() ) && ( pt.getY() <= ext.getMaxy() );
    }

    /**
     *
     * @todo ������ext��Χ�ڵĵ�
     * @param ext ������Χ
     * @return ���󼯺�
     * @throws IOException
     */
    public VList search( CBox ext ) throws IOException
    {
        if( null == this.shpRecords )
        {
            getAllShapeRecords();
        }

        Object obj = null;
        CPoint pt = null;
        CPolyLine pl = null;
        VArrayList al = new VArrayList();

        for ( int i = 0; i < shpRecords.size(); i++ )
        {
            obj = shpRecords.get( i ) ;
            if( obj instanceof CPoint ) //��
            {
                pt = (CPoint)obj;
                if ( ( pt.getX() >= ext.getMinx() ) && ( pt.getY() >= ext.getMiny() ) &&
                    ( pt.getX() <= ext.getMaxx() ) && ( pt.getY() <= ext.getMaxy() ))
               {
                   al.add( pt );
               }
            }
            else if( obj instanceof CPolyLine ) //��
            {
                pl = (CPolyLine)obj;
                CBox box = pl.getBox();

                ///////δʵ��
            }
            else if( obj instanceof CPolyLine ) //�����
            {

            }
        }

        return al;
    }

    /**
     * @todo ȡ�ö�����dbf�ļ��ж�Ӧ���ֶ�ֵ
     * @param feature ��ͼ���ζ���
     * @param fieldName �ֶ�����
     * @return
     */
    public String getFieldValue( Object feature, String fieldName ) throws IOException
    {
        if( null == this.shpRecords )
        {
            getAllShapeRecords();
        }

        int index = this.shpRecords.indexOf( feature );

        Object[] dbfRecord = this.getDBFRecord( index );
        return dbfRecord[this.fieldList.indexOf( fieldName )].toString();
    }

    public static void main( String[] args ) throws Exception
    {
        ShapeInfoReader t = new ShapeInfoReader( "c:/mapdata/", "roa_4m" );
    }
}
