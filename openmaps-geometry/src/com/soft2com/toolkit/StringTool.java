package com.soft2com.toolkit;

import java.util.StringTokenizer;
import java.util.Vector;

import android.graphics.Color;


/**
 * �ַ���������
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author soft2com
 * @version 1.0
 */
public class StringTool
{

    public StringTool()
    {
    }

    /**
     * �ж�s�Ƿ����sTobeContains
     */
    public static boolean containsIgnoreCase( String s, String sTobeContains )
    {
        return StringTool.contains( s.toLowerCase(), sTobeContains.toLowerCase() );
    }

    public static boolean contains( String s, String sTobeContains )
    {
        return s.indexOf( sTobeContains ) != -1;
    }

    /**
     * ��split()����������
     * ",abc,"����������һ��Ԫ�أ�abc,��split�����᷵������Ԫ�أ�"",abc,""
     * ���⣬"abc def",��������������Ԫ�أ�"abc","def",��split��������һ��Ԫ��"abc def"
     * @param s
     * @param spliter
     * @return
     */
    public static String[] splitByTokenizer( String s, String spliter )
    {

        StringTokenizer strToken = new StringTokenizer( s, spliter, false );
        StringTokenizer childToken = null;
        int tokenNum = strToken.countTokens();
        String sa[] = new String[tokenNum];
        int i = 0;
        while ( strToken.hasMoreTokens() )
        {
            childToken = new StringTokenizer( strToken.nextToken() );
            sa[i++] = childToken.nextToken();
        }

        return sa;
    }

    /**
     * ���ַ�����spliter�ֽ�Ϊһ������
     */
    public static String[] split( String s, String spliter )
    {
        Vector v = new Vector();
        String temp = s;
        int index, len = spliter.length();
        while ( ( index = temp.indexOf( spliter ) ) != -1 )
        {
            v.addElement( temp.substring( 0, index ) );
            temp = temp.substring( index + len );
        }
        v.addElement( temp );
        String[] rs = new String[v.size()];
        for ( int i = 0; i < v.size(); i++ )
        {
            rs[i] = ( String ) v.elementAt( i );
        }
        return rs;
    }

    /**
     * @todo ���ַ���ת��Ϊjava.awt.Color����
     * ������ַ���������r,g,b ��ʽ��
     * r.g.b������������
     */
    public static int createColor( String colorString )
    {
        int c = 0;
        if ( colorString.startsWith( "#" ) )
        {
            String paramValue = colorString.substring( 1 );
            int red;
            int green;
            int blue;
            red = ( Integer.decode( "0x" + paramValue.substring( 0, 2 ) ) ).
                intValue();
            green = ( Integer.decode( "0x" + paramValue.substring( 2, 4 ) ) ).
                intValue();
            blue = ( Integer.decode( "0x" + paramValue.substring( 4, 6 ) ) ).
                intValue();
            return Color.rgb(red, green, blue);
        }
        if ( colorString.equalsIgnoreCase( "white" ) )
        {
            c = Color.WHITE;
        }
        else if ( colorString.equalsIgnoreCase( "lightGray" ) )
        {
            c = Color.LTGRAY;
        }
        else if ( colorString.equalsIgnoreCase( "gray" ) )
        {
            c = Color.GRAY;
        }
        else if ( colorString.equalsIgnoreCase( "darkGray" ) )
        {
            c = Color.DKGRAY;
        }
        else if ( colorString.equalsIgnoreCase( "black" ) )
        {
            c = Color.BLACK;
        }
        else if ( colorString.equalsIgnoreCase( "red" ) )
        {
            c = Color.RED;
        }
        else if ( colorString.equalsIgnoreCase( "pink" ) )
        {
            c = Color.BLACK;
        }
        else if ( colorString.equalsIgnoreCase( "orange" ) )
        {
            c = Color.YELLOW;
        }
        else if ( colorString.equalsIgnoreCase( "yellow" ) )
        {
            c = Color.YELLOW;
        }
        else if ( colorString.equalsIgnoreCase( "green" ) )
        {
            c = Color.GREEN;
        }
        else if ( colorString.equalsIgnoreCase( "magenta" ) )
        {
            c = Color.MAGENTA;
        }
        else if ( colorString.equalsIgnoreCase( "cyan" ) )
        {
            c = Color.CYAN;
        }
        else if ( colorString.equalsIgnoreCase( "blue" ) )
        {
            c = Color.BLUE;
        }
        else
        {
            String sa[] = StringTool.split( colorString, "," );
            if ( sa.length == 3 )
                c =Color.rgb( Integer.parseInt( sa[0] ),
                               Integer.parseInt( sa[1] ),
                               Integer.parseInt( sa[2] ) );
            if ( c == 0 )
            {
                c = Color.BLACK;
            }
        }
        return c;
    }

    /**
     * @todo ��replacer�滻s�е�tobeReplaced�ַ���
     */
    public static String replace( String s, String tobeReplaced,
                                  String replacer )
    {
        String right = "", sr = "";
        int index = -1;
        String sb = tobeReplaced
            , st = replacer;

        if ( s == "" || sb == "" )
            return s;
        right = s;

        while ( ( index = right.indexOf( sb ) ) != -1 )
        {
            sr += right.substring( 0, index ) + st;
            right = right.substring( index + sb.length() );
        }
        return sr + right;
    }

    /**
     * ��Stringת��Ϊdouble
     */
    public static double stringToDouble( String s )
    {
        return Double.valueOf( s ).doubleValue();
    }

    public static boolean stringToBoolean( String booleanValue )
    {
        return Boolean.valueOf( booleanValue ).booleanValue();
    }

    /**
     * ��Stringת��ΪISO-8859-1������ַ���
     */
    public static String getGBString( String s )
    {
        String rs = "";
        if ( s != null )
        {
            try
            {
                rs = new String( s.getBytes( "ISO8859-1" ) );
            }
            catch ( Exception ex )
            {
                ex.printStackTrace();
            }
        }
        return rs;
    }

    public static void main(String[] args)
    {
        String str = "#FF60B0";
        int clr = createColor( str );
        System.out.println( clr );

    }
}