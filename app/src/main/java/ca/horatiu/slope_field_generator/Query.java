package ca.horatiu.slope_field_generator;

/**
 * Created by Horatiu on 14/06/2016.
 */
public class Query {
    private String expression = "";
    private int startX;
    private int endX;
    private int startY;
    private int endY;

    public Query(int startX, int endX, int startY, int endY, String expression){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.expression = expression;
    }

    public String getExpression(){
        return expression;
    }

    public int getStartX(){
        return startX;
    }

    public int getStartY(){
        return startY;
    }

    public int getEndX(){
        return endX;
    }

    public int getEndY(){
        return endY;
    }

}
