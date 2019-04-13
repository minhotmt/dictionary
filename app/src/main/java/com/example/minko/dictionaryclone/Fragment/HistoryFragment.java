package com.example.minko.dictionaryclone.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.minko.dictionaryclone.Adapter.CustomAdapterHistory;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.DBHistoryManager;

import java.util.ArrayList;


public class HistoryFragment extends Fragment {
    private ListView lstHistory;
    private ArrayAdapter<String> mAdapter;
    private CustomAdapterHistory customAdapterHistory;
    Context context;


    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        lstHistory = view.findViewById(R.id.lst_history);

        ArrayList<String> historys = new ArrayList<>();
        DBHistoryManager dbHistoryManager = new DBHistoryManager(getContext());
        historys = (ArrayList<String>) dbHistoryManager.getAllFavoriteString();
        context = container.getContext();
        if (mAdapter == null) {
            mAdapter = new CustomAdapterHistory(historys, context);
            lstHistory.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter = new CustomAdapterHistory(historys, context);
            mAdapter.notifyDataSetChanged();
        }
        lstHistory.setAdapter(mAdapter);
        return view;

    }


}
