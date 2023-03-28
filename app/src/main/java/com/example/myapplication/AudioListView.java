package com.example.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AudioListView extends AppCompatActivity {

    private File file;
    private List myList;

    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트 뷰 참조 및 Adapter 달기
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        myList = new ArrayList();
        String recordPath = getExternalFilesDir("/").getAbsolutePath();
        file = new File(recordPath + "");

        File list[] = file.listFiles();

        for(int i= 0; i < list.length; i++){
            adapter.addItem(list[i].getName(), R.drawable.record_btn_stopped, recordPath + "/" + list[i].getName());
        }

        adapter.notifyDataSetChanged();

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 뒤로가기
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }



}