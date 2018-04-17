package com.example.viltsu.transitionsbetweenviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class childActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ChildFragment())
                .commit();
    }

}
