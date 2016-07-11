package ca.horatiu.slope_field_generator;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

/**
 * This class is used for gestures.
 * Created by Horatiu on 14/06/2016.
 */
public class MyOnScaleGestureListener extends SimpleOnScaleGestureListener{
    /** This variable is a reference to the MainActivity. */
    MainActivity main;

    /**
     * THis is the class constructor.
     * @param main MainActivity This is a MainActivity reference variable.
     */
    public MyOnScaleGestureListener(MainActivity main){
        this.main = main;
    }

    /** This method is used for scaling. If the scalefactor is decreasing then we reduce the minimum amount of pixels per square.
     * @param detector ScaleGestureDetector This is used as a reference variable.
     * @return boolean This indicates if any scaling gestures have been detected.
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();
        if (scaleFactor < 1) {
            if (Graph.MIN_PIXELS_PER_SQUARE > 10)
                Graph.MIN_PIXELS_PER_SQUARE-=3;

            main.refresh(false);
        } else {
            Graph.MIN_PIXELS_PER_SQUARE += 3;
            main.refresh(false);
        }
        return true;
    }

    /** This method indicates when scaling has begun.
     * @param detector ScaleGestureDetector This is used as a reference variable.
     * @return boolean This indicates if any scaling gestures have been started.
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    /** This method indicates when scaling has ended.
     * @param detector ScaleGestureDetector This is used as a reference variable.
     * @return boolean This indicates if any scaling gestures have ended.
     */
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {}
}
