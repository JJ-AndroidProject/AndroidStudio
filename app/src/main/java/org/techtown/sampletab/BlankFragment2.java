package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 수입 부분에 대한 어댑터 생성 필요

public class BlankFragment2 extends Fragment {
    //현재 연도, 달, 그 달의 마지막 날짜를 받는다.
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int lastday = cal.getActualMaximum(Calendar.DATE);
    int today = cal.get(Calendar.DATE);
    String date = year + "." + (month+1);
    Button btn_before, btn_after;
    TextView datetext;
    RecyclerView recyclerView;
    Fragment2Adapter adapter;
    DBcommand command;

    private List<BlankFragment2.SubRecyclerItem> items = new ArrayList<BlankFragment2.SubRecyclerItem>();

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
        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.recyclerView2);
        try{
            dbSelectInput();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        adapter = new Fragment2Adapter(items);
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
                try{
                    dbSelectInput();
                    adapter = new Fragment2Adapter(items);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("BlankFragment2", "before가 눌렸습니다");
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
                try{
                    dbSelectInput();
                    adapter = new Fragment2Adapter(items);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.d("BlankFragment2", "after가 눌렸습니다");
            }

        });

        //직접 추가 버튼&리스너
        FloatingActionButton btn_direct_add = viewGroup.findViewById(R.id.btnDirectAdd);
        btn_direct_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //여기부터

                //다이얼로그 생성
                View dlgView = View.inflate(getContext(), R.layout.direct_add_dialog, null);

                TextView addDate = dlgView.findViewById(R.id.add_date);    //날짜
                TextView addTime = dlgView.findViewById(R.id.add_time);    //시간
                TextView bankname = dlgView.findViewById(R.id.add_bankname);     //결제수단
                EditText title = dlgView.findViewById(R.id.add_title);       //결제내역
                EditText money = dlgView.findViewById(R.id.add_money);        //금액
                EditText detail = dlgView.findViewById(R.id.add_detail);       //메모

                AlertDialog.Builder daDialog = new AlertDialog.Builder(getContext());
                daDialog.setTitle("수입 내역 추가");
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
                Button btndlgneg = (Button) dlgView.findViewById(R.id.btn_dlg_neg);
                Button btndlgpos = (Button) dlgView.findViewById(R.id.btn_dlg_pos);

                //추가 기능 버튼 리스너.
                btndlgextra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                            String strposttime = (strDate + " " + strTime + ":00");// xxxx-xx-xx xx:xx:00 형태. 데이터베이스 저장용
                            String strbankname = bankname.getText().toString(); //결제내역
                            String strtitle = title.getText().toString();       //결제내역
                            String strmoney = money.getText().toString();       //금액
                            intmoney = Integer.parseInt(strmoney);  //입력받은 금액 INT형으로 변환
                            String strdetail = detail.getText().toString();     //메모

                            String postTime = format.format(format.parse(strposttime));

                            command = new DBcommand(getContext());
                            command.insertDataInput(postTime, strbankname, null, strtitle, "미정", intmoney, strdetail);

                            //수입 리스트 갱신해줌
                            try{
                                dbSelectInput();
                                adapter = new Fragment2Adapter(items);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                recyclerView.setAdapter(adapter);
                            }catch (ParseException e) {
                                e.printStackTrace();
                            }
                            da.dismiss();
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
        //Log.e("TEST", "start : "+start+" last : "+last);

        DBOpenHelper dbOpenHelper = new DBOpenHelper(this.getContext());
        dbOpenHelper.open();
        dbOpenHelper.create();
        Cursor cursor = dbOpenHelper.selectColumnsInput();
        //Log.e("Cursor", "Cursor : "+cursor.getCount()+"개");
        int count = 1;
        items.clear();
        while(cursor.moveToNext()) {
            int postTimeInt = cursor.getColumnIndex("posttime");
            String postTime = cursor.getString(postTimeInt);
            String date = format.format(format.parse(postTime));
            if(start.compareTo(date) <= 0 && last.compareTo(date) >= 0) {
                //Log.e("TEST", "postTime : "+postTime+" PostTime이 start보다 크다");
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
