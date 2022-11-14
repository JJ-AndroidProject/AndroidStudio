package org.techtown.sampletab;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Inputbackup {

    private void inbackup(View v) { //백업
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//package name//databases//database_name"; // 경로 수정 필요
                String backupDBPath = "database_name";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    Log.e("inbackupok", "여기까지 완료");
                    src.close();
                    dst.close();
                    Snackbar.make(v, "백업이 완료되었습니다.", Snackbar.LENGTH_SHORT).show(); //수정할 필요가 있음
                }
            }
        } catch (Exception e) {
            Snackbar.make(v, "백업이 실패되었습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void inrestore(View v) { //복원, 백업과 이하동문
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//package name//databases//database_name";
                String backupDBPath = "database_name";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    Log.e("inrestoreok", "여기까지 완료");
                    src.close();
                    dst.close();
                    Snackbar.make(v, "복구가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Snackbar.make(v, "복구에 실패하였습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }
}

