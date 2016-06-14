package ca.horatiu.slope_field_generator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Graph renderScreen = new Graph(this);
        renderScreen.requestFocus();
        LinearLayout upper = (LinearLayout) findViewById(R.id.LinearLayout01);
        upper.addView(renderScreen);


        //Graph renderScreen = new Graph(this); //this places Graph at the top
        //setContentView(renderScreen);

        //setContentView(R.layout.activity_main);

    }
}
