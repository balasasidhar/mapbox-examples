package com.balasasidhar.mapboxdemos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.examplesListView)
    ListView mListView;

    private String[] examplesList = {"Simple Map", "Marker Annotation", "User Location"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, examplesList);
        mListView.setAdapter(listAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent mIntent;
        switch (position) {
            case 1: {
                mIntent = new Intent(this, MarkerActivity.class);
                break;
            }
            case 2: {
                mIntent = new Intent(this, UserLocationActivity.class);
                break;
            }
            default:
                mIntent = new Intent(this, SimpleMapActivity.class);
        }
        startActivity(mIntent);
    }
}
