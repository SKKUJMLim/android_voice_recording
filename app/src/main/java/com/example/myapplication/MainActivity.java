package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    MediaRecorder recorder;
    String fileName;
    MediaPlayer mediaPlayer;
    int position = 0;

    private Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.record_timer);

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // 녹음 시작
                if (recorder == null) {
                    recorder = new MediaRecorder(); // 미디어리코더 객체 생성
                }

                //파일의 외부 경로 확인
                String recordPath = getExternalFilesDir("/").getAbsolutePath();
                // 파일 이름 변수를 현재 날짜가 들어가도록 초기화. 그 이유는 중복된 이름으로 기존에 있던 파일이 덮어 쓰여지는 것을 방지하고자 함.
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                fileName = recordPath + "/" +"RecordExample_" + timeStamp + "_"+"audio.mp4";

                Log.d("RECORD", "파일 위치: " + fileName);

                //Start timer from 0
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();

                recorder.setAudioSource(MediaRecorder.AudioSource.MIC); // 오디오 입력 지정(마이크)
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);    // 출력 형식 지정
                //마이크로 들어오는 음성데이터는 용량이 크기 때문에 압축이 필요
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);   // 인코딩
                recorder.setOutputFile(fileName);  // 음성 데이터를 저장할 파일 지정

                try {
                    recorder.prepare();
                    recorder.start();
                    Toast.makeText(getApplicationContext(), "녹음시작", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {e.printStackTrace();}
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // 녹음 중지
                if (recorder != null) {
                    recorder.stop();
                    recorder.release();
                    recorder = null;
                }
                //Stop Timer, very obvious
                timer.stop();
                save(); // 녹음 파일 저장
                Toast.makeText(getApplicationContext(), "녹음중지", Toast.LENGTH_SHORT).show();
            }
        });


        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AudioListView.class);
                startActivity(intent);
            }
        });

    }

    // 녹음한 파일 저장
    public Uri save(){
        ContentValues values = new ContentValues(10);
        values.put(MediaStore.MediaColumns.TITLE, "Recorded");
        values.put(MediaStore.Audio.Media.ALBUM, "Audio_Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Ton");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Audio");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, 1);
        values.put(MediaStore.Audio.Media.IS_MUSIC, 1);
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis()/1000);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp4"); // 미디어 파일의 포맷
        values.put(MediaStore.Audio.Media.DATA, fileName); // 저장된 녹음 파일

        // ContentValues 객체를 추가할 때, 음성 파일에 대한 내용 제공자 URI 사용
        return getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
    }
}