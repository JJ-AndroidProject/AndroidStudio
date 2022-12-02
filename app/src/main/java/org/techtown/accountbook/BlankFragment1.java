package org.techtown.accountbook;

import static android.widget.Toast.LENGTH_SHORT;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class BlankFragment1 extends Fragment implements OnRefresh {
    DecimalFormat decFormat = new DecimalFormat("###,###");
    private int moneyTotal = 0;
    Calendar cal = Calendar.getInstance(); //현재 연도, 달, 그 달의 마지막 날짜를 받는다.
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int lastday = cal.getActualMaximum(Calendar.DATE);
    int today = cal.get(Calendar.DATE);
    String date = year + "." + (month+1);
    Button btn_before, btn_after;
    TextView datetext;
    TextView textTotal;
    TextView textMonth;
    RecyclerView recyclerView;
    Adapter adapter;
    private List<MainRecyclerItem> list = new ArrayList<MainRecyclerItem>();
    private List<SubRecyclerItem> items = new ArrayList<SubRecyclerItem>();


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
        textTotal = (TextView) viewGroup.findViewById(R.id.textOutputTotal);
        textMonth = (TextView) viewGroup.findViewById(R.id.textOutputMonth);
        btn_after = (Button) viewGroup.findViewById(R.id.btnafter);
        textTotal.setText("총 "+decFormat.format(moneyTotal)+"원");
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
        showDataBase(); // Adapter에 아이템을 넣어주는 함수
        Collections.reverse(list); // 리스트를 내림차순으로 만들어준다.

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
                showDataBase(); // Adapter에 아이템을 넣어주는 함수
                Collections.reverse(list); // 리스트를 내림차순으로 만들어준다.
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
                showDataBase(); // Adapter에 아이템을 넣어주는 함수
                Collections.reverse(list); // 리스트를 내림차순으로 만들어준다.
            }
        });

        //직접 추가 버튼&리스너
        FloatingActionButton btn_direct_add = viewGroup.findViewById(R.id.btnDirectAdd);
        btn_direct_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //여기부터 다이얼로그 생성
                View dlgView = View.inflate(getContext(), R.layout.direct_add_dialog, null);

                TextView addDate = dlgView.findViewById(R.id.add_date);    //날짜
                TextView addTime = dlgView.findViewById(R.id.add_time);    //시간
                TextView bankname = dlgView.findViewById(R.id.add_bankname);     //결제수단
                EditText title = dlgView.findViewById(R.id.add_title);       //결제내역
                EditText money = dlgView.findViewById(R.id.add_money);        //금액
                EditText detail = dlgView.findViewById(R.id.add_detail);       //메모

                AlertDialog.Builder daDialog = new AlertDialog.Builder(getContext());
                daDialog.setTitle("지출 내역 추가");
                daDialog.setView(dlgView);
                AlertDialog da = daDialog.create();    // 확인, 취소 클릭 시 다이얼로그를 종료(da.dismiss)시키기 위해 생성
                //현재 날짜, 시간을 디폴트 값으로 설정.
                addDate.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + today);    //날짜 디폴트 값을 당일로 설정

                LocalTime now = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    now = LocalTime.now();
                    int hour = now.getHour();
                    int minute = now.getMinute();
                    addTime.setText(hour + ":" + minute);   //시간 디폴트 값을 현재 시간으로 설정
                }

                //날짜 선택 시 달력에서 날짜 선택할 수 있게 함
                addDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //데이트피커 다이얼로그 리스너
                        DatePickerDialog.OnDateSetListener myDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                                addDate.setText(String.format("%d-%d-%d", yy, mm+1, dd)); // xxxx-xx-xx 형태로 표기&저장
                            }
                        };
                        //날짜 표기된 텍스트뷰를 클릭하면 날짜 선택 다이얼로그를 띄워줌
                        new DatePickerDialog(getContext(), myDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), today).show();
                    }
                });

                //시간 선택 시 스피너로 시간 선택할 수 있게 함
                addTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //타임피커 다이얼로그 리스너
                        TimePickerDialog.OnTimeSetListener myTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int h, int m) {
                                addTime.setText(h + ":" + m);   // xx:xx (시간:분) 형태로 표기&저장
                            }
                        };

                        LocalTime now = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            now = LocalTime.now();
                            int hour = now.getHour();
                            int minute = now.getMinute();

                            //시간 표기된 텍스트뷰를 클릭하면 시간 선택 다이얼로그를 띄워줌
                            TimePickerDialog picker = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_NoActionBar,
                                    myTimeSetListener, hour, minute, true);
                            picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            picker.show();
                        }
                    }
                });

                bankname.setText("현금");     //디폴트 값

                //결제수단 클릭해서 이미지를 고르면 해당 결제수단으로 입력받음
                bankname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 결제수단 다이얼로그 생성
                        View bsdlgView = View.inflate(getContext(), R.layout.bank_select_dialog, null);
                        AlertDialog.Builder bsDialog = new AlertDialog.Builder(getContext());
                        bsDialog.setView(bsdlgView);

                        AlertDialog ad = bsDialog.create();     //이미지 클릭 시 다이얼로그를 종료(ad.dismiss)시키기 위해 생성
                        ImageButton btn_cash = (ImageButton) bsdlgView.findViewById(R.id.cash);     //현금
                        ImageButton btn_kb = (ImageButton) bsdlgView.findViewById(R.id.kbbank);     //kb국민은행
                        ImageButton btn_nh = (ImageButton) bsdlgView.findViewById(R.id.nhbank);     //농협
                        ImageButton btn_ibk = (ImageButton) bsdlgView.findViewById(R.id.ibkbank);   //ibk기업은행
                        ImageButton btn_kakao = (ImageButton) bsdlgView.findViewById(R.id.kakaobank);//카카오뱅크
                        ImageButton btn_k = (ImageButton) bsdlgView.findViewById(R.id.kbank);       //케이뱅크

                        btn_cash.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bankname.setText("현금");
                                ad.dismiss();
                            }
                        });
                        btn_kb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bankname.setText("KB국민은행");
                                ad.dismiss();
                            }
                        });
                        btn_nh.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bankname.setText("농협(NH)");
                                ad.dismiss();
                            }
                        });
                        btn_ibk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bankname.setText("IBK기업은행");
                                ad.dismiss();
                            }
                        });
                        btn_kakao.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bankname.setText("카카오뱅크");
                                ad.dismiss();
                            }
                        });
                        btn_k.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bankname.setText("케이뱅크");
                                ad.dismiss();
                            }
                        });
                        ad.show();
                    }

                });


                Button btndlgextra = (Button) dlgView.findViewById(R.id.btn_dlg_extra);
                btndlgextra.setText("계속");
                Button btndlgneg = (Button) dlgView.findViewById(R.id.btn_dlg_neg);
                Button btndlgpos = (Button) dlgView.findViewById(R.id.btn_dlg_pos);

                //추가 기능 버튼 리스너. 다이얼로그를 종료하지 않고 계속 입력하는 기능으로 구현해봄
                btndlgextra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //null값 허용은 accountnomber, detail.
                        //title, type을 ""로 받을 것. 이 둘은 notnull
                        try {
                            int intmoney;
                            //입력한 값 받아오기
                            String strDate = addDate.getText().toString();      //날짜
                            String strTime = addTime.getText().toString();      //시간
                            String strposttime = (strDate+" "+strTime + ":00");// xxxx-xx-xx xx:xx:00 형태. 데이터베이스 저장용
                            String strbankname = bankname.getText().toString(); //결제수단
                            String strtitle = title.getText().toString();       //결제내역
                            String strmoney = money.getText().toString();       //금액
                            intmoney = Integer.parseInt(strmoney);  //입력받은 금액 INT형으로 변환
                            String strdetail = detail.getText().toString();     //메모

                            String postTime = format.format(format.parse(strposttime));

                            DBcommand command = new DBcommand(getContext());
                            command.insertDataOutput(postTime, strbankname, null, strtitle, "미정", intmoney, strdetail);
                            showDataBase(); // Adapter에 아이템을 넣어주는 함수

                            //다이얼로그에서 결제내역, 금액, 메모를 초기화해줌
                            title.setText(null);
                            money.setText(null);
                            detail.setText(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "취소됨", LENGTH_SHORT).show();   //오류 발생 시
                        }

                    }
                });

                //취소 버튼 리스너
                btndlgneg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "취소", LENGTH_SHORT).show();
                        da.dismiss();   //다이얼로그 종료
                    }
                });

                //확인 버튼 리스너
                btndlgpos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //null값 허용은 accountnomber, detail.
                        //title, type을 ""로 받을 것. 이 둘은 notnull
                        try {
                            int intmoney;
                            //입력한 값 받아오기
                            String strDate = addDate.getText().toString();      //날짜
                            String strTime = addTime.getText().toString();      //시간
                            String strposttime = (strDate+" "+strTime + ":00");// xxxx-xx-xx xx:xx:00 형태. 데이터베이스 저장용
                            String strbankname = bankname.getText().toString(); //결제수단
                            String strtitle = title.getText().toString();
                            String strmoney = money.getText().toString();       //금액
                            intmoney = Integer.parseInt(strmoney);  //입력받은 금액 INT형으로 변환
                            String strdetail = detail.getText().toString();     //메모

                            String postTime = format.format(format.parse(strposttime));

                            DBcommand command = new DBcommand(getContext());
                            command.insertDataOutput(postTime, strbankname, null, strtitle, "미정", intmoney, strdetail);
                            showDataBase(); // Adapter에 아이템을 넣어주는 함수
                            da.dismiss();   //다이얼로그 종료
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "취소됨", LENGTH_SHORT).show();   //오류 발생 시
                        }
                    }
                });
                //다이얼로그 보여주기
                da.show();
                //여기까지
            }
        });
        return viewGroup;
    }

    void dbSelectOutput() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int monthtmp = month;
        int yeartmp = year;
        int startday = PreferenceManager.getInt(getContext(), "startDayKey")-1;
        String startLine = yeartmp+"-"+(monthtmp+1)+"-"+(startday+1);
        String lastLine = yeartmp+"-"+(monthtmp+2)+"-"+(startday+1);
        String start = format.format(format.parse(startLine));
        String last = format.format(format.parse(lastLine));
        String lastLine2 = yeartmp+"-"+(monthtmp+2)+"-"+(startday);
        String last2 = format.format(format.parse(lastLine2));
        textMonth.setText(start+"~"+last2);
        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.getContext());
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsOutput();
        items.clear();
        moneyTotal = 0;
        while(cursor.moveToNext()) {
            int idInt = cursor.getColumnIndex("id");
            int postTimeInt = cursor.getColumnIndex("posttime");
            int id = cursor.getInt(idInt);
            String postTime = cursor.getString(postTimeInt);
            String date = format.format(format.parse(postTime));
            if(start.compareTo(date) <= 0 && last.compareTo(date) > 0) {
                int titleInt = cursor.getColumnIndex("title");
                int moneyInt = cursor.getColumnIndex("money");
                int bankInt = cursor.getColumnIndex("bankname");
                int moveInt = cursor.getColumnIndex("move");
                String bank = cursor.getString(bankInt);
                String title = cursor.getString(titleInt);
                int money = cursor.getInt(moneyInt);

                if(cursor.getString(moveInt) != null) {
                    String move = cursor.getString(moveInt);
                    if (move.equals("계좌이동")) {
                        Log.e("BlankFragment2", "계좌이동");
                        bank = "계좌이동";
                    }
                }else moneyTotal += money;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    items.add(new SubRecyclerItem(id, date, LocalTime.parse(postTime.split(" ")[1]), bank, title, money));
                }
            }
        }
    }

    void showDataBase(){
        try{
            dbSelectOutput();
            textTotal.setText("총 "+decFormat.format(moneyTotal)+"원");
            adapter = new Adapter(getActivity(), list, items, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // 화면을 갱신해주는 함수
    @Override
    public void refresh(int position) {
        showDataBase();
        recyclerView.scrollToPosition(position);
        Log.e("BlankFragment1", "BlankFragment1 refresh()");
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
        int id;
        String day;
        LocalTime time;
        String bank;
        String title;
        double money;
        String getDay(){
            return this.day;
        }
        LocalTime getTime(){return this.time;}
        String getBank(){
            return this.bank;
        }
        String getTitle(){
            return this.title;
        }
        double getMoney(){
            return this.money;
        }
        public SubRecyclerItem(int id, String day, LocalTime time, String bank, String title, double money){
            this.id = id;
            this.day = day;
            this.time = time;
            this.bank = bank;
            this.title = title;
            this.money = money;
        }
    }
}
