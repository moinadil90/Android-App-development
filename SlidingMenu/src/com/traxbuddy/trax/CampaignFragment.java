package com.traxbuddy.trax;

import com.traxbuddy.trax.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CampaignFragment extends Fragment {
	
	public CampaignFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_campaign, container, false);
         
        return rootView;
    }
}
