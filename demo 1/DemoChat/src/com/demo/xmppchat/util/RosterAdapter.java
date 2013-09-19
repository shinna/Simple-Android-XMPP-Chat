package com.demo.xmppchat.util;

import java.util.List;

import com.demo.xmppchat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RosterAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<String> listStringData;

    public RosterAdapter(Context context, List<String> listStringData) {
        this.context = context;
        this.listStringData = listStringData;
    }

    public int getCount() {
        return listStringData.size();
    }

    public Object getItem(int position) {
        return listStringData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        String entry = listStringData.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);
        }
        
        TextView tvTime = (TextView) convertView.findViewById(R.id.row_name);
        tvTime.setText(entry);
        
        return convertView;
    }

    @Override
    public void onClick(View view) {
        String entry = (String) view.getTag();
        listStringData.remove(entry);
        // listStringData.remove(view.getId());
        notifyDataSetChanged();
    }
}


