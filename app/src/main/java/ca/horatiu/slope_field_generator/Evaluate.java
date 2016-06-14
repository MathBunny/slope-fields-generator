package ca.horatiu.slope_field_generator;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by Horatiu on 13/06/2016.
 */
public class Evaluate {

    public static void evaluate(){
        Expression e = new ExpressionBuilder("3 * sin(y) - 2 / (x - 2)")
                .variables("x", "y")
                .build()
                .setVariable("x", 2.3)
                .setVariable("y", 3.14);
        double result = e.evaluate();
    }

    public Evaluate(){

    }
}
