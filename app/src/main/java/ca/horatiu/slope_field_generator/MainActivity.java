package ca.horatiu.slope_field_generator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    String yExpression = "";
    String xExpression = "";
    Graph renderScreen;

    private EditText equation;


    public void getStartEnd(){
        int startX = 0;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        renderScreen = new Graph(this);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.addView(renderScreen);

        //Graph renderScreen = new Graph(this); //this places Graph at the top
        //setContentView(renderScreen);

        //setContentView(R.layout.activity_main);
    }

    public void evaluateExpression(){


    }
}
