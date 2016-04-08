package com.openmaps.layer;

import java.util.Vector;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.openmaps.Map;
import com.openmaps.events.BaseEvent;
import com.openmaps.events.EventUtil;
import com.openmaps.events.IEventListener;

public class LayerManager  implements  IEventListener{
	private Map mMap;

	private PopupWindow layersManager;
	private ListView layersListView;
	private MyAdapter myAdapter;
	
	public LayerManager(Map map){
		//super(map.getContext());
		this.mMap = map;
		this.mMap.addEventListener(EventUtil.LAYER_ADDED,this);
		this.mMap.addEventListener(EventUtil.LAYER_REMOVED,this);
		
		initLayersListView();
		initLayersManager();
	//	Show();
	}
	
	public void Show(){
		if(!layersManager.isShowing())
		layersManager.showAtLocation(mMap, Gravity.TOP, 0, 250);
	}
	public void hide(){
		layersManager.dismiss();
	}

	private void initLayersManager(){
		layersManager=new PopupWindow(layersListView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, false);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		layersManager.setBackgroundDrawable(dw);
		layersManager.setOutsideTouchable(true);
		layersManager.setFocusable(true);
		layersManager.setAnimationStyle(com.openmaps.R.style.mainMenu);
		
	}

	private void initLayersListView(){
		layersListView=new ListView(mMap.getContext());
		myAdapter=new MyAdapter(mMap.getContext());
		myAdapter.setLayers(mMap.getAllLayers());
		layersListView.setAdapter(myAdapter);
	}


	class MyAdapter extends BaseAdapter{
		private Context mContext;
		private Vector<Layer> mLayers;
		public MyAdapter(Context context){
			mContext=context;	
		}
		public void setLayers(Vector<Layer> layers){
			mLayers=layers;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mLayers==null?0:mLayers.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return mLayers.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			if(view==null){
			view=getTextView(mLayers.get(arg0));
			}
			return view;
		}

		private TextView getTextView(Layer l){
			final Layer ml=l;
			TextView	txtView=new TextView(mContext);
			txtView.setBackgroundColor(0xb0ffffff);
			txtView.setGravity(0);
			txtView.setText(l.getName());
			txtView.setHeight(50);
			txtView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//ml.setEnabled(false);
					if(ml.getVisibility()==ml.VISIBLE){
						ml.setVisibility(ml.GONE);
						v.setBackgroundColor(0xa0ababac);
					}else{
						ml.setVisibility(ml.VISIBLE);
						v.setBackgroundColor(0xb0ffffff);
					}
				}
			});
			
			return txtView;
		}
	}


	@Override
	public void respond(BaseEvent event) {
		// TODO Auto-generated method stub
		switch (event.getType()) {
		case EventUtil.LAYER_ADDED:
			myAdapter.setLayers(mMap.getAllLayers());
			break;
		case EventUtil.LAYER_REMOVED:
			myAdapter.setLayers(mMap.getAllLayers());
			break;
		default:
			break;
		}
	}
}
