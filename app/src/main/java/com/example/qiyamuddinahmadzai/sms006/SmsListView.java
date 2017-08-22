package com.example.qiyamuddinahmadzai.sms006;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qiyamuddinahmadzai.sms006.db.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SmsListView extends AppCompatActivity {

    ListView list;
    private DBHelper dbSms ;
    View layBar;
    Button btnSyncEmail;
    TextView tvtitle;
    String[] cIdArr, cNumArr, cBodyArr, cDateArr;
    int length = 0;

    static int flagSyncAllEmail = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_list_view);



        tvtitle = (TextView) findViewById(R.id.txt_u_s_Title);
        btnSyncEmail = (Button) findViewById(R.id.btnSync);
        layBar = findViewById(R.id.layBar);

        btnSyncEmail.setVisibility(View.INVISIBLE);

        dbSms = new DBHelper(this);

        Log.i("00001","Number of Data : "+ dbSms.numberOfRows());
        btnSyncEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SmsListView.this);
                builder.setTitle("Confirm");
                builder.setMessage("Do you want to send all these SMS messages!");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    setFlagSyncAllEmail(421);
                    Intent innnt = new Intent(SmsListView.this, MainActivity.class);
                    innnt.putExtra(MainActivity.EMAIL_MESSAGE, "ToPassIfStatement");
                    startActivity(innnt);

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


        //boolean a = dbSms.insertSms("0720008314", "this body 3", "0");
        //Toast.makeText(SmsListView.this, "Resp : "+ a, Toast.LENGTH_SHORT).show();
      // Toast.makeText(SmsListView.this, "Count : "+ dbSms.numberOfRows(), Toast.LENGTH_SHORT).show();

      /*   Cursor cr =  dbSms.getSms(1);
        cr.moveToNext();
        Toast.makeText(SmsListView.this, "Data : \nNumber : "+
                cr.getString(cr.getColumnIndex(DBHelper.SMS_COLUMN_NUMBER))+"\nBody : "+
                        cr.getString(cr.getColumnIndex(DBHelper.SMS_COLUMN_BODY)),
                Toast.LENGTH_SHORT).show();
        cr.close();*/

       /* String[]  idArr = new String[dbSms.numberOfRows()];
        String[]  numArr = new String[dbSms.numberOfRows()];
        String[] bodyArr = new String[dbSms.numberOfRows()];
        String[] dateArr = new String[dbSms.numberOfRows()];*/


        String tit = getIntent().getExtras().getString("key_un_sent");
       // Toast.makeText(SmsListView.this, "tit : "+ tit, Toast.LENGTH_SHORT).show();
        reCallSMSLit(tit);

/*        Custom_List_One adapter = new Custom_List_One(SmsListView.this, numArr, bodyArr, dateArr);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);*/


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] idd = getcIdArr();
                String[] num = getcNumArr();
                String[] sms = getcBodyArr();
                String[] date = getcDateArr();

               // Toast.makeText(SmsListView.this, "You Clicked at " + (position+1) + " Long : "+ id , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SmsListView.this, SmsDisplay.class);
                intent.putExtra("sidd", idd[position].toString());
                intent.putExtra("num", num[position].toString());
                intent.putExtra("body", sms[position].toString());
                intent.putExtra("date", date[position].toString());
                startActivity(intent);

            }
        });
