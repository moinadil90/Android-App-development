package com.traxbuddy.trax;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Sos extends Activity {
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_friends);
	 
	        // get action bar   
	        ActionBar actionBar = getActionBar();
	 
	        // Enabling Up / Back navigation
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        
	        //ImageButton
	        ImageButton imageButton5= (ImageButton) findViewById(R.id.imageButton5);

	        imageButton5.setOnClickListener(new View.OnClickListener(){

	            public void onClick(View v) {
	            	Toast.makeText(Sos.this,"SOSButton has been Clicked", Toast.LENGTH_SHORT).show();
	            	
	             }

	    });

	    }

}
