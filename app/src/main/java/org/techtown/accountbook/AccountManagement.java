package org.techtown.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.techtown.accountbook.R;

public class AccountManagement extends AppCompatActivity {
    Button btn_account_add, btn_account_delete;

    //뒤로가기 버튼 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_back_menu, menu) ;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back :
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        btn_account_add = (Button) findViewById(R.id.btnAccountAdd);    //계좌 추가 버튼
        btn_account_delete = (Button) findViewById(R.id.btnAccountDelete);  //계좌 삭제 버튼

        //계좌 추가 버튼 리스너
        btn_account_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "계좌 추가가 눌렸습니다", Toast.LENGTH_SHORT).show();
                /*
                    여기에 계좌 추가 작성
                 */

            }
        });

        //계좌 삭제 버튼 리스너
        btn_account_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "계좌 삭제가 눌렸습니다", Toast.LENGTH_SHORT).show();
                /*
                    여기에 계좌 삭제 작성
                 */

            }
        });
    }

}