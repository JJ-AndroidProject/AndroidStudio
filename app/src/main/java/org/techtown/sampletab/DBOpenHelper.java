package org.techtown.sampletab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBOpenHelper {

    private static final String DATABASE_NAME = "accountBook.db";
    private static final int DATABASE_VERSION = 1;

    public static SQLiteDatabase db;
    private DatabaseHelper databaseHelper;
    private Context context;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            // 지출 테이블
            String outputTable = "create table if not exists output(" +
                    "posttime text not null PRIMARY KEY," +
                    "bankname text not null ," +
                    "move text default null ," +
                    "accountnumber text , " +
                    "title text not null ," +
                    "type text not null ," +
                    "money integer not null , " +
                    "detail text);";
            db.execSQL(outputTable);
            // 수입 테이블
            //db.execSQL("DROP TABLE IF EXISTS input");
            String inputTable = "create table if not exists input(" +
                    "posttime text not null PRIMARY KEY," +
                    "bankname text not null ," +
                    "move text default null ," +
                    "accountnumber text , " +
                    "title text not null ," +
                    "type text not null ," +
                    "money integer not null , " +
                    "detail text);";
            db.execSQL(inputTable);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS output");
            onCreate(db);
        }
    }

    public DBOpenHelper(Context context){
        this.context = context;
    }

    public DBOpenHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = databaseHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        databaseHelper.onCreate(db);
    }

    public void close(){
        db.close();
    }

    public long insertColumnOutput(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        ContentValues values = new ContentValues();
        values.put("posttime", posttime);
        values.put("bankname", bankname);
        values.put("accountnumber", accountnumber);
        values.put("title", title);
        values.put("type", type);
        values.put("money", money);
        values.put("detail", detail);
        return db.insert("output", null, values);
    }

    public long insertColumnInput(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        ContentValues values = new ContentValues();
        values.put("posttime", posttime);
        values.put("bankname", bankname);
        values.put("accountnumber", accountnumber);
        values.put("title", title);
        values.put("type", type);
        values.put("money", money);
        values.put("detail", detail);
        return db.insert("input", null, values);
    }

    public Cursor selectColumnsOutput(){
        return db.rawQuery("SELECT * FROM output", null);
    }
    public Cursor selectColumnsInput(){
        return db.rawQuery("SELECT * FROM input", null);
    }
}

class DBcommand{
    Context context;
    public DBcommand(Context context){
        this.context = context;
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
    }

    void insertDataOutput(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        if(dbOpenHelper.insertColumnOutput(posttime, bankname, accountnumber, title, type, money, detail) != -1) {
            Log.e("DBinsert", "데이터 추가 성공");
        }else{
            Log.e("DBinsert", "posttime이 중복된 데이터");
        }
    }

    void insertDataInput(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        if(dbOpenHelper.insertColumnInput(posttime, bankname, accountnumber, title, type, money, detail) != -1) {
            Log.e("DBinsert", "데이터 추가 성공");
        }else{
            Log.e("DBinsert", "posttime이 중복된 데이터");
        }
    }

