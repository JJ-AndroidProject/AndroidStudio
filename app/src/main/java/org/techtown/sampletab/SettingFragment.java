package org.techtown.sampletab;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingFragment extends PreferenceFragmentCompat{
    //test
    private static final String SETTING_STARTDAY = "start_day";
    private static final String SETTING_DELETEDATA = "delete_data";
    private static final String SETTING_BACKUPDATA = "backup_data";
    private static final String SETTING_RECOVERYDATA = "recovery_data";
    private static final String SETTING_USEMAXEXPENSES = "use_max_expenses";
    private static final String SETTING_MAXEXPENSES = "max_expenses";
    private static final String SETTING_ADDACCOUNT = "add_account";
    private static final String SETTING_DELETEACCOUNT = "delete_account";
    private static final String SETTING_USEPASSWORD = "use_password";
    private static final String SETTING_PASSWORD = "passward";
    SharedPreferences prefs;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    // key값에 해당하는 명령 넣기
                    if (key.equals(SETTING_STARTDAY)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_DELETEDATA)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_BACKUPDATA)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_RECOVERYDATA)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_USEMAXEXPENSES)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_MAXEXPENSES)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_ADDACCOUNT)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_DELETEACCOUNT)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_USEPASSWORD)) {
                        Log.d("TAG", key + "SELECTED");
                    } else if (key.equals(SETTING_PASSWORD)) {
                        Log.d("TAG", key + "SELECTED");
                    }
                }
            };

}
