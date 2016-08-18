package com.iems5722.group13;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class PlayerActivity extends Activity {

    private static final String KEY_PATH = "key_path";

    String play_address;

    @Bind(R.id.vitamio_videoView)
    VideoView vVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this))
            return;
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String LiveURL = intent.getStringExtra("play_address");
        String path = "rtmp://54.199.250.250/live/livestream" + LiveURL;

        ButterKnife.bind(this);
//        String path = getIntent().getStringExtra(KEY_PATH);
        initVideo(path);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    private void initVideo(String path){
        HashMap<String, String> options = new HashMap<>();
        options.put("rtmp_live", "1");
        vVideoView.setVideoURI(Uri.parse(path), options);
        vVideoView.setMediaController(new MediaController(this));
        vVideoView.requestFocus();

        vVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }
}
