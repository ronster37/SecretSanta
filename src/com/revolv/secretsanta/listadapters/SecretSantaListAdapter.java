package com.revolv.secretsanta.listadapters;

import java.util.LinkedList;

import com.revolv.secretsanta.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecretSantaListAdapter extends BaseAdapter {

    Context context;
    LinkedList<SecretSantaListData> data;
    private static LayoutInflater inflater = null;

    public SecretSantaListAdapter(Context context, LinkedList<SecretSantaListData> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setData(LinkedList<SecretSantaListData> data) {
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
        if (vi == null) vi = inflater.inflate(R.layout.row_secret_santas, null);
        TextView tv_santa = (TextView) vi.findViewById(R.id.tv_santa);
        TextView tv_giftee = (TextView) vi.findViewById(R.id.tv_giftee);
        tv_santa.setText("Santa: " + data.get(position).getSanta());
        tv_giftee.setText("Giftee: " + data.get(position).getGiftee());
        return vi;
    }
}