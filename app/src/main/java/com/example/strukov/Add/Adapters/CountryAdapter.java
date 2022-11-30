package com.example.strukov.Add.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.strukov.Add.Entities.Country;
import com.example.strukov.R;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private List<Country> mCountryList;
    private Context mContext;

    public CountryAdapter(List<Country> mCountryList, Context mContext)
    {
        this.mContext = mContext;
        this.mCountryList = mCountryList;
    }
    @Override
    public int getCount() {
        return mCountryList.size();
    }

    @Override
    public Object getItem(int i) {
        return mCountryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_countries, null );

        TextView txtName = view.findViewById(R.id.textView_Countries);
        txtName.setText(mCountryList.get(i).getName());


        return view;
    }
}

