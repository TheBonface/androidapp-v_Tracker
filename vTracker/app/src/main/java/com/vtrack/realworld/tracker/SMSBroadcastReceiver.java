package com.vtrack.realworld.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by root on 8/26/15.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver  {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSBroadcastReceiver";
    private String lattitude;
    private String longitude;
    private String power;
    private String Door;
    private String ACC;
    private String speed;
    public String from;
    public String from1;
    public String str = "";


    @Override
    public void onReceive(Context context, Intent intent){

        //---get the SMS message passed in---
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;

        if (bundle != null){
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if (i == 0) {
                    from = msgs[i].getOriginatingAddress();
                }
                //---get the message body---
                str += msgs[i].getMessageBody().toString();
            }


            String[] tokens = str.split("\\s");

            lattitude = tokens[0];
            longitude = tokens[1];
            speed = tokens[2];
            power = tokens[7];
            Door = tokens[9];
            ACC = tokens[11];

            //Extract lattitude parameters
            int start_latt = lattitude.indexOf(":") + 1;
            String new_latt = lattitude.substring(start_latt);

            //Extract longitude parameters
            int start_long = longitude.indexOf(":") + 1;
            String new_long = longitude.substring(start_long);

            //Extract speed
            int start_speed = speed.indexOf(":") + 1;
            String new_speed = speed.substring(start_speed);

            //Toast.makeText(context,new_long,Toast.LENGTH_LONG).show();


            try {
                intent = new Intent();
                intent.setClassName("com.vtrack.realworld.tracker", "com.vtrack.realworld.tracker.Vtrackmap");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.putExtra(Intent.EXTRA_TEXT,Door);

                intent.putExtra("latt", new_latt);
                intent.putExtra("long", new_long);
                intent.putExtra("speed", new_speed);
                intent.putExtra("power", power);
                intent.putExtra("acc", ACC);
                intent.putExtra("Door", Door);

                context.startActivity(intent);
            }
            catch (InstantiationError e){
                e.printStackTrace();
            }

        }



    }



}