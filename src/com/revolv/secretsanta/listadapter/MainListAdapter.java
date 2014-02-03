package com.revolv.secretsanta.listadapter;

import java.util.LinkedList;

import com.revolv.secretsanta.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainListAdapter extends BaseAdapter {

    Context context;
    LinkedList<MainListData> data;
    private static LayoutInflater inflater = null;

    public MainListAdapter(Context context, LinkedList<MainListData> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setData(LinkedList<MainListData> data) {
    	this.data = data;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) vi = inflater.inflate(R.layout.secret_santa_row, null);
        TextView text = (TextView) vi.findViewById(R.id.tv_list_name);
        text.setText(data.get(position).name);
        return vi;
    }
}