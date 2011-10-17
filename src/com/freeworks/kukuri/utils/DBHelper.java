package com.freeworks.kukuri.utils;

import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    
    private final static String DB_FILE_NAME = "KUKURI_DATABASE";
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("AppQueen", "CREATE TABLEs");
        db.execSQL(
                "create table weight_table(" + 
                "   now_date text not null," + 
                "   weight text not null" + ");"
        );
    }

    public void insertWeight(String date,double weight) {
        SQLiteDatabase db = getWritableDatabase();

        String sql = String.format(
                "select now_date from apptable  where now_date = '%s'",
                date);
        Log.v("AppQueen", "SQL:" + sql);
        Cursor c = db.rawQuery(sql, null);        
        if (!c.moveToFirst()) {
            String insertSql = String.format(
                    "insert into weight_table(now_date,weight) values ('%s', %f);",
                    date, weight);
            Log.v("AppQueen", "SQL:" + insertSql);
            db.execSQL(insertSql);
        } 
        else {
            String updateSql = String.format("UPDATE weight_table SET weight = %f WHERE now_date = '%s'", weight,date);
            Log.v("AppQueen", "SQL:" + updateSql);
            db.execSQL(updateSql);
        }
        c.close();
        db.close();

    }

    public int countWeightData(String start_date,String end_date) {
        SQLiteDatabase db = getReadableDatabase();

        

        String sql = String.format(
                "select weight from weight_table where now_date >= '%s' and now_date <= '%s'",start_date,end_date);
        Log.v("AppQueen", "SQL:" + sql);
        Cursor c = db.rawQuery(sql, null);
        int num = c.getCount();
        c.close();
        db.close();
        return num;
    }
    
    public HashMap<String,Double> getDateData(String getdate){
    	SQLiteDatabase db = getReadableDatabase();
    	String sql = String.format(
                "select * from weight_table where now_date = '%s'",getdate);
        Log.v("AppQueen", "SQL:" + sql);
        Cursor c = db.rawQuery(sql, null);
        
        boolean isEof = c.moveToFirst();
        if (!isEof) {
            c.close();
            db.close();
            return null;
        }
        
        HashMap<String,Double> weighthash = new HashMap<String,Double>();
        
        String now_date = c.getString(0);
        Double weight   = c.getDouble(1);
        weighthash.put(now_date, weight);
                    
        c.close();
        db.close();
        return weighthash;        
    }
    
    public HashMap<String,Double> getWeightLastData(){
    	SQLiteDatabase db = getReadableDatabase();
    	String sql = String.format(
                "select * from weight_table order by now_date desc");
        Log.v("AppQueen", "SQL:" + sql);
        Cursor c = db.rawQuery(sql, null);
        
        boolean isEof = c.moveToFirst();
        if (!isEof) {
            c.close();
            db.close();
            return null;
        }
        
        HashMap<String,Double> weighthash = new HashMap<String,Double>();
        
        String now_date = c.getString(0);
        Double weight   = c.getDouble(1);
        weighthash.put(now_date, weight);
                    
        c.close();
        db.close();
        return weighthash;        
    }
    

    public HashMap<String,Double> getWeightList(String start_date,String end_date) {
    	SQLiteDatabase db = getReadableDatabase();

        
        String sql = String.format(
                "select * from weight_table where now_date >= '%s' and now_date <= '%s' order by now_date", start_date,end_date);
        Log.v("AppQueen", "SQL:" + sql);
        Cursor c = db.rawQuery(sql, null);

        boolean isEof = c.moveToFirst();
        if (!isEof) {
            c.close();
            db.close();
            return null;
        }
        HashMap<String,Double> weighthash = new HashMap<String,Double>();
        while (isEof) {
            String now_date = c.getString(0);
            Double weight   = c.getDouble(1);
            weighthash.put(now_date, weight);
            isEof = c.moveToNext();
        }
        c.close();
        db.close();
        return weighthash;
    }

    public void deleteData(String now_date) {//‘½•ªŽg‚í‚È‚¢

        SQLiteDatabase db = getWritableDatabase();

        String sql = String.format(
                "delete from weight_table where now_date = '%s'", now_date);
        Log.v("AppQueen", "SQL:" + sql);
        db.execSQL(sql);
        db.close();

    }
    
}
