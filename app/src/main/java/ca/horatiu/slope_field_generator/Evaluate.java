package ca.horatiu.slope_field_generator;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 * Created by Horatiu on 13/06/2016.
 */
public class Evaluate {

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
            /*AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setCancelable(false); // This blocks the 'BACK' button
            ad.setMessage("Hello World");
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            ad.show()*/
        }
        return -Double.MAX_VALUE;
    }

    public static double evaluateFractionalSeperableDifferenial(String top, String bottom, double xValue, double yValue){
        try {
            Expression e = new ExpressionBuilder(top)
                    .variables("y")
                    .build()
                    .setVariable("y", yValue);
            Expression ee = new ExpressionBuilder(bottom)
                    .variables("x")
                    .build()
                    .setVariable("x", xValue);

            return e.evaluate() / ee.evaluate();
        }
        catch(Exception e){
            //error
        }
        return -Double.MAX_VALUE;
    }

    public Evaluate(){}
}
