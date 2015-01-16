package com.traxbuddy.trax;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
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
	        Cursor c =  managedQuery(contactData, null, null, null, null);
	        if (c.moveToFirst()) {
	          String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	          // TODO Fetch other Contact details as you want to use

	        }
	      }
	      break;
	  }
	}
}