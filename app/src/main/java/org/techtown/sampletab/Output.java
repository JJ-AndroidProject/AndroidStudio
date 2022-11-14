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

public class Output {

    public static final class CreateDB implements BaseColumns {
        public static final String DAY = "day"; //날짜
        public static final String TIME = "time"; //시간
        public static final String OUTPUT = " output"; // 지출
        public static final String BANK = "bank"; //은행
        public static final String MEMO = "memo"; //메모
        public static final String _TABLENAME1 = "outtable";
        public static final String _CREATE1 = "create table if not exists "+_TABLENAME1+"(" // 데이터베이스가 없을 경우 생성.
                +_ID+" integer primary key autoincrement, " //중복되지 않는 값 생성하지 않도록 1씩 추가.
                +DAY
                +TIME
                +OUTPUT
                +BANK
                +MEMO + ");";
    }

    public static class OutHelper {

        private static final String DATABASE_NAME = "output.db";
        private static final int DATABASE_VERSION = 1;
        public static SQLiteDatabase ODB;
        private DatabaseHelper ODBHelper;
        private Context OCtx;


        private class DatabaseHelper extends SQLiteOpenHelper {

            public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CreateDB._CREATE1);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + CreateDB._TABLENAME1);
                onCreate(db);
            }
        }

        public OutHelper(Context context){
            this.OCtx = context;
        }

        public OutHelper open() throws SQLException {
            ODBHelper = new DatabaseHelper(OCtx, DATABASE_NAME, null, DATABASE_VERSION);
            ODB = ODBHelper.getWritableDatabase();
            return this;
        }

        public long outputinsertColumn(int day, int time, int output , String bank, String memo) { // 수입 데이터 삽입(Insert)
            SQLiteDatabase ODB = ODBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CreateDB.DAY, day);
            values.put(CreateDB.TIME, time);
            values.put(CreateDB.OUTPUT, output);
            values.put(CreateDB.BANK, bank);
            values.put(CreateDB.MEMO, memo);
            Log.e("삽입","완료");
            return ODB.insert(CreateDB._TABLENAME1, null, values);
        }

        public Cursor outputselectColumns(){ //데이터 선택(SELECT)
            return ODB.query(CreateDB._TABLENAME1, null, null, null, null, null, null);
        }


        public boolean outputupdateColumn(long id, int day, int time, int output , String bank, String memo){ //삽입 데이터 갱신
            SQLiteDatabase ODB = ODBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CreateDB.DAY, day);
            values.put(CreateDB.TIME, time);
            values.put(CreateDB.OUTPUT, output);
            values.put(CreateDB.BANK, bank);
            values.put(CreateDB.MEMO, memo);
            Log.e("업데이트", "완료");
            return ODB.update(CreateDB._TABLENAME1, values, "_id=" + id, null) > 0;
        }

        // 데이터 읽어오기
        public Cursor outputgetAllData() {
            SQLiteDatabase ODB = ODBHelper.getWritableDatabase();
            Cursor Ores = ODB.rawQuery("select * from "+ "outtable", null);
            return Ores;
        }

        // 전체 삭제
        public void outdeleteAllColumns() {
            ODB.delete(CreateDB._TABLENAME1, null, null);
            Log.e("데이터 전체 삭제", "완료 ");
        }

        // 부분 삭제 (특정 행 삭제)
        public boolean outdeleteColumn(long id){
            return ODB.delete(CreateDB._TABLENAME1, "_id="+id, null) > 0;
        }

        public void outcreate(){
            ODBHelper.onCreate(ODB);
        }

        public void outclose(){
            ODB.close();
        }
    }
}
