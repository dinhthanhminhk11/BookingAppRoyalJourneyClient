package com.example.bookingapproyaljourney.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.bookingapproyaljourney.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RevenueAdapter extends BaseExpandableListAdapter implements Filterable {
    Context context;
    List<String> title;
    HashMap<String, List<String>> toppic;
    List<String> listTemp;

    public RevenueAdapter(Context context, List<String> title, HashMap<String, List<String>> toppic) {
        this.context = context;
        this.title = title;
        this.toppic = toppic;
        this.listTemp = title;
    }


    @Override
    public int getGroupCount() {
        return title == null ? 0 : title.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return toppic.get(title.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return title.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return toppic.get(title.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_group, null);
        }

        TextView textView = convertView.findViewById(R.id.tv_group);
        textView.setText(group);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String topic = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_child, null);
        }

        TextView textView = convertView.findViewById(R.id.tv_child);
        textView.setText(topic);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSeach = constraint.toString();
                if (strSeach.isEmpty()) {
                    title = listTemp;
                } else {
                    List<String> list = new ArrayList<>();
                    for (String x : listTemp) {
                        if (x.toString().toLowerCase().contains(strSeach.toLowerCase())) {
                            list.add(x);
                        }
                    }
                    title = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = title;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                title = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}