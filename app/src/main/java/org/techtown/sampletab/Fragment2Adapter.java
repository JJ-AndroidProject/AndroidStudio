package org.techtown.sampletab;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fragment2Adapter extends RecyclerView.Adapter<Fragment2Adapter.ViewHolder> {
    Context context;
    private List<BlankFragment2.SubRecyclerItem> items;

    public Fragment2Adapter(List<BlankFragment2.SubRecyclerItem> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decFormat = new DecimalFormat("###,###"); // 3자리마다 콤마를 찍어주는 포맷
        SimpleDateFormat reset = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        try {
            Date date = reset.parse(items.get(position).day);
            holder.timeText.setText(format.format(date));
            holder.titleText.setText(items.get(position).title);
            holder.moneyText.setText(decFormat.format((int)items.get(position).money)+"원");

            holder.titleText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, items.get(position).day + items.get(position).time + items.get(position).title, Toast.LENGTH_SHORT).show();

                    //다이얼로그 생성
                    View dlgView = View.inflate(context, R.layout.direct_add_dialog, null);

                    TextView addDate = dlgView.findViewById(R.id.add_date);    //날짜
                    TextView addTime = dlgView.findViewById(R.id.add_time);    //시간
                    TextView bankname = dlgView.findViewById(R.id.add_bankname);     //결제수단
                    EditText title = dlgView.findViewById(R.id.add_title);       //결제내역
                    EditText money = dlgView.findViewById(R.id.add_money);        //금액
                    EditText detail = dlgView.findViewById(R.id.add_detail);       //메모

                    AlertDialog.Builder daDialog = new AlertDialog.Builder(context);
                    daDialog.setTitle("수입 내역 수정");
                    daDialog.setView(dlgView);
                    AlertDialog da = daDialog.create();    // 확인, 취소 클릭 시 다이얼로그를 종료(da.dismiss)시키기 위해 생성
                    //현재 날짜, 시간을 디폴트 값으로 설정.
                    addDate.setText(items.get(position).day);    //날짜 디폴트 값을 당일로 설정

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
                            String date = items.get(position).day;
                            String divdate[] = date.split("-");

                            //날짜 표기된 텍스트뷰를 클릭하면 날짜 선택 다이얼로그를 띄워줌
                            new DatePickerDialog(context, myDateSetListener,
                                    Integer.parseInt(divdate[0]), Integer.parseInt(divdate[1]), Integer.parseInt(divdate[2])).show();
                        }
                    });

                    String strTime = ("12:10:20");      //시간 넣어주세용
                    addTime.setText(strTime);

                    String[] divtime = strTime.split(":");
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

                                //시간 표기된 텍스트뷰를 클릭하면 시간 선택 다이얼로그를 띄워줌
                            TimePickerDialog picker = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_NoActionBar, myTimeSetListener,
                                    Integer.parseInt(divtime[0]), Integer.parseInt(divtime[1]), true);
                            picker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            picker.show();

                        }
                    });

                    String bankName = ("현금");       //결제수단 넣어주세용
                    bankname.setText(bankName);

                    //결제수단 클릭해서 이미지를 고르면 해당 결제수단으로 입력받음
                    bankname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 결제수단 다이얼로그 생성
                            View bsdlgView = View.inflate(context, R.layout.bank_select_dialog, null);
                            AlertDialog.Builder bsDialog = new AlertDialog.Builder(context);
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


                    title.setText(items.get(position).title);    //결제내역
                    money.setText(decFormat.format((int)items.get(position).money));

                    String strdetail = ("");       //메모 넣어주세용
                    detail.setText(strdetail);

                    Button btndlgdelete = (Button) dlgView.findViewById(R.id.btn_dlg_extra);
                    Button btndlgneg = (Button) dlgView.findViewById(R.id.btn_dlg_neg);
                    Button btndlgpos = (Button) dlgView.findViewById(R.id.btn_dlg_pos);

                    //삭제 버튼 리스너.
                    btndlgdelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*
                                여기에 데이터 삭제 기능 넣어주시면 됩니다
                            */
                            da.dismiss();   //다이얼로그 종료
                        }
                    });

                    //취소 버튼 리스너
                    btndlgneg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(context, "취소", LENGTH_SHORT).show();
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

                                //이부분을 입력이 아니라 수정으로 바꿔주시면 되겠습니다
                                DBcommand command = new DBcommand(context);
                                command.insertDataOutput(postTime, strbankname, null, strtitle, "미정", intmoney, strdetail);

                                //지출 리스트 갱신해줌

                                da.dismiss();   //다이얼로그 종료
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(context, "취소됨", LENGTH_SHORT).show();   //오류 발생 시
                            }
                        }
                    });

                    //다이얼로그 보여주기
                    da.show();

                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeText;
        TextView titleText;
        TextView moneyText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.SubTimeText);
            titleText = itemView.findViewById(R.id.SubTitleText);
            moneyText = itemView.findViewById(R.id.SubMoneyText);
        }
    }
}





