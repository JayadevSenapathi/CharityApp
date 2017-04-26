package com.example.chaitanya.charity;

/**
 * Created by chaitanya on 16-10-2016.
 */
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;

public class BackgroundWorker2 extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String ltype,amount;
    String phoneno,name;

    BackgroundWorker2(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://chaitanya.freeoda.com/received.php";
        if(type.equals("received"))
        {
            try {
                name = params[1];
                String org = params[2];
                phoneno=params[3];
                amount=params[4];


                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("org","UTF-8")+"="+URLEncoder.encode(org,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Donation Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();
        try{
            SmsManager smsOperation = SmsManager.getDefault();
            PendingIntent sentPI;
            String sent = "SMS_SENT";
            sentPI = PendingIntent.getBroadcast(context, 0, new Intent(sent), 0);
            smsOperation.sendTextMessage(phoneno, null, name + " has agreed to donate Rupees: " + amount, sentPI, null);
            Toast.makeText(context, " SMS Sent Successfully ", Toast.LENGTH_SHORT).show();
        } catch(Exception e){}

        Timer t = new Timer();
        t.schedule(new CloseDialodTimerTask(alertDialog), 5000);

        String[] strArray = result.split(" ");
        ltype = strArray[0];
        if(ltype.equals("Error")) {
            Toast.makeText(context, "Not Received due to Invalid data", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Thank you for your donation", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, login.class);
            context.startActivity(intent);
        }

        //GoogleCloudMessaging googleCloudMessaging = new GoogleCloudMessaging();
        //googleCloudMessaging.send();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}