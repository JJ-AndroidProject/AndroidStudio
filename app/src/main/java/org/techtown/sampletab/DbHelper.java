package org.techtown.sampletab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbHelper(Context context){
        this.mCtx = context;
    }

    public DbHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }
    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }
    /*                                      */

    public long insertColumn(String userid, int input , int output ){ //데이터 삽입(Insert)
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.USERID, userid);
        values.put(DataBases.CreateDB.INPUT, input);
        values.put(DataBases.CreateDB.OUTPUT, output);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    public Cursor selectColumns(){ //데이터 선택(SELECT)
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    public boolean updateColumn(long id, String userid, int input , int output){ //데이터 갱신
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.USERID, userid);
        values.put(DataBases.CreateDB.INPUT, input);
        values.put(DataBases.CreateDB.OUTPUT, output);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    // 전체 삭제
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // 부분 삭제
    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }
}



