package ca.horatiu.slope_field_generator;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

/**
 * Created by Horatiu on 14/06/2016.
 */
public class MyOnScaleGestureListener extends
        SimpleOnScaleGestureListener {

    MainActivity main;

    public MyOnScaleGestureListener(MainActivity main){
        this.main = main;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float scaleFactor = detector.getScaleFactor();

        if (scaleFactor < 1) {
            Log.d("Zoom", "Zooming out!"); //decrease?
            if (Graph.MIN_PIXELS_PER_SQUARE > 20)
                Graph.MIN_PIXELS_PER_SQUARE-=3;
            main.refresh();
            //scaleText.setText("Zooming Out");
        } else {
            Log.d("Zoom", "Zooming in!");
            Graph.MIN_PIXELS_PER_SQUARE += 3;
            main.refresh();
            //scaleText.setText("Zooming In");
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}