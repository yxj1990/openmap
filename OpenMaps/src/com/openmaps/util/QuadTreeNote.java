package com.openmaps.util;


import java.util.Vector;

import com.openmaps.feature.Feature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.Geometry;

public class QuadTreeNote {

	public Bounds bounds;
	public Vector<QuadTreeNote> note=new Vector<QuadTreeNote>();
	public Vector<Feature> noteData=new Vector<Feature>();
	public  int dataCount=0;
	//public  int noteCount;
	
	/**
	 * Çå³ý
	 */
	public void clear(){
		bounds=null;
		note.removeAllElements();
//		noteData.clear();
		dataCount=0;
	}
}
