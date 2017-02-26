package com.anwesome.app.soundvisualization;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;

import com.anwesome.app.soundvisualization.graphs.VisualizingGraph;
import com.anwesome.app.soundvisualization.graphs.VisualizingGraphType;

import java.io.FileDescriptor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by anweshmishra on 25/02/17.
 */
public class VisualizingPlayer {
    private boolean isRunning = true;
    private AtomicBoolean aBoolean = new AtomicBoolean();
    private Thread dataExtractThread;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Activity activity;
    private Visualizer visualizer;
    private VisualizingView visualizingView;
    private boolean prepared = false;
    private String fileName;
    public VisualizingPlayer(Activity activity, String fileName, VisualizingGraphType visualizingGraphType) {
        this.activity = activity;
        visualizingView = new VisualizingView(activity,visualizingGraphType);
        this.fileName = fileName;
        setupMediaPlayer();
        activity.setContentView(visualizingView);
    }
    protected void setupMediaPlayer() {
        try {
            AssetFileDescriptor assetFileDescriptor = activity.getAssets().openFd(fileName);
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            mediaPlayer.setDataSource(fileDescriptor);
            mediaPlayer.prepare();
            prepared = true;
        }
        catch (Exception ex) {

        }
    }
    public void play() {
        if(prepared && !mediaPlayer.isPlaying()) {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.start();
            visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
            visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            visualizer.setEnabled(true);
            aBoolean.set(false);
            dataExtractThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    }
                    catch (Exception ex) {

                    }
                    while(isRunning) {
                        if(!aBoolean.get() && !visualizingView.isUpdating()) {
                            aBoolean.set(true);
                            final byte[] bytes = new byte[visualizer.getCaptureSize()];
                            visualizer.getFft(bytes);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    visualizingView.updateSoundData(bytes);
                                    aBoolean.set(false);
                                }
                            });
                        }
                        try {
                            Thread.sleep(50);
                        }
                        catch (Exception ex) {

                        }
                    }
                }
            });
            dataExtractThread.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    pause();
                }
            });
        }
    }
    public void resume() {
        if(!isRunning) {
            isRunning = true;
            dataExtractThread.start();
            mediaPlayer = new MediaPlayer();
            setupMediaPlayer();
            play();
        }
    }
    public void pause() {
        if(isRunning) {
            mediaPlayer.stop();
            mediaPlayer.release();
            visualizer.setEnabled(false);
            isRunning = false;
            while (true) {
                try {
                    dataExtractThread.join();
                    break;
                } catch (Exception ex) {

                }
            }
        }
    }
}
