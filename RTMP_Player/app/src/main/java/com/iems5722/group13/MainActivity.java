package com.iems5722.group13;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


public class MainActivity extends ActionBarActivity {

    @Bind(R.id.vitamio_videoView)
    VideoView vVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String path = "rtmp://54.199.250.250/live/livestream";

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
