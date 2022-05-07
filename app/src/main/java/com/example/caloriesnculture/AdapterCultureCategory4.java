package com.example.caloriesnculture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterCultureCategory4 extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Post> mDatas;

    public AdapterCultureCategory4(Context _context, PostList _postList)
    {
        mContext = _context;
        mLayoutInflater = LayoutInflater.from(mContext);

        mDatas = _postList.posts;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Post getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //View view = mLayoutInflater.inflate(R.layout.layout_culture_category4_item, null);
        View view = mLayoutInflater.inflate(R.layout.layout_culture_category4_item, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 250;
        view.setLayoutParams(layoutParams);

        TextView postIdView = (TextView)view.findViewById(R.id.postId);
        TextView postTitleView = (TextView)view.findViewById(R.id.postTitle);

        postIdView.setText(mDatas.get(position).title);
        postTitleView.setText(mDatas.get(position).content);

        return view;
    }
}