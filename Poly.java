/**
 * Class to represent a polynomial, e.g. 3.5x^4 + 3x^2 - 4.
 * 
 * Polynomials can be added, subtracted, multiplied, and divided.
 * 
 * This class is a skeleton. You need to provide implementations
 * for the methods here defined. Feel free however, to add additional
 * methods as you see fit.
 *
 * @author Rommel
 */

import java.util.ArrayList;
import java.util.Set;  

public class Poly{

    MyArrayList<Mono> poly = new MyArrayList<Mono>();

    /**
     * Creates a new polynomial containing a single term with the coefficient
     * and exponent passed in as arguments. E.g. when called with coefficient
     * 3.5 and exponent 2, it should create a polynomial 3.5x^2.
     * 
     * You can create additional constructors if you'd like, but it's 
     * imperative that this one exists.
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the polynomial created.
     */
    public Poly(double coef, int exp){
        this.poly.add(new Mono(coef,exp));
    }

    public Poly(){
    }

    /**
     * Adds the polynomial passed in as an argument, p, to the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this + p".
     * 
     * @param p the polynomial to add onto the polynomial on which the method is called on.
     * @return a polynomial representing the result of the addition.
     */
    public Poly add(Poly p){
        Poly retPoly = new Poly();

        //creates iterators for both poly objects
        MyIterator<Mono> itr = this.poly.iterator();
        MyIterator<Mono> itr2 = p.poly.iterator(); 

        Mono curMono = itr.next();
        Mono curMono2 = itr2.next();

        //
        while(curMono != null && curMono2 != null){
            if(curMono.exp > curMono2.exp){
                retPoly.poly.add(curMono);
                curMono = itr.next();
            }
            else if(curMono.exp < curMono2.exp ){
                retPoly.poly.add(curMono2);
                curMono2 = itr2.next();
            }
            else if(curMono.exp == curMono2.exp){
                retPoly.poly.add(new Mono((curMono.coef + curMono2.coef),(curMono.exp)));
                curMono = itr.next();
                curMono2 = itr2.next();
            }
        }
        //adds any remaining Mono objects onto retPoly
        if(curMono != null){
            while(curMono != null){
                retPoly.poly.add(curMono);
                curMono = itr.next();
            }
        }
        if(curMono2 != null){
            while(curMono2 != null){
                retPoly.poly.add(curMono2);
                curMono2 = itr2.next();
            }
        }
        return retPoly;
    }

    /**
     * Subtracts the polynomial passed in as an argument, p, from the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this - p".
     * 
     * @param p the polynomial to be subtracted from the polynomial on which the method is called on.
     * @return a polynomial representing the result of the subtraction.
     */
    public Poly subtract(Poly p){
        //Multiplies p by -1
        Poly neg = new Poly(-1,0);
        Poly negP2 = neg.multiply(p); 

        return add(negP2).remove0();
    }

    /**
     * Multiplies the polynomial passed in as an argument, p, by the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this * p".
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the result of the multiplication.
     */
    public Poly multiply(Poly p){
        //creates an array to hold all 
        MyArrayList<Poly> mult = new MyArrayList<Poly>();
        Poly multiPoly = new Poly();

        //multiples each Mono to each Mono of the p object
        for(int i = 0; i < this.poly.size(); i++){
            for(int j = 0; j < p.poly.size(); j++){
                Mono tempPoly = new Mono(this.poly.get(i).coef * p.poly.get(j).coef, this.poly.get(i).exp + p.poly.get(j).exp);
                multiPoly.poly.add(tempPoly);
            }
            mult.add(multiPoly);
            multiPoly = new Poly();
        }

        //adds all the resulting polynomials and returns result
        for(int i = 0; i < mult.size(); i++){
            multiPoly = multiPoly.add(mult.get(i));
        }

        return multiPoly;
    }

    /**
     * Divides the polynomial on which the method is called on (the "this" polynomial), by
     * the polynomial passed in as an argument, p, and returns a new polynomial
     * with the resulting quotient. I.e., it returns "this / p".
     * 
     * The division should be performed according to the polynomial long division algorithm
     * ( https://en.wikipedia.org/wiki/Polynomial_long_division ).
     * 
     * Polynomial division may end with a non-zero remainder, which means the polynomials are
     * indivisible. In this case the method should return null. A division by zero should also
     * yield a null return value.
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the quotient of the division, or null if they're indivisible.
     */    
    public Poly divide(Poly p){
        ArrayList<Poly> holdPoly = new ArrayList();
        Poly retPoly = new Poly();

        Poly newP = this.remove0();
        Poly holdP = p.remove0();

        //checks if either Poly is equal to 0 and returns appropriate division
        if(newP.poly.size() == 0 && holdP.poly.size() != 0) return new Poly(0.0,0);
        if(newP.poly.size() != 0 && holdP.poly.size() == 0) return null;
        
        
        
        int i = 0;
        while(newP.poly.size() > 0){
            if(newP.poly.get(0).exp - holdP.poly.get(0).exp <= -1){
                return null;
            }

            holdPoly.add(new Poly((newP.poly.get(0).coef / holdP.poly.get(0).coef), newP.poly.get(0).exp - holdP.poly.get(0).exp));
            newP = newP.subtract(holdP.multiply(holdPoly.get(i)));
            i++;
        }
        
        //combines the Poly objects together for return
        for(i = 0; i < holdPoly.size(); i++){
            retPoly = retPoly.add(holdPoly.get(i));
        }
        
        return retPoly;
    }

