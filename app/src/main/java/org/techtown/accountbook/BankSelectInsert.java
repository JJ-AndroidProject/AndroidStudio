package org.techtown.accountbook;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import org.w3c.dom.Text;

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
    DBcommand command;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DecimalFormat decFormat = new DecimalFormat("###,###");
    Context context;
    NotificationMessages messages;

    public BankSelectInsert(StatusBarNotification sbn, Context context) throws NullPointerException{
        this.context = context;
        command = new DBcommand(context);
        String packageName = sbn.getPackageName();
        try{
            if(packageName.contains("messaging")){
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

    // MainActivity로 인텐트를 보내는 함수
    private void sendToActivity(Context context, int flag){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent에 담을 데이터의 키 값과 데이터
        intent.putExtra("flag", flag);
        //Log.e("SendToActivity", line);
        context.startActivity(intent); // Intent에 데이터를 담은 뒤 Activity에 보낸다.
        //Toast.makeText(context, "SendToActivity", Toast.LENGTH_SHORT).show();
    }
/*
농협 출금13500원
12/09 15:14 111-****-1111-11 김종원 잔액230,356원

농협 입금13500원
12/09 15:14 111-****-1111-11 김종원 잔액230,356원
*/
    // 안드로이드 스튜디오 메시지 테스트 용도
    private void messaging(StatusBarNotification sbn){
        Bundle extras = sbn.getNotification().extras;
        String postTime = format.format(new Date().getTime());
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";

        String[] line = text.split("\\n");
        String[] a = line[0].split(" ");
        String[] b = line[1].split(" ");
        String bank = a[0];
        int money = Integer.parseInt(a[1].replaceAll("[^0-9]", ""));
        String account = b[2];
        String title = "";
        for(int i=3;i<b.length-1;i++) {
            if (i == 3) title = title + b[i];
            else title = title + " " + b[i];
        }
        messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
        if(text.contains("출금")){
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText); // output 테이블에 데이터를 저장
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money)); // 상단에 알림을 보내주는 클래스
            command.selectCount();
            sendToActivity(context, 1); // MainActivity로 신호를 보내는 함수
            Log.e("Message", bank+"(출금) : "+title+" "+account+" "+decFormat.format(money));
        }else if(text.contains("입금")){
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
            sendToActivity(context, 1); // MainActivity로 신호를 보내는 함수
            Log.e("Message", bank+"(입금) : "+title+" "+account+" "+decFormat.format(money));
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
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(line[0].split(" ")[0].contains("입금")){
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
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(titleNoti.contains("입금")){ // input 테이블에 추가
            String[] line = text.split(" ");
            String title = line[0];
            String account = line[3].replace("입출금통장(", "").replace(")", "");
            int money = Integer.parseInt(titleNoti.split(" ")[1].replace("원", "").replace(",",""));
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
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(a[0].contains("입금")){ // input 테이블에 추가
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
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money));
            command.selectCount();
        }else if(line[5].contains("입금")){ // input 테이블에 추가
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
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


        int money = Integer.parseInt(a[1].replaceAll("[^0-9]", ""));

        if(line[0].contains("출금")) {
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataOutput(postTime, bank, account, title, "미정", money, subText); // output 테이블에 데이터를 저장
            messages = new NotificationMessages(context, title, bank+"(출금) : "+decFormat.format(money)); // 상단에 알림을 보내주는 클래스
            command.selectCount();
        }else if(line[0].contains("입금")){
            //PostTime, BankName, AccountNumber, Title, Type, Money, Detail
            command.insertDataInput(postTime, bank, account, title, "미정", money, subText);
            messages = new NotificationMessages(context, title, bank+"(입금) : "+decFormat.format(money));
            command.selectCount();
        }
    }
}
