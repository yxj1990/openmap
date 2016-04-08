package com.openmaps.data.shp;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author 
 * @version 1.0
 */

@SuppressWarnings("serial")
public class JDBFException extends Exception
{

    public JDBFException(String s)
    {
        this(s, null);
    }

    public JDBFException(Throwable throwable)
    {
        this(throwable.getMessage(), throwable);
    }

    public JDBFException(String s, Throwable throwable)
    {
        super(s);
        detail = throwable;
    }

    public String getMessage()
    {
        if(detail == null)
            return super.getMessage();
        else
            return super.getMessage();
    }

    public void printStackTrace(PrintStream printstream)
    {
        if(detail == null)
            super.printStackTrace(printstream);
        else
            synchronized(printstream)
            {
                printstream.println(this);
                detail.printStackTrace(printstream);
            }
    }

    public void printStackTrace()
    {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintWriter printwriter)
    {
        if(detail == null)
            super.printStackTrace(printwriter);
        else
            synchronized(printwriter)
            {
                printwriter.println(this);
                detail.printStackTrace(printwriter);
            }
    }

    private Throwable detail;
}
