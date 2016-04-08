package com.soft2com.toolkit;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log
{
    /**
     * 日志文件路径
     */
    private static String filePath = null;

    private static java.io.PrintWriter pw = null;

    private static boolean isDebug = false;

    /**
     * 日志类初始化
     * @param filePath
     */
    public static void init( String filePath )
    {
        if( null == filePath )
        {
            filePath = filePath;
        }

        try
        {
            pw = new PrintWriter( new java.io.FileWriter(
                filePath, true ) );
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
        }
    }

    /**
     * 输出调试信息
     * @param msg
     */
    public static void debug( Object msg )
    {
        if( isDebug )
        {
        System.out.println( msg );
//            java.util.Calendar cal = java.util.Calendar.getInstance();
//            Date currentDate = cal.getTime();
//            SimpleDateFormat format = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss" );
//            String strDate = format.format( currentDate );
//            pw.println( strDate + "[debug] " + msg );
//            pw.flush();
        }
    }

    /**
     * 打印到屏幕
     * @param msg
     */
    public static void println( String msg )
    {
        Log.debug(msg);
    }
}