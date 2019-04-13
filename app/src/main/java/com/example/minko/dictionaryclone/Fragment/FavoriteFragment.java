package com.example.minko.dictionaryclone.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.minko.dictionaryclone.Adapter.CustomAdapterFavorite;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.DBFavoriteManager;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private ListView mFovListView;
    private CustomAdapterFavorite mAdapter;
    private Context context;

    public FavoriteFragment() {

    }

    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        mFovListView = view.findViewById(R.id.lst_favor);
        DBFavoriteManager dbFavoriteManager = new DBFavoriteManager(getContext());
        ArrayList<String> arr = (ArrayList<String>) dbFavoriteManager.getAllFavoriteString();

        context = container.getContext();
        if (mAdapter == null) {
            mAdapter = new CustomAdapterFavorite(arr, context);
            mFovListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter = new CustomAdapterFavorite(arr, context);
            mAdapter.notifyDataSetChanged();
        }
        mFovListView.setAdapter(mAdapter);
        return view;
    }


}
