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
    Paint paint;

    int startX = 0;
    int endX = 500;
    int startY = 0;
    int endY = 100;
    int height; //height
    int width; //width
    int startYGFX; //position to start executing Y in graphics

    int skip;

    public Graph(Context context) {
        super(context);
        paint = new Paint();
    }

    private void drawSegment(int x, int y, int slope, Canvas canvas){

    }

    private void drawSlopes(Canvas canvas){
        int iterX = 0;
        for(int x = MIN_PIXELS_PER_SQUARE; x <= width; x+=MIN_PIXELS_PER_SQUARE, iterX++){ //<= width-MIN_
            int xLoc = (startX + iterX * skip); //start at x, then add accordingly :-)
            int iterY = 0;
            for(int y = startYGFX; y >= 0; y-=MIN_PIXELS_PER_SQUARE, iterY++){ //<= width-MIN_
                int yLoc = (startY + iterY * skip); //this is the Y coordinate

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
        height = getHeight()/2;
        width = getWidth();
        int stepHorizontal = ((endX-startX+2)*MIN_PIXELS_PER_SQUARE)/width+1; //bug: 0-500
        int stepVertical = ((endY-startY+2)*MIN_PIXELS_PER_SQUARE)/height+1;
        Log.d("skipGap", "Gap: " + stepHorizontal + " " + stepVertical);

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        generateStep();
        generateGraph(canvas);
        drawXAxisText(canvas);
        drawYAxisText(canvas);
        drawSlopes(canvas);
    }
}
