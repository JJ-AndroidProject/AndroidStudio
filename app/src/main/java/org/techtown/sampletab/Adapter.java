package org.techtown.sampletab;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    SubAdapter adapter;
    private SparseBooleanArray selectedItems = new SparseBooleanArray(); // Item의 클릭 상태를 저장할 array 객체
    private int prePosition = -1; // 직전에 클릭됐던 Item의 position
    private List<BlankFragment1.MainRecyclerItem> list;
    private List<BlankFragment1.SubRecyclerItem> items;
    private Context context;

    public Adapter(Context context, List<BlankFragment1.MainRecyclerItem> list, List<BlankFragment1.SubRecyclerItem> items){
        this.context = context;
        this.list = list;
        this.items = items;
    }

    public interface OnViewHolderItemClickListener {
        void onViewHolderItemClick();
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position){
        List<BlankFragment1.SubRecyclerItem> item = new ArrayList<BlankFragment1.SubRecyclerItem>();
        for(int i=0;i<items.size();i++){
            if(items.get(i).getYear() == list.get(position).getYear()
                    && items.get(i).getMonth() == list.get(position).getMonth()
                    && items.get(i).getDay() == list.get(position).getDay()){
                item.add(items.get(i));
            }
        }
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new SubAdapter(item);
        holder.recyclerView.setAdapter(adapter);

        holder.textView.setText(list.get(position).getTitle()); // item_list.xml에서 textView의 text를 수정
        holder.button.setText("Button");  // item_list.xml에서 button의 text를 수정
        holder.onBind(position);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItems.get(position)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position);
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition);
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true);
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                // 클릭된 position 저장
                prePosition = position;
            }
        });
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    //뷰홀더 객체에 저장되어 화면에 표시되고, 필요에 따라 생성 또는 재활용 된다.
    public class ViewHolder extends RecyclerView.ViewHolder{
        private int position;
        public TextView textView;
        public Button button;
        public LinearLayout linearLayout;
        public RelativeLayout relativeLayout;
        public RecyclerView recyclerView;

        OnViewHolderItemClickListener onViewHolderItemClickListener;

        public ViewHolder(View view){
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

        public void onBind(int position){
            this.position = position;
            changeVisibility(selectedItems.get(position));
        }

        private void changeVisibility(final boolean isExpanded) {
            // height 값을 dp로 지정해서 넣고싶으면 아래 소스를 이용
            int dpValue = 200;
            float d = context.getResources().getDisplayMetrics().density;
            int height = (int) (dpValue * d);

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, height) : ValueAnimator.ofInt(height, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(100);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // value는 height 값
                    int value = (int) animation.getAnimatedValue();
                    // imageView의 높이 변경
                    recyclerView.getLayoutParams().height = value;
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




