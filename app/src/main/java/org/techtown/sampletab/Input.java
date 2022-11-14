package org.techtown.sampletab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;

public class Input {

    public static final class CreateDB implements BaseColumns {
        public static final String DAY = "day"; //날짜
        public static final String TIME = "time"; //시간
        public static final String INPUT = "input"; // 수입
        public static final String BANK = "bank"; //은행
        public static final String MEMO = "memo"; //메모
        public static final String _TABLENAME0 = "inputtable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"(" // 데이터베이스가 없을 경우 생성.
                +_ID+" integer primary key autoincrement, " //중복되지 않는 값 생성하지 않도록 1씩 추가.
                +DAY
                +TIME
                +INPUT
                +BANK
                +MEMO + ");";
    }

    public static class InputHelper {

        private static final String DATABASE_NAME = "input.db";
        private static final int DATABASE_VERSION = 1;
        public static SQLiteDatabase IDB;
        private DatabaseHelper IDBHelper;
        private Context ICtx;


        private class DatabaseHelper extends SQLiteOpenHelper {

            public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CreateDB._CREATE0);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + CreateDB._TABLENAME0);
                onCreate(db);
            }
        }

        public InputHelper(Context context){
            this.ICtx = context;
        }

        public InputHelper open() throws SQLException {
            IDBHelper = new DatabaseHelper(ICtx, DATABASE_NAME, null, DATABASE_VERSION);
            IDB = IDBHelper.getWritableDatabase();
            return this;
        }

        public long ininsertColumn(int day, int time, int input , String bank, String memo) { // 수입 데이터 삽입(Insert)
            SQLiteDatabase IDB = IDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CreateDB.DAY, day);
            values.put(CreateDB.TIME, time);
            values.put(CreateDB.INPUT, input);
            values.put(CreateDB.BANK, bank);
            values.put(CreateDB.MEMO, memo);
            Log.e("삽입","완료");
            return IDB.insert(CreateDB._TABLENAME0, null, values);
        }

        public Cursor inselectColumns(){ //데이터 선택(SELECT)
            return IDB.query(CreateDB._TABLENAME0, null, null, null, null, null, null);
        }


        public boolean inupdateColumn(long id, int day, int time, int input , String bank, String memo){ //삽입 데이터 갱신
            SQLiteDatabase IDB = IDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CreateDB.DAY, day);
            values.put(CreateDB.TIME, time);
            values.put(CreateDB.INPUT, input);
            values.put(CreateDB.BANK, bank);
            values.put(CreateDB.MEMO, memo);
            Log.e("업데이트", "완료");
            return IDB.update(CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
        }

        // 데이터 읽어오기
        public Cursor ingetAllData() {
            SQLiteDatabase IDB = IDBHelper.getWritableDatabase();
            Cursor Ires =  IDB.rawQuery("select * from "+ "inputtable", null);
            return Ires;
        }

        // 전체 삭제
        public void indeleteAllColumns() {
            IDB.delete(CreateDB._TABLENAME0, null, null);
            Log.e("데이터 전체 삭제", "완료 ");
        }

        // 부분 삭제 (특정 행 삭제)
        public boolean indeleteColumn(long id){
            return IDB.delete(CreateDB._TABLENAME0, "_id="+id, null) > 0;
        }

        public void increate(){
            IDBHelper.onCreate(IDB);
        }

        public void inclose(){
            IDB.close();
        }
    }
}
