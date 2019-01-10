package com.example.minko.dictionaryclone.Activity;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.minko.dictionaryclone.Fragment.FavoriteFragment;
import com.example.minko.dictionaryclone.Fragment.HistoryFragment;
import com.example.minko.dictionaryclone.Fragment.HomeFragment;
import com.example.minko.dictionaryclone.Fragment.NounFragment;
import com.example.minko.dictionaryclone.Fragment.SearchFragment;
import com.example.minko.dictionaryclone.Fragment.TranslatorFragment;
import com.example.minko.dictionaryclone.Fragment.WebFragment;
import com.example.minko.dictionaryclone.R;
import com.example.minko.dictionaryclone.Service.FloatingViewService;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        SearchFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener,
        FavoriteFragment.OnFragmentInteractionListener, TranslatorFragment.OnFragmentInteractionListener,
        NounFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener,
        WebFragment.OnFragmentInteractionListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView defaultBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find IDs for all Navigation Views
        defaultBottomNavigationView = findViewById(R.id.bottom_navigation);

        setListeners();
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
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fr_child, new SearchFragment());
        fragmentTransaction.commit();

    }

    private void setListeners() {
        defaultBottomNavigationView.setOnNavigationItemSelectedListener(this);
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
        Bundle bundle = new Bundle();
        ArrayList<String> lst = new ArrayList<>();
        if (id == R.id.home) {
            Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
        } else if (id == R.id.nouns) {
            lst.add("General Information");
            lst.add("Plurals");
            lst.add("Nouns in Sentence");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Nouns");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.article) {
            lst.add("Indefinite Article");
            lst.add("Definite Article");
            lst.add("No Article");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Article");

            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.pronoun) {
            lst.add("General Information");
            lst.add("The Use of Pronouns");
            lst.add("Pronouns in Sentence");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Pronouns");

            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.numberal) {
            lst.add("General Information");
            lst.add("Cardinal Numberals");
            lst.add("Ordinal Numberals");
            lst.add("Numberals in Sentence");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Numberals");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.adjective) {
            lst.add("General Information");
            lst.add("Degrees of comparison");
            lst.add("Adjective in Sentence");

            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Adjective");

            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.adverb) {
            lst.add("General Information");
            lst.add("Degrees of comparison");
            lst.add("Classification of Adverbs");
            lst.add("Adverb in Sentence");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Adverb");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.verb) {
            lst.add("General Information");
            lst.add("Personal/impersonal Verbs");
            lst.add("Regular/Irregular Verbs");
            lst.add("Transitive/Irregular Verbs");
            lst.add("Person and Number");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Verb");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.preposition) {
            lst.add("General Information");
            lst.add("Frequently Used");
            lst.add("Preposition in Sentence");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Preposition");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.conjunction) {
            lst.add("General Information");
            lst.add("Coordinative Conjunctions");
            lst.add("Subordinative Conjunctions");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Conjunction");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.particles) {
            lst.add("General Information");
            lst.add("Difference of Particles");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Particles");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.interjection) {
            lst.add("General Information");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Interjection");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.nav_book1) {
            lst.add("Common Phrases");
            lst.add("Meeting. Communication");
            lst.add("Trip. Journey");
            lst.add("Hotel. Service");
            lst.add("Cafe. Restaurant");
            lst.add("City. Transport");
            lst.add("Telephone. Mail");
            lst.add("Bank. Money");
            lst.add("Business Communication");
            lst.add("Health");
            lst.add("Problems");
            lst.add("Miscellaneous");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Book1");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.nav_book2) {
            lst.add("General expressions");
            lst.add("Conversation");
            lst.add("About me");
            lst.add("Trip");
            lst.add("In the city");
            lst.add("Hotel");
            lst.add("Services");
            lst.add("In guest");
            lst.add("Moneys. Business");
            lst.add("Food and drinks");
            lst.add("Shopping");
            lst.add("Entertainment");
            lst.add("Sport");
            lst.add("Tour");
            lst.add("Journey");
            lst.add("Transport");
            lst.add("Phone. Mail");
            lst.add("Health");
            lst.add("Problems");
            lst.add("Numbers");
            lst.add("Time and date");
            lst.add("Colours");
            lst.add("Weather");
            lst.add("Questions");
            lst.add("Inscriptions and signs");
            bundle.putStringArrayList("lstMenu", lst);
            bundle.putString("titleMenu", "Book2");
            NounFragment fragment = new NounFragment();
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fr_layout, fragment);
        } else if (id == R.id.nav_fast) {
            startService(new Intent(getApplicationContext(), FloatingViewService.class));
        } else if (id == R.id.nav_share) {
            share();
        } else if (id == R.id.nav_feedback) {
            sendFeedBack();
        } else if (id == R.id.nav_rate) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.hdpsolution.englishrussiandict")));
        } else if (id == R.id.nav_search) {
            fragmentTransaction.replace(R.id.fr_child, new SearchFragment());
        } else if (id == R.id.nav_history) {
            fragmentTransaction.replace(R.id.fr_child, new HistoryFragment());
        } else if (id == R.id.nav_favorite) {
            fragmentTransaction.replace(R.id.fr_child, new FavoriteFragment());
        } else if (id == R.id.transator) {
            fragmentTransaction.replace(R.id.fr_child, new TranslatorFragment());
        } else if (id == R.id.traslate_web) {
            fragmentTransaction.replace(R.id.fr_child, new WebFragment());
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void sendFeedBack() {
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

    public void share() {
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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
