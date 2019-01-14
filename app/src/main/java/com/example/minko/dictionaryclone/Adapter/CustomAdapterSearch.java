package com.example.minko.dictionaryclone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minko.dictionaryclone.Model.Favorite;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.DBFavoriteManager;

import java.util.ArrayList;

public class CustomAdapterSearch extends ArrayAdapter<Favorite> {

    Context mContext;
    private ArrayList<Favorite> dataSet;
    private int lastPosition = -1;


    public CustomAdapterSearch(ArrayList<Favorite> data, Context context) {
        super(context, R.layout.item_no_favor, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Favorite dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_no_favor, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.txt_favor);
            viewHolder.imgFavor = convertView.findViewById(R.id.img_favor);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        final DBFavoriteManager dbFavoriteManager = new DBFavoriteManager(getContext());

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.imgFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.imgFavor.setImageResource(R.drawable.ic_favorited);
                dbFavoriteManager.addFavorite(dataModel);
            }
        });


        ArrayList<String> arr = (ArrayList<String>) dbFavoriteManager.getAllFavoriteString();
        for (String item: arr){
            if (dataModel.equals(item)){
                viewHolder.imgFavor.setImageResource(R.drawable.ic_favorited);
            } else
                viewHolder.imgFavor.setImageResource(R.drawable.ic_not_favorite);
        }

        return convertView;
    }

    // View lookup cache
    public static class ViewHolder {
        TextView txtName;
        ImageView imgFavor;
    }
}

