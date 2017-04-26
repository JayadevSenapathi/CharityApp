package com.example.chaitanya.charity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;

public class BeforeDonation extends AppCompatActivity {
    String ssel = "";
    TextView tv;
    String phoneno = " ";
    dbBackground dh;
    String name = " ";

    SQLiteDatabase sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_req);

        Intent ii = getIntent();
        Bundle bd = ii.getExtras();
        name = (String) bd.get("name");

        BackgroundOptions backgroundWorker = new BackgroundOptions(this);
        backgroundWorker.execute("options");

        dh = new dbBackground(this);
        sd = dh.getWritableDatabase();

        tv = (TextView) findViewById(R.id.orgs);

        String data = dh.get_All_Data(sd);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        String[] starr = data.split(",");
        for (int i = 0; i < starr.length; i++)
            list.add(starr[i]);
        ArrayAdapter<String> da = new ArrayAdapter<String>(this, simple_spinner_dropdown_item, list);
        spin.setAdapter(da);
        spin.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ssel = parent.getItemAtPosition(position).toString();
                String res = dh.getDetails(sd, ssel);
                String[] resarray = res.split(",");
                phoneno = dh.getPhone(sd, ssel);
                tv.setText(resarray[0]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void func(View v) {
        String type = "received";
        EditText et = (EditText)findViewById(R.id.editText);
        String amount = et.getText().toString();
        BackgroundWorker2 backgroundWorker = new BackgroundWorker2(this);
        backgroundWorker.execute(type, name, ssel,phoneno, amount);
    }
}