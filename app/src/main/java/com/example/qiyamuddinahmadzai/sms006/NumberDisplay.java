package com.example.qiyamuddinahmadzai.sms006;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiyamuddinahmadzai.sms006.db.DBHelper;

public class NumberDisplay extends AppCompatActivity {

    private DBHelper dbSms ;

    TextView txtNumber, txtDEsc;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_display);

        dbSms = new DBHelper(this);

        final String num = getIntent().getExtras().getString("num_d");
        final String desc = getIntent().getExtras().getString("desc_d");
        final String idd = getIntent().getExtras().getString("idd").toString();

       // Toast.makeText(NumberDisplay.this, "idd "+ idd, Toast.LENGTH_SHORT).show();

        txtNumber = (TextView) findViewById(R.id.nd_number);
        txtDEsc = (TextView) findViewById(R.id.nd_Description);
        btnDelete = (Button) findViewById(R.id.btnNdDelete);

        txtNumber.setText(num.toString());
        txtDEsc.setText(desc.toString());



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(NumberDisplay.this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want to DELETE this number!");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dbSms.deleteNumber(idd); //dbSms.deleteNumber(num);
                        Toast.makeText(NumberDisplay.this, "Delete Successfuly! ", Toast.LENGTH_SHORT).show();
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
}
