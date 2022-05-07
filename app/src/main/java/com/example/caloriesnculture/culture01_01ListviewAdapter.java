package com.example.caloriesnculture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class culture01_01ListviewAdapter extends BaseAdapter {
    private ArrayList<MainActivity_culture_mlistview1> mlist = new ArrayList<>();
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_culture_mlistviewlayout1, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView txt_culture01_mname = convertView.findViewById(R.id.txt_culture01_mname);
        TextView txt_culture01_mreview =  convertView.findViewById(R.id.txt_culture01_mgenre) ;
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        MainActivity_culture_mlistview1 datalist = mlist.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        txt_culture01_mname.setText(datalist.getMnameli());
        txt_culture01_mreview.setText(datalist.getMreview());

        return convertView;

    }
    public void addItem(String mname, String mno,String mreview) {
        MainActivity_culture_mlistview1  item = new MainActivity_culture_mlistview1(mname,mno,mreview);

        mlist.add(item);
    }
}
