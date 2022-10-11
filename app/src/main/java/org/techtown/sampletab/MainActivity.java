package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_SHORT;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // 뷰페이저를 이용해서 화면을 좌우로 볼 수 있음
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), this.getApplicationContext());
        viewPager.setAdapter(adapter);
        // 상단에 제목이 있는 부분
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}

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
        //Log.e("PageAdapter", "PageAdapter 작동");
    }

    // 탭 레이아웃에 있는 제목을 보여줌
    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) return "지출";
        else if(position == 1) return "수입";
        else if(position == 2) return "지출 패턴";
        Toast.makeText(context, "getPageTitle 작동", LENGTH_SHORT).show();
        Log.e("getPageTitle", "getPageTitle 작동");
        return null;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //Toast.makeText(context, "getItem 작동", LENGTH_SHORT).show();
        //Log.e("getItem", "getItem 작동");
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}