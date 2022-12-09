package org.techtown.accountbook;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {
    public static int isPasswordInput = 0;
    //뒤로가기 버튼 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_back_menu, menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id. back :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView monthStart = (TextView) findViewById(R.id.month_start);
        TextView dataBackup = (TextView) findViewById(R.id.data_backup);
        TextView dataRestore = (TextView) findViewById(R.id.data_restore);
        Switch warning = (Switch) findViewById(R.id.warning);
        TextView upperLine = (TextView) findViewById(R.id.upper_line);
        Switch passwordUse = (Switch) findViewById(R.id.password_use);
        TextView passwordInput = (TextView) findViewById(R.id.password_input);
        int usePassword = PreferenceManager.getInt(SettingActivity.this, "usePassword");
        if(usePassword == 1) {  // 비밀번호 사용안할때
            passwordUse.setChecked(false);
        }
        if(usePassword == 0) {  // 비밀번호 사용할때
            passwordUse.setChecked(true);
        }
        //월 시작일 리스너
        monthStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //월 시작일 설정 클릭 시 다이얼로그 띄워줌
                View dlgView = View.inflate(SettingActivity.this, R.layout.month_start_dialog, null);

                EditText receiveStartDay = dlgView.findViewById(R.id.receive_start_day);

                AlertDialog.Builder msDialog = new AlertDialog.Builder(SettingActivity.this);
                msDialog.setTitle("월 시작일 설정");
                msDialog.setMessage("1~28의 숫자를 입력해 주세요");
                msDialog.setView(dlgView);
                msDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    //입력받은 시작일이 1~28이라면 Preference에 저장해줌
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            int day;
                            String str = receiveStartDay.getText().toString();
                            day = Integer.parseInt(str);
                            if(day < 1 || day > 28){
                                Toast.makeText(SettingActivity.this, "잘못된 입력입니다.", LENGTH_LONG).show();
                            }
                            else {
                                str = ("월 시작일이 " + day + "일로 저장되었습니다.");
                                PreferenceManager.setInt(SettingActivity.this, "startDayKey", day);
                                Toast.makeText(SettingActivity.this, str, LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(SettingActivity.this, "월 시작일을 입력해 주세요", LENGTH_SHORT).show();
                        }

                    }
                });
                msDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                msDialog.show();
            }
        });

        // 데이터 백업 리스너
        dataBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    DataBaseBackup db = new DataBaseBackup(SettingActivity.this);
                    db.save();
                    Toast.makeText(SettingActivity.this, "SaveClick", LENGTH_SHORT).show();
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //데이터 복구 리스너
        dataRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    DataBaseBackup db = new DataBaseBackup(SettingActivity.this);
                    db.restore();
                    Toast.makeText(SettingActivity.this, "RestoreClick", LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

        // 지출 상한 경고 리스너
        warning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttomView, boolean isChecked) {
                if (isChecked){

                }else{

                }

            }
        });

        // 지출 상한선 리스너
        upperLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //비밀번호 사용 리스너
        passwordUse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttomView, boolean isChecked) {
                if (isChecked){     //활성화 시
                    PreferenceManager.setInt(SettingActivity.this, "usePassword", 0);
                    String pw = PreferenceManager.getString(SettingActivity.this,"passwordKey");

                    if(pw.equals("EMPTY")){
                        isPasswordInput = 1;
                        Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                        startActivity(intent);
                    }
                    passwordInput.setFocusableInTouchMode(true);
                    passwordInput.setClickable(true);
                }else{      //비활성화 시
                    PreferenceManager.setInt(SettingActivity.this, "usePassword", 1);
                    passwordInput.setFocusableInTouchMode(false);
                    passwordInput.setClickable(false);
                }

            }
        });

        //비밀번호 입력 리스너
        passwordInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordInput = 1;
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);
            }
        });

    }
}