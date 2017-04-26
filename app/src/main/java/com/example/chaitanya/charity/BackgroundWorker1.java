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

public class BackgroundWorker1 extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String ltype;
    // String usertype;
    BackgroundWorker1 (Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://chaitanya.freeoda.com/register.php";
        if(type.equals("register"))
        {
            try {
                String name2 = params[1];
                String surname2 = params[2];
                String age2 = params[3];
                String username2 = params[4];
                String password2 = params[5];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name2,"UTF-8")+"&"
                        +URLEncoder.encode("surname","UTF-8")+"="+URLEncoder.encode(surname2,"UTF-8")+"&"
                        +URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age2,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username2,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password2,"UTF-8");
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
        alertDialog.setTitle("SignUp Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();

        Timer t=new Timer();
        t.schedule(new CloseDialodTimerTask(alertDialog),5000);

        String[] strArray = result.split(" ");
        ltype=strArray[0];
        if(ltype.equals("Error")==true)
        {
            Toast.makeText(context, "Not Registered due to Invalid data", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(context, login.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}