    /**
     * Removes all 0.0 monomials in an array
     * 
     * @return Poly with all exp objects with a coef of 0.0. 
     */
    public Poly remove0(){
        Poly retPoly = new Poly();
        MyIterator<Mono> itr = this.poly.iterator();

        for(Mono curMono = itr.next(); curMono != null; curMono = itr.next()){
            if(curMono.coef != 0.0){
                retPoly.poly.add(curMono);
            }
        }
        return retPoly;
    }

    /**
     * Compares the polynomial on which the method is called (the "this" polynomial), 
     * to the object passed in as argument, o. o is to be considered equal to the "this"
     * polynomial if they both represent equivalent polynomials.
     * 
     * E.g., "3.0x^4 + 0.0x^2 + 5.0" and "3.0x^4 + 5.0" should be considered equivalent.
     * "3.0x^4 + 5.0" and "3.0x^4 + 3.0" should not.
     * 
     * @param o the object to be compared against the polynomial the method is called on.
     * @return true if o is a polynomial equivalent to the one the method was called on,
     * and false otherwise.
     */
    public boolean equals(Object o){
        if(!(o instanceof Poly)) {
            return false;
        }
        // polymorphism
        Poly p2 = (Poly) o;

        //creates empty poly for adjusted p
        Poly c1Poly = remove0();
        Poly c2Poly = p2.remove0();

        
        //creates adjusted itr
        MyIterator<Mono> adj1Itr = c1Poly.poly.iterator();
        MyIterator<Mono> adj2Itr = c2Poly.poly.iterator();

        //checks if poly is empty or sizes are unequal(this part may be unnecessary)
        if(c1Poly.poly.size() == 0 && c2Poly.poly.size() == 0){
            return true;
        }
        if(c1Poly.poly.size() != c2Poly.poly.size()){
            return false;
        }

        //compares both Poly
        for(Mono curMono3 = adj1Itr.next(), curMono4 = adj2Itr.next(); curMono3 != null ||  curMono4 != null; curMono3 = adj1Itr.next(), curMono4 = adj2Itr.next()){
            if(curMono3.coef != curMono4.coef || curMono3.exp != curMono4.exp){
                return false;
            }
        }
        return true;

    }

    /**
     * Returns a textual representation of the polynomial the method is called on.
     * The textual representation should be a sum of monomials, with each monomial 
     * being defined by a double coefficient, the letters "x^", and an integer exponent.
     * Exceptions to this rule: coefficients of 1.0 should be omitted, as should "^1",
     * and "x^0".
     * 
     * Terms should be listed in decreasing-exponent order. Terms with zero-coefficient
     * should be omitted. Each exponent can only appear once. 
     * 
     * Rules for separating terms, applicable to all terms other that the largest exponent one:
     *   - Terms with positive coefficients should be preceeded by " + ".
     *   - Terms with negative coefficients should be preceeded by " - ".
     * 
     * Rules for the highest exponent term (i.e., the first):
     *   - If the coefficient is negative it should be preceeded by "-". E.g. "-3.0x^5".
     *   - If the coefficient is positive it should not preceeded by anything. E.g. "3.0x^5".
     * 
     * The zero/empty polynomial should be represented as "0.0".
     * 
     * Examples of valid representations: 
     *   - "2.0x^2 + 3.0"
     *   - "3.5x + 3.0"
     *   - "4.0"
     *   - "-2.0x"
     *   - "4.0x - 3.0"
     *   - "0.0"
     *   
     * Examples of invalid representations: 
     *   - "+2.0x^2+3.0x^0"
     *   - "3.5x -3.0"
     *   - "- 4.0 + x"
     *   - "-4.0 + x^7"
     *   - ""
     * 
     * @return a textual representation of the polynomial the method was called on.
     */
    public String toString(){
        String retStr = "";
        if(this.equals(new Poly(0,0))) return "0.0";
        retStr += poly.get(0).toString();

        for(int i = 1; i < poly.size(); i++){      
            if(poly.get(i).coef > 0) retStr += " + " + poly.get(i).toString();
            if(poly.get(i).coef < 0) retStr += " - " + new Mono(-poly.get(i).coef, poly.get(i).exp);
        }

        return retStr;
    }
}
