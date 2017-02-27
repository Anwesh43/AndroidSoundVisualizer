package com.anwesome.app.musicvisualization;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.app.soundvisualization.VisualizingPlayer;
import com.anwesome.app.soundvisualization.graphs.VisualizingGraphType;

public class MainActivity extends AppCompatActivity {
    VisualizingPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player = new VisualizingPlayer(this,"surc.mp3", VisualizingGraphType.CIRCLEGRAPH);
        player.play();
    }
    public void onPause() {
        super.onPause();
        player.pause();
    }
    public void onResume() {
        super.onResume();
        player.resume();
    }
}
