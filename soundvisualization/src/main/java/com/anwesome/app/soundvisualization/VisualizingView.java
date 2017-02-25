package com.anwesome.app.soundvisualization;

import android.content.Context;
import android.graphics.*;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anweshmishra on 25/02/17.
 */
public class VisualizingView extends View {
    private int maxLimit = 256;
    private boolean updating = false;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Integer> soundData = new ArrayList<>();
    public VisualizingView(Context context) {
        super(context);
    }
    public void setMaxLimit(int maxLimit){
        if(maxLimit!=0) {
            this.maxLimit = maxLimit;
        }
    }
    public boolean isUpdating() {
        return updating;
    }
    public void updateSoundData(byte[] fftData) {

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
        updating = true;
        invalidate();
    }
    public void onDraw(Canvas canvas) {
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
            updating = false;
            soundData = new ArrayList<>();
        }
    }
}
