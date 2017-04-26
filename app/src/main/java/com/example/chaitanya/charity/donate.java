package com.example.chaitanya.charity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class donate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        Intent i=new Intent();
        String sss=i.getExtras().toString();
    }

    public void dFunction(View v) {
    }

}
