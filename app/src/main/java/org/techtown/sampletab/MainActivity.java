package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_SHORT;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static final int PERMISSIONS_REQUEST = 0x00000001;
    static final int REQUEST_CODE = 0x00000001;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_setting_menu, menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings :
                // 액티비티의 화면 전환
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                return true ;
            // ...
            // ...
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*          NotificationListener start                 */
        permissionGrantred(); // Notification 관련 권한 설정 함수
        //textList.setText(fileRead()); // 처음 어플을 실행했을 때 data.txt 파일을 읽고 화면에 출력해줌
        //new BlankFragment4().textSetting(fileRead());

        /*            NotificationListener end                 */

        // 뷰페이저를 이용해서 화면을 좌우로 볼 수 있음
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), this.getApplicationContext());
        viewPager.setAdapter(adapter);
        // 상단에 제목이 있는 부분
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    /*          NotificationListener start                 */
    // 새로운 인텐트가 오는 경우
    @Override
    protected void onNewIntent(Intent intent){
        /*
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //textList.setText(intent.getStringExtra("line"));
        BlankFragment4 blankFragment4 = new BlankFragment4();
        Bundle bundle = new Bundle();
        bundle.putString("line", intent.getStringExtra("line"));
        blankFragment4.setArguments(bundle);
        //transaction.replace(R.id.pager, blankFragment4);
        transaction.commit();
        Log.e("onNewIntent", intent.getStringExtra("line"));
        //Toast.makeText(this, "onNewIntent", LENGTH_SHORT).show();
        */
        super.onNewIntent(intent);
    }

    // 처음 실행할 때 권한 설정하는 창을 보여줌
    private void permissionGrantred() {
        // Notification Listener에 대한 권한 설정
        Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(this);
        if (sets != null && sets.contains(getPackageName())) {
        } else {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }
    }

    // 어플을 처음 실행할 때 Mydate 의 위치에 data.txt 파일을 읽고 String으로 리턴하는 함수
    private String fileRead(){
        String text = "";
        // data.txt 파일 위치
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyData/data.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line=reader.readLine()) != null){
                text = text + line+"\n";
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return text;
    }
    /*            NotificationListener end                 */
}


/*               Fragment start                 */
class PageAdapter extends FragmentPagerAdapter{
    private ArrayList<Fragment> list;
    Context context;
    // 뷰페이저에 프래그먼트를 넣음
    public PageAdapter(@NonNull FragmentManager fm, Context con) {
        super(fm);
        list = new ArrayList<>();
        list.add(new BlankFragment1());
        list.add(new BlankFragment2());
        list.add(new BlankFragment3());
        this.context = con; // Toast를 사용하기 위해 임시로 추가함
        //Toast.makeText(context, "PageAdapter 작동", LENGTH_SHORT).show();
        Log.e("PageAdapter", "PageAdapter 작동");
    }

    // 탭 레이아웃에 있는 제목을 보여줌
    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) return "지출";
        else if(position == 1) return "수입";
        else if(position == 2) return "지출 패턴";
        else if(position == 3) return "알림 확인용";
        else if(position == 4) return "테스트";
        //Toast.makeText(context, "getPageTitle 작동", LENGTH_SHORT).show();
        Log.e("getPageTitle", "getPageTitle 작동");
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //Toast.makeText(context, "getItem 작동", LENGTH_SHORT).show();
        Log.e("getItem", "  getItem 작동");
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
/*               Fragment end                    */