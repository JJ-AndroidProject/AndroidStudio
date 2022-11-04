package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_SHORT;
import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;
import android.content.SharedPreferences;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView monthStart = (TextView) findViewById(R.id.month_start);
        TextView dataManagement = (TextView) findViewById(R.id.data_management);
        TextView dataDelete = (TextView) findViewById(R.id.data_delete);
        TextView dataBackup = (TextView) findViewById(R.id.data_backup);
        TextView dataRestore = (TextView) findViewById(R.id.data_restore);
        Switch warning = (Switch) findViewById(R.id.warning);
        TextView upperLine = (TextView) findViewById(R.id.upper_line);
        TextView accountManagement = (TextView) findViewById(R.id.account_management);
        TextView accountAdd = (TextView) findViewById(R.id.account_add);
        Switch passwordUse = (Switch) findViewById(R.id.password_use);
        TextView passwordInput = (TextView) findViewById(R.id.password_input);



        monthStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "월 시작일이 눌렸습니다", LENGTH_SHORT).show();

                Context monthStartContext = getApplicationContext();
                LayoutInflater inflater = (LayoutInflater)monthStartContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                View dlgView = View.inflate(SettingActivity.this, R.layout.month_start_dialog, null);

                EditText receiveStartDay = dlgView.findViewById(R.id.receive_start_day);

                AlertDialog.Builder msDialog = new AlertDialog.Builder(SettingActivity.this);
                msDialog.setTitle("월 시작일 설정");
                msDialog.setMessage("시작일을 입력해주세요");
                msDialog.setView(dlgView);
                msDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String str = receiveStartDay.getText().toString();
                        PreferenceManager.setInt(SettingActivity.this, "startDayKey", Integer.parseInt(str));

                        //Toast.makeText(getApplicationContext(), startDay, LENGTH_SHORT).show();
                    }
                });
                msDialog.show();
                //Toast.makeText(getApplicationContext(), startDay, LENGTH_SHORT).show();

            }
        });

        dataManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dataDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dataBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dataRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        upperLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        accountManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        accountAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        passwordUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "비밀번호 사용이 눌렸습니다", LENGTH_SHORT).show();
            }
        });
        passwordInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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