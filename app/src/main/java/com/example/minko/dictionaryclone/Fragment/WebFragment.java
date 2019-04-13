package com.example.minko.dictionaryclone.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.minko.dictionaryclone.Activity.WebViewActivity;
import com.example.minko.dictionaryclone.R;

public class WebFragment extends Fragment {


    public WebFragment() {
        // Required empty public constructor
    }

    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
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
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        // Inflate the layout for this fragment
        String url = "https://translate.google.com/#view=home&op=translate&sl=en&tl=ru";
        WebView views = view.findViewById(R.id.web);
        views.getSettings().setJavaScriptEnabled(true);
        views.loadUrl(url);
        views.setWebViewClient(new WebViewActivity.Mybrowser());
        return view;

    }

}
