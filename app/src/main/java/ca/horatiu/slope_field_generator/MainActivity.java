package ca.horatiu.slope_field_generator;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.*;

/** Handles the main screen
 * @author Horatiu Lazu
 * @version 1.0
 */

public class MainActivity extends ActionBarActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    /** renderScreen Graph This is the graph */
    Graph renderScreen;
    /** query Query This is the query with the expression, domain and range */
    Query query;
    /** scaleGestureDetector This is the detector for gestures */
    ScaleGestureDetector scaleGestureDetector;
    /** mDetector GestureDetectorCompat This is for gestures like pinch to zoom */
    private GestureDetectorCompat mDetector;

    /**
     * This method is called when the screen is opened.
     * @param savedInstanceState This is the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateQuery(true);
        renderScreen = new Graph(this, query);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.addView(renderScreen);

        scaleGestureDetector = new ScaleGestureDetector(this, new MyOnScaleGestureListener(this));
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
    }

    /**
     * This is called upon a tap
     * @param event MotionEvent This is the MotionEvent reference
     * @return boolean Indicates if it was touched
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        this.mDetector.onTouchEvent(event);
        return true;
    }

    /**
     * This method generates the query for the graph to render
     * @param shouldChangeExpression This indicates if the expression should be changed
     */
    public void generateQuery(boolean shouldChangeExpression){
        int startX = Math.min(Integer.parseInt(((EditText)(findViewById(R.id.xStart))).getText().toString()), Integer.parseInt(((EditText)(findViewById(R.id.xEnd))).getText().toString()));
        int startY = Math.min(Integer.parseInt(((EditText)(findViewById(R.id.yStart))).getText().toString()), Integer.parseInt(((EditText)(findViewById(R.id.yEnd))).getText().toString()));
        int endX = Math.max(Integer.parseInt(((EditText)(findViewById(R.id.xStart))).getText().toString()), Integer.parseInt(((EditText)(findViewById(R.id.xEnd))).getText().toString()));
        int endY = Math.max(Integer.parseInt(((EditText)(findViewById(R.id.yStart))).getText().toString()), Integer.parseInt(((EditText)(findViewById(R.id.yEnd))).getText().toString()));
        if (shouldChangeExpression) {
            String expression = ((EditText) (findViewById(R.id.equation))).getText().toString();
            if (!Evaluate.validateExpression(expression)) {
                //error!
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error! Invalid Expression");
                alertDialog.setMessage("Please fix your expression to only use x and y variables");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
            }
            query = new Query(startX, endX, startY, endY, expression);
            return;
        }
        query = new Query(startX, endX, startY, endY, query.getExpression());
    }

    /**
     * This method calls refresh
     * @param view View This is the view
     */
    public void compute(View view){
        refresh(true);
    }

    /**
     * This app refreshes the view
     * @param shouldChangeExpression
     */
    public void refresh(boolean shouldChangeExpression){
        generateQuery(shouldChangeExpression);
        renderScreen = new Graph(this, query);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.removeAllViews();
        upper.addView(renderScreen);
    }


    /**
     * This indicates if a tap is currently down
     * @param event
     * @return boolean If it is currently down
     */
    @Override
    public boolean onDown(MotionEvent event) {
        Log.d("Sensor","onDown: " + event.toString());
        return true;
    }

    /**
     * This indicates if there was a fling
     * @param event1
     * @param event2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        Log.d("Sensor", "onFling: " + event1.toString()+event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d("Sensor", "onLongPress: " + event.toString());
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.d("Sensor", "onScroll: " + e1.toString()+e2.toString());
        final int skipVal = renderScreen.skip;
        if (distanceX < 0) {
            ((EditText) findViewById(R.id.xStart)).setText(query.getStartX()+skipVal + "");
            ((EditText) findViewById(R.id.xEnd)).setText(query.getEndX()+skipVal + "");
        }
        else{
            ((EditText) findViewById(R.id.xStart)).setText(query.getStartX()-skipVal +"");
            ((EditText) findViewById(R.id.xEnd)).setText(query.getEndX()-skipVal+"", TextView.BufferType.EDITABLE);
        }
        if (distanceY > 0) {
            ((EditText) findViewById(R.id.yStart)).setText(query.getStartY()+skipVal+"", TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.yEnd)).setText(query.getEndY()+skipVal+"", TextView.BufferType.EDITABLE);
        }
        else{
            ((EditText) findViewById(R.id.yStart)).setText(query.getStartY()-skipVal+"", TextView.BufferType.EDITABLE);
            ((EditText) findViewById(R.id.yEnd)).setText(query.getEndY()-skipVal+"", TextView.BufferType.EDITABLE);
        }
        refresh(false);
        try{
            Thread.sleep(100);
        }
        catch(InterruptedException e){}
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d("Sensor", "onShowPress: " + event.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d("Sensor", "onSingleTapUp: " + event.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d("Sensor", "onDoubleTap: " + event.toString());
        ((EditText) findViewById(R.id.yStart)).setText("-10", TextView.BufferType.EDITABLE);
        ((EditText) findViewById(R.id.yEnd)).setText("10", TextView.BufferType.EDITABLE);
        ((EditText) findViewById(R.id.xStart)).setText("-10");
        ((EditText) findViewById(R.id.xEnd)).setText("10");
        Graph.MIN_PIXELS_PER_SQUARE = 100;
        refresh(false);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d("Sensor", "onDoubleTapEvent: " + event.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d("Sensor", "onSingleTapConfirmed: " + event.toString());
        return true;
    }

}
