package org.techtown.accountbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBOpenHelper {

    private static final String DATABASE_NAME = "accountBook.db"; // 데이터베이스 파일의 이름
    private static final int DATABASE_VERSION = 1; // 데이터베이스의 현재 버전

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
                    "id integer primary key autoincrement," +
                    "posttime text not null ," +
                    "bankname text not null ," +
                    "move text default null ," +
                    "accountnumber text , " +
                    "title text not null ," +
                    "type text not null ," +
                    "money integer not null , " +
                    "detail text);";
            db.execSQL(outputTable);

            //db.execSQL("DROP TABLE IF EXISTS input");

            // 수입 테이블
            String inputTable = "create table if not exists input(" +
                    "id integer primary key autoincrement," +
                    "posttime text not null ," +
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
    } // 데이터베이스 생성

    public void close(){
        db.close();
    } // 데이터베이스 종료

    // output 테이블에 insert 하는 함수
    public long insertColumnOutput(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        ContentValues values = new ContentValues();
        values.put("posttime", posttime); // 시간
        values.put("bankname", bankname); // 은행
        values.put("accountnumber", accountnumber); // 계좌번호
        values.put("title", title); // 제목
        values.put("type", type); // 분류
        values.put("money", money); // 금액
        values.put("detail", detail); // 세부사항
        Log.e("insertColumnOutput", posttime+" "+bankname+" "+accountnumber+" "+title+" "+type+" "+money+" "+detail);
        return db.insert("output", null, values);
    }

    // input 테이블에 insert 하는 함수
    public long insertColumnInput(String posttime, String bankname, String accountnumber, String title, String type, int money, String detail){
        ContentValues values = new ContentValues();
        values.put("posttime", posttime); // 시간
        values.put("bankname", bankname); // 은행
        values.put("accountnumber", accountnumber); // 계좌번호
        values.put("title", title); // 제목
        values.put("type", type); // 분류
        values.put("money", money); // 금액
        values.put("detail", detail); // 세부사항
        return db.insert("input", null, values);
    }

    // 계좌 간 이동을 할 때 move의 값을 업데이트 해주는 함수
    public boolean updateColumn(long id, String table, String move){ //데이터 갱신
        ContentValues values = new ContentValues();
        values.put("move", move);
        return db.update(table, values, "id=" + id, null) > 0;
    }

    // 수정한 값을 업데이트 해주는 함수
    public boolean updateColumnArrayList(ArrayList<String> list, String table){ //데이터 갱신
        long id = Long.parseLong(list.get(0));
        ContentValues values = new ContentValues();
        values.put("posttime", list.get(1)); // 시간
        values.put("bankname", list.get(2)); // 은행
        values.put("move", list.get(3));
        values.put("accountnumber", list.get(4)); // 계좌번호
        values.put("title", list.get(5)); // 제목
        values.put("type", list.get(6)); // 분류
        values.put("money", Integer.parseInt(list.get(7))); // 금액
        values.put("detail", list.get(8)); // 세부사항
        return db.update(table, values, "id=" + id, null) > 0;
    }

    // 부분 삭제 (특정 행 삭제)
    public boolean deleteColumn(long id, String table){
        return db.delete(table, "id = "+id, null) > 0;
    }

    // output 테이블의 튜플을 가져오는 함수
    public Cursor selectColumnsOutput(){ return db.rawQuery("SELECT * FROM output", null); }

    // input 테이블의 튜플을 가져오는 함수
    public Cursor selectColumnsInput(){
        return db.rawQuery("SELECT * FROM input", null);
    }
}

// 데이터베이스를 조작하는 명령어 모음
class DBcommand{
    Context context;
    public DBcommand(Context context){
        this.context = context;
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
    }

    // output 테이블에 데이터를 추가하는 부분(데이터가 제대로 들어 갔는지 로그로 확인)
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

    // input 테이블에 데이터를 추가하는 부분(데이터가 제대로 들어 갔는지 로그로 확인)
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

