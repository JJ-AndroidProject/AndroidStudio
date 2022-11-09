package org.techtown.sampletab;

import android.database.Cursor;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        list.clear();

        int flag = 0;
        int monthtmp = month;
        int yeartmp = year;
        int startday = PreferenceManager.getInt(getContext(), "startDayKey") - 1;
        int i = startday;
        while(true){
            if(i >= lastday){
                i = 0;  monthtmp++;
            }
            if(i == startday) flag++;
            if(flag > 1) break;
            if(monthtmp > 11){
                monthtmp = 0;   yeartmp++;
            }
            String str = yeartmp + "." + (monthtmp + 1) + "." + (i + 1);
            String setDay = yeartmp + "-" + (monthtmp + 1) + "-" + (i + 1);
            try {
                String day = format.format((format.parse(setDay)));
                list.add(new MainRecyclerItem(day, str));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            i++;
        }
        items.clear();
        try{
            dbSelect();
            adapter = new Adapter(getActivity(), list, items);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }

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
                int flag = 0;
                int monthtmp = month;
                int yeartmp = year;
                int startday = PreferenceManager.getInt(getContext(), "startDayKey") - 1;
                int i = startday;
                while(true){
                    if(i >= lastday){
                        i = 0;  monthtmp++;
                    }
                    if(i == startday) flag++;
                    if(flag > 1) break;
                    if(monthtmp > 11){
                        monthtmp = 0;   yeartmp++;
                    }
                    String str = yeartmp + "." + (monthtmp + 1) + "." + (i + 1);
                    String setDay = yeartmp + "-" + (monthtmp + 1) + "-" + (i + 1);
                    try {
                        String day = format.format((format.parse(setDay)));
                        list.add(new MainRecyclerItem(day, str));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                /* 해당하는 달에 대한 item_sub의 리사이클러뷰에 들어갈 아이템을 불러와야한다.
                items.clear(); // items 리스트를 초기화 한다.

                데이터베이스에서 해당하는 월에 대한 데이터를 가져온다.
                */

                //Toast.makeText(getContext(), "before가 눌렸습니다", Toast.LENGTH_SHORT).show();
                items.clear();
                try{
                    dbSelect();
                    adapter = new Adapter(getActivity(), list, items);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }catch(Exception e){
                    e.printStackTrace();
                }
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
                int flag = 0;
                int monthtmp = month;
                int yeartmp = year;
                int startday = PreferenceManager.getInt(getContext(), "startDayKey") - 1;
                int i = startday;
                while(true){
                    if(i >= lastday){
                        i = 0;  monthtmp++;
                    }
                    if(i == startday) flag++;
                    if(flag > 1) break;
                    if(monthtmp > 11){
                        monthtmp = 0;   yeartmp++;
                    }
                    String str = yeartmp + "." + (monthtmp + 1) + "." + (i + 1);
                    String setDay = yeartmp + "-" + (monthtmp + 1) + "-" + (i + 1);
                    try {
                        String day = format.format((format.parse(setDay)));
                        list.add(new MainRecyclerItem(day, str));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                /* 해당하는 달에 대한 item_sub의 리사이클러뷰에 들어갈 아이템을 불러와야한다.
                items.clear(); // items 리스트를 초기화 한다.

                데이터베이스에서 해당하는 월에 대한 데이터를 가져온다.
                */

                //Toast.makeText(getContext(), "after가 눌렸습니다", Toast.LENGTH_SHORT).show();
                items.clear();
                try{
                    dbSelect();
                    adapter = new Adapter(getActivity(), list, items);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return viewGroup;
    }

    void dbSelect() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int flag = 0;
        int monthtmp = month;
        int yeartmp = year;
        int startday = PreferenceManager.getInt(getContext(), "startDayKey")-1;
        int i = startday;
        String startLine = yeartmp+"-"+(monthtmp+1)+"-"+(startday+1);
        String lastLine = yeartmp+"-"+(monthtmp+2)+"-"+(startday+1);
        String start = format.format(format.parse(startLine));
        String last = format.format(format.parse(lastLine));
        Log.e("TEST", "start : "+start+" last : "+last);

        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.getContext());
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsOutput();
        Log.e("DB", cursor.getCount()+"개");
        int count = 1;
        //items.clear();
        while(cursor.moveToNext()) {
            int postTimeInt = cursor.getColumnIndex("posttime");
            String postTime = cursor.getString(postTimeInt);
            String date = format.format(format.parse(postTime));
            if(start.compareTo(date) <= 0 && last.compareTo(date) >= 0) {
                Log.e("TEST", "postTime : "+postTime+" PostTime이 start보다 크다");
                int titleInt = cursor.getColumnIndex("title");
                int moneyInt = cursor.getColumnIndex("money");
                /*
                int bankNameInt = cursor.getColumnIndex("bankname");
                int accountNumberInt = cursor.getColumnIndex("accountnumber");
                int typeInt = cursor.getColumnIndex("type");
                int detailInt = cursor.getColumnIndex("detail");
                String bankName = cursor.getString(bankNameInt);
                String accountNumber = cursor.getString(accountNumberInt);
                String type = cursor.getString(typeInt);
                String detail = cursor.getString(detailInt);
                */
                String title = cursor.getString(titleInt);
                int money = cursor.getInt(moneyInt);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    items.add(new SubRecyclerItem(date, LocalTime.parse(postTime.split(" ")[1]), title, money));
                }
            }
            /*
            String result = postTime + "||" + bankName + "||" + accountNumber + "||" + title + "||" + type + "||" + money + "||" + detail;
            line += count + "번 : " + result + "\n";
            Log.e("DB : ", result);
            count++;
            */
        }
    }

    public class MainRecyclerItem{
        String day;
        String title;
        String getDay(){
            return this.day;
        }
        String getTitle(){
            return this.title;
        }
        public MainRecyclerItem(String day, String title){
            this.day = day;
            this.title = title;
        }
    }

    public class SubRecyclerItem{
        String day;
        LocalTime time;
        String title;
        double money;
        String getDay(){
            return this.day;
        }
        LocalTime getTime(){return this.time;}
        String getTitle(){
            return this.title;
        }
        double getMoney(){
            return this.money;
        }
        public SubRecyclerItem(String day, LocalTime time, String title, double money){
            this.day = day;
            this.time = time;
            this.title = title;
            this.money = money;
        }
    }
}
