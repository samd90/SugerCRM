package com.androidcrm.wakensys.sugercrm.fragment;

import com.androidcrm.wakensys.sugercrm.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Resent extends Fragment {
	
	public static Fragment_Resent newInstance(){
		return new Fragment_Resent();
	}
	
	public final static String TAG = Fragment_Resent.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_recent, container, false);
		
		return rootView;

		
	}

}
