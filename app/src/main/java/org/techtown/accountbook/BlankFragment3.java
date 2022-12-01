package org.techtown.accountbook;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BlankFragment3 extends Fragment {

    TextView textView;
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_blank3, container, false);
        /*         테스트용으로 사용중 나중에는 지출 패턴으로 사용             */
        textView = (TextView) viewGroup.findViewById(R.id.textView);
        DBcommand command = new DBcommand(getContext());
        //String line = command.selectAllOutput();
        //textView.setText(line);

        //밑 차트는 예시용. 천천히 수정할 필요가 있음.

        pieChart = (PieChart) viewGroup.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        yValues.add(new PieEntry(34f, "한식"));
        yValues.add(new PieEntry(23f, "중식"));
        yValues.add(new PieEntry(14f, "일식"));
        yValues.add(new PieEntry(35f, "양식"));
        yValues.add(new PieEntry(40f, "동남아"));
        yValues.add(new PieEntry(40f, "기타"));

        Description description = new Description();
        description.setText("세계 요리"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues, "음식 종류");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        //pieChart.invalidate(); // 회전 및 터치 효과 사라짐
        //pieChart.setTouchEnabled(false);

        pieChart.setData(data);

        return viewGroup;
    }
}