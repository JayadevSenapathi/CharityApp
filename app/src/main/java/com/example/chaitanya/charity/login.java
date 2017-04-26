package com.example.chaitanya.charity;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.EditText;

    public class login extends AppCompatActivity {

        EditText   UsernameEt,  PasswordEt;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            UsernameEt = (EditText)findViewById(R.id.etUserName);
            PasswordEt = (EditText)findViewById(R.id.etPassword);
        }

        public void login(View view) {
            String username = UsernameEt.getText().toString();
            String password = PasswordEt.getText().toString();
            String type = "login";

            if( UsernameEt.getText().length()== 0) {
                UsernameEt.setError("Enter the user name");
                UsernameEt.requestFocus();
            }
            else if(PasswordEt.getText().length()== 0) {
                PasswordEt.setError("enter the password");
                PasswordEt.requestFocus();
            }
            else {
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute(type, username, password);
            }
        }
        public void signup(View v) {
            Intent i = new Intent(this, register.class);
            startActivity(i);
        }
    }
