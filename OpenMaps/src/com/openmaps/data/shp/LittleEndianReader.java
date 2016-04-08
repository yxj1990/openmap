package com.openmaps.data.shp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LittleEndianReader
{
    public LittleEndianReader()
    {
    }

    /**
     * @todo 读取整型数据
     * @param w
     * @return
     * @throws IOException
     */
    public static int readInt( byte[] w ) throws IOException
    {
        return ( w[3] ) << 24 | ( w[2] & 0xff ) << 16 | ( w[1] & 0xff ) << 8 |
            ( w[0] & 0xff );
    }

    /**
     * @todo 读取double型数据
     * @param w
     * @return
     * @throws IOException
     */
    public static double readDouble(  byte[] w ) throws IOException
    {
        long lvalue= ( long ) ( w[7] ) << 56 | ( long ) ( w[6] & 0xff ) << 48 |
            ( long ) ( w[5] & 0xff ) << 40 | ( long ) ( w[4] & 0xff ) << 32 |
            ( long ) ( w[3] & 0xff ) << 24 | ( long ) ( w[2] & 0xff ) << 16 |
            ( long ) ( w[1] & 0xff ) << 8 | ( long ) ( w[0] & 0xff );
        return Double.longBitsToDouble( lvalue );
    }
    /** 
     * double转换byte 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static byte[]  putDouble( double x, int index) {  
    	
         byte[] bb = new byte[8];  
        long l = Double.doubleToLongBits(x);  
        for (int i = 0; i < 8; i++) {  
            bb[index + i] = new Long(l).byteValue();  
            l = l >> 8;  
        }  
        return bb;
    } 
    /** 
     * 转换int为byte数组 
     *  
     * @param bb 
     * @param x 
     * @param index 
     */  
    public static byte[] putLittleEndianInt( int x, int index) {  
    	byte[] bb=new byte[4];
    	bb[index + 3] = (byte) (x >> 24);  
        bb[index + 2] = (byte) (x >> 16);  
        bb[index + 1] = (byte) (x >> 8);  
        bb[index + 0] = (byte) (x >> 0);  
        return bb;
    }  
    public static byte[] putBigEndianInt(int x) throws Exception {
    	 
    	byte[] bb=new byte[4];
    	bb[0] = (byte) (x >> 24);  
        bb[1] = (byte) (x >> 16);  
        bb[2] = (byte) (x >> 8);  
        bb[3] = (byte) (x >> 0);  
        return bb;
//        
//    	ByteArrayOutputStream buf = new ByteArrayOutputStream();   
//    	  DataOutputStream out = new DataOutputStream(buf);   
//    	  out.writeInt(i);   
//    	  byte[] b = buf.toByteArray();
//    	  out.close();
//    	  buf.close();
//    	  return b;
    	 }
    public static void main(String[] args)
    {
      //  LittleEndianReader littleEndianReader1 = new LittleEndianReader();
    }

	

}