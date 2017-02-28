package com.anwesome.app.soundvisualization.graphs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.List;

/**
 * Created by anweshmishra on 28/02/17.
 */
public class DotLineGraph extends VisualizingGraph {
    protected void drawVisualizingGraph(Canvas canvas, Paint paint, List<Integer> soundData, int maxLimit) {
        float gap= (canvas.getWidth()*1.0f)/(soundData.size()),x = gap/2,y = 2*canvas.getHeight()/3,h = canvas.getHeight()/2;
        paint.setColor(Color.parseColor("#F57F17"));
        paint.setStrokeWidth(gap);
        canvas.drawLine(0,y,canvas.getWidth(),y,paint);
        paint.setColor(Color.parseColor("#00695C"));
        paint.setStrokeWidth(gap/10);
        for(Integer data:soundData) {
            float h1 = (data*1.0f)/maxLimit;
            h1*=h;
            canvas.drawLine(x,y,x,y-h1,paint);
            canvas.drawCircle(x,y-h1-gap/3,gap/3,paint);
        }
    }
}
