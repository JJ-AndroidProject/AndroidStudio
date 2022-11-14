package org.techtown.sampletab;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Outputbackup {

    private void outbackup(View v) { //백업
        try {
            File sd2 = Environment.getExternalStorageDirectory();
            File data2 = Environment.getDataDirectory();

            if (sd2.canWrite()) {
                String currentDBPath2 = "//data//package name//databases//database_name"; // 경로 수정 필요
                String backupDBPath2 = "database_name";
                File currentDB2 = new File(data2, currentDBPath2);
                File backupDB2 = new File(sd2, backupDBPath2);

                if (currentDB2.exists()) {
                    FileChannel src2 = new FileInputStream(currentDB2).getChannel();
                    FileChannel dst2 = new FileOutputStream(backupDB2).getChannel();
                    dst2.transferFrom(src2, 0, src2.size());
                    Log.e("확인 검사", "여기까지 완료");
                    src2.close();
                    dst2.close();
                    Snackbar.make(v, "백업이 완료되었습니다.", Snackbar.LENGTH_SHORT).show(); //수정할 필요가 있음
                }
            }
        } catch (Exception e) {
            Snackbar.make(v, "백업이 실패되었습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void outrestore(View v) { //복원, 백업과 이하동문
        try {
            File sd2 = Environment.getExternalStorageDirectory();
            File data2 = Environment.getDataDirectory();

            if (sd2.canWrite()) {
                String currentDBPath2 = "//data//package name//databases//database_name";
                String backupDBPath2 = "database_name";
                File currentDB2 = new File(data2, currentDBPath2);
                File backupDB2 = new File(sd2, backupDBPath2);

                if (currentDB2.exists()) {
                    FileChannel src2 = new FileInputStream(backupDB2).getChannel();
                    FileChannel dst2 = new FileOutputStream(currentDB2).getChannel();
                    dst2.transferFrom(src2, 0, src2.size());
                    Log.i("확인 검사", "여기까지 완료");
                    src2.close();
                    dst2.close();
                    Snackbar.make(v, "복구가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Snackbar.make(v, "복구에 실패하였습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }
}

