package org.techtown.sampletab;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MyNotificationListener extends NotificationListenerService {
    public final static String TAG = "MyNotificationListener";


    // 상단에 표시되어 있는 알림을 지울때 작동이 됩니다.
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    // 상단의 알림이 오면 작업을 시작
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        if(findText(sbn) == true){
            //fileSave(sbn);
            //fileRead();
            dbInsert(sbn);
        }

        /*
        if(findText(sbn)){ // targetList에 있는 단어가 title, text, subText에 있다면 내용을 저장한다.

        };
        */
        /*
        String tag = "onNotificationPosted";
        Notification notification = sbn.getNotification();
        Bundle extras = sbn.getNotification().extras;
        Log.d(tag, "onNotificationPosted가 작동됨");
        Log.d(tag, "packageName : "+sbn.getPackageName());
        Log.d(tag, "id : "+sbn.getId());
        Log.d(tag, "postTime : "+sbn.getPostTime());
        Log.d(tag, "title : "+extras.getString(Notification.EXTRA_TITLE));
        Log.d(tag, "text : "+extras.getCharSequence(Notification.EXTRA_TEXT));
        Log.d(tag, "subText : "+extras.getCharSequence(Notification.EXTRA_SUB_TEXT));
        Toast.makeText(this, "onNotificationPosted call("+sbn.getPackageName()+")", Toast.LENGTH_SHORT).show();
        String line = sbn.getPackageName()+"\n"+sbn.getId()+"\n"+sbn.getPostTime()+"\n"+extras.getString(Notification.EXTRA_TITLE)+"\n"+extras.getCharSequence(Notification.EXTRA_TEXT);
        */
    }


    // 테스트용 데이터베이스 insert
    void dbInsert(StatusBarNotification sbn){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Bundle extras = sbn.getNotification().extras;
        String packageName = sbn.getPackageName();
        String postTime = format.format(new Date().getTime());
        String title = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";
        DBcommand command = new DBcommand(this);
        command.insertDataOutput(postTime, packageName, "xxx-xxxx-xxxx-xx", title, "미정", 0, text);
    }

    // NH에서 오는 알림에 대한 insert
    void NHInsert(StatusBarNotification sbn){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Bundle extras = sbn.getNotification().extras;
        String packageName = sbn.getPackageName();
        String postTime = format.format(new Date().getTime());
        String title = extras.getString(Notification.EXTRA_TITLE);
        String text = extras.getCharSequence(Notification.EXTRA_TEXT)+"";
        String subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"";

        String[] line = text.split(" ");
        String bankName = line[0];
        int money = -1;
        if(line[1].contains("출금")){
            money = Integer.parseInt(line[1].replace("출금", "").replace("원", ""));
        }
        DBcommand command = new DBcommand(this);
        command.insertDataOutput(postTime, line[0], line[4], line[5], "미정", money, subText);
    }

    // targetList에 있는 단어가 title, text, subText에 있다면 내용을 저장한다.
    // messaging은 TITLE(전화번호로 분류)
    // 지원하는 은행 : 농협(NH), KB국민은행, IBK기업은행, 카카오뱅크, 토스, 우리은행, 케이뱅크 지원 예정
    /*
    이름           어플 패키지명
    일반 메시지      android.messaging
    KB국민은행      kbstar.kbbank
    농협(NH)        nh.mobilenoti
    IBK기업은행      ibk.android.ionebank
    카카오뱅크       kakaobank.channel
    케이뱅크        kbankwith.smartbank

    우리은행
    토스
    */
    // 메시지를 거르는 역활
    private boolean findText(StatusBarNotification sbn){
        ArrayList<String> targetList
                = new ArrayList<>(Arrays.asList("출금", "입금"));
        ArrayList<String> bank = new ArrayList<>(Arrays.asList(
                "kbstar.kbbank", "nh.mobilenoti", "android.messaging", "kbankwith.smartbank", "ibk.android.ionebank", "kakaobank.channel"));
        try{
            Notification notification = sbn.getNotification();
            Bundle extras = sbn.getNotification().extras;
            for(String t : bank){
                if(sbn.getPackageName().contains(t)){
                    for(String target : targetList){
                        String title = extras.getString(Notification.EXTRA_TITLE);
                        String text = extras.getString(Notification.EXTRA_TEXT);
                        String subText = extras.getString(Notification.EXTRA_SUB_TEXT);
                        if(title.contains(target) || text.contains(target) || subText.contains(target)) {
                            Log.e(TAG, "Notification findText 작동");
                            return true;
                        }
                    }
                }
            }
        }catch(NullPointerException e){
            Log.e(TAG, "NullPointerException Catch"); // 값이 null 일 때 발생하는 오류를 catch 한다.
            return false;
        }
        return false;
    }
    
    // 파일 저장
    private void fileSave(StatusBarNotification sbn){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // date 변수의 형식
        Bundle extras = sbn.getNotification().extras;
        FileWriter writer;
        try{
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyData";
            File dir = new File(dirPath);
            Log.d("fileSave", "dirPath : "+dir.toString()+"\n");
            if(!dir.exists()) dir.mkdir(); // /MyData 폴더가 없으면 생성
            File file = new File(dir+"/data.txt");
            if(!file.exists()) file.createNewFile(); // data.txt 파일이 없으면 생성
            writer = new FileWriter(file,true);
            writer.write("======================"+"\n");
            writer.write("PackageName : "+sbn.getPackageName()+"\n");
            writer.write("Id : "+sbn.getId()+"\n");
            writer.write("PostTime : "+format.format(new Date().getTime())+"\n");
            writer.write("Title : "+extras.getString(Notification.EXTRA_TITLE)+"\n");
            writer.write("Text : "+extras.getCharSequence(Notification.EXTRA_TEXT)+"\n");
            writer.write("SubText : "+extras.getCharSequence(Notification.EXTRA_SUB_TEXT)+"\n");
            writer.flush();
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // /Mydate 의 위치에 data.txt 파일을 읽는다.
    private String fileRead(){
        String text = "";
        // data.txt 파일 위치
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyData/data.txt");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line=reader.readLine()) != null){
                text = text + line+"\n";
            }
            sendToActivity(this.getApplicationContext(), text); // MainActivity로 인텐트를 보내는 함수
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //Toast.makeText(this, "fileread", Toast.LENGTH_SHORT).show();
        return text;
    }

    // MainActivity로 인텐트를 보내는 함수
    private void sendToActivity(Context context, String line){
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent에 담을 데이터의 키 값과 데이터
        intent.putExtra("line", line);
        //Log.e("SendToActivity", line);
        context.startActivity(intent); // Intent에 데이터를 담은 뒤 Activity에 보낸다.
        //Toast.makeText(context, "SendToActivity", Toast.LENGTH_SHORT).show();
    }
}
