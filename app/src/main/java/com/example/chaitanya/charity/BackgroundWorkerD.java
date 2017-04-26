package com.example.chaitanya.charity;

/**
 * Created by chaitanya on 16-10-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

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

/**
 * Created by Dharaneshwar on 20-Sep-16.
 */
public class BackgroundWorkerD extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String utype = "", ltype = "";
    BackgroundWorkerD(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://chaitanya.freeoda.com/donate.php";
        if(type.equals("donate")) {
            try {
                String name = params[1];
                //String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                // httpURLConnection.getRequestMethod();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("oname", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                //  username1=URLEncoder.encode(user_name,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
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
        //String[] u_type=result;
        alertDialog.setMessage(result);
        alertDialog.show();
        Timer t=new Timer();
        t.schedule(new CloseDialodTimerTask(alertDialog),5000);
        String[] strArray = result.split(" ");
        ltype=strArray[1];
        if(ltype.equals("failed")) {
            Toast.makeText(context, "Invalid User", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(context, Home_Page.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}