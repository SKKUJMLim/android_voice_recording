package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CustomDialog extends Dialog {

    MediaRecorder recorder;
    String fileName;
    MediaPlayer mediaPlayer;
    int position = 0;


    public CustomDialog(@NonNull Context context, String fileName) {
        super(context);

        setContentView(R.layout.activity_custom_dialog);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 재생
                try {
                    if(mediaPlayer != null){    // 사용하기 전에
                        mediaPlayer.release();  // 리소스 해제
                        mediaPlayer = null;
                    }
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(fileName); // 음악 파일 위치 지정
                    mediaPlayer.prepare();  // 미리 준비
                    mediaPlayer.start();    // 재생
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  // 일시정지
                if(mediaPlayer != null) {
                    position = mediaPlayer.getCurrentPosition();    // 어디까지 재생되었는지 알아냄
                    mediaPlayer.pause();    // 일시정지
                    Toast.makeText(context, "일시정지", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 다시시작
                if(mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(position);   // 시작되는 위치
                    mediaPlayer.start();    // 시작
                    Toast.makeText(context, "다시시작", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // 정지
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.stop(); // 정지
                    Toast.makeText(context, "정지", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btn_sharefile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File recordFile = new File(fileName);

                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", recordFile);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("video/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                context.startActivity(Intent.createChooser(shareIntent,"공유"));
            }
        });

        findViewById(R.id.btn_shutdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}