    void selectCount(){ // 계좌 간 이동 확인
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursorOutput = dbOpenHelper.selectColumnsOutput();
        Cursor cursorInput = dbOpenHelper.selectColumnsInput();

        cursorOutput.moveToLast();

        int postTimeIntOutput = cursorOutput.getColumnIndex("posttime");
        int moneyIntOutput = cursorOutput.getColumnIndex("money");
        String postTimeOutput = cursorOutput.getString(postTimeIntOutput);
        int moneyOutput = cursorOutput.getInt(moneyIntOutput);

        Log.e("SelectCountOutput", postTimeOutput+" "+moneyOutput+"원");

        cursorInput.moveToLast();

        int postTimeIntInput = cursorInput.getColumnIndex("posttime");
        int moneyIntInput = cursorInput.getColumnIndex("money");
        String postTimeInput = cursorInput.getString(postTimeIntInput);
        int moneyInput = cursorInput.getInt(moneyIntInput);

        Log.e("SelectCountInput", postTimeInput+" "+moneyInput+"원");
        try{
            Date format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postTimeOutput);
            Date format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postTimeInput);
            long out = (format2.getTime() - format1.getTime())/1000;
            Log.e("SelectCount", "비교 : "+Math.abs(out));
        }catch(ParseException e){

        }


    }

    ArrayList<String> OutputLast(){
        ArrayList<String> list = new ArrayList<String>();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsOutput();
        if(cursor.moveToLast()){
            int postTimeInt = cursor.getColumnIndex("posttime");
            int titleInt = cursor.getColumnIndex("title");
            int typeInt = cursor.getColumnIndex("type");
            int moneyInt = cursor.getColumnIndex("money");

            String postTime = cursor.getString(postTimeInt);
            String title = cursor.getString(titleInt);
            String type = cursor.getString(typeInt);
            String money = Integer.toString(cursor.getInt(moneyInt));
            list.add(postTime);
            list.add(title);
            list.add(type);
            list.add(money);

            return list;
        }else{
            return null;
        }
    }

    ArrayList<String> InputLast(){
        ArrayList<String> list = new ArrayList<String>();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsInput();
        if(cursor.moveToLast()){
            int postTimeInt = cursor.getColumnIndex("posttime");
            int titleInt = cursor.getColumnIndex("title");
            int typeInt = cursor.getColumnIndex("type");
            int moneyInt = cursor.getColumnIndex("money");

            String postTime = cursor.getString(postTimeInt);
            String title = cursor.getString(titleInt);
            String type = cursor.getString(typeInt);
            String money = Integer.toString(cursor.getInt(moneyInt));
            list.add(postTime);
            list.add(title);
            list.add(type);
            list.add(money);

            return list;
        }else{
            return null;
        }
    }

    String selectAllOutput(){
        String line = "";
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsOutput();
        Log.e("DB", cursor.getCount()+"개");
        int count = 1;
        while(cursor.moveToNext()){
            int postTimeInt = cursor.getColumnIndex("posttime");
            int bankNameInt = cursor.getColumnIndex("bankname");
            int accountNumberInt = cursor.getColumnIndex("accountnumber");
            int titleInt = cursor.getColumnIndex("title");
            int typeInt = cursor.getColumnIndex("type");
            int moneyInt = cursor.getColumnIndex("money");
            int detailInt = cursor.getColumnIndex("detail");

            String postTime = cursor.getString(postTimeInt);
            String bankName = cursor.getString(bankNameInt);
            String accountNumber = cursor.getString(accountNumberInt);
            String title = cursor.getString(titleInt);
            String type = cursor.getString(typeInt);
            int money = cursor.getInt(moneyInt);
            String detail = cursor.getString(detailInt);

            String result = postTime+"||"+bankName+"||"+accountNumber+"||"+title+"||"+type+"||"+money+"||"+detail;
            line += count+"번 : "+result+"\n";
            Log.e("DB : ", result);
            count++;
        }
        return line;
    }
    String selectAllInput(){
        String line = "";
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsInput();
        Log.e("DB", cursor.getCount()+"개");
        int count = 1;
        while(cursor.moveToNext()){
            int postTimeInt = cursor.getColumnIndex("posttime");
            int bankNameInt = cursor.getColumnIndex("bankname");
            int accountNumberInt = cursor.getColumnIndex("accountnumber");
            int titleInt = cursor.getColumnIndex("title");
            int typeInt = cursor.getColumnIndex("type");
            int moneyInt = cursor.getColumnIndex("money");
            int detailInt = cursor.getColumnIndex("detail");

            String postTime = cursor.getString(postTimeInt);
            String bankName = cursor.getString(bankNameInt);
            String accountNumber = cursor.getString(accountNumberInt);
            String title = cursor.getString(titleInt);
            String type = cursor.getString(typeInt);
            int money = cursor.getInt(moneyInt);
            String detail = cursor.getString(detailInt);

            String result = postTime+"||"+bankName+"||"+accountNumber+"||"+title+"||"+type+"||"+money+"||"+detail;
            line += count+"번 : "+result+"\n";
            Log.e("DB : ", result);
            count++;
        }
        return line;
    }
}



