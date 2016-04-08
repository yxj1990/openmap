package com.soft2com.toolkit;

import java.net.URL;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MapTool
{
    public static String serverHost = "localhost";
    public static String serverPort = "8080";
    public static URL codeBase = null;
    public static String vdir = null;
    public static String mapDataPath = null;

    public MapTool()
    {
    }

    public static void setHost( String serverHost )
    {
        MapTool.serverHost = serverHost;
    }

    public static void setPort( String serverPort )
    {
        MapTool.serverPort = serverPort;
    }

    public static void setCodeBase( URL codeBase )
    {
        MapTool.codeBase = codeBase;
    }

    public static void setVDir( String vdir )
    {
        MapTool.vdir = vdir;
    }

    public static void setMapDataPath( String mapDataPath )
    {
        MapTool.mapDataPath =  mapDataPath;
    }
}