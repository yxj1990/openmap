package com.soft2com.toolkit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.soft2com.vjavalib.vio.VFileFilter;
import java.io.FileFilter;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: soft2com</p>
 * @author not attributable
 * @version 1.0
 */
public class CToolkit
{

    public CToolkit()
    {
    }

    /**
     * @todo 对象转换为字节数组
     * @param serializableObject
     * @return
     * @throws IOException
     */
    public static byte[] objectToByteArray( java.io.Serializable
                                            serializableObject ) throws
        IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( serializableObject );
        oos.close();
        return baos.toByteArray();
    }

    /**
     * <pre>
     * 列出一个目录中扩展名为指定扩展名的文件
     * 注意：
     * 1、扩展名中不能包含.
     * 2、如果扩展名等于null，则列出所有文件。
     * </pre>
     */
    public static File[] listDirContent( File dir,
                                         final String fileExtensionName )
    {
        File[] fl = dir.listFiles(
            new FileFilter()
        {
            public boolean accept( File file )
            {
                if ( fileExtensionName == null )
                {
                    return true;
                }
                if ( file.isFile() )
                {
                    String fileName = file.getName();
                    Log.debug( "file name: " + fileName );
                    String ext = fileName.substring( fileName.lastIndexOf( "." +
                        fileExtensionName ) );
                    return ext.equals( "." + fileExtensionName );
                }
                return false;
            }
        }
        );

        return fl;
    }
}