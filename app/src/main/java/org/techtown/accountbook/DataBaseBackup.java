package org.techtown.accountbook;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DataBaseBackup {
    Context context = null;
    public DataBaseBackup(Context context){
        this.context = context;
    }
    void save() throws IOException {
        Log.e("save", "눌림");
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        if (sd.canWrite()) {
            String currentDBPath ="//data/org.techtown.accountbook//databases//accountBook.db";  // 경로 수정 필요
            String backupDBPath = "//Download//accountBook.db";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(context, "백업이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "백업이 실패되었습니다.", Toast.LENGTH_SHORT).show();
        }
    } //save

    void restore() throws IOException  { //복원, 백업과 이하동문
        Log.e("restore", "눌림");
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        if (sd.canWrite()) {
            String currentDBPath ="//data/org.techtown.accountbook//databases//accountBook.db";  // 경로 수정 필요
            String backupDBPath = "//Download//accountBook.db";
            File currentDB = new File(data, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            FileChannel src = new FileInputStream(backupDB).getChannel();
            FileChannel dst = new FileOutputStream(currentDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(context, "백업이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "백업이 실패되었습니다.", Toast.LENGTH_SHORT).show();
        }
    } // restore
}

