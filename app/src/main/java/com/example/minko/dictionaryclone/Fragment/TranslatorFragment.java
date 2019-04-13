package com.example.minko.dictionaryclone.Fragment;

import android.app.Activity;
import android.content.Context;
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
import com.example.minko.dictionaryclone.Service.DBHistoryManager;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.Locale;

public class TranslatorFragment extends Fragment implements TextToSpeech.OnInitListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextToSpeech tts;
    private EditText edtText;
    private TextView txtWord;
    private ImageButton iconListen;
    private Boolean status = true;

    public TranslatorFragment() {
        // Required empty public constructor
    }

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

    public static String TranslatorBack(String text) {
        TranslateOptions options = TranslateOptions.newBuilder()
                .setApiKey("AIzaSyCesRSQ_xOlB489lpIZvOxSBFRPv6a_lNk")
                .build();
        com.google.cloud.translate.Translate translate = options.getService();
        try {
            Translation translation = translate.translate(text, // dogings[0] = "dog"
                    com.google.cloud.translate.Translate.TranslateOption.sourceLanguage("ru"),
                    com.google.cloud.translate.Translate.TranslateOption.targetLanguage("en"));
            return translation.getTranslatedText();
        } catch (Exception e) {
            return e.getMessage();
        }
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
                status = !status;
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
                iconDelete.setVisibility(View.VISIBLE);
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

    public class MyAsyncTask extends AsyncTask<String, String, Void> {

        Activity contextParent;

        public MyAsyncTask(Activity contextParent) {
            this.contextParent = contextParent;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String a = "";
            if (status) {
                a = Translator(params[0]);
            } else a = TranslatorBack(params[0]);
            publishProgress(a);
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String mean = values[0];
            TextView textView = (TextView) contextParent.findViewById(R.id.txtWord);
            textView.setText(mean);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
