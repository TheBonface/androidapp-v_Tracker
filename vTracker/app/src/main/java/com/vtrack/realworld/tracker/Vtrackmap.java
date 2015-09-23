package com.vtrack.realworld.tracker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Vtrackmap extends FragmentActivity {


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vtrackmap);
        setUpMapIfNeeded();



Bundle Broadcastdata=getIntent().getExtras(); //enabling Vtrackmap class to accept data from Sms broadcast Receiver class using Intent
        if(Broadcastdata==null){
            return;

        }
//data received from SMSBroadcast Receiver intent is referenced using the Texview Id's to display the fetched data, display
        String power =Broadcastdata.getString("power");
        final TextView  Rpower=(TextView)findViewById(R.id.pwr);
        Rpower.setText(power);

        String new_speed =Broadcastdata.getString("speed");
        final TextView  Rnew_speed=(TextView)findViewById(R.id.rspeed);
        Rnew_speed.setText(new_speed);

        String Door =Broadcastdata.getString("Door");
        final TextView  RDoor=(TextView)findViewById(R.id.rdoor);
        RDoor.setText(Door);

        String Acc =Broadcastdata.getString("acc");
        final TextView  RAcc=(TextView)findViewById(R.id.racc);
        RAcc.setText(Acc);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    public void setUpMap() {


        Bundle Broadcastdata=getIntent().getExtras(); //enabling Vtrackmap class to accept data from Sms broadcast Receiver class using Intent
        if(Broadcastdata==null){
            return;

        }
//data received from SMSBroadcast Receiver intent is referenced using the Texview Id's to display the fetched data, display
        String new_latt =Broadcastdata.getString("latt");
        Double lattitude=Double.parseDouble(new_latt);

        String new_long=Broadcastdata.getString("long");
        Double longitude=Double.parseDouble(new_long);
        mMap.getMaxZoomLevel();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.addMarker(new MarkerOptions().position(new LatLng(lattitude, longitude)).title("vehicle is here"));


    }


    }





