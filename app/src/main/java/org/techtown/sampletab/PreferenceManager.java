package org.techtown.sampletab;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    /*Preference 클래스. Preference로 저장&불러오기를 할 때 호출됨
    11.05 기준 월 시작일만을 위해 만들어졌으므로 INT타입만 사용할 수 있도록 함
    11.09 기준 NotificationManager의 NotificationChannel을 위해 만들어졌으며 String타입만 사용할 수 있도록 함
     */
    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
    }

    public static void setInt(Context context, String key, int value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public static int getInt(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key, 1);
        return value;
    }

    public static void setString(Context context, String key, String value){
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, "EMPTY");
        return value;
    }
}
