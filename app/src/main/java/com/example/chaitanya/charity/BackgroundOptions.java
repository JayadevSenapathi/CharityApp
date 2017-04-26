package com.example.chaitanya.charity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Deepu on 10-10-2016.
 */
public class BackgroundOptions extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    ProgressDialog loading;
    Context ctx;

    public BackgroundOptions(Context ctx) {
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String getdata_url = "http://chaitanya.freeoda.com/getData.php";
        String method = params[0];
        if(method.equals("options")) {
            try {
                URL url = new URL(getdata_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String response = "";
                String line = "";
                while((line = br.readLine()) != null) {
                    response += line;
                }
                JSONObject jo = new JSONObject(response);
                JSONArray result1 = jo.getJSONArray("result");
                String temp = " ";
                for(int i = 0; i < result1.length(); i++) {
                    JSONObject c = result1.getJSONObject(i);
                    String name = c.getString("name");
                    temp = temp + name + " \n";
                }
                return temp;
            }
            catch (JSONException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        loading.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(ctx, "Please Wait", null, true, true);
    }
}
