package com.example.qiyamuddinahmadzai.sms006.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Qiyamuddin Ahmadzai on 2/27/2017.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 7;


    public static final String DATABASE_NAME = "Db_Unread.db";
    public static final String SMS_TABLE_NAME = "tb_sms";
    public static final String SMS_COLUMN_ID = "id";
    public static final String SMS_COLUMN_NUMBER = "sms_number";
    public static final String SMS_COLUMN_BODY = "sms_body";
    public static final String SMS_COLUMN_STATUS = "sms_status";
    public static final String SMS_COLUMN_DATETIME = "sms_datetime";


    public static final String _COLUMN_NUMBER = "number";
    public static final String _COLUMN_DESCRIPTION = "description";
    public static final String _WHITE_LIST_TABLE_NAME = "white_list";

    private HashMap hp;



    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table tb_sms " +
                "(id integer primary key, sms_number text,sms_body text,sms_status text,sms_datetime text)"
        );

        db.execSQL(
                "create table " + _WHITE_LIST_TABLE_NAME +
                "(id integer primary key, number text, description text)"
        ); //primary key
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tb_sms");
        db.execSQL("DROP TABLE IF EXISTS white_list");
        onCreate(db);
    }

    public boolean insertSms (String number, String body, String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SMS_COLUMN_NUMBER, number);
        contentValues.put(SMS_COLUMN_BODY, body);
        contentValues.put(SMS_COLUMN_STATUS, status);
        contentValues.put(SMS_COLUMN_DATETIME, date);

        db.insert(SMS_TABLE_NAME, null, contentValues);
        return true;
    }

    public Integer deleteSms (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(SMS_TABLE_NAME,
                "id = ? ",
                new String[] { id });
    }


    public ArrayList<String> getAllSms() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+SMS_COLUMN_ID+", "+SMS_COLUMN_NUMBER+", "+SMS_COLUMN_BODY+", "+SMS_COLUMN_STATUS+", "+SMS_COLUMN_DATETIME
                +" from "+ SMS_TABLE_NAME +" ORDER BY "+SMS_COLUMN_DATETIME+" desc"  , null );
    //    Cursor res =  db.rawQuery( "select * from "+ SMS_TABLE_NAME , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(
                            res.getString(res.getColumnIndex(SMS_COLUMN_ID))+">"+
                            res.getString(res.getColumnIndex(SMS_COLUMN_NUMBER))+">"+
                            res.getString(res.getColumnIndex(SMS_COLUMN_BODY))+">"+
                            res.getString(res.getColumnIndex(SMS_COLUMN_STATUS))+">"+
                            res.getString(res.getColumnIndex(SMS_COLUMN_DATETIME))
            );
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getSentSms() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+SMS_COLUMN_ID+", "+SMS_COLUMN_NUMBER+", "+SMS_COLUMN_BODY+", "+SMS_COLUMN_STATUS+", "+SMS_COLUMN_DATETIME
                +" from "+ SMS_TABLE_NAME +" where sms_status = 1 ORDER BY "+SMS_COLUMN_DATETIME+" desc"  , null );
       // Cursor res =  db.rawQuery( "select * from "+ SMS_TABLE_NAME +" where sms_status = 1 ORDER BY "+SMS_COLUMN_DATETIME+" desc" , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(
                           res.getString(res.getColumnIndex(SMS_COLUMN_ID))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_NUMBER))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_BODY))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_STATUS))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_DATETIME))
                        );
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getUnSentSms() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+SMS_COLUMN_ID+", "+SMS_COLUMN_NUMBER+", "+SMS_COLUMN_BODY+", "+SMS_COLUMN_STATUS+", "+SMS_COLUMN_DATETIME
                                  +" from "+ SMS_TABLE_NAME +" where sms_status = 0 ORDER BY "+SMS_COLUMN_DATETIME+" desc"  , null ); // AND "+SMS_COLUMN_DATETIME+">='1489951800000'
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(
                           res.getString(res.getColumnIndex(SMS_COLUMN_ID))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_NUMBER))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_BODY))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_STATUS))+">"+
                           res.getString(res.getColumnIndex(SMS_COLUMN_DATETIME))
                        );
            res.moveToNext();
        }
        return array_list;
    }

    public boolean resetAllSMS() {
      //  ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+SMS_COLUMN_ID+", "+SMS_COLUMN_NUMBER+", "+SMS_COLUMN_BODY+", "+SMS_COLUMN_STATUS+", "+SMS_COLUMN_DATETIME
                                  +" from "+ SMS_TABLE_NAME +" where sms_status = 1" , null ); // AND "+SMS_COLUMN_DATETIME+">='1489951800000'
        res.moveToFirst();
        int a = 0;
        while(res.moveToNext()){
           // array_list.add(

            updateSms(  res.getString(res.getColumnIndex(SMS_COLUMN_ID)),  res.getString(res.getColumnIndex(SMS_COLUMN_NUMBER)),
                        res.getString(res.getColumnIndex(SMS_COLUMN_BODY)), "0",  "" );

            Log.i("007", (a+1)+"-ID : " + res.getString(res.getColumnIndex(SMS_COLUMN_ID)));
             //           );
         //   res.moveToNext();
        }

        return true;
    }

    public Cursor getSms(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tb_sms where id="+ id +"", null );
        return res;
    }


    public int getSmsStatus(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tb_sms where id="+ id +"", null );
        res.moveToNext();
        String status = res.getString(res.getColumnIndex(SMS_COLUMN_STATUS));
        return Integer.parseInt(status);
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SMS_TABLE_NAME);
        return numRows;
    }

    public int deleteNumber (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(_WHITE_LIST_TABLE_NAME,
                "id = ? ",
                new String[] { id });
    }

    public Integer deleteNumberById (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(_WHITE_LIST_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public boolean updateSms (String id, String number, String body, String status, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(SMS_COLUMN_NUMBER, number);
        contentValues.put(SMS_COLUMN_BODY, body);
        contentValues.put(SMS_COLUMN_STATUS, status);
        contentValues.put(SMS_COLUMN_DATETIME, date);
        //String updateQuery = "Update tb_sms set " + SMS_COLUMN_NUMBER + " = '"+ number +"' where " + COLUMN_USERNAME + "="+"'"+ r.getUname() +"'";
       // db.execSQL(updateQuery);
       // db.close();

/*

        String strSQL = "UPDATE "+SMS_TABLE_NAME+" SET "+SMS_COLUMN_NUMBER+" = "+number+
                        ", "+SMS_COLUMN_BODY+" = "+body+", "+SMS_COLUMN_STATUS+" = "+status+
                        ", "+SMS_COLUMN_DATETIME+" = "+date+
                        " WHERE id = "+ 11;
*/


      /* String strSQL = "UPDATE "+SMS_TABLE_NAME+" SET "+SMS_COLUMN_NUMBER+" = '"+number+"', "
                        +SMS_COLUMN_BODY+" = '"+body+"', "+SMS_COLUMN_STATUS+" = '"+status+"', "
                        +SMS_COLUMN_DATETIME+" = '"+date+"' WHERE "+SMS_COLUMN_NUMBER+" = '"+ number+"' AND "
                        +SMS_COLUMN_BODY+" = '"+body+"' AND "+SMS_COLUMN_DATETIME +" = '"+ date+"'";
*/
        //db. rawQuery(strSQL, null);
      /*  String updateQuery = "Update tb_sms set " + SMS_COLUMN_STATUS + " = '"+ status +"' where " + SMS_COLUMN_DATETIME + "="+"'"+ date +"'";
        db.execSQL(updateQuery);
        db.close();*/
    //    db.execSQL(strSQL);

      //  db.update(SMS_TABLE_NAME, contentValues,"sms_datetime = ?",new String[]{date.toString()});
       db.update(SMS_TABLE_NAME, contentValues, "id = ? ", new String[] { id } );
        return true;
    }






    public boolean insertNumber (String number, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(_COLUMN_NUMBER, number);
        contentValues.put(_COLUMN_DESCRIPTION, desc);

        db.insert(_WHITE_LIST_TABLE_NAME, null, contentValues);
        return true;
    }

    public ArrayList<String> getNumbers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select id, number, description  from "+_WHITE_LIST_TABLE_NAME  , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(
                            res.getString(res.getColumnIndex("id"))+" > "+
                            res.getString(res.getColumnIndex(_COLUMN_NUMBER))+" > "+
                            res.getString(res.getColumnIndex(_COLUMN_DESCRIPTION))

            );
            res.moveToNext();

        }
      //  Toast.makeText(DBHelper.this, "", Toast.LENGTH_SHORT).show();
        return array_list;
    }


    public ArrayList<String> getOnlyNumbers() {
        ArrayList<String> array_list = new ArrayList<String>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+_COLUMN_NUMBER+" from "+_WHITE_LIST_TABLE_NAME  , null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(
                            res.getString(res.getColumnIndex(_COLUMN_NUMBER))
            );
            res.moveToNext();
        }
        return array_list;
    }


    public int WhiteListNumberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, _WHITE_LIST_TABLE_NAME);
        return numRows;
    }




}
