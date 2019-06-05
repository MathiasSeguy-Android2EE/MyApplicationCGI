package com.android2ee.formation.cgi.juinmmxvi.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.android2ee.formation.cgi.juinmmxvi.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    /***********************************************************
    *  Managing LifeCycle
    **********************************************************/
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //instanciate the view
        setContentView(R.layout.activity_main);

    }





}
