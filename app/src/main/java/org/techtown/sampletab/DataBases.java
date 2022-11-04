package org.techtown.sampletab;

import android.provider.BaseColumns;

public class DataBases {

        public static final class CreateDB implements BaseColumns {
            public static final String USERID = "userid";  // 유저 아이디 (가입자)
            public static final String INPUT = "input"; // 수입
            public static final String OUTPUT = "OUTPUT"; //지출
            public static final String INFORM = "OUTPUT"; //계좌
            public static final String _TABLENAME0 = "usertable";
            public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"(" // 데이터베이스가 없을 경우 생성.
                    +_ID+" integer primary key autoincrement, " //중복되지 않는 값 생성하지 않도록 1씩 추가.)
                    +USERID+" text not null , "
                    +INPUT+" integer not null , "
                    +INFORM+" integer not null , "
                    +OUTPUT+" integer not null ); ";
        }
}
