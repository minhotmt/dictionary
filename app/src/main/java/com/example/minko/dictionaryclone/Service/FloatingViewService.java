package com.example.minko.dictionaryclone.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minko.dictionaryclone.Activity.MainActivity;
import com.example.minko.dictionaryclone.Fragment.TranslatorFragment;
import com.example.minko.dictionaryclone.R;

public class FloatingViewService extends Service implements TextToSpeech.OnInitListener {

    WindowManager.LayoutParams mWindowsParams;
    private Context mContext;
    private WindowManager mWindowManager;
    private View mView;
    private boolean wasInFocus = true;
    private TextToSpeech tts;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mView = LayoutInflater.from(this).inflate(R.layout.widget_fast, null);
        //The root element of the collapsed view layout

        //Set the close button
        ImageView closeButtonCollapsed = mView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });

        //Open the application on thi button click
        Button openButton = (Button) mView.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the application  click.
                Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //close the service and remove view from the view hierarchy
                stopSelf();
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        allAboutLayout(intent);
        moveView();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        if (mView != null) {
            mWindowManager.removeView(mView);
        }
        super.onDestroy();
    }

    private void moveView() {
        final View collapsedView = mView.findViewById(R.id.collapse_view);
        //The root element of the expanded view layout
        final View expandedView = mView.findViewById(R.id.expanded_container);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int width = (int) (metrics.widthPixels * 0.7f);
        int height = (int) (metrics.heightPixels * 0.45f);

        mWindowsParams = new WindowManager.LayoutParams(
                width,//WindowManager.LayoutParams.WRAP_CONTENT,
                height,//WindowManager.LayoutParams.WRAP_CONTENT,
                //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,

                (Build.VERSION.SDK_INT <= 25) ? WindowManager.LayoutParams.TYPE_PHONE : WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, // Not displaying keyboard on bg activity's EditText
                //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //Not work with EditText on keyboard
                PixelFormat.TRANSLUCENT);
        ImageView closeButton = mView.findViewById(R.id.close_button);
        ImageView closeButtonCollapsed = mView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close the service and remove the from from the window
                stopSelf();
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
        Button openButton = (Button) mView.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the application  click.
                Intent intent = new Intent(FloatingViewService.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //close the service and remove view from the view hierarchy
                stopSelf();
            }
        });
        mWindowsParams.gravity = Gravity.TOP | Gravity.LEFT;
        //params.x = 0;
        mWindowsParams.y = 100;
        mWindowManager.addView(mView, mWindowsParams);

        mView.setOnTouchListener(new View.OnTouchListener() {
            long startTime = System.currentTimeMillis();
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (System.currentTimeMillis() - startTime <= 300) {
                    return false;
                }
                if (isViewInBounds(mView, (int) (event.getRawX()), (int) (event.getRawY()))) {
                    editTextReceiveFocus();
                } else {
                    editTextDontReceiveFocus();
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = mWindowsParams.x;
                        initialY = mWindowsParams.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);


                        //The check for Xdiff <10 && YDiff< 10 because sometime elements moves a little while clicking.
                        //So that is click event.
                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mWindowsParams.x = initialX + (int) (event.getRawX() - initialTouchX);
                        mWindowsParams.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mView, mWindowsParams);
                        break;
                }
                return false;
            }
        });
    }

    private boolean isViewInBounds(View view, int x, int y) {
        Rect outRect = new Rect();
        int[] location = new int[2];
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0], location[1]);
        return outRect.contains(x, y);
    }

    private void editTextReceiveFocus() {
        if (!wasInFocus) {
            mWindowsParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            mWindowManager.updateViewLayout(mView, mWindowsParams);
            wasInFocus = true;
        }
    }

    private boolean isViewCollapsed() {
        return mView == null || mView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    private void editTextDontReceiveFocus() {
        if (wasInFocus) {
            mWindowsParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
            mWindowManager.updateViewLayout(mView, mWindowsParams);
            wasInFocus = false;
        }
    }

    private void allAboutLayout(Intent intent) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.widget_fast, null);

        final EditText edtWord = mView.findViewById(R.id.edtText);
        final TextView tvMean = mView.findViewById(R.id.txtWord);
        final TextView tv1 = mView.findViewById(R.id.txt1);
        final TextView tv2 = mView.findViewById(R.id.txt2);
        final ImageView img1 = mView.findViewById(R.id.img1);
        final ImageView img2 = mView.findViewById(R.id.img2);
        final ImageButton imgChange = mView.findViewById(R.id.imgChange);
        final ImageButton imgReset = mView.findViewById(R.id.imgReset);
        final ImageButton imgListen = mView.findViewById(R.id.imgListen);
        tts = new TextToSpeech(getApplicationContext(), this);

        edtWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
            }
        });

        edtWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtWord.getText().toString().equals("")) {
                    imgListen.setVisibility(View.INVISIBLE);
                } else imgListen.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change(img1, tv1);
                change(img2, tv2);
            }
        });

        imgReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtWord.setText("");
            }
        });

        edtWord.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    //Translate api
                    new MyAsyncTask().execute(edtWord.getText().toString());
                    imgListen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = edtWord.getText().toString();
                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    });
                    return true;
                }
                return false;
            }
        });


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

    }

    public class MyAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String a = TranslatorFragment.Translator(strings[0]);
            publishProgress(a);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String mean = values[0];
            TextView textView = mView.findViewById(R.id.txtWord);
            textView.setText(mean);
        }

    }
}
