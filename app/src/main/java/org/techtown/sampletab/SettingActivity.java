package org.techtown.sampletab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SettingActivity extends AppCompatActivity {

    public static int startDay = 28;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        /*
            각 클릭 이벤트 처리하는 부분이 필요하다.
            sharedpreferences 를 사용하여 설정한 데이터를 저장하고 불러온다.
            그룹으로 묶을 수 있는 부분은 최대한 묶어서 사용
            월별 시작일 다이얼로그 만들고 입력 받아서 startDay에 저장하면 됨
            before after 월별 시작일 값으로 수정
        */

        /*
         1순위 월시작일
         2순위 데이터 백업&복구 (클릭이벤트만)
         3순위 계좌 관리 (액티비티)

        폰트 크기 변경 추가하기
         */
    }
}