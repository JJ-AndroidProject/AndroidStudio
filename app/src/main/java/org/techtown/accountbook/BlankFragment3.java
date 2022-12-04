package org.techtown.accountbook;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BlankFragment3 extends Fragment {

    TextView textView;
    PieChart pieChart;
    DBcommand command;

    ArrayList<PieEntry> pie = new ArrayList<PieEntry>();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "프래그먼트 3 생성", Toast.LENGTH_SHORT).show();
        Log.d("BlankFragment3-onCreate", "BlankFragment3-onCreate 작동");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank3, container, false);
        /*         테스트용으로 사용중 나중에는 지출 패턴으로 사용             */
        textView = (TextView) viewGroup.findViewById(R.id.textView);


        //String line = command.selectAllOutput();
        //textView.setText(line);



        pieChart = (PieChart) viewGroup.findViewById(R.id.piechart);

        pieChart.invalidate(); //회전
        pieChart.setTouchEnabled(false); //터치
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false); //드래그
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setNoDataText("현재 지출이 아무것도 없습니다.");



        Description description = new Description();
        description.setText("현재 수입 지출"); //라벨
        description.setTextSize(15);
        description.setEnabled(true);
        pieChart.setDescription(description);

        Legend legend = pieChart.getLegend(); //하단 설명
        legend.setEnabled(true); //커밋 수정
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(15);
        legend.setForm(Legend.LegendForm.SQUARE);

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

        PieDataSet piedataset = new PieDataSet(pie, "지출 내역"); //pie와 지정할 label 넘김.
        piedataset.setValueTextColor(Color.BLACK);
        piedataset.setSliceSpace(1); //조각 사이의 거리
        piedataset.setSelectionShift(12f); //기본값 : 12f

        piedataset.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(piedataset);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);


        pieChart.setData(data);

        return viewGroup;

        //밑 차트는 예시용. 수정할 필요가 있음.

    }



}