;
    }

    private void reCallSMSLit(String tit) {
        String[]  idArr = new String[dbSms.numberOfRows()];
        String[]  numArr = new String[dbSms.numberOfRows()];
        String[] bodyArr = new String[dbSms.numberOfRows()];
        String[] dateArr = new String[dbSms.numberOfRows()];

        if(tit.equals("0_u")){
              //   dbSms.deleteSms("1566");
              //   dbSms.deleteSms("1569");
             //    dbSms.deleteSms("1571");
           // dbSms.deleteSms("1566");
          //  dbSms.deleteSms("1569");
           // dbSms.deleteSms("1571");
            tvtitle.setText("Unsent");
            btnSyncEmail.setVisibility(View.VISIBLE);
            layBar.setBackground(getResources().getDrawable(R.drawable.lablestwo));
            ArrayList<String> array_sms_list = new ArrayList<String>();
            array_sms_list = dbSms.getUnSentSms();
            Toast.makeText(SmsListView.this,"Number of Re : "+array_sms_list.size(),  Toast.LENGTH_LONG).show();
            idArr = new String[array_sms_list.size()];
            numArr = new String[array_sms_list.size()];
            bodyArr = new String[array_sms_list.size()];
            dateArr = new String[array_sms_list.size()];

            setcIdArr(idArr);
            setcNumArr(numArr);
            setcBodyArr(bodyArr);
            setcDateArr(dateArr);

            if(array_sms_list.size() > 0 ){
                for (int i=0; i < array_sms_list.size(); i++){
                    String st = array_sms_list.get(i);
                    String[] content = st.split(">");

                    idArr[i] = content[0];
                    numArr[i] = content[1];
                    bodyArr[i] = content[2];
                    String stt = content[4].trim();

                    if(content[4].contains(":")) {
                        dateArr[i] = content[4];
                        Log.i("tag007", "condiation if");
                    }else if(stt.length() == 4) { // stt.length() == null which mean length is 4
                        Log.i("tag007", "condiation if else");
                        dateArr[i] = stt;
                    }else {
                        Log.i("tag007", "condiation else");
                        dateArr[i] = getFormattedDateFromTimestamp(Long.valueOf(stt));
                    }
                    ///dateArr[i] = getFormattedDateFromTimestamp(Long.valueOf(stt));

                   /* String stt = dateArr[i].trim();//"1488788908000";//content[4];//"1488785845123454";
                    Long ln = Long.valueOf(stt);
                    Log.i("tag007", "Long Value : "+ ln );*/
                    //Log.i("tag007", "Date and Time : "+getFormattedDateFromTimestamp( ln ));

                }

            }

        }
        else if (tit.equals("1_s")){

            tvtitle.setText("Sent");
            ArrayList<String> array_sms_list = new ArrayList<String>();

            array_sms_list = dbSms.getSentSms();
           // Log.i("tag007", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX After DB : "+ array_sms_list.size());
            Toast.makeText(SmsListView.this,"Number of Re-S : "+array_sms_list.size(),  Toast.LENGTH_LONG).show();
            idArr = new String[array_sms_list.size()];
            numArr = new String[array_sms_list.size()];
            bodyArr = new String[array_sms_list.size()];
            dateArr = new String[array_sms_list.size()];
          //  Log.i("tag007", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX before setcDateArr ");
            setcIdArr(idArr);
            setcNumArr(numArr);
            setcBodyArr(bodyArr);
            setcDateArr(dateArr);
           // Log.i("tag007", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX setcDateArr ");
            if(array_sms_list.size() > 0 ){
                for (int i=0; i < array_sms_list.size(); i++){

                    String st = array_sms_list.get(i);
                    String[] content = st.split(">");
                    idArr[i] = content[0];
                    numArr[i] = content[1];
                    bodyArr[i] = content[2];
                    String stt = content[4].trim();
                    Log.i("tag007", "ID : "+ content[0] + " Date :"+ content[4]);
                    if(content[4].contains(":")) {
                        dateArr[i] = content[4];
                        Log.i("tag007", "sent condition  if part");
                    }else if(stt.length() == 4) { // stt.length() == null which mean length is 4
                        Log.i("tag007", "sent condition  if else");
                        dateArr[i] = stt;
                    }else {
                      //  Log.i("tag007", "sent condition  else");
                        dateArr[i] = getFormattedDateFromTimestamp(Long.valueOf(stt));
                    }
                    //dateArr[i] = getFormattedDateFromTimestamp(Long.valueOf(stt));
      //              dateArr[i] = ((content[4].contains(":")? content[4] : getFormattedDateFromTimestamp(Long.valueOf(stt))));

                }

            }

        }
        else if (tit.equals("1_us")){
            tvtitle.setText("Inbox");
            ArrayList<String> array_sms_list = new ArrayList<String>();
            array_sms_list = dbSms.getAllSms();

            idArr = new String[array_sms_list.size()];
            numArr = new String[array_sms_list.size()];
            bodyArr = new String[array_sms_list.size()];
            dateArr = new String[array_sms_list.size()];

            setcIdArr(idArr);
            setcNumArr(numArr);
            setcBodyArr(bodyArr);
            setcDateArr(dateArr);

            if(array_sms_list.size() > 0 ){
                for (int i=0; i < array_sms_list.size(); i++){
                    String st = array_sms_list.get(i);
                    String[] content = st.split(">");

                    idArr[i] = content[0];
                    numArr[i] = content[1];
                    bodyArr[i] = content[2];
                    String stt = content[4].trim();

                    if(content[4].contains(":")) {
                        dateArr[i] = content[4];
                        Log.i("tag007", "condiation if");
                    }else if(stt.length() == 4) { // stt.length() == null which mean length is 4
                        Log.i("tag007", "condiation if else");
                        dateArr[i] = stt;
                    }else {
                        Log.i("tag007", "condiation else");
                        dateArr[i] = getFormattedDateFromTimestamp(Long.valueOf(stt));
                    }
                //    dateArr[i] = ((content[4].contains(":")? content[4] : getFormattedDateFromTimestamp(Long.valueOf(stt))));

                }

            }

        }

        Custom_List_One adapter = new Custom_List_One(SmsListView.this, numArr, bodyArr, dateArr);
        list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

    }


    @Override
    protected void onResume() {
        super.onResume();
     //   continue onCreateMethod;
        String tit = getIntent().getExtras().getString("key_un_sent");
        reCallSMSLit(tit);
    }

 /*   public static String getFormattedDateFromTimestamp(long timestampInMilliSeconds)
    {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        String formattedDate=new SimpleDateFormat( "dd/MM/yyyy hh:mm:ss.SSS").format(date);
        return formattedDate;

    }   */

    public static String getFormattedDateFromTimestamp(long timestampInMilliSeconds)
    {
        Date date = new Date();
        date.setTime(timestampInMilliSeconds);
        String formattedDate=new SimpleDateFormat("hh:mm a dd/MM/yyyy ").format(date);
        return formattedDate;

    }

    public String[] getcNumArr() {
        return cNumArr;
    }

    public void setcNumArr(String[] cNumArr) {
        this.cNumArr = cNumArr;
    }

    public String[] getcBodyArr() {
        return cBodyArr;
    }

    public void setcBodyArr(String[] cBodyArr) {
        this.cBodyArr = cBodyArr;
    }

    public String[] getcDateArr() {
        return cDateArr;
    }

    public void setcDateArr(String[] cDateArr) {
        this.cDateArr = cDateArr;
    }

    public String[] getcIdArr() {
        return cIdArr;
    }

    public void setcIdArr(String[] cId) {
        this.cIdArr = cId;
    }

    public static int getFlagSyncAllEmail() {
        return flagSyncAllEmail;
    }

    public static void setFlagSyncAllEmail(int flagSyncAllEmail) {
        SmsListView.flagSyncAllEmail = flagSyncAllEmail;
    }


}
