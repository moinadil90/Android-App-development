package com.traxbuddy.trax;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.traxbuddy.trax.slidingmenu.adapter.NavDrawerListAdapter;
import com.traxbuddy.trax.slidingmenu.model.NavDrawerItem;


public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener, LocationListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	private String TAG2 = this.getClass().getSimpleName();
	public GoogleMap googleMap;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	public ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
	//5/1/2014
	public final String TAG = "MainActivity";
	public String atoken = "";
    public String utoken = "";
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	
	//private String TAG2 = this.getClass().getSimpleName();
	public Location location;
	public static double lat, lng;
	public Location loc;
	private LocationClient locationclient;
	
	protected static final int PICK_CONTACT = 0;
	//public static final String TAG = null;
	protected static int check= 10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i("Check","Value of check: " +check);
		if (check==10){
		SignUp();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Adding FusedLocationProvider
        int resp =GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(resp == ConnectionResult.SUCCESS){
			locationclient = new LocationClient(this,this,this);
			locationclient.connect();
		}
		else{
			Toast.makeText(this, "Google Play Service Error " + resp, Toast.LENGTH_LONG).show();
			
		}
		//Added FLP
		
		imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(this);
        
        imageButton2 = (ImageButton)findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(this);
        
        imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(this);
        
        imageButton4 = (ImageButton)findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(this);
        
        imageButton5 = (ImageButton)findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(this);
    
		        
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		
		//My Profile
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Be a part of this Campaign
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Settings
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Help
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// History
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// Log Out
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)/*, true, "50+"*/));
		//
		//navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(6, -1)));   
		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		// get action bar   
        ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(-1);
		}
		
		// Loading map
		initializeMap();
    		

		// Changing map type
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
		// Showing / hiding your current location
		googleMap.setMyLocationEnabled(true);

		// Enable / Disable zooming controls
		googleMap.getUiSettings().setZoomControlsEnabled(false);

		// Enable / Disable my location button
		googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		// Enable / Disable Compass icon
		googleMap.getUiSettings().setCompassEnabled(true);

		// Enable / Disable Rotate gesture
		googleMap.getUiSettings().setRotateGesturesEnabled(true);

		// Enable / Disable zooming functionality
		googleMap.getUiSettings().setZoomGesturesEnabled(true); 
		
	//}// check() closed
		
	}
	//Display what happens on clicking ImageButtons
	public void onClick(View v) {

		Toast pieceToast=null;

		switch (v.getId()) {

		case R.id.imageButton1:
			pieceToast= Toast.makeText(getApplicationContext(), "Image Button One Clicked", Toast.LENGTH_SHORT);
			pieceToast.show();		
			//Adding code for opening contact list.
		    Intent intent= new Intent(Intent.ACTION_PICK,  Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT); 
			
            break;

		case R.id.imageButton2:
			pieceToast= Toast.makeText(getApplicationContext(), "Image Button Two Clicked", Toast.LENGTH_SHORT);
			pieceToast.show();
			Add_friends();
			break;

		case R.id.imageButton3:
			pieceToast= Toast.makeText(getApplicationContext(), "Image Button Three Clicked", Toast.LENGTH_SHORT);
			pieceToast.show();
            SignUp();
			break;

		case R.id.imageButton4:			
			pieceToast= Toast.makeText(getApplicationContext(), "Image Button Four Clicked", Toast.LENGTH_SHORT);
			pieceToast.show();
			Add_friends();
			break;
			
		case R.id.imageButton5:			
			pieceToast= Toast.makeText(getApplicationContext(), "Image Button Five Clicked", Toast.LENGTH_SHORT);
			//Log.i("FLP", "Calling FLP");
	       	pieceToast.show();
			break;
		
		default:
			break;
		}

	}
	

	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
			
		}
		
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
 
        return super.onCreateOptionsMenu(menu);
    }

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}*/
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) 
			return true;
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        
        case R.id.action_settings:
			return true;
        case R.id.add_friends:
            // search action
        Add_friends();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    
		// Handle action bar actions click
		/*switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}*/
	}
	
	private void Add_friends() {
        Intent i = new Intent(MainActivity.this, Add_friends.class);
        startActivity(i);
    }
	
	private void SignUp() {
        Intent i = new Intent(MainActivity.this, SignUp.class);
        startActivity(i);
        
    }
	
	public void FLP(){
		//Adding FLP
		   if(locationclient!=null && locationclient.isConnected()){
			loc =locationclient.getLastLocation();
			 //lat = loc.getLatitude();
       	     //lng = loc.getLongitude();
			lat= 26.8494842;
			lng= 80.9217358;
			
       	    Log.i("Fused Location Provider", "Fused Location Provider"+lat );
       	    Log.i("Fused Location Provider", "Fused Location Provider"+lng );
       	 Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + lat + "\nLong: " + lng, Toast.LENGTH_LONG).show();	
      // Adding a marker
			MarkerOptions marker = new MarkerOptions().position(
					//new LatLng(randomLocation[0], randomLocation[1]))
					new LatLng(lat, lng))
					.title("personName" /*+ i*/);
			
		
				marker.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
			
				googleMap.addMarker(marker);

			// Move the camera to last position with a zoom level
			//if (i == 9) {
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(lat, lng)).zoom(15).build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
		}
		 //Added FLP
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new MyProfileFragment();
			break;
		case 1:
			fragment = new CampaignFragment();
			break;
		case 2:
			fragment = new SettingsFragment();
			break;
		case 3:
			fragment = new HelpFragment();
			break;
		case 4:
			fragment = new HistoryFragment();
			break;
		case 5:
			fragment = new LogOutFragment();
			break;
		
		default:
			
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
                         .replace(R.id.frame_container, fragment).commit();
			//googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					//R.id.frame_container)).getMap();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
		// get action bar   
       // ActionBar actionBar = getActionBar();
 
        // Enabling Up / Back navigation
        //actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	
	
	 private void initializeMap() {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();
				
		        //Log.i("FLP2", Double.toString(MainActivity.lat));
				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
					
					if (googleMap != null) {
                     FLP();
                     Log.i("FLP", "FLP");
                     lat= 26.8494842;
         			 lng= 80.9217358;
                     CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(lat, lng)).zoom(15).build();
               googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
               Log.i("FLP2", Double.toString(MainActivity.lat));
					}
						
				}
			}
			
		}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		
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
              Log.i("ContactName: ", name);
              Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
            		    Uri.parse("http://maps.google.com/maps?daddr=26.8500000,81.9166700"));
            		startActivity(intent);
            		//saddr=26.8500000,80.9166700&
	        }
	      }
	      break;
	  }
	}

	
}
