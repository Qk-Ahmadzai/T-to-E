package com.example.qiyamuddinahmadzai.sms006;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.qiyamuddinahmadzai.sms006.db.DBHelper;

import java.util.ArrayList;

/**
 * Created by Qiyamuddin Ahmadzai on 2/22/2017.
 */
public class SmsReceiver extends BroadcastReceiver {
    private DBHelper dbSms ;
    String sms = "";
    String senderNumber = "";
    String dateTime = "";

    public void onReceive(Context context, Intent intent) {
       dbSms = new DBHelper(context);
       Toast.makeText(context, "message Is received!", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        Object[] objects = (Object[])bundle.get("pdus");

            for (int i=0; i < objects.length; i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])objects[i]);
                String smsBody = smsMessage.getMessageBody();
                senderNumber = smsMessage.getDisplayOriginatingAddress();
                dateTime = smsMessage.getTimestampMillis()+"";
                sms += smsBody;
            }

          //  String[] st = sms.split(":");
          //  int colonCounter = (st.length - 1);
         //   Toast.makeText(context, "Timestamp in Millis : "+ dateTime, Toast.LENGTH_LONG).show();

           // Toast.makeText(context, "Message Is received!\n"+ sms, Toast.LENGTH_LONG).show();
         /*   if((colonCounter <= 3 && colonCounter >= 2) || sms.contains("Sensor") ) {
             //   String[] add_body_status =
                boolean a = dbSms.insertSms( senderNumber, sms, "0", dateTime);
                Toast.makeText(context, "SMS Added Success "+ a, Toast.LENGTH_SHORT).show();
            }*/

        ArrayList<String> array_list_numbers = new ArrayList<String>();
        array_list_numbers = dbSms.getOnlyNumbers();
        Toast.makeText(context, "Contains : "+ array_list_numbers.contains(senderNumber), Toast.LENGTH_LONG).show();

        if(array_list_numbers.contains(senderNumber)) {
            callMainActivity(context, sms);
        }


    }

    public void callMainActivity(Context context, String sms){
        Intent result = new Intent(context, MainActivity.class);

        result.putExtra(MainActivity.EMAIL_NUMBER, senderNumber);
        result.putExtra(MainActivity.EMAIL_MESSAGE, sms);
        result.putExtra(MainActivity.EMAIL_DATETIME, dateTime);

        result.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(result);
    }








}
