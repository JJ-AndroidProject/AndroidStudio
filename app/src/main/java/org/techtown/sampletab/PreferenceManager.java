package org.techtown.sampletab;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

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
}
