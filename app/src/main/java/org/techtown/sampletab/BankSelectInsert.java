package org.techtown.sampletab;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// MyNotificationListener에서 사용하는 클래스
// 은행마다 오는 메시지가 서로 다르기 때문에 데이터베이스에 Insert하는 부분을 다르게 설정해 주어야한다.

/*
    이름           어플 패키지명
    일반 메시지      android.messaging
    KB국민은행      kbstar.kbbank O
    농협(NH)        nh.mobilenoti O
    IBK기업은행      ibk.android.ionebank O
    카카오뱅크       kakaobank.channel O
    케이뱅크        kbankwith.smartbank O
*/

public class BankSelectInsert {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DecimalFormat decFormat = new DecimalFormat("###,###");
    Context context;
    NotificationMessages messages;

    public BankSelectInsert(StatusBarNotification sbn, Context context) throws NullPointerException{
        this.context = context;

        String packageName = sbn.getPackageName();
        try{
            if(packageName.contains("android.messaging")){
                messaging(sbn);
            }else if(packageName.contains("kbstar.kbbank")){
                KBInsert(sbn);
            }else if(packageName.contains("nh.mobilenoti")){
                NHInsert(sbn);
            }else if(packageName.contains("ibk.android.ionebank")){
                IBKInsert(sbn);
            }else if(packageName.contains("kakaobank.channel")){
                KaKaOInsert(sbn);
            }else if(packageName.contains("kbankwith.smartbank")){
                KBankInsert(sbn);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // KBank에서 오는 알림 데이터베이스에 Insert 해주는 함수
    private void KBankInsert(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String postTime = format.format(new Date().getTime());
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";

        String bank = extras.getString(Notification.EXTRA_TITLE);
        String[] line = text.split("\\n");
        int money = Integer.parseInt(line[0].split(" ")[1].replace("원", "").replace(",",""));
        String title = line[1].split(" ")[0];
        String account = line[1].split(" ")[2].replace("MY입출금통장(", "").replace(")", "");

        if(line[0].split(" ")[0].contains("출금")){
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(line[0].split(" ")[0].contains("입금")){
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
   }

    // KaKaO에서 오는 알림 데이터베이스에 Insert 해주는 함수
    private void KaKaOInsert(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String postTime = format.format(new Date().getTime());
        String titleNoti = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";
        String bank = "카카오뱅크";
        if(titleNoti.contains("출금")){ // output 테이블에 추가
            String[] line = text.split(" ");
            String title = line[3];
            String account = line[1].replace("입출금통장(", "").replace(")", "");
            int money = Integer.parseInt(titleNoti.split(" ")[1].replace("원", "").replace(",",""));
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(titleNoti.contains("입금")){ // input 테이블에 추가
            String[] line = text.split(" ");
            String title = line[0];
            String account = line[3].replace("입출금통장(", "").replace(")", "");
            int money = Integer.parseInt(titleNoti.split(" ")[1].replace("원", "").replace(",",""));
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
    }

    // IBK에서 오는 알림 데이터베이스에 Insert 해주는 함수
    private void IBKInsert(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String postTime = format.format(new Date().getTime());
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";


        String[] line = text.split("\\n");
        String[] a = line[0].split(" ");
        String bank = "IBK국민은행";
        String account = line[1];
        String title = a[2];
        int money = Integer.parseInt(a[1].replace("원", "").replace(",",""));

        if(a[0].contains("출금")){ // output 테이블에 추가
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(a[0].contains("입금")){ // input 테이블에 추가
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
    }

    // KB에서 오는 알림 데이터베이스에 Insert 해주는 함수
    private void KBInsert(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String postTime = format.format(new Date().getTime());
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";

        String[] line = text.split(" ");

        String title = line[4];
        String bank = "KB국민은행";
        String account = line[3];
        int money = Integer.parseInt(line[6].replace(",",""));

        if(line[5].contains("출금")) { // output 테이블에 추가
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(line[5].contains("입금")){ // input 테이블에 추가
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
    }

    private void messaging(StatusBarNotification sbn){

    }
    
    // NH(농협)에서 오는 알림 데이터베이스에 Insert 해주는 함수
    private void NHInsert(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String postTime = format.format(new Date().getTime());
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";

        String[] line = text.split("\\n");
        String[] a = line[0].split(" ");
        String[] b = line[1].split(" ");
        String title = b[3];
        String bank = a[0];
        String account = b[2];

        if(line[0].contains("출금")) {
            int money = Integer.parseInt(a[1].replace("출금", "").replace("원", "").replace(",",""));
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText); // output 테이블에 데이터를 저장
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money)); // 상단에 알림을 보내주는 클래스
            command.selectCount();
        }else if(line[0].contains("입금")){
            int money = Integer.parseInt(a[1].replace("입금", "").replace("원", "").replace(",",""));
            DBcommand command = new DBcommand(context);
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
    }
}
