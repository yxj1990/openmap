package com.openmaps.data.shp;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * 此类是shx文件存储的所有读取方法
 * @author 
 * @version 1.0
 */
public class ShxIndexReader
{
    /**
     * @todo 取得索引集合
     * 每条记录包含offset和记录长度
     * @param dis
     * @return
     * @throws IOException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList readIndex( DataInputStream dis ) throws IOException
    {
        ShapefileHeader header = ShapefileHeader.readerHeader( dis );
        //VList al = new VArrayList();
        ArrayList al = new ArrayList();
        int[] index = null;

        while( dis.available() > 0 )
        {
            index = new int[2];

            int offset = dis.readInt();
            index[0] = offset;

            int length = dis.readInt();
            index[1] = length;

            al.add( index );
        }
        return al;
    }

    /**
     * @todo 读取指定的索引信息
     * @param index
     * @param dis shp文件流
     * @return 索引信息包括偏移量和记录长度
     * @throws IOException
     */
    public static int[] getIndex( int index,DataInputStream dis )  throws IOException
    {
    	ArrayList coll = readIndex( dis );
        return ( int[] ) coll.get( index );
    }
}