package com.vtrack.realworld.tracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Bonface on 20-Aug-15.
 */
public class Homescreen extends Activity {
    public Button exitbutton,btrack;
    public EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        btrack=(Button)findViewById(R.id.bTrack);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);

        exitbutton=(Button)findViewById(R.id.bExit);

        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        // receive notifications of telephony state changes
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        exitbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });


        btrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Class trackIt= null;
                try {
                    trackIt = Class.forName("com.vtrack.realworld.tracker.Vtrackmap");
           String uri = "tel:"+phoneNumber.getText().toString();

                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
                    startActivity(callIntent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Your call has failed...", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }

    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(Homescreen.this, incomingNumber + " calls you",
                            Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(Homescreen.this, "on call...",
                            Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:

                    // in initialization of the class and at the end of phone call
                    // detect flag from CALL_STATE_OFFHOOK
                    if (onCall == true) {
                        Toast.makeText(Homescreen.this, "restart app after call",
                                Toast.LENGTH_LONG).show();
                        Intent restart = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);


                        onCall = false;
                    }
                    break;
                default:
                    break;
            }

        }

    }


    }


