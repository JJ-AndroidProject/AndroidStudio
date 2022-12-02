package org.techtown.accountbook;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

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
    Context context;


    // 상단에 표시되어 있는 알림을 지울때 작동이 됩니다.
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        context = this;
    }

    // 상단의 알림이 오면 작업을 시작
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        try{
            new BankSelectInsert(sbn, this);
        }catch(NullPointerException e){
            Log.e(TAG, "NullPointerException Catch"); // 값이 null 일 때 발생하는 오류를 catch 한다.
        }
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
}
