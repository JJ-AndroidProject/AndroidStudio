package org.techtown.sampletab;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
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
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList("입금", "출금", "계좌", "카드", "대금", "NH카드", "nh카드", "농협", "kb", "KB", "국민", "승인", "결제", "잔액", "입출금", "NH스마트알림", "카카오뱅크"));
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
        if(sbn != null){
            //Toast.makeText(this, "lineContains on", Toast.LENGTH_SHORT).show();
            fileSave(sbn);
            fileRead();
        }
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
    
    // 파일 저장
    private void fileSave(StatusBarNotification sbn){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // date 변수의 형식
        Bundle extras = sbn.getNotification().extras;
        FileWriter writer;
        try{
            String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyData";
            File dir = new File(dirPath);
            Log.d("fileSave", "dirPath : "+dir.toString());
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
        Log.e("SendToActivity", line);
        context.startActivity(intent); // Intent에 데이터를 담은 뒤 Activity에 보낸다.
        //Toast.makeText(context, "SendToActivity", Toast.LENGTH_SHORT).show();
    }
}
