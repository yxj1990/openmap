package com.openmaps.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Vector;

import android.util.Log;

import com.openmaps.feature.Feature;
import com.openmaps.geometry.Bounds;
import com.openmaps.geometry.GeoPoint;
import com.openmaps.geometry.GeoPolygon;
import com.openmaps.geometry.GeoPolyline;
import com.openmaps.geometry.Geometry;
import com.openmaps.geometry.GeometryUtils;

public class QuadTree {
	
	//public static ParentNote parent=new ParentNote();
	public static final int NoteMaxSize=800;
	
	
	/***
	 * 创建四叉树索引
	 * @param arr
	 */
	public static QuadTreeNote BuildTree(Vector<Feature> arr){
		//查询所有要素的范围，获取最大的范围
		//ArrayList<Geometry>	 arr=new ArrayList<Geometry>();
		QuadTreeNote quadTreeNote=new QuadTreeNote();
		//if(quadTreeNote==null)quadTreeNote=new QuadTreeNote();
		Bounds bounds=null;
		for(Feature f: arr){
			Geometry geo=f.getGeometry();
			bounds=Bounds.getMaxBounds(geo.getBounds(),bounds);
			quadTreeNote.dataCount+=1;
		}
		quadTreeNote.bounds=bounds;
		quadTreeNote.noteData=arr;
		if(quadTreeNote.dataCount>NoteMaxSize){
			createTreeBranch(quadTreeNote);
		}
		return quadTreeNote;
	}

	/**
	 * 创建分支
	 */
	public static void createTreeBranch(QuadTreeNote quadTreeNote){
		if(quadTreeNote.dataCount<NoteMaxSize)return;
		double l=quadTreeNote.bounds.left;
		double r=quadTreeNote.bounds.right;
		double t=quadTreeNote.bounds.top;
		double b=quadTreeNote.bounds.bottom;
		
		QuadTreeNote p1=new QuadTreeNote();
		QuadTreeNote p2=new QuadTreeNote();
		QuadTreeNote p3=new QuadTreeNote();
		QuadTreeNote p4=new QuadTreeNote();
		p1.bounds=new Bounds(l, (t+b)/2,(l+r)/2,t);
		p2.bounds=new Bounds((l+r)/2,(t+b)/2,r,t);
		p3.bounds=new Bounds(l, b,(l+r)/2,(t+b)/2);
		p4.bounds=new Bounds((l+r)/2, b,r,(t+b)/2);
		
		for(int i=0;i<quadTreeNote.dataCount;i++){
			Geometry geo=quadTreeNote.noteData.get(i).getGeometry();
			if(geo.getBounds().left<(l+r)/2){
				if(geo.getBounds().top>(t+b)/2){
//					p1.bounds=Bounds.getMaxBounds(p1.bounds, parent.noteData.get(i).getBounds());
					p1.dataCount++;
					p1.noteData.add(quadTreeNote.noteData.get(i));
				}else{
//					p3.bounds=Bounds.getMaxBounds(p3.bounds, parent.noteData.get(i).getBounds());
					p3.dataCount++;
					p3.noteData.add(quadTreeNote.noteData.get(i));
				}
			}else{
				if(geo.getBounds().top>(t+b)/2){
//					p2.bounds=Bounds.getMaxBounds(p2.bounds, parent.noteData.get(i).getBounds());
					p2.dataCount++;
					p2.noteData.add(quadTreeNote.noteData.get(i));
				}else{
//					p4.bounds=Bounds.getMaxBounds(p4.bounds, parent.noteData.get(i).getBounds());
					p4.dataCount++;
					p4.noteData.add(quadTreeNote.noteData.get(i));
				}
			}
		}
		quadTreeNote.note.add(p1);
		quadTreeNote.note.add(p2);
		quadTreeNote.note.add(p3);
		quadTreeNote.note.add(p4);
		createTreeBranch(p1);
		createTreeBranch(p2);
		createTreeBranch(p3);
		createTreeBranch(p4);
	}

	/**
	 * 根据范围查询要素
	 * @param bounds
	 * @param quadTreeNote
	 * @return
	 */
	public static Vector<Feature> selectGeometry(Bounds bounds,QuadTreeNote quadTreeNote){
		Vector<Feature> arr=new Vector<Feature>();
		if(Bounds.getIntersectBounds(bounds,quadTreeNote.bounds)!=null){
			selectGeometryBranch(bounds,quadTreeNote,arr);
			Log.d("selectcount", arr.size()+"");
		}
		return arr;
	}

