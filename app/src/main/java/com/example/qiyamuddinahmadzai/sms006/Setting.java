package com.example.qiyamuddinahmadzai.sms006;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class Setting extends AppCompatActivity {


    public static String _senderMailAdd;
    public static String _SenderMailPass;
    public static String _ReceiverMailAdd;
    public static String _MailSubject;

    static String _KEYSENDERMAILADD = "k_s_m_a";
    static String _KEYSENDERMAILPASS = "k_s_p";
    static String _KEYRECEVIERMAILADD = "k_r_m_a";
    static String _KEYMAILSUBJECT = "m_s";

    EditText txtSenderEmailAdd, txtSenderEmailPassword, txtReceiverEmailAdd, txtEmailSubject;
    Button btnSave;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editer = pref.edit();

        txtSenderEmailAdd = (EditText) findViewById(R.id.txtSenderEmailAdd);
        txtSenderEmailPassword = (EditText) findViewById(R.id.txtSenderEmailPassword);
        txtReceiverEmailAdd = (EditText) findViewById(R.id.txtReceiverEmailAdd);
        txtEmailSubject = (EditText) findViewById(R.id.txtEmailSubject);
        btnSave = (Button) findViewById(R.id.btnInfoDetails);

        String st1 = pref.getString(_KEYSENDERMAILADD, null);
        String st2 = pref.getString(_KEYSENDERMAILPASS, null);
        String st3 = pref.getString(_KEYRECEVIERMAILADD, null);
        String st4 = pref.getString(_KEYMAILSUBJECT, null);

         if(st1 != null ){ txtSenderEmailAdd.setText(st1); }
         if(st2 != null ){ txtSenderEmailPassword.setText(st2); }
         if(st3 != null ){ txtReceiverEmailAdd.setText(st3); }
         if(st4 != null ){ txtEmailSubject.setText(st4); }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String senderEmail = txtSenderEmailAdd.getText().toString();
                String senderEmailPass = txtSenderEmailPassword.getText().toString();
                String receiverEmail = txtReceiverEmailAdd.getText().toString();
                String EmailSubject = txtEmailSubject.getText().toString();

                editer.putString( _KEYSENDERMAILADD, senderEmail );
                editer.putString( _KEYSENDERMAILPASS, senderEmailPass );
                editer.putString( _KEYRECEVIERMAILADD, receiverEmail );
                editer.putString( _KEYMAILSUBJECT, EmailSubject );
                editer.commit();
                //editer.apply();

                Toast.makeText(Setting.this,
                        "Sender Email : "+ senderEmail+"\n"+
                        "Sender Email Pass : "+ senderEmailPass +"\n"+
                        "Receiver Email : "+ receiverEmail+"\n"+
                        "Email Subject : "+ EmailSubject+"\n"+
                        "Info is Stored successfuly",
                        Toast.LENGTH_LONG).show();

            }
        });


    }

    public static String get_senderMailAdd() {
        return _senderMailAdd;
    }

    public static String get_SenderMailPass() {
        return _SenderMailPass;
    }

    public static String get_ReceiverMailAdd() {
        return _ReceiverMailAdd;
    }

    public static String get_MailSubject() {
        return _MailSubject;
    }


    public static void set_senderMailAdd(String _senderMailAdd) {
        Setting._senderMailAdd = _senderMailAdd;
    }

    public static void set_SenderMailPass(String _SenderMailPass) {
        Setting._SenderMailPass = _SenderMailPass;
    }

    public static void set_ReceiverMailAdd(String _ReceiverMailAdd) {
        Setting._ReceiverMailAdd = _ReceiverMailAdd;
    }

    public static void set_MailSubject(String _MailSubject) {
        Setting._MailSubject = _MailSubject;
    }

}
