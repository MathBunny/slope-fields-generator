package ca.horatiu.slope_field_generator;

/**
 * This class encapsulates a coordinate.
 * @author Horatiu Lazu
 * Created by Horatiu on 13/06/2016.
 */
public class Coordinate {
    /** x int This is the x coordinate. */
    private int x;
    /** y int This is the y coordinate. */
    private int y;

    /** This is the class constructor for coordinate.
     * @param x int This is the x coordinate.
     * @param y int This is the y coordinate.
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    /** This method returns the x.
     * @return int This is the x value.
     */
    public int getX(){
        return x;
    }

    /** This method returns the y.
     * @return int This is the y value.
     */
    public int getY(){
        return y;
    }

    /** This method sets the x value.
     * @param x int This is the x value.
     */
    public void setX(int x){
        this.x = x;
    }

    /** This method sets the y value.
     * @param y int This is the y value.
     */
    public void setY(int y){
        this.y = y;
    }
}
