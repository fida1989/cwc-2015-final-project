package com.mobioapp.klassify.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobioapp.klassify.R;

public class PostAdFragment extends Fragment {
	

	public PostAdFragment() {
		// Empty constructor required for fragment subclasses
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_submitad,
				container, false);

		return rootView;
	}
}
