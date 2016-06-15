package ca.horatiu.slope_field_generator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    Graph renderScreen;
    Query query;
    ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateQuery();
        renderScreen = new Graph(this, query);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.addView(renderScreen);

        scaleGestureDetector = new ScaleGestureDetector(this, new MyOnScaleGestureListener(this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    public void generateQuery(){
        int startX = Integer.parseInt(((EditText)(findViewById(R.id.xStart))).getText().toString());
        int startY = Integer.parseInt(((EditText)(findViewById(R.id.yStart))).getText().toString());
        int endX = Integer.parseInt(((EditText)(findViewById(R.id.xEnd))).getText().toString());
        int endY = Integer.parseInt(((EditText)(findViewById(R.id.yEnd))).getText().toString());
        String expression = ((EditText)(findViewById(R.id.equation))).getText().toString();
        query = new Query(startX, endX, startY, endY, expression);
    }

    public void compute(View view){
        refresh();
        /*generateQuery();
        renderScreen = new Graph(this, query);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.removeAllViews();
        upper.addView(renderScreen);
        Log.d("Here", "Processed.");*/
    }

    public void refresh(){
        generateQuery();
        renderScreen = new Graph(this, query);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.removeAllViews();
        upper.addView(renderScreen);
    }

}
