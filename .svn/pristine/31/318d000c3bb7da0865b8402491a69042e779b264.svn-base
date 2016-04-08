package com.openmaps.util;

import java.io.File;
import java.io.FileFilter;

public class FileFilterFactory {
	/**
	 * getFileTypeFileFilter("map","dat",.....)
	 * @param type   
	 * @return
	 */
	public static  FileFilter getFileTypeFileFilter(final String... type){
		FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				for(int i=0;i<type.length;i++){
					if(pathname.getName().endsWith("."+type[i]))
						return true;
				}
				return false;
			}
			
		};
		return filter; 
	}
}
