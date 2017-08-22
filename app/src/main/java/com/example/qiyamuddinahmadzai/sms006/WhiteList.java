package com.example.qiyamuddinahmadzai.sms006;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.qiyamuddinahmadzai.sms006.db.DBHelper;

import java.util.ArrayList;

public class WhiteList extends AppCompatActivity {

    ListView listView;
    Button btnSave;
    EditText txtNumber, txtDescription;
    private  static String _NUMBER;
    private  static String  _DESC;


    private static String[] _id_list;
    private static String[] _number_list;
    private static String[] _desc_list;


    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);

        listView = (ListView) findViewById(R.id.listNumber);
        txtNumber = (EditText) findViewById(R.id.txtListNumnber);
        txtDescription = (EditText) findViewById(R.id.txtListDescription);
        btnSave = (Button) findViewById(R.id.btnListSave);

        dbHelper = new DBHelper(this);


       // ArrayList<String> array_sms_list = new ArrayList<String>();
       // array_sms_list = dbHelper.getNumbers();
       // Toast.makeText(WhiteList.this, "Number Of Rows : "+ array_sms_list.size(), Toast.LENGTH_SHORT).show();
        retriveData();

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 String num = txtNumber.getText().toString();
                 String desc =  txtDescription.getText().toString();

                 if(num.trim().equals("") && desc.trim().equals("")){
                     Toast.makeText(WhiteList.this, "Enter Number And Description", Toast.LENGTH_SHORT).show();
                 }else{

                     boolean b = dbHelper.insertNumber(num, desc);
                     if(b) {
                         txtNumber.setText("");
                         txtDescription.setText("");
                         retriveData();
                         Toast.makeText(WhiteList.this, "Number Added Successfuly!", Toast.LENGTH_SHORT).show();
                     }


                 }



                }
            });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] idd = get_id_list();
                String[] num = get_number_list();
                String[] desc = get_desc_list();

               // idd.

               // Toast.makeText(WhiteList.this, "You Clicked at " + (position+1) + " Long : "+ id , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WhiteList.this, NumberDisplay.class);
                intent.putExtra("num_d", num[position].toString());
                intent.putExtra("desc_d", desc[position].toString());
                intent.putExtra("idd",  idd[position]);
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveData();
    }

    public void retriveData(){
        String[]  idArr = new String[dbHelper.numberOfRows()];
        String[]  numArr = new String[dbHelper.numberOfRows()];
        String[] descArr = new String[dbHelper.numberOfRows()];

        // boolean b = dbHelper.insertNumber("0746355555", "this is testing number..");
        // Toast.makeText(WhiteList.this, "Number added --> "+ b, Toast.LENGTH_SHORT).show();

        ArrayList<String> array_sms_list = new ArrayList<String>();
        array_sms_list = dbHelper.getNumbers();
        //Toast.makeText(WhiteList.this, "Number : "+ array_sms_list.size(), Toast.LENGTH_SHORT).show();
        idArr = new String[array_sms_list.size()];
        numArr = new String[array_sms_list.size()];
        descArr = new String[array_sms_list.size()];

        set_id_list(idArr);
        set_number_list(numArr);
        set_desc_list(descArr);

        if(array_sms_list.size() > 0 ){
            for (int i=0; i < array_sms_list.size(); i++){
                String st = array_sms_list.get(i);
                String[] content = st.split(">");

                _id_list[i] = content[0];
                _number_list[i] = content[1];
                _desc_list[i] = content[2];
              //  Toast.makeText(WhiteList.this, "ID  : "+st+"\nNumber  : "+content[1]+"\nDescription  : "+content[2], Toast.LENGTH_SHORT).show();
            }
        }

        Custom_Number_List_One adapter = new Custom_Number_List_One(WhiteList.this, get_number_list(), get_desc_list());
        listView.setAdapter(adapter);
    }

    /*public void diaLogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }*/




    public static String[] get_number_list() {
        return _number_list;
    }

    public static void set_number_list(String[] _number_list) {
        WhiteList._number_list = _number_list;
    }

    public static String[] get_desc_list() {
        return _desc_list;
    }

    public static void set_desc_list(String[] _desc_list) {
        WhiteList._desc_list = _desc_list;
    }


    public static String[] get_id_list() {
        return _id_list;
    }

    public static void set_id_list(String[] _id_list) {
        WhiteList._id_list = _id_list;
    }
}

