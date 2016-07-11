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
 * This class renders the graph.
 * @author Horatiu Lazu
 */
public class Graph extends View {
    /** MIN_PIXELS_PER_SQUARE int This is the minimum number of pixels per square. */
    static int MIN_PIXELS_PER_SQUARE = 100; //100
    /** TANGENT_THICKNESS int This is the thickness of the tangent. */
    static final int TANGENT_THICKNESS = 3; //2
    /** GRID_THICKNESS int This is the thickness of the grid. */
    static final int GRID_THICKNESS = 1; //2
    /** paint Paint This is the paint reference variable. */
    Paint paint;
    /** query Query This is a query reference variable, used for the queries */
    Query query;
    /** startX int This is the starting x value on the graph. */
    private int startX = 0;
    /** endX int This is the ending x value on the graph. */
    private int endX = 8;
    /** startY int This is the starting y value on the graph. */
    private int startY = 0;
    /** endY int This is the ending y value on the graph. */
    private int endY = 8;
    /** height int This is the height of the graph. */
    private int height; //height
    /** width int This is the width of the graph. */
    private int width; //width
    /** startYGFX int This is the starting y in terms of graphics execution. */
    private int startYGFX; //position to start executing Y in graphics
    /** expression String This is the expression being evaluted. */
    private String expression = "";
    /** skip int This is the skip value (used for zooming). */
    int skip;

    /** This is a class constructor for the Graph class.
     * @param context Context This is the context reference.
     * @param query Query This is the query reference variable, storing the information for the query.
     */
    public Graph(Context context, Query query) {
        super(context);
        paint = new Paint();
        this.query = query;
    }

    /** This method returns the red in RGB.
     * @param color String This is the color.
     * @return int This is the red value (0, 255)
     */
    private int getRed(String color){
        return Integer.parseInt(color.substring(1, 3), 16);
    }

    /** This method returns the green value in RGB.
     * @param color String This is the color.
     * @return int This is the green value.
     */
    private int getGreen(String color){
        return Integer.parseInt(color.substring(3, 5), 16);
    }

    /** This method returns the blue value in RGB.
     * @param color String This is the color.
     * @return int This is the blue value.
     */
    private int getBlue(String color){
        return Integer.parseInt(color.substring(5, 7), 16);
    }

    /**
     * This method draws a line segment.
     * @param x int This is the x value of the segment.
     * @param y int This is the y value of the segment.
     * @param slope double This is the slope.
     * @param xCanvas int This is the x value on the canvas.
     * @param yCanvas int This is the y value on the canvas.
     * @param canvas Canvas This is the canvas where the line will be drawn.
     */
    private void drawSegment(int x, int y, double slope, int xCanvas, int yCanvas, Canvas canvas){
        //MIN_PIXELS_PER_SQUARE ...
        double maxLength = MIN_PIXELS_PER_SQUARE/2;
        int val = (int)(Math.atan(slope)*(180/Math.PI));
        int angle = Math.abs(val);

        int xLen = (int)(Math.cos(angle * Math.PI/180.0)* maxLength);
        int yLen = (int)(Math.sin(angle * Math.PI/180.0)* maxLength);

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

        String [] blue = {"#CCE5FF", "#99CCFF", "#66B2FF", "#3399FF", "#0080FF", "#0066CC", "#004C99", "#003366", "#001933"};//ascending
        String white = "#000000"; //black?
        String [] orange = {"#FFE5CC", "#FFCC99", "#FFB266", "#FF9933", "#FF8000", "#CC6600", "#994C00", "#663300", "#331900"};
        String [] purple = {"#FFCCE5", "#FF99CC", "#FF66B2", "#FF3399", "#FF007F", "#CC0066", "#99004C", "#660033", "#330019"};

        String colorChosen;

        if (slope > 50){ //Hurray!!! Look at that ^_^
            colorChosen = blue[8];
        }
        else if (slope > 10){
            colorChosen = blue[7];
        }
        else if (slope > 5){
            colorChosen = blue[6];
        }
        else if (slope > 2){
            colorChosen = blue[5];
        }
        else if (slope >= 1){
            colorChosen = blue[4];
        }
        else if (slope >= 0.8){
            colorChosen = blue[3];
        }
        else if (slope >= 0.5) {
            colorChosen = blue[2];
        }
        else if (slope > 0.25) {
            colorChosen = blue[1];
        }
        else if (slope > 0.1){
            colorChosen = blue[0];
        }
        else if (Math.abs(slope) <= 0.1){ //white
            colorChosen = white;
        }
        else if (slope > -0.25){
            colorChosen = purple[0];
        }
        else if (slope > -0.5){
            colorChosen = purple[1];
        }
        else if (slope > -0.8){
            colorChosen = purple[2];
        }
        else if (slope >= -1){
            colorChosen = purple[3];
        }
        else if (slope >= -2){
            colorChosen = purple[4];
        }
        else if (slope >= -5){
            colorChosen = purple[5];
        }
        else if (slope >= -10) {
            colorChosen = purple[6];
        }
        else if (slope > -50) {
            colorChosen = purple[7];
        }
        else{
            colorChosen = purple[8];
        }
        paint.setColor(Color.rgb(getRed(colorChosen), getGreen(colorChosen), getBlue(colorChosen)));

        for(int z = 0; z < TANGENT_THICKNESS; z++)
            canvas.drawLine(xStart+z, yStart, xEnd+z, yEnd, paint);
    }

