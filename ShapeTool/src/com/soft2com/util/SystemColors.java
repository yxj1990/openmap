package com.soft2com.util;

import android.graphics.Color;


/**
 *
 * <p>Title: 系统颜色</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author not attributable
 * @version 1.0
 */
public class SystemColors
{
    public static final int DEFAULT_COLOR = Color.BLACK;// Color.black;
    public static final int BORDER_COLOR =Color.rgb(255, 144, 0);
    public static final int BACKGROUND_COLOR = Color.rgb(153, 204, 255);
    public static final int BUSSTOP_COLOR = Color.YELLOW;
    public static final int CROSSROAD_COLOR = Color.rgb(102, 102, 102);
    public static final int DARKEN_PATH_COLOR = Color.BLUE;
    public static final int HIGHLIGHT_PATH_COLOR = Color.RED;
    public static final int RESIDENT_COLOR = Color.rgb(255, 204, 102);
    public static final int ROAD_COLOR = Color.rgb(255, 255, 255);
    public static final int RIVER_COLOR = Color.rgb(0, 102, 255);
    public static final int LAKE_COLOR =Color.rgb(0, 204, 255);
    public static final int TREE_COLOR = Color.rgb(0, 204, 102);
    public static final int SELECTED_BUSSTOP_COLOR = Color.CYAN;
    public static final int NORMAL_UNIT_TEXTCOLOR = Color.rgb(0, 0, 0);
    public static final int SELECTED_UNIT_TEXTCOLOR = Color.RED;
    public static final int NORMAL_UNIT_COLOR = Color.rgb(144, 192, 192);
    public static final int ROAD_ANNOTATION_COLOR = Color.MAGENTA;
    public static final int ROAD_CENTELINE_COLOR = Color.DKGRAY;
    public static final int SELECTED_UNIT_COLOR =Color.rgb(144, 102, 255);

    /**
     * @todo 取得图层颜色
     * @param typeID
     * @return
     */
    public static int getDrawColor( int typeID )
    {
      int ret = SystemColors.DEFAULT_COLOR;
      switch ( typeID )
      {
        case 1: // '\001'
          ret = SystemColors.RESIDENT_COLOR;
          break;

        case 2: // '\002'
          ret = SystemColors.ROAD_COLOR;
          break;

        case 3: // '\003'
          ret = SystemColors.RIVER_COLOR;
          break;

        case 4: // '\004'
          ret = SystemColors.LAKE_COLOR;
          break;

        case 6: // '\006'
          ret = SystemColors.TREE_COLOR;
          break;
      }
      return ret;
    }

}