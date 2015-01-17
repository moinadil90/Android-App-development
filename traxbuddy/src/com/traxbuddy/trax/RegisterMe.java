package com.traxbuddy.trax;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterMe extends Activity implements OnClickListener, TraxStore.HttpResponseIf {
    private MainActivity mActivity;
    public String imei;
    public static String mobile;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.registerme);
       //TelephonyManager tmngr = (TelephonyManager)mActivity.getSystemService(Context.TELEPHONY_SERVICE); 
      //final String imei = tmngr.getDeviceId();
     //Getting the Object of TelephonyManager 
       TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
     //Getting IMEI Number of Devide
       imei=tManager.getDeviceId();
       
       ImageButton bRegister= (ImageButton) findViewById(R.id.bRegister);
       bRegister.setOnClickListener(new View.OnClickListener(){
  

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	EditText eMobileNo = (EditText) findViewById(R.id.eMobileNo);
    mobile = eMobileNo.getText().toString().replaceAll("[^\\d]", "");;

   

    String tmp = "Sign up code sent to " + mobile + " imei: " + imei;
    Toast.makeText(RegisterMe.this,"Register button Clicked", Toast.LENGTH_SHORT).show();
    
    postData();
    ConfirmToken();

}});
       }
   
   public void postData() { 
   //test
  Thread thread = new Thread(new Runnable(){
  @Override
  public void run() { 		       
  try {
		            //Your code goes here
	  Log.i("TraxBuddy", "Inside postData()");
	  // Creating HTTP client
  		HttpClient httpClient = new DefaultHttpClient();
  		// Creating HTTP Post
  	  	 HttpPost httpPost = new HttpPost(
  				//"http://www.traxbuddy.com/updt");
  			"http://www.traxbuddy.com/register");   

  		// Building post parameters
  		// key and value pair
  		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
  		Log.i("Get Contact No. ", "Get Contact No. "+ mobile);
  	    nameValuePair.add(new BasicNameValuePair("mobile", mobile));
  		nameValuePair.add(new BasicNameValuePair("imei", "911372300074425"/*imeicontact*/));
  		 	      
  		  Log.i("POST call", "curl --data mobile=919839354435&imei=imeicontact http://www.traxbuddy.com/register"); 
      	 
  		  // Url Encoding the POST parameters
  		try {
  			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
  		} catch (UnsupportedEncodingException e) {
  			// writing error to Log
  			e.printStackTrace();
  		}

  		// Making HTTP Request
  		try {
  			HttpResponse response = httpClient.execute(httpPost);

  			// writing response to log
  			Log.d("Http Response:", response.toString());
		        	//Your code goes here  
  		}
  		catch (IOException e) {
    			// writing exception to log
    			e.printStackTrace();

    		}
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

}
                                          });
  Log.i("Starting thread", "Starting thread");
  thread.start(); 
   }//post data() ends
   //test
 	         		    
private void ConfirmToken() {
    Intent i = new Intent(RegisterMe.this, ConfirmToken.class);
    startActivity(i);
}

@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	
}
@Override
public void postExecute(HttpResponse r) {
	// TODO Auto-generated method stub
	
}
   }

