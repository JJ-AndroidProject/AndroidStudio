package org.techtown.sampletab;

import android.content.Context;
import android.util.Log;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {
    Context context;
    private List<BlankFragment1.SubRecyclerItem> items;

    public SubAdapter(List<BlankFragment1.SubRecyclerItem> items){
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DecimalFormat decFormat = new DecimalFormat("###,###"); // 3자리마다 콤마를 찍어주는 포맷
        String time = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            time = items.get(position).time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        holder.timeText.setText(time);
        holder.titleText.setText(items.get(position).title);
        holder.moneyText.setText(decFormat.format((int)items.get(position).money)+"원");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeText;
        TextView titleText;
        TextView moneyText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.SubTimeText);
            titleText = itemView.findViewById(R.id.SubTitleText);
            moneyText = itemView.findViewById(R.id.SubMoneyText);
        }
    }
}





