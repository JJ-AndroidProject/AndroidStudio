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
import java.util.List;

// 수입 부분에 대한 어댑터 생성 필요

public class BlankFragment2 extends Fragment {
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
    private List<BlankFragment2.MainIncome> list = new ArrayList<BlankFragment2.MainIncome>();
    /*            test 중(김종원)             */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "프래그먼트 2 생성", Toast.LENGTH_SHORT).show();
        Log.d("BlankFragment2-onCreate", "BlankFragment2-onCreate 작동");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank2, container, false);
        //before, after버튼과 날짜 표기
        btn_before = (Button) viewGroup.findViewById(R.id.btnbefore);
        datetext = (TextView) viewGroup.findViewById(R.id.monthtext);
        btn_after = (Button) viewGroup.findViewById(R.id.btnafter);
        datetext.setText(date);
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView);



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
                    list.add(new BlankFragment2.MainIncome(year, (month+1), (i+1), str));
                }

                /* 해당하는 달에 대한 item_list의 리사이클러뷰에 들어갈 아이템을 불러와야한다.
                list.clear(); // list 리스트를 초기화 한다.

                데이터베이스에서 해당하는 월에 대한 데이터를 가져온다.
                */


                Log.d("BlankFragment2", "before가 눌렸습니다");
                //Toast.makeText(getContext(), "before가 눌렸습니다", Toast.LENGTH_SHORT).show();
                /* 수정 필요
                adapter = new Adapter(getActivity(), list, items);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                */
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
                    list.add(new BlankFragment2.MainIncome(year, (month+1), (i+1), str));
                }
                /* 해당하는 달에 대한 item_list의 리사이클러뷰에 들어갈 아이템을 불러와야한다.
                list.clear(); // list 리스트를 초기화 한다.

                데이터베이스에서 해당하는 월에 대한 데이터를 가져온다.
                */



                Log.d("BlankFragment2", "after가 눌렸습니다");
                //Toast.makeText(getContext(), "after가 눌렸습니다", Toast.LENGTH_SHORT).show();
                /* 수정 필요
                adapter = new Adapter(getActivity(), list, items);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                */

            }
        });
        return viewGroup;
    }
    public class MainIncome{
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
        public MainIncome(int year, int month, int day, String title){
            this.year = year;
            this.month = month;
            this.day = day;
            this.title = title;
        }
    }
}
