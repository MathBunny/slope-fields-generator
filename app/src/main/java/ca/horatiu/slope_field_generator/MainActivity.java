package ca.horatiu.slope_field_generator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Graph renderScreen = new Graph(this);
        setContentView(renderScreen);

        //setContentView(R.layout.activity_main);

    }
}
