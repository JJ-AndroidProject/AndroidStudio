package org.techtown.accountbook;

import android.database.sqlite.SQLiteDatabase;
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
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BlankFragment3 extends Fragment {

    TextView textView;
    PieChart pieChart;
    DBcommand dBcommand;
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase db;

    ArrayList<PieEntry> pie = new ArrayList<PieEntry>();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getContext(), "프래그먼트 3 생성", Toast.LENGTH_SHORT).show();
        Log.d("BlankFragment3-onCreate", "BlankFragment3-onCreate 작동");

        dbOpenHelper = new DBOpenHelper(getActivity());


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

            pieChart.setTouchEnabled(false); //터치
            pieChart.setUsePercentValues(true); //백분율
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5, 10, 5, 5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

            pieChart.setDrawHoleEnabled(false); //드래그
            pieChart.setHoleColor(Color.BLACK);
            pieChart.setTransparentCircleRadius(61f);
            pieChart.setNoDataText("현재 지출이 아무것도 없습니다.");

            pie.add(new PieEntry(140, "aasfasdg"));
            pie.add(new PieEntry(204, "basgdasdg"));
            pie.add(new PieEntry(440, "cqweasd"));
            pie.add(new PieEntry(340, "dasdsad"));
            pie.add(new PieEntry(510, "fgasdg"));
            pie.add(new PieEntry(630, "gqwqwwq"));


            Description description = new Description();
            description.setText("지출 분포"); //라벨
            description.setTextSize(23);
            description.setEnabled(true);
            pieChart.setDescription(description);

            Legend legend = pieChart.getLegend(); //하단 설명
            legend.setEnabled(true); //
            legend.setTextColor(Color.BLACK);
            legend.setTextSize(15);
            legend.setForm(Legend.LegendForm.SQUARE);

            pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

            PieDataSet piedataset = new PieDataSet(pie, "");//pie와 지정할 label 넘김.
            piedataset.setValueTextSize(15f);
            piedataset.setValueTextColor(Color.BLACK);
            piedataset.setValueFormatter(new PercentFormatter());
            piedataset.setSliceSpace(1); //조각 사이의 거리
            piedataset.setSelectionShift(10f); //기본값 : 12f
            piedataset.setColors(ColorTemplate.PASTEL_COLORS);

            PieData piedata = new PieData(piedataset);
            piedata.setValueTextSize(15f);
            piedata.setValueFormatter(new PercentFormatter());
            piedata.setValueTextColor(Color.WHITE);

            pieChart.setData(piedata);

            pieChart.invalidate();
            pieChart.notifyDataSetChanged();


            return viewGroup;

        }





    }




