package com.anwesome.app.soundvisualization.graphs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.anwesome.app.soundvisualization.VisualizingPlayer;

import java.util.*;
/**
 * Created by anweshmishra on 26/02/17.
 */
public class BarGraph extends VisualizingGraph{

    public BarGraph() {

    }
    protected void drawVisualizingGraph(Canvas canvas,Paint paint,List<Integer> soundData,int maxLimit) {
        int h = canvas.getHeight()/2;
        paint.setColor(Color.parseColor("#e53935"));
        paint.setStrokeWidth(canvas.getWidth()/20);
        canvas.drawLine(0,h,canvas.getWidth(),h,paint);
        if(soundData.size()>0 && maxLimit!=0) {
            float gap = canvas.getWidth()/soundData.size();
            float x = gap/2;
            paint.setColor(Color.parseColor("#00BCD4"));
            for (Integer data : soundData) {
                int y = h - ((data*h)/maxLimit);
                canvas.drawLine(x,h,x,y,paint);
                x+=gap;
            }
        }
    }
}
