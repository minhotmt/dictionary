package com.example.minko.dictionaryclone.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.minko.dictionaryclone.Activity.MeanDetailActivity;
import com.example.minko.dictionaryclone.Adapter.CustomAdapterSearch;
import com.example.minko.dictionaryclone.Model.Favorite;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.MyDatabase;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String talk = "";
    private Cursor employees;
    private MyDatabase db;
    private ArrayList<Favorite> lstFavortite;
    private CustomAdapterSearch customAdapterSearch;
    private Context context;
    private Boolean status = true;

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {

        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
        db = new MyDatabase(getActivity());
        new getDatabase().execute();
    }

    public class getDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            lstFavortite = db.getAllWord();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,
                container, false);

        ImageButton image = view.findViewById(R.id.imgSearch);
        final ListView lstSearch = view.findViewById(R.id.lstSearch);
        final EditText editText = view.findViewById(R.id.edtText);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
        context = container.getContext();

        editText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                ListView lstSearch = getView().findViewById(R.id.lstSearch);
                ArrayList<Favorite> suggest = new ArrayList<>();
                for (Favorite favorite : lstFavortite) {
                    if (favorite.getName().startsWith(editText.getText().toString().toLowerCase())) {
                        suggest.add(favorite);
                    }
                }

                if (customAdapterSearch == null) {
                    customAdapterSearch = new CustomAdapterSearch(suggest, context);
                    lstSearch.setAdapter(customAdapterSearch);
                } else {
                    customAdapterSearch.clear();
                    customAdapterSearch = new CustomAdapterSearch(suggest, context);
                    customAdapterSearch.notifyDataSetChanged();
                }
                if (editText.getText().toString().equals("")) {
                    customAdapterSearch.clear();
                }
                lstSearch.setAdapter(customAdapterSearch);
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            public void afterTextChanged(Editable arg0) {
            }
        });

        lstSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MeanDetailActivity.class);
                Favorite favorite = (Favorite) parent.getItemAtPosition(position);
                intent.putExtra("word", "" + favorite.getName());
                intent.putExtra("mean", "" + favorite.getDifinition());
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (employees != null) {
            employees.close();
            db.close();
        }

    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say somthing...");
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    talk = result.get(0);
                    EditText editText = getView().findViewById(R.id.edtText);
                    editText.setText(result.get(0));
                }
                break;
            }
        }
    }


}
