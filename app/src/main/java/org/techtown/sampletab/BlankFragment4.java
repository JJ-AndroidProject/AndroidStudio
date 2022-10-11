package org.techtown.sampletab;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/*                    데이터 확인용 프래그먼트 나중에 삭제할 예정                            */
public class BlankFragment4 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank4, container, false);
        textView = (TextView) viewGroup.findViewById(R.id.textList);
        //textView.setText(getArguments().getString("MessageData"));
        return viewGroup;
    }


}