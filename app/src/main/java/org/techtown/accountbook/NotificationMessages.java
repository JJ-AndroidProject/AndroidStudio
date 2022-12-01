package org.techtown.accountbook;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

public class NotificationMessages {
    String CHANNELID = "";
    int NOTIFICATION_ID = 7;
    public NotificationMessages(Context context, String title, String text){
        Notification.Builder builder = null;
        PendingIntent pendingIntent;
        Intent intent = new Intent(context, MainActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_IMMUTABLE);
        } else{
            pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String prefID = PreferenceManager.getString(context, "channelID");
            if(prefID.equals("1")){
                CHANNELID = "1";
            }
            else{
                NotificationChannel notificationChannel = new NotificationChannel("1", "가계부", NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setDescription("channel description");
                notificationChannel.setLightColor(Color.GREEN); // 알림 RGB 색깔
                notificationChannel.enableVibration(true); // 진동 여부
                notificationChannel.enableLights(true); // 장치에 알림 표시
                notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200}); // 진동 패턴
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE); // 잠금화면에 표시되는지 여부
                notificationManager.createNotificationChannel(notificationChannel);
                CHANNELID = "1";
                PreferenceManager.setString(context, "channelID", "1");
            }
            builder = new Notification.Builder(context, CHANNELID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        }else {
            builder = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
        }
        Notification notification = builder.build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
