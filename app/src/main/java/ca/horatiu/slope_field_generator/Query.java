package ca.horatiu.slope_field_generator;

/**
 * This class encapsulates a query sent by the user.
 * Created by Horatiu on 14/06/2016.
 */
public class Query {
    /** expression String This is the expression entered by the user. */
    private String expression = "";
    /** startX int This is the starting x value. */
    private int startX;
    /** endX int This is the ending x value. */
    private int endX;
    /** startY int This is the starting y value. */
    private int startY;
    /** endY int This is the ending y value. */
    private int endY;

    /**
     * This is the class constructor for a Query.
     * @param startX int This is the starting x value.
     * @param endX int This is the ending x value.
     * @param startY int This is the starting y value.
     * @param endY int This is the ending y value.
     * @param expression String This is the expression.
     */
    public Query(int startX, int endX, int startY, int endY, String expression){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.expression = expression;
    }

    /**
     * This method returns the expression.
     * @return String This is the expression.
     */
    public String getExpression(){
        return expression;
    }

    /** This method returns the starting x coordinate.
     * @return int This is the starting x coordinate.
     */
    public int getStartX(){
        return startX;
    }

    /** This method returns the starting y coordinate.
     * @return int This is the starting y coordinate.
     */
    public int getStartY(){
        return startY;
    }

    /**
     * This is the ending x coordinate.
     * @return int This is the ending x coordinate.
     */
    public int getEndX(){
        return endX;
    }

    /** This is the ending y coordinate.
     * @return int This is the ending y coordinate.
     */
    public int getEndY(){
        return endY;
    }

}
