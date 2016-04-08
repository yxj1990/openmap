package com.openmaps.data.shp;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * ������shx�ļ��洢�����ж�ȡ����
 * @author 
 * @version 1.0
 */
public class ShxIndexReader
{
    /**
     * @todo ȡ����������
     * ÿ����¼����offset�ͼ�¼����
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
     * @todo ��ȡָ����������Ϣ
     * @param index
     * @param dis shp�ļ���
     * @return ������Ϣ����ƫ�����ͼ�¼����
     * @throws IOException
     */
    public static int[] getIndex( int index,DataInputStream dis )  throws IOException
    {
    	ArrayList coll = readIndex( dis );
        return ( int[] ) coll.get( index );
    }
}