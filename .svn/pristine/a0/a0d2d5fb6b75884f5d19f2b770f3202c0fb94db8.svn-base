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
     * @todo 初始化地图数据信息
     * @param pathName 数据文件路径, eg. c:\\mapdata
     * @param datasetName 数据集名称 eg. res2_4m
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
     * 方法测试通过,暂时未用
     * 根据索引取得一条记录,索引从0开始
     * @param index 记录索引号
     * @return shape信息包括几何信息和记录信息
     */
    public ShapeInfoObject getShapeRecord( int index ) throws IOException
    {
        int[] index2 = IndexReader.getIndex( index, shxdis );

        Object obj = ShapeReader.readRecordXOffset( index2[0], shpdis );

        //取dbf数据
        Object[] fields = this.getDBFRecord( index );
        return new ShapeInfoObject( obj,fields );
    }

    /**
     * @todo 从shp文件中取得全部记录
     * 集合元素类型为Polygon,Point或Polygon
     * @return 记录集合
     * @throws IOException
     */
    public VList getAllShapeRecords() throws IOException
    {
        if( null == this.shpRecords )
        {
            //读文件头
            getShapeHeader();

            this.shpRecords = ShapeReader.readRecord( shpdis );
        }
        return this.shpRecords;
    }

    /**
     * @todo 从dbf文件中取得一条记录,索引从0开始
     * @param index 记录索引号
     * @return 记录信息数组
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
     * @todo 取得全部dbf记录
     * 记录类型为Object[],字段值数组
     * @return 所有记录的集合
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

                //取得字段数组
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
     * @todo 取得shape文件头对象
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
     * @todo 取得shape边界
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
     * @todo 判断box是否落在extent内
     * 应该是寻找相交点，这是实现是错误的
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
     * @todo 判断点是否落在extent内
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
     * @todo 搜索在ext范围内的点
     * @param ext 搜索范围
     * @return 对象集合
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
            if( obj instanceof CPoint ) //点
            {
                pt = (CPoint)obj;
                if ( ( pt.getX() >= ext.getMinx() ) && ( pt.getY() >= ext.getMiny() ) &&
                    ( pt.getX() <= ext.getMaxx() ) && ( pt.getY() <= ext.getMaxy() ))
               {
                   al.add( pt );
               }
            }
            else if( obj instanceof CPolyLine ) //线
            {
                pl = (CPolyLine)obj;
                CBox box = pl.getBox();

                ///////未实现
            }
            else if( obj instanceof CPolyLine ) //多边形
            {

            }
        }

        return al;
    }

    /**
     * @todo 取得对象在dbf文件中对应的字段值
     * @param feature 地图几何对象
     * @param fieldName 字段名称
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