    /** This method draws the individual slopes.
     * @param canvas Canvas This is the canvas reference.
     */
    private void drawSlopes(Canvas canvas){
        int iterX = 0;
        for(int x = MIN_PIXELS_PER_SQUARE; x <= width; x+=MIN_PIXELS_PER_SQUARE, iterX++){ //<= width-MIN_
            int xLoc = (startX + iterX * skip); //start at x, then add accordingly :-)
            int iterY = 0;
            for(int y = startYGFX; y >= 0; y-=MIN_PIXELS_PER_SQUARE, iterY++){ //<= width-MIN_
                int yLoc = (startY + iterY * skip); //this is the Y coordinate
                drawSegment(xLoc, yLoc, Evaluate.evaluateGeneralDifferential(expression, xLoc, yLoc), x, y, canvas);
            }
        }
    }

    /** This method draws the x axis text.
     * @param canvas Canvas This is the canvas reference variable.
     */
    private void drawXAxisText(Canvas canvas){
        final int yBump = 20;//10
        final int xBump = 9;
        paint.setColor(android.graphics.Color.BLACK);
        paint.setTextSize(35);
        int iter = 0;
        for(int x = MIN_PIXELS_PER_SQUARE; x <= width; x+=MIN_PIXELS_PER_SQUARE, iter++){
            String output = (startX + iter * skip) + "";
            canvas.drawText(output, x-((int)output.length())*xBump, height-yBump, paint);
        }
    }

    /**
     * This method draws the y axis text.
     * @param canvas
     */
    private void drawYAxisText(Canvas canvas){
        final int yBump = 10;
        final int xBump = 9;
        paint.setColor(android.graphics.Color.BLACK);
        paint.setTextSize(35); //start at the end
        int iter = 0;
        for(int y = startYGFX; y >= 0; y-=MIN_PIXELS_PER_SQUARE, iter++){
            String output = (startY + iter * skip) + "";
            canvas.drawText(output, xBump, y-yBump, paint);
        }
    }

    /**
     * This method draws the grid.
     * @param canvas Canvas This is the canvas reference variable.
     */
    private void drawGrid(Canvas canvas){
        paint.setColor(Color.BLACK);

        for(int x = 0; x <= width; x+= MIN_PIXELS_PER_SQUARE){
            canvas.drawLine(x, 0,  x,  height, paint);
        }
        for(int y = 0; y <= height; y+= MIN_PIXELS_PER_SQUARE){
            for(int z = 0; z < GRID_THICKNESS; z++)
                canvas.drawLine(0, y+z, width, y+z+1, paint);
            startYGFX = y;
        }
    }

    /**
     * This method generates the steps for the equation domain/range.
     */
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

    /** This method generates the graph.
     * @param canvas Canvas This is the canvas reference variable.
     */
    private void generateGraph(Canvas canvas){
        drawGrid(canvas);
    }

    /** This method updates the fields.
     *
     */
    private void updateFields(){
        startX = query.getStartX();
        startY = query.getStartY();
        endX = query.getEndX();
        endY = query.getEndY();
        expression = query.getExpression();
    }

    /**
     * This method draws the graphics, and if the zoom amount is large enough draws x and y axis text.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        updateFields();
        generateStep();
        generateGraph(canvas);
        if (MIN_PIXELS_PER_SQUARE >= 50) {
            drawXAxisText(canvas);
            drawYAxisText(canvas);
        }
        drawSlopes(canvas);
    }
}
