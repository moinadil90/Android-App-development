package com.traxbuddy.trax;

import com.traxbuddy.trax.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyProfileFragment extends Fragment {
	
	public MyProfileFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_myprofile, container, false);
         
        return rootView;
    }
}
