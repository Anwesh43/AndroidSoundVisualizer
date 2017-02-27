package com.anwesome.app.soundvisualization.graphs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

/**
 * Created by anweshmishra on 27/02/17.
 */
public class CircularGraph extends VisualizingGraph{
    protected void drawVisualizingGraph(Canvas canvas, Paint paint, List<Integer> soundData, int maxLimit) {
        int w = canvas.getWidth()/2,h = canvas.getHeight()/2;
        float pivotX = w/2,pivotY = h/2,r = w/3;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#00BFA5"));
        paint.setStrokeWidth(r/10);
        if(soundData.size()>0) {
            int deg = 0, gap = 360 / soundData.size();
            for (Integer data : soundData) {
                float radius = r*((data*1.0f)/maxLimit);
                if(radius>r) {
                    continue;
                }
                float x = pivotX +  radius * (float) Math.cos(deg * Math.PI / 180), y = pivotY + radius * (float) Math.sin(deg * Math.PI / 180);
                canvas.drawLine(pivotX,pivotY,x,y,paint);
                deg+=gap;
            }
        }
    }
}
