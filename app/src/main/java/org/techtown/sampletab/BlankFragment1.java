package org.techtown.sampletab;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class BlankFragment1 extends Fragment {
    //현재 연도, 달, 그 달의 마지막 날짜를 받는다.
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int lastday = cal.getActualMaximum(Calendar.DATE);
    String date = year + "." + (month+1);
    Button btn_before, btn_after;
    TextView datetext;
    RecyclerView recyclerView;
    Adapter adapter;

    /*            test 중(김종원)             */
    // ArrayList에 String 정보를 담아 Adapter로 보낸다.
    private List<MainRecyclerItem> list = new ArrayList<MainRecyclerItem>();
    //ArrayList<String> list = new ArrayList<>();
    RecyclerView recyclerViewSub;
    SubAdapter subAdapter;
    private List<SubRecyclerItem> items = new ArrayList<SubRecyclerItem>();
    /*            test 중(김종원)             */


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "프래그먼트 1 생성", Toast.LENGTH_SHORT).show();
        Log.e("BlankFragment1-onCreate", "BlankFragment1-onCreate 작동");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank1, container, false);
        //before, after버튼과 날짜 표기
        btn_before = (Button) viewGroup.findViewById(R.id.btnbefore);
        datetext = (TextView) viewGroup.findViewById(R.id.monthtext);
        btn_after = (Button) viewGroup.findViewById(R.id.btnafter);
        datetext.setText(date);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView);



        list.clear();
        for(int i = 0; i<lastday; i++){
            String str =year + "." + (month + 1) + "." + (i + 1);
            list.add(new MainRecyclerItem(year, (month+1), (i+1), str));
        }
        items.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            items.add(new SubRecyclerItem(2022, 10, 25, LocalTime.now(), "Test1", 5000));
            items.add(new SubRecyclerItem(2022, 10, 26, LocalTime.now(), "Test2", 60000));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test3", 8065));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test4", 5480));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test5", 15480));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test6", 55480));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test7", 95480));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test8", 1280));
            items.add(new SubRecyclerItem(2022, 10, 28, LocalTime.now(), "Test9", 580));
        }

        adapter = new Adapter(getActivity(), list, items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //before, after버튼에 리스너 달기
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            // before 버튼
            public void onClick(View view) {
                cal.set(year, month - 1, 1);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                lastday = cal.getActualMaximum(Calendar.DATE);
                date = year + "." + (month+1);

                datetext.setText(date);

                list.clear(); // 월별 일을 표시해주는 리스트를 초기화
                for(int i = 0; i<lastday; i++){
                    String str =year + "." + (month + 1) + "." + (i + 1);
                    list.add(new MainRecyclerItem(year, (month+1), (i+1), str));
                }

                /* 해당하는 달에 대한 item_sub의 리사이클러뷰에 들어갈 아이템을 불러와야한다.
                items.clear(); // items 리스트를 초기화 한다.

                데이터베이스에서 해당하는 월에 대한 데이터를 가져온다.
                */

                //Toast.makeText(getContext(), "before가 눌렸습니다", Toast.LENGTH_SHORT).show();
                adapter = new Adapter(getActivity(), list, items);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }
        });
        btn_after.setOnClickListener(new View.OnClickListener() {
            @Override
            // after 버튼
            public void onClick(View view) {
                cal.set(year, month + 1, 1);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                lastday = cal.getActualMaximum(Calendar.DATE);
                date = year + "." + (month+1);

                datetext.setText(date);

                list.clear();
                for(int i = 0; i<lastday; i++){
                    String str =year + "." + (month + 1) + "." + (i + 1);
                    list.add(new MainRecyclerItem(year, (month+1), (i+1), str));
                }
                /* 해당하는 달에 대한 item_sub의 리사이클러뷰에 들어갈 아이템을 불러와야한다.
                items.clear(); // items 리스트를 초기화 한다.

                데이터베이스에서 해당하는 월에 대한 데이터를 가져온다.
                */

                //Toast.makeText(getContext(), "after가 눌렸습니다", Toast.LENGTH_SHORT).show();
                adapter = new Adapter(getActivity(), list, items);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }
        });
        return viewGroup;
    }

    public class MainRecyclerItem{
        int year;
        int month;
        int day;
        String title;
        int getYear(){
            return this.year;
        }
        int getMonth(){
            return this.month;
        }
        int getDay(){
            return this.day;
        }
        String getTitle(){
            return this.title;
        }
        public MainRecyclerItem(int year, int month, int day, String title){
            this.year = year;
            this.month = month;
            this.day = day;
            this.title = title;
        }
    }

    public class SubRecyclerItem{
        int year;
        int month;
        int day;
        LocalTime time;
        String title;
        double money;
        int getYear(){
            return this.year;
        }
        int getMonth(){
            return this.month;
        }
        int getDay(){
            return this.day;
        }
        LocalTime getTime(){return this.time;}
        String getTitle(){
            return this.title;
        }
        double getMoney(){
            return this.money;
        }
        public SubRecyclerItem(int year, int month, int day, LocalTime time, String title, double money){
            this.year = year;
            this.month = month;
            this.day = day;
            this.time = time;
            this.title = title;
            this.money = money;
        }
    }
}
