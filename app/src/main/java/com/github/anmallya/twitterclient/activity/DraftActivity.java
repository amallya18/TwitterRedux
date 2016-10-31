package com.github.anmallya.twitterclient.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.github.anmallya.twitterclient.R;

import java.util.ArrayList;

public class DraftActivity extends AppCompatActivity {

    private ListView listview;
    private ArrayList<String> draftList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Drafts");
        getSupportActionBar().setElevation(5);
        draftList = new ArrayList<String>();
        readValues();
        listview = (ListView)findViewById(R.id.list_view);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, draftList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent output = new Intent();
                output.putExtra("drafts", draftList.get(position));
                setResult(RESULT_OK, output);
                finish();
            }
        });
    }

    private void readValues(){
        SharedPreferences prefs = getSharedPreferences("drafts", 0);
        int size = prefs.getInt("drafts" + "_size", 0);
        for(int i=0;i<size;i++)
            draftList.add(prefs.getString("drafts" + "_" + i, null));
    }
}
