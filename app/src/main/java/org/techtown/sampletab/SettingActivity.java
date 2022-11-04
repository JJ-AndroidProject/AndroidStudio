package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_back_menu, menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id. back :
                finish();// 액티비티의 화면 전환
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


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
                //Toast.makeText(getApplicationContext(), "월 시작일이 눌렸습니다", LENGTH_SHORT).show();

                View dlgView = View.inflate(SettingActivity.this, R.layout.month_start_dialog, null);

                EditText receiveStartDay = dlgView.findViewById(R.id.receive_start_day);

                AlertDialog.Builder msDialog = new AlertDialog.Builder(SettingActivity.this);
                msDialog.setTitle("월 시작일 설정");
                msDialog.setMessage("1~28의 숫자를 입력해 주세요");
                msDialog.setView(dlgView);
                msDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int day;
                        String str = receiveStartDay.getText().toString();
                        day = Integer.parseInt(str);
                        if(day < 1 || day > 28){
                            Toast.makeText(SettingActivity.this, "잘못된 입력입니다.", LENGTH_LONG);
                        }
                        else {
                            str = ("월 시작일이 " + day + "일로 저장되었습니다.");
                            PreferenceManager.setInt(SettingActivity.this, "startDayKey", day);
                            Toast.makeText(SettingActivity.this, str, LENGTH_SHORT);
                        }

                    }
                });
                msDialog.show();
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
        그룹으로 묶을 수 있는 부분은 최대한 묶어서 사용
        계좌 관리 클릭시 액티비티 띄우기

        폰트 크기 변경 추가하기
         */
    }
}