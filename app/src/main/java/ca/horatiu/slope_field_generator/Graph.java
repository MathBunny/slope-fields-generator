package ca.horatiu.slope_field_generator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

/**
 * Created by Horatiu on 13/06/2016.
 */
public class Graph extends View {
    static final int MIN_PIXELS_PER_SQUARE = 100;
    static final int TANGENT_THICKNESS = 2;
    Paint paint;
    Query query;

    private int startX = 0;
    private int endX = 8;
    private int startY = 0;
    private int endY = 8;
    private int height; //height
    private int width; //width
    private int startYGFX; //position to start executing Y in graphics
    private String expression = "";


    int skip;

    public Graph(Context context, Query query) {
        super(context);
        paint = new Paint();
        this.query = query;
    }

    public void setExpression(){

    }

    private void drawSegment(int x, int y, double slope, int xCanvas, int yCanvas, Canvas canvas){
        //MIN_PIXELS_PER_SQUARE ...
        double maxLength = MIN_PIXELS_PER_SQUARE/2;
        int val = (int)(Math.atan(slope)*(180/Math.PI));
        int angle = Math.abs(val);

        int xLen = (int)(Math.cos(angle * Math.PI/180.0)*maxLength);
        int yLen = (int)(Math.sin(angle * Math.PI/180.0)*maxLength);
        Log.d("Angles",  xLen + " " + yLen + " " + angle);
        Log.d("Length",  maxLength + "");

        int xStart;
        int xEnd;
        int yStart;
        int yEnd;

        xStart = xCanvas-xLen;
        xEnd = xCanvas+xLen;

        if (val < 0){
            yStart = yCanvas-yLen;
            yEnd = yCanvas+yLen;
        }
        else if (val > 0){
            yStart = yCanvas+yLen;
            yEnd = yCanvas-yLen;
        }
        else{ //flat line!
            xStart = xCanvas-(int)maxLength;
            xEnd = xCanvas+(int)maxLength;
            yStart = yCanvas;
            yEnd = yCanvas;
        }
        if (slope > 2)
         paint.setColor(Color.BLACK);
        else if (slope < 2 && slope >= 1)
            paint.setColor(Color.BLUE);
        else if (slope > 0){
            paint.setColor(Color.CYAN);
        }
        else if (slope < 0 && slope >= -1){
            paint.setColor(Color.MAGENTA);
        }
        else{
            paint.setColor(Color.RED);
        }

        for(int z = 0; z < TANGENT_THICKNESS; z++)
            canvas.drawLine(xStart+z, yStart+z, xEnd+z, yEnd+z, paint); //#HOPE!

        //you can go along the diagonal.. halfway

    }

    private void drawSlopes(Canvas canvas){
        int iterX = 0;
        for(int x = MIN_PIXELS_PER_SQUARE; x <= width; x+=MIN_PIXELS_PER_SQUARE, iterX++){ //<= width-MIN_
            int xLoc = (startX + iterX * skip); //start at x, then add accordingly :-)
            int iterY = 0;
            for(int y = startYGFX; y >= 0; y-=MIN_PIXELS_PER_SQUARE, iterY++){ //<= width-MIN_
                int yLoc = (startY + iterY * skip); //this is the Y coordinate

                double [] arr = {-2.5, -2, -0.6, 0.2, 1, 2.2};
                drawSegment(xLoc, yLoc, arr[(int)(Math.random()*6)], x, y, canvas); //drawSegment(0, 0, 2, xLoc, yLoc, canvas);
                //canvas.drawText(xLoc+" " + yLoc, x, y, paint);

            }
        }

    }

    private void drawXAxisText(Canvas canvas){
        final int yBump = 20;//10
        final int xBump = 9;
        paint.setColor(android.graphics.Color.BLACK);
        paint.setTextSize(35);
        int iter = 0;
        for(int x = MIN_PIXELS_PER_SQUARE; x <= width; x+=MIN_PIXELS_PER_SQUARE, iter++){ //<= width-MIN_
            String output = (startX + iter * skip) + ""; //start at x, then add accordingly :-)
            canvas.drawText(output, x-((int)output.length())*xBump, height-yBump, paint);
        }
    }

    private void drawYAxisText(Canvas canvas){
        final int yBump = 10;
        final int xBump = 9;
        paint.setColor(android.graphics.Color.BLACK);
        paint.setTextSize(35); //start at the end
        int iter = 0;
        for(int y = startYGFX; y >= 0; y-=MIN_PIXELS_PER_SQUARE, iter++){ //<= width-MIN_
            String output = (startY + iter * skip) + "";
            canvas.drawText(output, xBump, y-yBump, paint);
        }
    }

    //draws the line
    private void drawGrid(Canvas canvas){
        paint.setColor(Color.BLACK);

        for(int x = 0; x <= width; x+= MIN_PIXELS_PER_SQUARE){
            canvas.drawLine(x, 0,  x,  height, paint);
        }
        for(int y = 0; y <= height; y+= MIN_PIXELS_PER_SQUARE){
            for(int z = 0; z < 4; z++)
                canvas.drawLine(0, y+z, width, y+z+1, paint);
            startYGFX = y;
        }
        //canvas.save();
        //Log.d("OK", "OK __ ADDED" +skip);
    }

    private void generateStep(){
        height = getHeight(); //divide by 2!
        width = getWidth();
        int stepHorizontal = ((endX-startX+2)*MIN_PIXELS_PER_SQUARE)/width+1; //bug: 0-500
        int stepVertical = ((endY-startY+2)*MIN_PIXELS_PER_SQUARE)/height+1;
        //Log.d("skipGap", "Gap: " + stepHorizontal + " " + stepVertical);

        skip = Math.max(stepHorizontal, stepVertical); //okay done. fix this in the future by centering.
        if (skip == 0)
            skip = 1;
    }

    private void generateGraph(Canvas canvas){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap bmp = Bitmap.createBitmap(getWidth(), getHeight(), conf);

        /*for(int x = 0; x < getWidth(); x+= 10){
            for(int y = 0; y < getHeight(); y+= 10){
                for(int z = 0; z <= 10; z++)
                    if (x+z < getWidth() && y+z < getHeight())
                     bmp.setPixel(x+z, y+z, Color.BLACK);
            }
        }
        Log.d("OK", "HERE" + getHeight());*/
        //canvas.drawBitmap(bmp, new Matrix(), null);
        drawGrid(canvas);
    }

    private void updateFields(){
        startX = query.getStartX();
        startY = query.getStartY();
        endX = query.getEndX();
        endY = query.getEndY();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateFields();
        generateStep();
        generateGraph(canvas);
        drawXAxisText(canvas);
        drawYAxisText(canvas);
        drawSlopes(canvas);
    }
}
