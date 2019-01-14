package com.example.minko.dictionaryclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.DBFavoriteManager;

import java.util.ArrayList;

public class CustomAdapterFavorite extends ArrayAdapter<String> {

    Context mContext;
    private ArrayList<String> dataSet;
    private int lastPosition = -1;


    public CustomAdapterFavorite(ArrayList<String> data, Context context) {
        super(context, R.layout.item_history, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final String dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_favor, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.txt_favor);
            viewHolder.imgFavor = convertView.findViewById(R.id.img_favor);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        viewHolder.txtName.setText(dataModel);
        viewHolder.imgFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBFavoriteManager dbFavoriteManager = new DBFavoriteManager(getContext());
                dbFavoriteManager.deleteFavoriteByName(dataModel);
                dataSet.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    // View lookup cache
    public static class ViewHolder {
        TextView txtName;
        ImageView imgFavor;
    }
}
