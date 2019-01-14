package com.example.minko.dictionaryclone.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minko.dictionaryclone.Activity.MainActivity;
import com.example.minko.dictionaryclone.Activity.MeanDetailActivity;
import com.example.minko.dictionaryclone.Adapter.CustomAdapterSearch;
import com.example.minko.dictionaryclone.Model.Favorite;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.MyDatabase;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String talk = "";
    private Cursor employees;
    private MyDatabase db;
    private ArrayList<Favorite> lstFavortite;
    private ArrayAdapter<String> mAdapter;
    private CustomAdapterSearch customAdapterSearch;
    private Button btnTest;
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = new MyDatabase(getActivity());
        lstFavortite = db.getAllWord();
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

                ArrayList<Favorite> arr = new ArrayList<>();
                for (Favorite favorite : suggest) {
                    arr.add(favorite);
                }

                if (customAdapterSearch == null) {
                    customAdapterSearch = new CustomAdapterSearch(arr, context);
                    lstSearch.setAdapter(customAdapterSearch);
                } else {
                    customAdapterSearch.clear();
                    customAdapterSearch = new CustomAdapterSearch(arr, context);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
