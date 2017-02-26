package com.anwesome.app.soundvisualization.graphs;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 26/02/17.
 */
public class VisualizingGraph {
    private List<Integer> soundData = new ArrayList<>();
    private int maxLimit = 256;
    public void update(byte[] fftData) {
        if(fftData.length > 2) {
            soundData.add(new Integer((int)Math.log10(fftData[0])*10));
            soundData.add(new Integer((int)Math.log10(fftData[1])));
            int max = Math.max(soundData.get(0),soundData.get(1));
            for (int i = 2;i<fftData.length;i+=2 ) {
                int re = fftData[i],im = fftData[i+1];
                int f = (int)(10*Math.log10(re*re+im*im));
                if(f>max) {
                    max = f;
                }
                if(f == 0) {
                    continue;
                }
                soundData.add(f);
            }
            maxLimit = max;
        }
    }
    public void draw(Canvas canvas, Paint paint) {
        drawVisualizingGraph(canvas,paint,soundData,maxLimit);
        soundData = new ArrayList<>();
    }
    protected void drawVisualizingGraph(Canvas canvas,Paint paint,List<Integer> soundData,int maxLimit) {

    }
}
