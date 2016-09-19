package ca.horatiu.slope_field_generator;
import android.content.DialogInterface;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;

/**
 * This utlility class evaluates expressions.
 * @author Horatiu Lazu
 * Created by Horatiu on 13/06/2016.
 */
public class Evaluate {

    /** This method will evaluate the differential.
     * @param expression String This is the expression.
     * @param xValue int This is the x value.
     * @param yValue int This is the y value.
     * @return double This is the return value (evaluated).
     */
    public static double evaluateGeneralDifferential(String expression, double xValue, double yValue){
        try {
            Expression e = new ExpressionBuilder(expression)
                    .variables("x", "y")
                    .build()
                    .setVariable("x", xValue)
                    .setVariable("y", yValue);
            return e.evaluate();
        }
        catch(Exception e){
        }
        return -Double.MAX_VALUE;
    }

    /** This method will validate the expression.
     * @param expression String This is the expression.
     * @return boolean This indicates true/false if the expression is valid.
     */
    public static boolean validateExpression(String expression){
        try {
            Expression e = new ExpressionBuilder(expression)
                    .variables("x", "y")
                    .build();

            ValidationResult res = e.validate(false);
            return res.isValid();
        }
        catch(Exception e){
            return false;
        }
    }
    public Evaluate(){}
}
