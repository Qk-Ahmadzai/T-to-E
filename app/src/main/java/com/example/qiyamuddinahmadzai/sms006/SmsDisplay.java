package com.example.qiyamuddinahmadzai.sms006;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiyamuddinahmadzai.sms006.db.DBHelper;

public class SmsDisplay extends AppCompatActivity {

    TextView txtNumber, txtBody, txtDateTime;
    Button btnSend, btnDelete;
    private DBHelper dbSms ;

    static int flagSmsDisplay = 0;
    static String ID_NUMBER = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_display);

        dbSms = new DBHelper(this);

        final String sId = getIntent().getExtras().getString("sidd");
        final String num = getIntent().getExtras().getString("num");
        final String body = getIntent().getExtras().getString("body");
        final String datetime = getIntent().getExtras().getString("date");

        txtNumber = (TextView) findViewById(R.id.sd_number);
        txtBody = (TextView) findViewById(R.id.sd_sms);
        txtDateTime = (TextView) findViewById(R.id.sd_time);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        txtNumber.setText(num);
        txtBody.setText(body);
        txtDateTime.setText(datetime);


        Toast.makeText(SmsDisplay.this, "sId : "+ sId, Toast.LENGTH_SHORT).show();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFlagSmsDisplay(420);
                setIdNumber(sId);

                Intent innnt = new Intent(SmsDisplay.this, MainActivity.class);
                innnt.putExtra(MainActivity.EMAIL_NUMBER, txtNumber.getText().toString());
                innnt.putExtra(MainActivity.EMAIL_MESSAGE, txtBody.getText().toString());
                innnt.putExtra(MainActivity.EMAIL_DATETIME, txtDateTime.getText().toString());
                startActivity(innnt);

                if(isOnline() == true){ finish(); }

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(SmsDisplay.this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want to Delete this SMS message!");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dbSms.deleteSms(sId);
                        Toast.makeText(SmsDisplay.this, "Delete Successfuly! ", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


    }



    public static int getFlagSmsDisplay() {
        return flagSmsDisplay;
    }

    public static void setFlagSmsDisplay(int flagSmsDisplay) {
        SmsDisplay.flagSmsDisplay = flagSmsDisplay;
    }

    public static String getIdNumber() {
        return ID_NUMBER;
    }

    public static void setIdNumber(String idNumber) {
        ID_NUMBER = idNumber;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}



