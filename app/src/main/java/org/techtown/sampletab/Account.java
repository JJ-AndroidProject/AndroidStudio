package org.techtown.sampletab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class Account {

    public static final class CreateDB implements BaseColumns {
        public static final String NAME = "name"; //이름
        public static final String ACCOUNT = "account"; //계좌 정보
        public static final String BACK = "bank"; //은행
        public static final String MEMO = "memo"; //메모
        public static final String _TABLENAME2 = "accounttable";
        public static final String _CREATE2 = "create table if not exists "+_TABLENAME2+"(" // 데이터베이스가 없을 경우 생성.
                +_ID+" integer primary key autoincrement, " //중복되지 않는 값 생성하지 않도록 1씩 추가.)
                +NAME
                +ACCOUNT
                +BACK
                +MEMO + " ); ";
    }

    public static class AcData {

        private static final String DATABASE_NAME = "account.db";
        private static final int DATABASE_VERSION = 1;
        public static SQLiteDatabase ADB;
        private DatabaseHelper ADBHelper;
        private Context ACtx;


        private class DatabaseHelper extends SQLiteOpenHelper {

            public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CreateDB._CREATE2);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + CreateDB._TABLENAME2);
                onCreate(db);
            }
        }

        public AcData(Context context){
            this.ACtx = context;
        }

        public AcData open() throws SQLException {
            ADBHelper = new DatabaseHelper(ACtx, DATABASE_NAME, null, DATABASE_VERSION);
            ADB = ADBHelper.getWritableDatabase();
            return this;
        }

        public long AcinsertColumn1(String name, int account, String bank, String memo) { //데이터 삽입(Insert)
            SQLiteDatabase ADB = ADBHelper.getWritableDatabase();
            ContentValues values1 = new ContentValues();
            values1.put(CreateDB.NAME, name);
            values1.put(CreateDB.ACCOUNT, account);
            values1.put(CreateDB.BACK, bank);
            values1.put(CreateDB.MEMO, memo);
            Log.e("삽입","완료");
            return ADB.insert(CreateDB._TABLENAME2, null, values1);
        }

        public boolean AcupdateColumn1(long id, String name, int account, String bank, String memo){ //데이터 갱신
            SQLiteDatabase ADB = ADBHelper.getWritableDatabase();
            ContentValues values1 = new ContentValues();
            values1.put(CreateDB.NAME, name);
            values1.put(CreateDB.ACCOUNT, account);
            values1.put(CreateDB.BACK, bank);
            values1.put(CreateDB.MEMO, memo);
            Log.e("업데이트", "완료");
            return ADB.update(CreateDB._TABLENAME2, values1, "_id=" + id, null) > 0;
        }

        public Cursor AcinselectColumns(){ //데이터 선택(SELECT)
            return ADB.query(CreateDB._TABLENAME2, null, null, null, null, null, null);
        }


        // 전체 삭제
        public void AcdeleteAllColumns() {ADB.delete(CreateDB._TABLENAME2, null, null);
            Log.e("데이터 전체 삭제", "완료 ");
        }

        // 부분 삭제 (특정 행 삭제)
        public boolean AcdeleteColumn(long id){
            return ADB.delete(CreateDB._TABLENAME2, "_id="+id, null) > 0;
        }

        //데이터 읽어오기
        public Cursor AcgetAllData() {
            SQLiteDatabase IDB = ADBHelper.getWritableDatabase();
            Cursor Ares =  IDB.rawQuery("select * from "+ "accounttable", null);
            return Ares;
        }


        public void Acreate(){
            ADBHelper.onCreate(ADB);
        }

        public void Acclose(){
            ADB.close();
        }


    }
}

//파일 암호화 예제
//파일이 암호화 되는지 확인해 볼 필요가 있음.
//다른 작업이 우선시 되므로 삭제나 수정 예정
//public class AES256Chiper {
//public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
//public static String secretKey = "비밀키"; //비밀 번호 설정할 것

//AES256 암호화
// public static String AES_Encode(String str)    throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,    IllegalBlockSizeException, BadPaddingException {

//  byte[] textBytes = str.getBytes("UTF-8");
//  AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//   SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
//   Cipher cipher = null;
//  cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//  cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

//  return Base64.encodeToString(cipher.doFinal(textBytes), 0);
// }

//AES256 복호화
//public static String AES_Decode(String str)    throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

//  byte[] textBytes =Base64.decode(str,0);
// //byte[] textBytes = str.getBytes("UTF-8");
// AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
// SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
// cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
// return new String(cipher.doFinal(textBytes), "UTF-8");
// }
//}

//메소드 호출 예제
//   public static String secretKey = "비밀키";
//AES256Chiper.AES_Encode("암호화 내용");
//   AES256Chiper.AES_Decode("복호화 내용");

