package com.traxbuddy.trax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Type;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

public class ConfirmToken extends Activity //implements OnClickListener, TraxStore.HttpResponseIf 
{
    //private MainActivity mActivity;
	public String mtokenno;
	public EditText eConfirm;
	public String line;
	public int i;
	Map<String, Object> map = new HashMap<String, Object>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);
      
        
        eConfirm= (EditText) findViewById(R.id.eConfirm);
        //ImageButton
        ImageButton bConfirm= (ImageButton) findViewById(R.id.bConfirm);
        

        bConfirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
            	Toast.makeText(ConfirmToken.this,"Confirm button Clicked", Toast.LENGTH_SHORT).show();
                Log.i("Latitude", Double.toString(MainActivity.lat)); 
                mtokenno = eConfirm.getText().toString();
                postData();
                

               
         }
            //test
            public void postData() {
            	
            	 //String line;
         		//obj=new MainActivity();
         		Thread thread = new Thread(new Runnable(){
         		    @Override
         		    public void run() {
         		        try {
         		            //Your code goes here
         		        	try{
         		        	    // Create a new HttpClient and Post Header
         		        	    HttpClient httpclient = new DefaultHttpClient();
         		        	    HttpPost httpPost3 = new HttpPost("http://www.traxbuddy.com/confirm.json");

         		        	    Log.i("Inside TokenPost", "Inside TokenPost");
         		        	       	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
         		            		//String foo= "id_token="+token+"&lat="+59.8944400+"&lng="+30.2641700;
         		            	    //nameValuePair1.add(new BasicNameValuePair("id_token", token ));
         		        	       	
         		        	       	  Log.i("NMA Mobile Token", "NMA Mobile Token" + mtokenno);
         		        	       	  Log.i("NMA Mobile No.", "NMA Mobile No." + RegisterMe.mobile);
         		        	       	  Log.i("NMA IMEI", "NMA IMEI" + "911372300074425"/*imeicontact*/);
         		        	       	  
         		        	          nameValuePair.add(new BasicNameValuePair("mobile", RegisterMe.mobile));
         		         		      nameValuePair.add(new BasicNameValuePair("imei", "911372300074425"));
         		         		      nameValuePair.add(new BasicNameValuePair("stoken", mtokenno));
         		         		     // 
         		         		   try {
         	         	       			httpPost3.setEntity(new UrlEncodedFormEntity(nameValuePair));
         	         	       		} catch (UnsupportedEncodingException e) {
         	         	       			// writing error to Log
         	         	       			e.printStackTrace();
         	         	       		}
         		         		   //
         		         		      
         		        	        // Execute HTTP Post Request
         		         		    try{
         		        	        HttpResponse response = httpclient.execute(httpPost3);
         		        	        
         		        	        //Log.i("resp.length", ""+response.getEntity().getContentLength());
         		        	        Log.i("Response from Http Post Request", "Response from Http Post Request: "+ response);
         		        	        System.out.println("Response: " +response.toString());
         		        	        
         		        	   
         		        	       Log.i("Starting BufferedReader", "Starting BufferedReader");
         		        	       BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
         	         	 	       StringBuilder builder = new StringBuilder();
         	         	 	       for (line = null; (line = reader.readLine()) != null;) {
         	         	 	           builder.append(line).append("\n");
         	         	 	    	  
         	         	 	           System.out.println(line); 
         	         	 	           
         	         	 	         //Class Deserializer
         	         	 	           
         	         	 	       class NaturalDeserializer implements JsonDeserializer<Object> {
             	          	 		  public Object deserialize(JsonElement json, Type typeOfT, 
             	          	 		      JsonDeserializationContext context) {
             	          	 		    if(json.isJsonNull()) return null;
             	          	 		    else if(json.isJsonPrimitive()) return handlePrimitive(json.getAsJsonPrimitive());
             	          	 		    else if(json.isJsonArray()) return handleArray(json.getAsJsonArray(), context);
             	          	 		    else return handleObject(json.getAsJsonObject(), context);
             	          	 		  }
             	          	 		  private Object handlePrimitive(JsonPrimitive json) {
             	          	 		    if(json.isBoolean())
             	          	 		      return json.getAsBoolean();
             	          	 		    else if(json.isString())
             	          	 		      return json.getAsString();
             	          	 		    else {
             	          	 		      BigDecimal bigDec = json.getAsBigDecimal();
             	          	 		      // Find out if it is an int type
             	          	 		      try {
             	          	 		        bigDec.toBigIntegerExact();
             	          	 		        try { return bigDec.intValueExact(); }
             	          	 		        catch(ArithmeticException e) {}
             	          	 		        return bigDec.longValue();
             	          	 		      } catch(ArithmeticException e) {}
             	          	 		      // Just return it as a double
             	          	 		      return bigDec.doubleValue();
             	          	 		    }
             	          	 		  }
             	          	 		  private Object handleArray(JsonArray json, JsonDeserializationContext context) {
             	          	 		    Object[] array = new Object[json.size()];
             	          	 		    for(int i = 0; i < array.length; i++)
             	          	 		      array[i] = context.deserialize(json.get(i), Object.class);
             	          	 		    return array;
             	          	 		  }
             	          	 		  public Object handleObject(JsonObject json, JsonDeserializationContext context) {
             	          	 		    //Map<String, Object> map = new HashMap<String, Object>();
             	          	 		  
             	          	 		    for(Map.Entry<String, JsonElement> entry : json.entrySet())
             	          	 		    
             	          	 		   //System.out.println(map.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class)));
             	          	 		   map.put(entry.getKey(), context.deserialize(entry.getValue(), Object.class));
             	          	 		    System.out.println("~~~~~~~~~~~~Size:"+map.size());
             	          	 		   //Log.i("Tango Inside Deserialize", "Tango Inside Deserialize"); 	
             	          	 		/*System.out.println(map.get("utoken"));
             	          	 		utoken= (String) (map.get("utoken"));*/
             	          	 		    return map;
             	          	 		    
             	          	 		  }
             	 					@Override
             	 					public Object deserialize(JsonElement arg0,
             	 							java.lang.reflect.Type arg1,
             	 							JsonDeserializationContext arg2)
             	 							throws JsonParseException {
             	 						// TODO Auto-generated method stub.
             	 						return null;
             	 					}
             	          	 		}//Class Natural Deserializer ends here
         	         	 	       
             	             		GsonBuilder gsonBuilder = new GsonBuilder();
             	             	 	gsonBuilder.registerTypeAdapter(Object.class, new NaturalDeserializer());
             	             	 	Gson gson = gsonBuilder.create();
             	             	 	
             	             	 	Object natural = gson.fromJson(line, Object.class);
             	             	 	//System.out.println(json);
             	             	 System.out.println(natural);
             	             	 Log.i("NaturalDeserializer", "NaturalDeserializer");
             	             	 Log.i("Check", "Check");
             	             	 JSONObject fieldsJson = new JSONObject(line);
             	             	 String uvalue = fieldsJson.getString("utoken");
             	             	 System.out.println(uvalue);
             	             	 String avalue = fieldsJson.getString("atoken");
             	             	 
             	             	 	
             	             	 //Making 3rd POST call curl --data "utoken=7d02a59410722737566f33879dcd061d5f45bc5a&lat=26.850307&lng=80.916853" http://www.traxbuddy.com/updt	
             	             	
             	             	HttpClient httpclient3 = new DefaultHttpClient();
         		        	    HttpPost httpPost3a = new HttpPost("http://www.traxbuddy.com/updt");

         		        	    Log.i("III POST Call", "III POST Call");
         		        	       	List<NameValuePair> nameValuePair3a = new ArrayList<NameValuePair>(3);
         		            		
         		        	       	  Log.i("utoken", "utoken" + uvalue);
         		        	       	  Log.i("Latitude for III POST Call", "Latitude for III POST Call" + MainActivity.lat);
         		        	       	  Log.i("Longitude for III POST Call", "Longitude for III POST Call" + MainActivity.lng);
         		        	       	  
         		        	          nameValuePair3a.add(new BasicNameValuePair("utoken", uvalue));
         		         		      nameValuePair3a.add(new BasicNameValuePair("lat", Double.toString(MainActivity.lat)));
         		         		      nameValuePair3a.add(new BasicNameValuePair("lng", Double.toString(MainActivity.lng)));
         		         		     // 
         		         		   try {
         	         	       			httpPost3a.setEntity(new UrlEncodedFormEntity(nameValuePair3a));
         	         	       		} catch (UnsupportedEncodingException e) {
         	         	       			// writing error to Log.
         	         	       			e.printStackTrace();
         	         	       		}
         		         		   //
         		         		      
         		        	        // Execute HTTP Post Request
         		         		    try{
         		        	        HttpResponse response3a = httpclient3.execute(httpPost3a);
         		        	        
         		        	        //Log.i("resp.length", ""+response.getEntity().getContentLength());
         		        	        Log.i("Response from III Http Post Request", "Response from III Http Post Request: "+ response3a);
         		        	        System.out.println("Response: " +response3a.toString());
         		        	        
         		        	        // Making IV POST Call
         		        	        //curl --data "atoken=98ac1866b41746c09a81f19bb705fc8d418e6b4e" http://www.traxbuddy.com/coords.json
         		        	    HttpClient httpclient4 = new DefaultHttpClient();
           		        	    HttpPost httpPost4 = new HttpPost("http://www.traxbuddy.com/coords.json");

           		        	    Log.i("IV POST Call", "IV POST Call");
           		        	       	List<NameValuePair> nameValuePair4 = new ArrayList<NameValuePair>(1);
           		            		
           		        	       	
           		        	       	  Log.i("atoken", "atoken" + avalue);
           		        	          nameValuePair4.add(new BasicNameValuePair("atoken", avalue));
           		         		     
           		         		   try {
           	         	       			httpPost4.setEntity(new UrlEncodedFormEntity(nameValuePair4));
           	         	       		} catch (UnsupportedEncodingException e) {
           	         	       			// writing error to Log
           	         	       			e.printStackTrace();
           	         	       		}
           		         		   //
           		         		      
           		        	        // Execute HTTP Post Request
           		         		    try{
           		        	        HttpResponse response4 = httpclient4.execute(httpPost4);
           		        	        
           		        	        Log.i("Response from IV Http Post Request", "Response from IV Http Post Request: "+ response3a);
           		        	        System.out.println("Response: " +response4.toString());
           		        	        
           		        	        //Making V POST Call
           		        	        //curl --data "atoken=98ac1866b41746c09a81f19bb705fc8d418e6b4e&email=moinadil90@gmail.com" http://www.traxbuddy.com/set_email
            		        	    HttpClient httpclient5 = new DefaultHttpClient();
           		        	    HttpPost httpPost5 = new HttpPost("http://www.traxbuddy.com/set_email");

           		        	    Log.i("V POST Call", "V POST Call");
           		        	       	List<NameValuePair> nameValuePair5 = new ArrayList<NameValuePair>(2);
           		            		
           		        	       	
           		        	       	  Log.i("atoken", "atoken" + avalue);
           		        	          nameValuePair5.add(new BasicNameValuePair("atoken", avalue));
           		        	          nameValuePair5.add(new BasicNameValuePair("email", "moinadil90@gmail.com"/*Plus.AccountApi.getAccountName(mGoogleApiClient)*/));
           		         		     
           		        	          //Database Storage
           		        	      // Inserting Contacts
           		        	        Log.d("Insert: ", "Inserting ..");
           		        	       // db.addContact(new Contact(Plus.AccountApi.getAccountName(mGoogleApiClient), phNo));
           		        	        //db.addContact(new Contact("Srinivas", "9199999999"));
           		        	        //db.addContact(new Contact("Tommy", "9522222222"));
           		        	        //db.addContact(new Contact("Karthik", "9533333333"));
           		        	 
           		        	        // Reading all contacts
           		        	        Log.d("Reading: ", "Reading all contacts..");
           		        	       // List<Contact> contacts = db.getAllContacts();       
           		        	 
           		        	        /*for (Contact cn : contacts) {
           		        	            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
           		        	                // Writing Contacts to log
           		        	        Log.d("Name: ", log);
           		        	        
           		        	        }*/
           		        	        //Reading Contacts From Phone
           		        	   
           		        	        //Reading Contacts From Phone
           		        	          //Database Storage
           		        	          
           		         		   try {
           	         	       			httpPost5.setEntity(new UrlEncodedFormEntity(nameValuePair5));
           	         	       		} catch (UnsupportedEncodingException e) {
           	         	       			// writing error to Log
           	         	       			e.printStackTrace();
           	         	       		}
           		         		   //
           		         		      
           		        	        // Execute HTTP Post Request
           		         		    try{
           		        	        HttpResponse response5 = httpclient5.execute(httpPost5);
           		        	        
           		        	        Log.i("Response from V Http Post Request", "Response from V Http Post Request: "+ response3a);
           		        	        System.out.println("Response: " +response5.toString());
           		        	        
           		        	              	             		        	       
           	         	 	   }
           		         		 catch (ClientProtocolException e) {
                   	       			// writing exception to log
                   	       			e.printStackTrace();//
                   	       		}
           		        	        
           		        	        
           		        	        //V POST Call ends here
           		        	      
         		        	        
           	         	 	   }
           	         	 	       //
           	         	 	       
           		         		 catch (ClientProtocolException e) {
                   	       			// writing exception to log
                   	       			e.printStackTrace();//
                   	       		}
         		        	        
         		        	        // IV POST Call ends here
         		        	      
       		        	        
         	         	 	   }
         	         	 	       //
         	         	 	       
         		         		 catch (ClientProtocolException e) {
                 	       			// writing exception to log
                 	       			e.printStackTrace();//
                 	       		}
             	             	 //   III POST call ends here   	                 	             	    	                 	             	 
             	     	 	   		
             	     	 	   	
         	             	 	//System.out.println(json);
         	             	 	
         	             	    //System.out.println(natural);
             	     	 	   	   //// System.out.println(((HashMap<String,Object>) natural).get("utoken")); 
             	     	 	   	    //System.out.println(utoken);
             	     	 	   	   //
             	     	 	   	   
         	         	 	       
         	         	 	           
         	         	 	       }
         	         	 	       //
         		         		    }//try block of II POST call
         		         		 catch (ClientProtocolException e) {
                 	       			// writing exception to log
                 	       			e.printStackTrace();//
                 	       		}
         		        	        
         		        	    }
         		        	    catch (ClientProtocolException e) {
         		        	        // TODO Auto-generated catch block
         		        	    } catch (IOException e) {
         		        	        // TODO Auto-generated catch block
         		        	    }
         		        	
         		        } catch (Exception e) {
         		            e.printStackTrace();
         		        }
         		    }

					
         		});
               Log.i("Starting thread", "Starting thread");
         		thread.start(); 
         		
         		i=1;
         		Log.i("IIII", "IIII"+i);
         		  if(i==1){
         			MainActivity.check=100;  
         			//refer to protected static int check= 10; declared in MainActivity.java
         			Intent i=new Intent(getApplicationContext(), MainActivity.class);
         	        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         	        startActivity(i);
         	       }
         	}//postData 
            //test
          });

      
    }
    
    
	
  
}
