package com.anwesome.app.soundvisualization;

import android.content.Context;
import android.graphics.*;
import android.view.View;

import com.anwesome.app.soundvisualization.graphs.BarGraph;
import com.anwesome.app.soundvisualization.graphs.VisualizingGraph;
import com.anwesome.app.soundvisualization.graphs.VisualizingGraphType;

/**
 * Created by anweshmishra on 25/02/17.
 */
public class VisualizingView extends View {
    private boolean updating = false;
    private VisualizingGraph visualizingGraph;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public VisualizingView(Context context,VisualizingGraphType visualizingGraphType) {
        super(context);
        initVisualizngGraph(visualizingGraphType);
    }
    public void initVisualizngGraph(VisualizingGraphType visualizingGraphType) {
        switch (visualizingGraphType) {
            case BARGRAPH:
                this.visualizingGraph = new BarGraph();
                break;
            case CIRCLEGRAPH:
                break;
            default:
                break;
        }
    }
    public boolean isUpdating() {
        return updating;
    }
    public void updateSoundData(byte[] fftData) {
        if(visualizingGraph!=null) {
            visualizingGraph.update(fftData);
            updating = true;
            invalidate();
        }
    }
    public void onDraw(Canvas canvas) {
        if(visualizingGraph!=null) {
            visualizingGraph.draw(canvas,paint);
            updating = false;
        }
    }
}
