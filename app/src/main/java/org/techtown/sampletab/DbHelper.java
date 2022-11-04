package org.techtown.sampletab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DbHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;
    private Object getView;


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB._TABLENAME0);
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

    // 부분 삭제 (특정 행 삭제)
    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }

    private void backup (View v){ //백업, 현재 오류가 없게 코드 수정했지만 UI 연동시 이벤트가 발생되도록 수정 할 필요 있음.
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File currentDB = new File(data, "/data/패키지명/databases/파일명"); //데이터 주소 (임시), 변경할 필요가 있음.
                File backupDB = new File(sd, "/Download/파일명"); // 휴대폰 파일 주소

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());

                src.close();
                dst.close();
                Snackbar.make(v, "백업이 완료되었습니다.", Snackbar.LENGTH_SHORT).show(); //수정할 필요가 있음
            }
        } catch (Exception e) {
            Snackbar.make(v, "백업이 실패되었습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void restore (View v) { //복원, 백업과 이하동문
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File currentDB = new File(sd, "/Download/DB파일");
                File restoreDB = new File(data, "/data/패키지명/databases/DB파일");

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(restoreDB).getChannel();
                dst.transferFrom(src, 0, src.size());

                src.close();
                dst.close();
                Snackbar.make(v, "복구가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Snackbar.make(v, "복구에 실패하였습니다.", Snackbar.LENGTH_SHORT).show();
        }

    }

    private View getView() { // 이 부분을 이벤트 리스너와 조합해서 변경 할 예정
        return null;
    }


    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }
}



