package com.androidcrm.wakensys.sugercrm.fragment;

import com.androidcrm.wakensys.sugercrm.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_about extends Fragment {
	
	public static Fragment_about newInstance(){
		return new Fragment_about();
	}
	
	public final static String TAG = Fragment_about.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_about, container, false);
		
		return rootView;

		
	}

}
