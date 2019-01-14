package com.example.minko.dictionaryclone.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minko.dictionaryclone.Model.Favorite;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.DBFavoriteManager;
import com.example.minko.dictionaryclone.Service.DBHistoryManager;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TranslatorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TranslatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TranslatorFragment extends Fragment implements TextToSpeech.OnInitListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextToSpeech tts;
    private EditText edtText;
    private TextView txtWord;
    private ImageButton iconListen;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
//    private MyAsyncTask myAsyncTask;

    public TranslatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TranslatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TranslatorFragment newInstance(String param1, String param2) {
        TranslatorFragment fragment = new TranslatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String Translator(String text) {
        TranslateOptions options = TranslateOptions.newBuilder()
                .setApiKey("AIzaSyCesRSQ_xOlB489lpIZvOxSBFRPv6a_lNk")
                .build();
        com.google.cloud.translate.Translate translate = options.getService();
        try {
            Translation translation = translate.translate(text, // dogings[0] = "dog"
                    com.google.cloud.translate.Translate.TranslateOption.sourceLanguage("en"), // en
                    com.google.cloud.translate.Translate.TranslateOption.targetLanguage("ru")); // vi
            return translation.getTranslatedText();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translator, container, false);
        // Inflate the layout for this fragment
        txtWord = view.findViewById(R.id.txtWord);
        ImageButton imageButton = view.findViewById(R.id.imgChange);
        final ImageButton iconListen = view.findViewById(R.id.icon_listen);
        final ImageButton iconDelete = view.findViewById(R.id.imgDelete);
        final ImageView imageView1 = view.findViewById(R.id.img1);
        final ImageView imageView2 = view.findViewById(R.id.img2);
        final TextView txt1 = view.findViewById(R.id.txt1);
        final TextView txt2 = view.findViewById(R.id.txt2);
        iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtText.setText("");
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(imageView1, txt1);
                change(imageView2, txt2);
            }
        });
        iconListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });

        tts = new TextToSpeech(getContext(), this);
        edtText = view.findViewById(R.id.edtText);
        edtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtText.getText().toString().equals("")) {
                    iconListen.setVisibility(View.VISIBLE);
                } else {
                    iconListen.setVisibility(View.INVISIBLE);
                    txtWord.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });
        final DBHistoryManager dbFavoriteManager = new DBHistoryManager(getContext());
        final Favorite favorite = new Favorite();

        edtText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    new MyAsyncTask(getActivity()).execute(edtText.getText().toString());
                    favorite.setName(edtText.getText().toString());
                    dbFavoriteManager.addHistory(favorite);
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void change(ImageView img, TextView txt) {
        String a = txt.getText().toString();
        if (a.equals("English")) {
            img.setImageResource(R.drawable.icon_russian);
            txt.setText("Russian");
        } else {
            img.setImageResource(R.drawable.ic_england);
            txt.setText("English");
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

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    public void speakOut() {
        String text = edtText.getText().toString();
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
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

    public class MyAsyncTask extends AsyncTask<String, String, Void> {

        Activity contextParent;
//        EditText edtWord;

        public MyAsyncTask(Activity contextParent) {
            this.contextParent = contextParent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Hàm này sẽ chạy đầu tiên khi AsyncTask này được gọi
            //Ở đây mình sẽ thông báo quá trình load bắt đâu "Start"
//            Toast.makeText(contextParent, "Start", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... params) {
            //Hàm được được hiện tiếp sau hàm onPreExecute()
            //Hàm này thực hiện các tác vụ chạy ngầm
            //Tuyệt đối k vẽ giao diện trong hàm này
            String a = Translator(params[0]);
            publishProgress(a);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //Hàm thực hiện update giao diện khi có dữ liệu từ hàm doInBackground gửi xuống
            super.onProgressUpdate(values);
            //Thông qua contextCha để lấy được control trong MainActivity
//            ProgressBar progressBar = (ProgressBar) contextParent.findViewById(R.id.prbDemo);
            //vì publishProgress chỉ truyền 1 đối số
            //nên mảng values chỉ có 1 phần tử
            //tăng giá trị của Progressbar lên
//            progressBar.setProgress(number);
            //đồng thời hiện thị giá trị là % lên TextView
            String mean = values[0];
            TextView textView = (TextView) contextParent.findViewById(R.id.txtWord);
            textView.setText(mean);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Hàm này được thực hiện khi tiến trình kết thúc
            //Ở đây mình thông báo là đã "Finshed" để người dùng biết
            //Toast.makeText(contextParent, "Okie, Finished", Toast.LENGTH_SHORT).show();
        }
    }
}
