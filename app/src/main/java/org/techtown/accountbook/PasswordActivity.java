package org.techtown.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    EditText pw_1, pw_2, pw_3, pw_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        pw_1 = (EditText) findViewById(R.id.pw1);
        pw_2 = (EditText) findViewById(R.id.pw2);
        pw_3 = (EditText) findViewById(R.id.pw3);
        pw_4 = (EditText) findViewById(R.id.pw4);


        //액티비티가 켜질 시 키보드 보여줌
        pw_1.postDelayed(new Runnable() {
            @Override
            public void run() {
                pw_1.requestFocus();

                manager.showSoftInput(pw_1, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 1000);


        pw_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {       //문자가 지워졌을 시 어플이 강제종료되어 try사용
                    int iS = Integer.parseInt(editable.toString());
                    if (iS < 10 && iS >= 0){
                        pw_2.requestFocus();
                    }
                }catch (Exception e){

                }
            }
        });

        pw_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {       //문자가 지워졌을 시 어플이 강제종료되어 try사용
                    int iS = Integer.parseInt(editable.toString());
                    if (iS < 10 && iS >= 0){
                        pw_3.requestFocus();
                    }
                }catch (Exception e){

                }
            }
        });
        pw_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {       //문자가 지워졌을 시 어플이 강제종료되어 try사용
                    int iS = Integer.parseInt(editable.toString());
                    if (iS < 10 && iS >= 0){
                        pw_4.requestFocus();
                    }
                }catch (Exception e){

                }
            }
        });
        pw_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {       //문자가 지워졌을 시 어플이 강제종료되어 try사용
                    int iS = Integer.parseInt(editable.toString());
                    if (iS < 10 && iS >= 0){
                        String inputPw = pw_1.getText().toString() + pw_2.getText().toString()
                                + pw_3.getText().toString() + pw_4.getText().toString();

                        String pw = PreferenceManager.getString(PasswordActivity.this, "passwordKey");

                        if(SettingActivity.isPasswordInput == 0){       // 비밀번호 기능을 사용하는 경우

                            //비밀번호가 맞았다면
                            if(inputPw.equals(pw)){
                                finish();
                            }else{      //비밀번호가 틀렸다면
                                Toast toast = Toast.makeText(PasswordActivity.this,
                                        "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                //커서 옮겨줌
                                pw_1.requestFocus();

                                    //입력했던 비밀번호 초기화
                                pw_1.setText(null);
                                pw_2.setText(null);
                                pw_3.setText(null);
                                pw_4.setText(null);

                                //키보드 띄워줌
                                manager.showSoftInput(pw_1, InputMethodManager.SHOW_IMPLICIT);
                            }
                        }else if(SettingActivity.isPasswordInput == 1){     //비밀번호를 입력받는 경우

                            PreferenceManager.setString(PasswordActivity.this, "passwordKey", inputPw);
                            Toast.makeText(PasswordActivity.this, "비밀번호가 저장되었습니다",
                                    Toast.LENGTH_SHORT).show();
                            SettingActivity.isPasswordInput = 0;
                            finish();
                        }
                    }
                }catch (Exception e){

                }
            }
        });


        //에디트텍스트들의 이전버튼 클릭 시
        pw_1.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                switch (actionId) {
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        //아무것도 안하게
                        break;
                }
                return true;
            }
        });
        pw_2.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                switch (actionId) {
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        pw_1.requestFocus();    //포커스 pw_1로 이동
                        pw_1.setText(null);     //pw_1내용 지우기
                        break;
                }
                return true;
            }
        });
        pw_3.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                switch (actionId) {
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        pw_2.requestFocus();    //포커스 pw_2로 이동
                        pw_2.setText(null);     //pw_2내용 지우기
                        break;
                }
                return true;
            }
        });
        pw_4.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                switch (actionId) {
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        pw_3.requestFocus();    //포커스 pw_3dm로 이동
                        pw_3.setText(null);     //pw_3내용 지우기
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        //뒤로가기 버튼으로 액티비티 종료되는 것 방지
    }


}