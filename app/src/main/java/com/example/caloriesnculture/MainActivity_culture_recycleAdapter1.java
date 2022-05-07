package com.example.caloriesnculture;

/*
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caloriesnculture.R;

import java.util.ArrayList;

public class MainActivity_culture_recycleAdapter1 extends RecyclerView.Adapter<MainActivity_culture_recycleAdapter1.ViewHolder>{

    private ArrayList<culture01_01listview> mlist = null;//박을 클래스 이름

    public MainActivity_culture_recycleAdapter1(ArrayList<culture01_01listview> list) {
        this.mlist = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.activity_culture_mlistviewlayout1, parent, false) ;
        MainActivity_culture_recycleAdapter1.ViewHolder vh = new MainActivity_culture_recycleAdapter1.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {//holder에 연결된 값들이 전부 저장됨
        holder.txt_culture01_mname.setText(mlist.get(position).getMnameli());
        holder.txt_culture01_mno.setText(mlist.get(position).getMnoli());
        holder.itemView.setOnClickListener(new View.OnClickListener() {//아이템뷰창 눌렀을시
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MainActivity_culture_reviewwrite_modify1.class);
                intent.putExtra("mno",holder.txt_culture01_mno.getText());

                v.getContext().startActivity(intent);
            }
        });


        holder.itemView.setTag(position);//저장

    }

    @Override
    public int getItemCount() {// 내가 만든 아이템들 숫자 카운터하기
        return mlist.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_culture01_mname;
        TextView txt_culture01_mno;

        public ViewHolder(@NonNull View itemView) {//아이템 연결 완료
            super(itemView);
            txt_culture01_mname = itemView.findViewById(R.id.txt_culture01_mname);
            txt_culture01_mno = itemView.findViewById(R.id.txt_culture01_mno);
        }
    }
}
*/