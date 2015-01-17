package com.traxbuddy.trax;

import com.traxbuddy.trax.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LogOutFragment extends Fragment {
	
	public LogOutFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_log_out, container, false);
        setRetainInstance(true);
        return rootView;
    }
}
