package com.traxbuddy.trax;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
 
public class SignUp extends Activity {
	private MainActivity mActivity;
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
 
        
        
        //ImageButton
        ImageButton signup= (ImageButton) findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
            	Toast.makeText(SignUp.this,"SignUp button Clicked", Toast.LENGTH_SHORT).show();
            	RegisterMe();   
         }
          });

    }
    
    private void RegisterMe() {
        Intent i = new Intent(SignUp.this, RegisterMe.class);
        startActivity(i);
    }
    
    public void onDestroy()
    {
       super.onDestroy();
       Log.i("Destroying SignUp", "In the onDestroy() event");
    }
    
    
}