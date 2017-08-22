package com.example.qiyamuddinahmadzai.sms006;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View.OnClickListener;

import com.example.qiyamuddinahmadzai.sms006.db.DBHelper;
import com.example.qiyamuddinahmadzai.sms006.gmailservices.GMailSender;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button button;
    GMailSender sender;
    String e_number, e_message, e_datetime;
    EditText txtMsg;
    TextView labSenderEmailAddress, labReceiverEmailAddress;

    private DBHelper dbSms ;

    String Sender_Email_Add;
    String Sender_Email_Pass;
    String Receiver_Email_Add;
    String Email_Subject;
   //sds

    SharedPreferences pref;
   // private int STORAGE_PERMISSION_CODE=23;

    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbSms = new DBHelper(this);

       // dbSms.updateSms("127", "+93728898017", "9AT 40 1489923936 72 25:9AT 40 1489927673 72 26:9AT 40 1489931406 66 21", "1", "1489915192000");
      //  dbSms.resetAllSMS();
        labSenderEmailAddress = (TextView) findViewById(R.id.labSenderEmailAdd);
        labReceiverEmailAddress = (TextView) findViewById(R.id.labReceiverEmailAdd);

        PackageManager pm = this.getPackageManager();


        //  ActivityCompat.requestPermissions(this,new String[{Manifest.permission.RECEIVE_MMS},STORAGE_PERMISSION_CODE);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            askForPermission(Manifest.permission.RECEIVE_SMS, SMS_PERMISSION_CODE);
        }

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(!Telephony.Sms.getDefaultSmsPackage(getApplicationContext()).equals(getApplicationContext().getPackageName())) {
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                        getApplicationContext().getPackageName());
                startActivity(intent);
            }
        }*/

        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        // final SharedPreferences.Editor editer = pref.edit();
        String s1 = pref.getString(Setting._KEYSENDERMAILADD, null);
        String s2 = pref.getString(Setting._KEYSENDERMAILPASS, null);
        String s3 = pref.getString(Setting._KEYRECEVIERMAILADD, null);
        String s4 = pref.getString(Setting._KEYMAILSUBJECT, null);

        if(s1 != null ){ Sender_Email_Add = s1; labSenderEmailAddress.setText( "Sender : "+ s1 ); }
        if(s2 != null ){ Sender_Email_Pass = s2; }
        if(s3 != null ){ Receiver_Email_Add = s3; labReceiverEmailAddress.setText( "Receiver : "+ s3 ); }
        if(s4 != null ){ Email_Subject = s4; }//else{ Email_Subject = "TTE"; }

        button = (Button) findViewById(R.id.button);
        txtMsg = (EditText) findViewById(R.id.txtMsg);

        // Add your mail Id and Password
        sender = new GMailSender( Sender_Email_Add, Sender_Email_Pass );
        //sender = new GMailSender("msi.artf7@gmail.com", "kabul@1234567");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        e_number = intent.getStringExtra(EMAIL_NUMBER);
        e_message = intent.getStringExtra(EMAIL_MESSAGE);
        e_datetime = intent.getStringExtra(EMAIL_DATETIME);

        if(e_message != null) {
            smsToEmail();
        }

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
                      e_message = txtMsg.getText().toString();
                      new MyAsyncClass().execute();

                } catch (Exception ex) {
                     Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_setting:
                ///Toast.makeText(MainActivity.this, "Setting Selected!", Toast.LENGTH_LONG).show();
                Intent innt = new Intent(MainActivity.this, Setting.class);
                startActivity(innt);
                return true;

            case R.id.menu_unsent:
               // Toast.makeText(MainActivity.this, "Unsent Selected!", Toast.LENGTH_LONG).show();
                Intent innttt = new Intent(MainActivity.this, SmsListView.class);
                innttt.putExtra("key_un_sent", "0_u");
                startActivity(innttt);
                return true;

            case R.id.menu_sent:
               // Toast.makeText(MainActivity.this, "Inbox Selected!", Toast.LENGTH_LONG).show();
                Intent inntt = new Intent(MainActivity.this, SmsListView.class);
                inntt.putExtra("key_un_sent", "1_s");
                startActivity(inntt);
                return true;

            case R.id.menu_inbox:
               // Toast.makeText(MainActivity.this, "Inbox Selected!", Toast.LENGTH_LONG).show();
                Intent inntttt = new Intent(MainActivity.this, SmsListView.class);
                inntttt.putExtra("key_un_sent", "1_us");
                startActivity(inntttt);
                return true;

        case R.id.menu_white_list:
               // Toast.makeText(MainActivity.this, "Inbox Selected!", Toast.LENGTH_LONG).show();
                Intent intnt = new Intent(MainActivity.this, WhiteList.class);
               // intnt.putExtra("key_un_sent", "1_us");
                startActivity(intnt);
                return true;

            case R.id.menu_about:
                Toast.makeText(MainActivity.this, "About Selected!", Toast.LENGTH_LONG).show();
                return true;

            case R.id.menu_help:
                Toast.makeText(MainActivity.this, "Help Selected!", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        int smsSentCounter = 0;
        int flag = 1;
        @Override
        protected Void doInBackground(Void... mApi) {
            try{
                // Add subject, Body, your mail Id, and receiver mail Id.
                if(SmsListView.getFlagSyncAllEmail() == 421){
                    Log.i("tag007", "getFlagSyncAllEmail XXXXs: "+ SmsListView.getFlagSyncAllEmail() );
                    Log.i("tag007", "Internet XXXXs: "+ isOnline() );

                    if (isOnline() == true) {
                        ArrayList<String> array_sms_list = new ArrayList<String>();
                        array_sms_list = dbSms.getUnSentSms();
                        Log.i("tag009", "Size Of unSend SMS xxxxx : "+ array_sms_list.size() );
                        for(int i=0; i < array_sms_list.size(); i++){
                            String st = array_sms_list.get(i);
                            String[] content = st.split(">");

                            sender.sendMail(Email_Subject, "" + content[2], Sender_Email_Add, Receiver_Email_Add);
                            dbSms.updateSms(content[0], content[1],  content[2], "1", "");
                            smsSentCounter++;
                            flag = 1;
                            Log.i("tag007", "Send smsSentCounter : "+ smsSentCounter );
                        }

                        Log.i("tag007", "Yes Internet : "+ SmsListView.getFlagSyncAllEmail() );
                        Log.i("tag007", "Unsent SMS : "+ array_sms_list.size() );
                    }else{ flag = 3;   Log.i("tag007", "No Internet : "+ SmsListView.getFlagSyncAllEmail() );}

                }else if(SmsDisplay.getFlagSmsDisplay() == 420){
                    Log.i("tag007", "getFlagSmsDisplay : "+ SmsDisplay.getFlagSmsDisplay());
                    String id = SmsDisplay.getIdNumber();

                    if (isOnline() == true) {
                        sender.sendMail(Email_Subject, "" + e_message, Sender_Email_Add, Receiver_Email_Add);
                        dbSms.updateSms(id, e_number, e_message, "1", e_datetime);
                        flag = 1;
                    }else{ flag = 3;}

                }else {

                    Log.i("tag007", "Else Part! ");
                    if (isOnline() == true) {
                        sender.sendMail(Email_Subject, "" + e_message, Sender_Email_Add, Receiver_Email_Add);
                        //sender.sendMail("TTE", ""+e_message, "msi.artf7@gmail.com", "msi.artf2@gmail.com");
                        dbSms.insertSms(e_number, e_message, "1", e_datetime);
                        flag = 1;
                    }else{
                        dbSms.insertSms(e_number, e_message, "0", e_datetime);
                        flag = 3;
                    }

                }

            }

            catch (Exception ex) {

                if(SmsListView.getFlagSyncAllEmail() == 421) { //Sync btn
                    Log.i("tag007", "Exception IF getFlagSyncAllEmail : "+ SmsListView.getFlagSyncAllEmail());
                    if(smsSentCounter > 0){ flag = 1;}else { flag = 0;}

                }else  if(SmsDisplay.flagSmsDisplay == 420) {
                    Log.i("tag007", "Exception else IF getFlagSmsDisplay : "+ SmsDisplay.getFlagSmsDisplay());
                    flag = 0;
                }else {
                    Log.i("tag007", "Exception Else getFlagSmsDisplay : "+ SmsDisplay.getFlagSmsDisplay());
                    dbSms.insertSms( e_number, e_message, "0", e_datetime);
                    flag = 0;
                }
                Log.i("tag007", "Exception IF getFlagSyncAllEmail : "+ex);
               // Toast.makeText(MainActivity.this, "Sent SMS Added Success "+ a, Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
           // Toast.makeText(getApplicationContext(), "Email send"+ isOnline(), Toast.LENGTH_LONG).show();
            String status = "";
            if(flag == 1){ status = (smsSentCounter > 0)? smsSentCounter+" Email send" : " Email send"; }
            else if(flag == 2){ status = "Email not send"; }
            else if(flag == 3){ status = "No Internet Connection!";}

            SmsListView.setFlagSyncAllEmail(0);
            SmsDisplay.setIdNumber("");
            SmsDisplay.setFlagSmsDisplay(0);

            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
            //MainActivity.this.finish();
          // finish();
          // System.exit(0);
            finishAndRemoveTask ();

        }
    }

    public static final String EMAIL_NUMBER = "number";
    public static final String EMAIL_MESSAGE = "message";
    public static final String EMAIL_DATETIME = "status";

    public void smsToEmail(){
        try {
            new MyAsyncClass().execute();

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

















    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }


    private int SMS_PERMISSION_CODE = 23;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == SMS_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }












}

