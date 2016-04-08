package com.soft2com.util.shape;

import java.io.DataInputStream;
import java.io.IOException;

import com.soft2com.vjavalib.vutil.*;

/**
 *
 * <p>Title: shape�����ļ��Ķ���</p>
 * <p>Description:ȡ��shape��������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author 
 * @version 1.0
 */
public class IndexReader
{
    /**
     * @todo ȡ����������
     * ÿ����¼����offset�ͼ�¼����
     * @param dis
     * @return
     * @throws IOException
     */
    public static VList readIndex( DataInputStream dis ) throws IOException
    {
        ShapefileHeader header = ShapeReader.readerHeader( dis );
        VList al = new VArrayList();
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
     * @param dis
     * @return ������Ϣ����ƫ�����ͼ�¼����
     * @throws IOException
     */
    public static int[] getIndex( int index,DataInputStream dis )  throws IOException
    {
        VList coll = readIndex( dis );
        return ( int[] ) coll.get( index );
    }
}