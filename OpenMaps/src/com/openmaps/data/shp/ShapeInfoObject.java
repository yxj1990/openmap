package com.openmaps.data.shp;

/**
 * 此类暂时没有什么用处，用来存放某一条要素的Geometry和属性信息的
 * @author yxj
 *
 */
public class ShapeInfoObject
{
    private Object shape = null;
    private Object[] dbfInfo = null;
    public ShapeInfoObject( Object shape, Object[] dbfInfo )
    {
        this.shape = shape;
        this.dbfInfo = dbfInfo;
    }

    public Object[] getDbfInfo()
    {
        return dbfInfo;
    }

    public Object getShape()
    {
        return shape;
    }

    public void setDbfInfo( Object[] dbfInfo )
    {
        this.dbfInfo = dbfInfo;
    }

    public void setShape( Object shape )
    {
        this.shape = shape;
    }
}