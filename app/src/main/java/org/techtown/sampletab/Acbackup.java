package org.techtown.sampletab;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class Acbackup {
    private void backup1(View v) { //백업
        try {
            File sd1 = Environment.getExternalStorageDirectory();
            File data1 = Environment.getDataDirectory();

            if (sd1.canWrite()) {
                String currentDBPath1 ="//data//package name//databases//database_name";  // 경로 수정 필요
                String backupDBPath1 = "database_name";
                File currentDB = new File(data1, currentDBPath1);
                File backupDB = new File(sd1, backupDBPath1);

                if (currentDB.exists()) {
                    FileChannel src1 = new FileInputStream(currentDB).getChannel();
                    FileChannel dst1 = new FileOutputStream(backupDB).getChannel();
                    dst1.transferFrom(src1, 0, src1.size());
                    Log.i("확인 검사", "여기까지 완료");
                    src1.close();
                    dst1.close();
                    Snackbar.make(v, "백업이 완료되었습니다.", Snackbar.LENGTH_SHORT).show(); //수정할 필요가 있음
                }
            }
        } catch (Exception e) {
            Snackbar.make(v, "백업이 실패되었습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void restore1(View v) { //복원, 백업과 이하동문
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath1 = "//data//package name//databases//database_name";
                String backupDBPath1 = "database_name";
                File currentDB1 = new File(data, currentDBPath1);
                File backupDB1 = new File(sd, backupDBPath1);

                if (currentDB1.exists()) {
                    FileChannel src1 = new FileInputStream(backupDB1).getChannel();
                    FileChannel dst1 = new FileOutputStream(currentDB1).getChannel();
                    dst1.transferFrom(src1, 0, src1.size());
                    Log.i("확인 검사", "여기까지 완료");
                    src1.close();
                    dst1.close();
                    Snackbar.make(v, "복구가 완료되었습니다.", Snackbar.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Snackbar.make(v, "복구에 실패하였습니다.", Snackbar.LENGTH_SHORT).show();
        }
    }
}

