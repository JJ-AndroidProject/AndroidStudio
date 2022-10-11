package org.techtown.sampletab;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;


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
    Adapter subAdapter;

    RecyclerView recyclerViewSub;
    ArrayList<String> sub = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "프래그먼트 1 생성", Toast.LENGTH_SHORT).show();
        //Log.e("BlankFragment1-onCreate", "BlankFragment1-onCreate 작동");
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



        recyclerViewSub = (RecyclerView) viewGroup.findViewById(R.id.recyclerViewSub);
        subAdapter = new Adapter(getActivity(), sub, year, month+1, 8);



        // ArrayList에 String 정보를 담아 Adapter로 보낸다.
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i<lastday; i++){
            String str =year + "." + (month + 1) + "." + (i + 1);
            list.add(str);
        }

        adapter = new Adapter(getActivity(), list, 0, 0, 0);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        //before, after버튼에 리스너 달기
        btn_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cal.set(year, month - 1, 1);
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                lastday = cal.getActualMaximum(Calendar.DATE);
                date = year + "." + (month+1);

                datetext.setText(date);

                list.clear();
                for(int i = 0; i<lastday; i++){
                    String str =year + "." + (month + 1) + "." + (i + 1);
                    list.add(str);
                }
                adapter = new Adapter(getActivity(), list, 0, 0, 0);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getContext(), "before가 눌렸습니다", Toast.LENGTH_SHORT).show();

            }
        });
        btn_after.setOnClickListener(new View.OnClickListener() {
            @Override
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
                    list.add(str);
                }
                adapter = new Adapter(getActivity(), list, 0, 0, 0);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getContext(), "after가 눌렸습니다", Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(getContext(), "프래그먼트 1 작동", Toast.LENGTH_SHORT).show();
        return viewGroup;
    }


}
