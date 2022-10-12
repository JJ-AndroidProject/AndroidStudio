package org.techtown.sampletab;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/*                    데이터 확인용 프래그먼트 나중에 삭제할 예정                            */
public class BlankFragment4 extends Fragment {
    TextView textView;
    ScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank4, container, false);
        textView = (TextView) viewGroup.findViewById(R.id.textList);
        scrollView = (ScrollView) viewGroup.findViewById(R.id.scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                //scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        textView.setText(fileRead());
        return viewGroup;
    }

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
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return text;
    }
}