    // 계좌 간 이동 확인
    void selectCount() {
        try {
            DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
            dbOpenHelper.open();
            dbOpenHelper.create();
            Cursor cursorOutput = dbOpenHelper.selectColumnsOutput();
            Cursor cursorInput = dbOpenHelper.selectColumnsInput();

            cursorOutput.moveToLast(); // cursor를 output 테이블의 마지막을 가르키게 한다.

            int postTimeIntOutput = cursorOutput.getColumnIndex("posttime");
            int moneyIntOutput = cursorOutput.getColumnIndex("money");
            int idOutput = cursorOutput.getColumnIndex("id");
            int moveOutput = cursorOutput.getColumnIndex("move");
            String postTimeOutput = cursorOutput.getString(postTimeIntOutput); // cursor를 통해 posttime 속성의 값을 가져온다.
            int moneyOutput = cursorOutput.getInt(moneyIntOutput); // cursor를 통해 money 속성의 값을 가져온다.
            int out_id = cursorOutput.getInt(idOutput); // cursor를 통해 id 속성의 값을 가져온다.
            String move_output = cursorOutput.getString(moveOutput); // cursor를 통해 move 속성의 값을 가져온다.

            Log.e("SelectCountOutput", postTimeOutput + " " + moneyOutput + "원");

            cursorInput.moveToLast(); // cursor를 input 테이블의 마지막을 가르키게 한다.

            int postTimeIntInput = cursorInput.getColumnIndex("posttime");
            int moneyIntInput = cursorInput.getColumnIndex("money");
            int idInput = cursorInput.getColumnIndex("id");
            int moveInput = cursorInput.getColumnIndex("move");
            String postTimeInput = cursorInput.getString(postTimeIntInput); // cursor를 통해 posttime 속성의 값을 가져온다.
            int moneyInput = cursorInput.getInt(moneyIntInput); // cursor를 통해 money 속성의 값을 가져온다.
            int in_id = cursorInput.getInt(idInput); // cursor를 통해 id 속성의 값을 가져온다.
            String move_input = cursorInput.getString(moveInput); // cursor를 통해 move 속성의 값을 가져온다.

            Log.e("SelectCountInput", postTimeInput + " " + moneyInput + "원");
            try {
                Date format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postTimeOutput);
                Date format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(postTimeInput);
                long out = (format2.getTime() - format1.getTime()) / 1000; // 두 입력된 값의 시간 차를 확인한다. (초 단위)
                Log.e("SelectCount", "비교 : " + Math.abs(out));
                if (out <= 5) { // 시간의 차가 5 이하인 경우 (5초)
                    if (moneyOutput == moneyInput) { // output의 money와 input의 money 값이 같은 지 확인
                        if (move_output == null && move_input == null) { // output과 input의 move 값이 null 일때 작동
                            Log.e("SelectCount", "계좌간 이동 설정");
                            dbOpenHelper.updateColumn(out_id, "output", "계좌이동"); // output 테이블의 id 값을 가진 튜플에 move를 계좌이동으로 변경
                            dbOpenHelper.updateColumn(in_id, "input", "계좌이동"); // input 테이블의 id 값을 가진 튜플에 move를 계좌이동으로 변경
                        }
                    }
                }
            } catch (ParseException e) {
                Log.e("SelectCount", "ParseException 오류");
            }
        } catch (CursorIndexOutOfBoundsException e) {
            Log.e("SelectCount", "CursorIndexOutOfBoundsException 오류");
        }
    }

    ArrayList<String> selectData(int select_id, String select_table){
        ArrayList<String> list = new ArrayList<String>();
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.context);
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = null;
        try{
            if(select_table.equals("output")){
                cursor = dbOpenHelper.selectColumnsOutput();
                Log.e("selectData", "output");
            }
            else if(select_table.equals("input")){
                cursor = dbOpenHelper.selectColumnsInput();
                Log.e("selectData", "input");
            }
            while(cursor.moveToNext()){
                int cursor_id = cursor.getColumnIndex("id");
                int id = cursor.getInt(cursor_id);
                if(select_id == id) {
                    int cursor_postTime = cursor.getColumnIndex("posttime");
                    int cursor_bankName = cursor.getColumnIndex("bankname");
                    int cursor_move = cursor.getColumnIndex("move");
                    int cursor_accountNumber = cursor.getColumnIndex("accountnumber");
                    int cursor_title = cursor.getColumnIndex("title");
                    int cursor_type = cursor.getColumnIndex("type");
                    int cursor_money = cursor.getColumnIndex("money");
                    int cursor_detail = cursor.getColumnIndex("detail");
                    String postTime = cursor.getString(cursor_postTime);
                    String bankName = cursor.getString(cursor_bankName);
                    String move = cursor.getString(cursor_move);
                    String accountNumber = cursor.getString(cursor_accountNumber);
                    String title = cursor.getString(cursor_title);
                    String type = cursor.getString(cursor_type);
                    int money = cursor.getInt(cursor_money);
                    String detail = cursor.getString(cursor_detail);
                    list.add(Integer.toString(id)); // 0
                    list.add(postTime); // 1
                    list.add(bankName); // 2
                    list.add(move); // 3
                    list.add(accountNumber); // 4
                    list.add(title); // 5
                    list.add(type); // 6
                    list.add(Integer.toString(money)); // 7

                    if(detail.equals("null")) list.add(""); // 8
                    else list.add(detail); // 8
                    String result = id + "||" + postTime + "||" + bankName + "||" + accountNumber + "||" + title + "||" + type + "||" + money + "||" + detail;
                    Log.e("DBSelectData", result);
                    break;
                }
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*
    // BlankFragment3에서 테스트 용도로 사용중인 output 테이블의 함수 (나중에는 지울 예정)
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
    // BlankFragment3에서 테스트 용도로 사용중인 input 테이블의 함수 (나중에는 지울 예정)
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
    */
}



