package org.techtown.sampletab;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {
    private Context context;
    private List<String> list = new ArrayList<>();
    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int year;
    private int month;
    private int day;


    public Adapter(Context context, List<String> list, int year, int month, int day){
        this.context = context;
        this.list = list;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public interface OnViewHolderItemClickListener {
        void onViewHolderItemClick();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position){
        int pos = position;
        holder.textView.setText(list.get(position)); // item_list.xml에서 textView의 text를 수정
        if(year == 2022 && month == 10 && day == 8){
            for(int i=0;i<5;i++){
                String str = "서브 메시지";

            }
        }
        holder.button.setText("Button");  // item_list.xml에서 button의 text를 수정
        holder.onBind(selectedItems, position);
        holder.setOnViewHolderItemClickListener(new OnViewHolderItemClickListener() {
            @Override
            public void onViewHolderItemClick() {
                Toast.makeText(context, "onViewHolderItemClick() " +pos, Toast.LENGTH_SHORT).show();
                if (selectedItems.get(pos)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(pos);
                } else {
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(pos, true);
                }
                // 해당 포지션의 변화를 알림
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    //뷰홀더 객체에 저장되어 화면에 표시되고, 필요에 따라 생성 또는 재활용 된다.
    public class Holder extends RecyclerView.ViewHolder{
        public TextView textView;
        public Button button;
        public LinearLayout linearLayout;
        public RelativeLayout relativeLayout;
        public RecyclerView recyclerView;

        OnViewHolderItemClickListener onViewHolderItemClickListener;

        public Holder(View view){
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            button = (Button) view.findViewById(R.id.button);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSub);
            linearLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(context, "Holder onClick()", Toast.LENGTH_SHORT).show();
                    onViewHolderItemClickListener.onViewHolderItemClick();
                }
            });

        }
        public void onBind(SparseBooleanArray selectedItems, int position){
            changeVisibility(selectedItems.get(position));
        }

        private void changeVisibility(final boolean isExpanded) {
            // 기본 = 0
            // 한 라인 생길때마다 그만큼 크기를 증가 시켜주면 됨
            int maxHeight = 100;
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, maxHeight) : ValueAnimator.ofInt(maxHeight, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(0);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // imageView의 높이 변경
                    recyclerView.getLayoutParams().height = (int) animation.getAnimatedValue();
                    recyclerView.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    recyclerView.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }

        public void setOnViewHolderItemClickListener(OnViewHolderItemClickListener onViewHolderItemClickListener) {
            this.onViewHolderItemClickListener = onViewHolderItemClickListener;
        }
    }
}