	/**
	 * 分支遍历查询要素
	 * @param bounds
	 * @param quadTreeNote
	 * @param arr
	 */
	private static void selectGeometryBranch(Bounds bounds,QuadTreeNote quadTreeNote,Vector<Feature> arr){
		
		if(quadTreeNote.note.size()==0){
			for(int i=0;i<quadTreeNote.dataCount;i++){
				Geometry geo=quadTreeNote.noteData.get(i).getGeometry();
				if(Bounds.getIntersectBounds(geo.getBounds(),bounds)!=null){
					if(bounds.contain(geo.getBounds())){
						arr.add(quadTreeNote.noteData.get(i));
						//Log.e("selectcount---------", arr.size()+"");
					}else if(geo instanceof GeoPolygon){
						if(GeometryUtils.intersects(Bounds.toGeometry(bounds), geo)){
							arr.add(quadTreeNote.noteData.get(i));
						}
					}else if(geo instanceof GeoPolyline){
						if(GeometryUtils.intersects(Bounds.toGeometry(bounds), geo)){
							arr.add(quadTreeNote.noteData.get(i));
						}
					}
				}
			}
		}else{
			for(int i=0;i<quadTreeNote.note.size();i++){
				if(Bounds.getIntersectBounds(quadTreeNote.note.get(i).bounds,bounds)!=null){
					selectGeometryBranch(bounds,quadTreeNote.note.get(i),arr);
				}
			}
		}
	}
	

	/**
	 * 插入索引
	 * @param geo
	 * @param quadTreeNote
	 */
	public static  QuadTreeNote insertGeometry(Feature feature,QuadTreeNote quadTreeNote){
		Geometry geo=feature.getGeometry();
		//Bounds.getIntersectBounds(geo.getBounds(),quadTreeNote.bounds)!=null
		if(!quadTreeNote.bounds.contain(geo.getBounds())){
			Vector<Feature> vec=(Vector<Feature>) quadTreeNote.noteData;
			quadTreeNote.clear();
			quadTreeNote=null;
			System.gc();
			//quadTreeNote.noteData.add(feature);
			Log.e("", vec.size()+"");
			return BuildTree(vec);
			
		}else{
			addGeometry(feature,quadTreeNote);
		}
		
		return quadTreeNote;
	}
	
private static void addGeometry(Feature feature,QuadTreeNote quadTreeNote){
	Geometry geo=feature.getGeometry();
	if(quadTreeNote.bounds.contain(geo.getBounds())){
		quadTreeNote.noteData.add(feature);
		quadTreeNote.dataCount++;
		if(quadTreeNote.dataCount<NoteMaxSize) return ;
		if(quadTreeNote.note.size()==0){
			createTreeBranch(quadTreeNote);
		}else{
			for(int i=0;i<quadTreeNote.note.size();i++){
				addGeometry(feature,quadTreeNote);
			}
		}
	}
}
	

	/**
	 * 移除索引
	 * @param geo
	 * @param quadTreeNote
	 */
	public static QuadTreeNote removeGeometry(Feature geo,QuadTreeNote quadTreeNote){
		QuadTreeNote quadtreeNote=new QuadTreeNote();
		if(quadTreeNote.noteData.remove(geo)){
			quadTreeNote.dataCount--;
//			parent.bounds=parent.noteData.get(0).getBounds();
//			for(int i=0;i<parent.dataCount;i++){
//				parent.bounds=Bounds.getMaxBounds(parent.noteData.get(i).getBounds(),parent.bounds);
//			}
			for(int i=0;i<quadTreeNote.note.size();i++){
				quadtreeNote=removeGeometry(geo,quadTreeNote);
			}
		}else{
			quadtreeNote=quadTreeNote;
		}
		return quadtreeNote;
	}

	/**
	 * 保存四叉树索引
	 * @param quadTreeNote
	 * @param file
	 * @throws IOException
	 */
	public static void saveTree(QuadTreeNote quadTreeNote,String file) throws IOException{

		FileWriter fw = new FileWriter(file);    
		String s = quadTreeNote.toString();    
		fw.write(s,0,s.length());    
		fw.flush();    
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));    
		osw.write(s,0,s.length());    
		osw.flush();    
		fw.close();    
		osw.close();    
	}

	public static QuadTreeNote loadTree(String path){
		StringBuffer sb=new StringBuffer();
		try {
			QuadTreeNote p;
		    File file=new File(path);
	         if(!file.exists()||file.isDirectory())
	             throw new FileNotFoundException();
	         FileInputStream fis=new FileInputStream(file);
	         byte[] buf = new byte[1024];
	         while((fis.read(buf))!=-1){
	             sb.append(new String(buf));    
	             buf=new byte[1024];//重新生成，避免和上次读取的数据重复
	         }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//(Parent) sb.toString();
		return null;
	}


}
