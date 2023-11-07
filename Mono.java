
/**
 * Write a description of class Monomial here.
 *
 * @author Rommel
 * @version (a version number or a date)
 */
public class Mono
{
    // instance variables - replace the example below with your own
    double coef;
    int exp;

    /**
     * Constructor for objects of class Monomial
     */
    public Mono(double coef, int exp){
        this.coef = coef;
        this.exp = exp;
    }

    public String toString(){

        if(coef != 1 && coef != -1 && coef != 0){ // not 0,1, -1 
            if(exp > 1 || exp <= -1)
                return coef + "x^" + exp; //ex: 2x^4, 3x^-2, 2x^-1 
            else if(exp == 1)
                return coef + "x";  //ex: 3x, 5x
            else if(exp == 0)
                return coef+""; // ex:  3.0, -2.0
        }
        if(coef == 1){
            if(exp > 1 || exp <=-1) 
                return "x^" + exp; //ex: x^4, x^-1
            else if(exp == 1)
                return "x"; //ex: x
            else if(exp == 0)
                return "1.0"; // ex: 1 like what did you expect
        }
        if(coef == -1){
            if(exp > 1 || exp <=-1)
                return "-x^" + exp; //ex: x^4, x^-1
            else if(exp == 1)
                return "-x"; //ex: x
            else if(exp == 0)
                return "-1.0"; // ex: insanity is repeating a task and expecting a different result
        }
        return "";
    }
}
