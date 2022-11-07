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

public class DBOpenHelper {

    private static final String DATABASE_NAME = "expenses.db";
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
            String query = "create table if not exists expenses(" +
                    "posttime text not null PRIMARY KEY," +
                    "bankname text not null ," +
                    "accountnumber text , " +
                    "title text not null ," +
                    "type text not null ," +
                    "money integer not null , " +
                    "detail text );";
            //db.execSQL("DROP TABLE IF EXISTS expenses");
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS expenses");
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

    /*                         */
    public long insertColumn(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        ContentValues values = new ContentValues();
        values.put("posttime", posttime);
        values.put("bankname", bankname);
        values.put("accountnumber", accountnumber);
        values.put("title", title);
        values.put("type", type);
        values.put("money", money);
        values.put("detail", detail);
        return db.insert("expenses", null, values);
    }

    public Cursor selectColumns(){
        return db.rawQuery("SELECT * FROM expenses", null);
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

    void insertData(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        if(dbOpenHelper.insertColumn(posttime, bankname, accountnumber, title, type, money, detail) != -1) {
            Log.e("DBinsert", "데이터 추가 성공");
        }else{
            Log.e("DBinsert", "posttime이 중복된 데이터");
        }
    }

    String selectAll(){
        String line = "";
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumns();
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
            String money = cursor.getString(moneyInt);
            String detail = cursor.getString(detailInt);

            String result = postTime+"||"+bankName+"||"+accountNumber+"||"+title+"||"+type+"||"+money+"||"+detail;
            line += count+"번 : "+result+"\n";
            Log.e("DB : ", result);
            count++;
        }
        return line;
    }
}



