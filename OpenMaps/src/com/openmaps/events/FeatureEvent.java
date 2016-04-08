package com.openmaps.events;

import java.util.HashMap;
import java.util.Vector;

import com.openmaps.feature.Feature;
import com.openmaps.layer.VectorLayer;


public class FeatureEvent extends BaseEvent {
	HashMap<VectorLayer, Vector<Feature>> mSelectedFeatures;
	
	public HashMap<VectorLayer, Vector<Feature>> getSelectedFeatures() {
		return mSelectedFeatures;
	}

	public void setSelectedFeatures(
			HashMap<VectorLayer, Vector<Feature>> mSelectedFeatures) {
		this.mSelectedFeatures = mSelectedFeatures;
	}

	public FeatureEvent(int type,HashMap<VectorLayer, Vector<Feature>> source){
		super(source);
		this.mSelectedFeatures = source;
		this.mType = type;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = EventUtil.FEATUREEVENTID;

}
