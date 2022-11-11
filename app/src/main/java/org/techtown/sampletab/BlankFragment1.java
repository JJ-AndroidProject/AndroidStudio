package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
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
        int startday = PreferenceManager.getInt(getContext(), "startDayKey");
        int i = startday;
        while(true){
            if(i > lastday){
                i = 1;  monthtmp++;
            }
            if(i == startday) flag++;
            if(flag > 1) break;
            if(monthtmp > 11){
                monthtmp = 0;   yeartmp++;
            }
            String str = yeartmp + "." + (monthtmp + 1) + "." + i;
            String setDay = yeartmp + "-" + (monthtmp + 1) + "-" + i;
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
            dbSelectOutput();
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
                int startday = PreferenceManager.getInt(getContext(), "startDayKey");
                int i = startday;
                while(true){
                    if(i > lastday){
                        i = 1;  monthtmp++;
                    }
                    if(i == startday) flag++;
                    if(flag > 1) break;
                    if(monthtmp > 11){
                        monthtmp = 0;   yeartmp++;
                    }
                    String str = yeartmp + "." + (monthtmp + 1) + "." + i;
                    String setDay = yeartmp + "-" + (monthtmp + 1) + "-" + i;
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
                    dbSelectOutput();
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
                int startday = PreferenceManager.getInt(getContext(), "startDayKey");
                int i = startday;
                while(true){
                    if(i > lastday){
                        i = 1;  monthtmp++;
                    }
                    if(i == startday) flag++;
                    if(flag > 1) break;
                    if(monthtmp > 11){
                        monthtmp = 0;   yeartmp++;
                    }
                    String str = yeartmp + "." + (monthtmp + 1) + "." + i;
                    String setDay = yeartmp + "-" + (monthtmp + 1) + "-" + i;
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
                    dbSelectOutput();
                    adapter = new Adapter(getActivity(), list, items);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        //직접 추가 버튼&리스너
        FloatingActionButton btn_direct_add = viewGroup.findViewById(R.id.btnDirectAdd);
        btn_direct_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //여기에 입력 다이얼로그 생성

                View dlgView = View.inflate(getContext(), R.layout.direct_add_dialog, null);

                EditText posttime = dlgView.findViewById(R.id.ex_add1);
                EditText bankname = dlgView.findViewById(R.id.ex_add2);
                EditText money = dlgView.findViewById(R.id.ex_add3);
                EditText detail = dlgView.findViewById(R.id.ex_add4);

                AlertDialog.Builder daDialog = new AlertDialog.Builder(getContext());
                daDialog.setTitle("지출 내역 추가");
                daDialog.setView(dlgView);

                //확인버튼 클릭 시
                daDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //null값 허용은 accountnomber, detail.
                        //title, type을 ""로 받을 것. 이 둘은 notnull
                        try {
                            int intmoney;
                            //입력한 값 받아오기
                            String strposttime = posttime.getText().toString();
                            String strbankname = bankname.getText().toString();
                            String strmoney = money.getText().toString();
                            String strdetail = detail.getText().toString();

                            intmoney = Integer.parseInt(strmoney);  //입력받은 금액 INT형으로 변환
                            Toast.makeText(getContext(), strposttime + strbankname + intmoney + strdetail, LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getContext(), "취소됨", LENGTH_SHORT).show();
                        }

                    }
                });

                //취소버튼 클릭 시
                daDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "취소", LENGTH_SHORT).show();
                    }
                });
                daDialog.show();
            }
        });
        return viewGroup;
    }

    void dbSelectOutput() throws ParseException {

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
        Log.e("Cursor", "Cursor : "+cursor.getCount()+"개");
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
                String title = cursor.getString(titleInt);
                int money = cursor.getInt(moneyInt);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    items.add(new SubRecyclerItem(date, LocalTime.parse(postTime.split(" ")[1]), title, money));
                }
            }
        }
    }

    void dbSelectInput() throws ParseException {

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
        Cursor cursor = dbOpenHelper.selectColumnsInput();
        Log.e("Cursor", "Cursor : "+cursor.getCount()+"개");
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
                String title = cursor.getString(titleInt);
                int money = cursor.getInt(moneyInt);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    items.add(new SubRecyclerItem(date, LocalTime.parse(postTime.split(" ")[1]), title, money));
                }
            }
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
