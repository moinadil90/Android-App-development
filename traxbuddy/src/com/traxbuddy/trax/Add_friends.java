package com.traxbuddy.trax;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
 
public class Add_friends extends Activity {
	
	protected static final int PICK_CONTACT = 0;
	public Add_friends(){}
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);
 
        // get action bar   
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        //ImageButton
        ImageButton imageButton1= (ImageButton) findViewById(R.id.imageButton1);

        imageButton1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
            	Toast.makeText(Add_friends.this,"ImageButton is Clicked", Toast.LENGTH_SHORT).show();
            	Intent intent= new Intent(Intent.ACTION_PICK,  Contacts.CONTENT_URI);

                startActivityForResult(intent, PICK_CONTACT);    
             }

    });

    }
    @Override
	public void onActivityResult(int reqCode, int resultCode, Intent data) {
	  super.onActivityResult(reqCode, resultCode, data);

	  switch (reqCode) {
	    case (PICK_CONTACT) :
	      if (resultCode == Activity.RESULT_OK) {
	        Uri contactData = data.getData();
	       
	        Cursor c =  managedQuery(contactData, null, null, null, null); //This is deprecated
	        //Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
	       if (c.moveToFirst()) {
	          //while (c.moveToNext()){
	          String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	          Log.i("Name: ", name);
	          String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
	          //String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	          // TODO Fetch other Contact details as you want to use

              if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
            	  Cursor pCur = managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            		        null,
            		         ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            		            + " = ?", new String[] { id },
            		       null);



            		 while (pCur.moveToNext()) {
            		 String phoneNo =   pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            		 Log.i("Phone Number: ", phoneNo);  }  
              }  
	         // Log.i("Name: ", name);
	         // Log.i("Phone Number: ", phoneNo);
                }
	       
	             
	        
	      }
	      break;
	  }
	}
}
    
    
    