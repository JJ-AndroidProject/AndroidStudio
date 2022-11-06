package org.techtown.sampletab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlankFragment3 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank3, container, false);
        textView = (TextView) viewGroup.findViewById(R.id.textView);
        DBcommand command = new DBcommand(getContext());
        String line = command.selectAll();
        textView.setText(line);
        return viewGroup;
    }
}