package com.example.minko.dictionaryclone;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.minko.dictionaryclone.Fragment.AdjectiveFragment;
import com.example.minko.dictionaryclone.Fragment.AdverbFragment;
import com.example.minko.dictionaryclone.Fragment.ArticleFragment;
import com.example.minko.dictionaryclone.Fragment.Book1Fragment;
import com.example.minko.dictionaryclone.Fragment.Book2Fragment;
import com.example.minko.dictionaryclone.Fragment.ConjunctionFragment;
import com.example.minko.dictionaryclone.Fragment.FavoriteFragment;
import com.example.minko.dictionaryclone.Fragment.HistoryFragment;
import com.example.minko.dictionaryclone.Fragment.HomeFragment;
import com.example.minko.dictionaryclone.Fragment.InterjectionFragment;
import com.example.minko.dictionaryclone.Fragment.NounFragment;
import com.example.minko.dictionaryclone.Fragment.NumberalFragment;
import com.example.minko.dictionaryclone.Fragment.ParticlesFragment;
import com.example.minko.dictionaryclone.Fragment.PrepositionFragment;
import com.example.minko.dictionaryclone.Fragment.PronounFragment;
import com.example.minko.dictionaryclone.Fragment.SearchFragment;
import com.example.minko.dictionaryclone.Fragment.TranslatorFragment;
import com.example.minko.dictionaryclone.Fragment.VerbFragment;
import com.example.minko.dictionaryclone.Fragment.WebFragment;
import com.example.minko.dictionaryclone.Service.FloatingViewService;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener,
        FavoriteFragment.OnFragmentInteractionListener, TranslatorFragment.OnFragmentInteractionListener,
        NounFragment.OnFragmentInteractionListener, ArticleFragment.OnFragmentInteractionListener,
        PronounFragment.OnFragmentInteractionListener, NumberalFragment.OnFragmentInteractionListener,
        AdjectiveFragment.OnFragmentInteractionListener, AdverbFragment.OnFragmentInteractionListener,
        VerbFragment.OnFragmentInteractionListener, PrepositionFragment.OnFragmentInteractionListener,
        ConjunctionFragment.OnFragmentInteractionListener, ParticlesFragment.OnFragmentInteractionListener,
        InterjectionFragment.OnFragmentInteractionListener, Book1Fragment.OnFragmentInteractionListener,
        Book2Fragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener,
        WebFragment.OnFragmentInteractionListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
                setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        setIcon();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {


            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }
    }


    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFragment(), "Search");
        adapter.addFragment(new HistoryFragment(), "History");
        adapter.addFragment(new FavoriteFragment(), "Favoriste");
        adapter.addFragment(new TranslatorFragment(), "Translator");
        adapter.addFragment(new WebFragment(), "Web");
        viewPager.setAdapter(adapter);
    }
    private void setIcon()
    {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_history);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite);
        tabLayout.getTabAt(3).setIcon(R.drawable.icon_translator);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_web);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        if (id == R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
        } else if (id == R.id.nouns) {
            fragmentTransaction.replace(R.id.fr_layout,new NounFragment());
        } else if (id == R.id.article) {
            fragmentTransaction.replace(R.id.fr_layout,new ArticleFragment());
        } else if (id == R.id.pronoun) {
            fragmentTransaction.replace(R.id.fr_layout,new PronounFragment());
        } else if (id == R.id.numberal) {
            fragmentTransaction.replace(R.id.fr_layout,new NumberalFragment());
        } else if (id == R.id.adjective) {
            fragmentTransaction.replace(R.id.fr_layout,new AdjectiveFragment());
        } else if (id == R.id.adverb) {
            fragmentTransaction.replace(R.id.fr_layout,new AdjectiveFragment());
        } else if (id == R.id.verb) {
            fragmentTransaction.replace(R.id.fr_layout,new VerbFragment());
        } else if (id == R.id.preposition) {
            fragmentTransaction.replace(R.id.fr_layout,new PrepositionFragment());
        } else if (id == R.id.conjunction) {
            fragmentTransaction.replace(R.id.fr_layout,new ConjunctionFragment());
        } else if (id == R.id.particles) {
            fragmentTransaction.replace(R.id.fr_layout,new ParticlesFragment());
        } else if (id == R.id.interjection) {
            fragmentTransaction.replace(R.id.fr_layout,new InterjectionFragment());
        } else if (id == R.id.nav_book1) {
            fragmentTransaction.replace(R.id.fr_layout,new Book1Fragment());
        } else if (id == R.id.nav_book2) {
            fragmentTransaction.replace(R.id.fr_layout,new Book2Fragment());
        } else if (id == R.id.nav_fast) {
            startService(new Intent(getApplicationContext(), FloatingViewService.class));
        } else if (id == R.id.nav_share) {
            share();
        } else if (id == R.id.nav_feedback) {
            sendFeedBack();
        } else if (id == R.id.nav_rate) {
            Toast.makeText(getApplicationContext(), "Show", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.hdpsolution.englishrussiandict")));
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendFeedBack(){
        try {
            int i = 3 / 0;
        } catch (Exception e) {
            ApplicationErrorReport report = new ApplicationErrorReport();
            report.packageName = report.processName = getApplication().getPackageName();
            report.time = System.currentTimeMillis();
            report.type = ApplicationErrorReport.TYPE_CRASH;
            report.systemApp = false;
            ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
            crash.exceptionClassName = e.getClass().getSimpleName();
            crash.exceptionMessage = e.getMessage();
            StringWriter writer = new StringWriter();
            PrintWriter printer = new PrintWriter(writer);
            e.printStackTrace(printer);
            crash.stackTrace = writer.toString();
            StackTraceElement stack = e.getStackTrace()[0];
            crash.throwClassName = stack.getClassName();
            crash.throwFileName = stack.getFileName();
            crash.throwLineNumber = stack.getLineNumber();
            crash.throwMethodName = stack.getMethodName();
            report.crashInfo = crash;
            Intent intent = new Intent(Intent.ACTION_APP_ERROR);
            intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
            startActivity(intent);
        }

    }

    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.hdpsolution.englishrussiandict";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    /**
     * Set and initialize the view elements.
     */
    private void initializeView() {
//        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startService(new Intent(MainActivity.this, FloatingViewService.class));
//                finish();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
