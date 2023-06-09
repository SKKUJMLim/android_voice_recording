package com.example.myapplication;

import android.app.LauncherActivity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {


    private CustomDialog customDialog;

    private ImageView iconImageView;
    private TextView titleTextView;
    private TextView contentTextView;


    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter(){

    }

    //Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 지정된 위치(Position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    // 지정된 위치(Position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Position에 위치한 데이터를 화면에 출력하는데 사용할 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        titleTextView = (TextView) convertView.findViewById(R.id.title);
        iconImageView = (ImageView) convertView.findViewById(R.id.icon);
        contentTextView = (TextView) convertView.findViewById(R.id.text);

        String title = listViewItemList.get(position).getTitleStr();
        String content = listViewItemList.get(position).getContentStr();

        //titleTextView.setText(title);
        contentTextView.setText(title);

        contentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog = new CustomDialog(context, content);
                customDialog.show();
            }
        });

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String title, int icon, String content){
        ListViewItem item = new ListViewItem();

        Log.d("log", "title === "+ title);
        Log.d("log", "content === "+ content);

        item.setTitleStr(title);
        item.setIconDrawable(icon);
        item.setContentStr(content);

        listViewItemList.add(item);
    }
}
