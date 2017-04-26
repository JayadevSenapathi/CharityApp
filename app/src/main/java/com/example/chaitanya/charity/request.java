package com.example.chaitanya.charity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class request extends AppCompatActivity {

    dbBackground dh;
    dbBackground2 dh2;

    SQLiteDatabase sd,sd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        dh = new dbBackground(this);
        sd = dh.getWritableDatabase();

        dh2 = new dbBackground2(this);
        sd2 = dh2.getWritableDatabase();
    }

    public void rFunction(View v) {

        String type = "request";

        EditText name = (EditText)findViewById(R.id.editText8);
        EditText phone = (EditText)findViewById(R.id.editText11);
        EditText address = (EditText)findViewById(R.id.editText9);
        EditText reason = (EditText)findViewById(R.id.editText10);
        EditText amount = (EditText)findViewById(R.id.editText2);

        String strName = name.getText().toString();
        String strPhone = phone.getText().toString();
        String strAddress = address.getText().toString();
        String strReason = reason.getText().toString();
        String strAmount = amount.getText().toString();

        BackgroundWorkerR backgroundWorker = new BackgroundWorkerR(this);
        backgroundWorker.execute(type, strName, strPhone, strAddress, strReason, strAmount);

        String str = dh2.get_All_Data(sd2);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        String numbers[] = str.split(",");
        for(int i = 0; i < numbers.length; i++) {
            String pno = numbers[i];
            String message = strName + " requires an amount of " + strAmount + ". If u want to donate, please login to Charity application and donate there.";
            try {
                SmsManager smsOperation = SmsManager.getDefault();
                PendingIntent sentPI;
                String sent = "SMS_SENT";
                sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(sent), 0);
                smsOperation.sendTextMessage(pno, null, message, sentPI, null);
            } catch(Exception ignored){

            }
        }
    }
}