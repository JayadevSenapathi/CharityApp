package com.example.chaitanya.charity;

    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                    }
                }
            };
            timer.start();
        }
    }