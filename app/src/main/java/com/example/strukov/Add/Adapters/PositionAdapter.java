package com.example.strukov.Add.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.strukov.Add.Entities.Position;
import com.example.strukov.R;

import java.util.List;

public class PositionAdapter extends BaseAdapter {

    private List<Position> mPositionList;
    private Context mContext;

    public PositionAdapter(List<Position> mPositionList, Context mContext)
    {
        this.mContext = mContext;
        this.mPositionList = mPositionList;
    }
    @Override
    public int getCount() {
        return mPositionList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPositionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mPositionList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_position, null );

        TextView txtName = view.findViewById(R.id.textView_Position);
        txtName.setText(mPositionList.get(i).getName());


        return view;
    }
}

