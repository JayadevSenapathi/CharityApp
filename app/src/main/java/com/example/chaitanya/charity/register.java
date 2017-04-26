package com.example.chaitanya.charity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class register extends AppCompatActivity {
    EditText name, email, phone, uname, password1, password2;
    dbBackground2 dh;
    SQLiteDatabase sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        uname = (EditText) findViewById(R.id.uname);
        password1 = (EditText) findViewById(R.id.password1);
        password2 = (EditText) findViewById(R.id.password2);

        dh = new dbBackground2(this);
        sd = dh.getWritableDatabase();
    }

    public void register(View v) {
        String nameStr = name.getText().toString();
        String emailStr = email.getText().toString();
        String phoneStr = phone.getText().toString();
        String unameStr = uname.getText().toString();
        String password1Str = password1.getText().toString();
        String password2Str = password2.getText().toString();
        String type = "register";
        if(name.getText().length() == 0) {
            name.setError("name should not be empty");
            name.requestFocus();
        }
        else if(email.getText().length() == 0) {
            email.setError("Email should not be empty ");
            email.requestFocus();
        }
        else if(phone.getText().length() != 10) {
            phone.setError("No. should be of length 10");
            phone.requestFocus();
        }
        else if(uname.getText().length() < 5) {
            uname.setError("username should be min of 6 character");
            uname.requestFocus();
        }
        else if(password1.getText().length() < 5) {
            password1.setError("password should be min of 6 character");
            password1.requestFocus();
        }
        else if(!password2.getText().toString().equals(password1.getText().toString())) {
            password2.setError("password do not match");
            password2.requestFocus();
        }
        else {
            BackgroundWorker1 backgroundWorker = new BackgroundWorker1(this);
            backgroundWorker.execute(type, nameStr, emailStr, phoneStr, unameStr, password1Str);
            dh.insert_Data(sd, phoneStr);
        }
    }
}