package com.example.minko.dictionaryclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minko.dictionaryclone.Model.Nouns;
import com.example.minko.dictionaryclone.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Nouns> {

    private Context context;
    private int resource;
    private List<Nouns> arrNouns;

    public CustomAdapter(Context context, int resource, ArrayList<Nouns> arrNouns) {
        super(context, resource, arrNouns);
        this.context = context;
        this.resource = resource;
        this.arrNouns = arrNouns;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.txtTitle);
            viewHolder.imgIcon = convertView.findViewById(R.id.imgIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Nouns nouns = arrNouns.get(position);
        viewHolder.tvName.setText(nouns.getName());
        return convertView;
    }

    public class ViewHolder {
        TextView tvName;
        ImageView imgIcon;
    }
}