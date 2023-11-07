
/**
 * Main program for Polynomial Calculator.
 *
 * @author Rommel Lantajo II
 * 
 */

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Arrays;
public class Main
{

    /**
     * Main program
     */
    public static void main(String[] args){
        Scanner input = new Scanner(System.in); // scanner for System.in input

        System.out.println("Welcome to Rommel's Polynomial Calculator!");
        System.out.println("If at any point you want to stop the program, just type in QUIT.");
        //saves ans poly answer
        Poly ans = new Poly();
        while(true){
            System.out.println("Please input your first polynomial (10, 5x, 4x^4+3x^10+9, etc.): ");
            String raw1;
            Poly p1 = new Poly();

            String raw2;
            Poly p2 = new Poly();

            while(true){
                try{
                    raw1 = input.next();
                    if(raw1.toLowerCase().equals("ans")){
                        p1 = ans;
                        break;
                    }
                    if(raw1.toLowerCase().equals("quit")){
                        System.out.println("Ending program...");
                        System.out.println("Goodbye.");
                        System.exit(0);
                    }
                    String[] splitplus = raw1.toLowerCase().replaceAll("\\^", "E").split(Pattern.quote("+"));    

                    for(int i = 0; i < splitplus.length; i++){
                        String[] tempP;                    
                        if(splitplus[i].contains("xE")){                        
                            tempP = splitplus[i].split("xE");
                            double coef = Double.parseDouble(tempP[0]);
                            int exp = Integer.parseInt(tempP[1]);

                            p1 = p1.add(new Poly(coef,exp));
                        }
                        else if(splitplus[i].contains("x")){
                            tempP = splitplus[i].split("x");
                            double coef = Double.parseDouble(tempP[0]);

                            p1 = p1.add(new Poly(coef, 1));
                        }

                        else{
                            double coef = Double.parseDouble(splitplus[i]);

                            p1 = p1.add(new Poly(coef,0));
                        }
                    }
                    break;
                }
                catch(Exception e){
                    System.out.println("Error!");
                    System.out.println("Please reenter your polynomial.");
                    input.nextLine();
                }
            }
            System.out.println("p1 = " + p1.toString());
            System.out.println();
            System.out.println();
            
            input.nextLine();
    
            System.out.println("What operation do you want to do? (+,-,*,/)");            
            String op;
            while(true){
                op = input.next();
                if(op.toLowerCase().equals("quit")){
                    System.out.println("Ending program...");
                    System.out.println("Goodbye.");
                    System.exit(0);
                }
                if(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")){
                    break;
                }

                System.out.println("Error!");
                System.out.println("Please reenter your operator.");
                input.nextLine();
            }

            System.out.println("operator = " + op);
            input.nextLine();
            
            System.out.println();
            System.out.println();

            System.out.println("Please input your second polynomial (10, 5x, 4x^4+3x^10+9, etc.): ");
            while(true){
                try{
                    while(true){
                        raw2 = input.next();
                        if(raw2.toLowerCase().equals("ans")){
                            p2 = ans;
                            break;
                        }
                        if(raw2.toLowerCase().equals("quit")){
                            System.out.println("Ending program...");
                            System.out.println("Goodbye.");
                            System.exit(0);
                        }
                        String[] splitplus = raw2.toLowerCase().replaceAll("\\^", "E").split(Pattern.quote("+"));    

                        for(int i = 0; i < splitplus.length; i++){
                            String[] tempP;                    
                            if(splitplus[i].contains("xE")){                        
                                tempP = splitplus[i].split("xE");
                                double coef = Double.parseDouble(tempP[0]);
                                int exp = Integer.parseInt(tempP[1]);

                                p2 = p2.add(new Poly(coef,exp));
                            }
                            else if(splitplus[i].contains("x")){
                                tempP = splitplus[i].split("x");
                                double coef = Double.parseDouble(tempP[0]);
                                p2 = p2.add(new Poly(coef, 1));
                            }

                            else{
                                double coef = Double.parseDouble(splitplus[i]);
                                p2 = p2.add(new Poly(coef,0));
                            }
                        }

                        if(op.equals("/") && p1.divide(p2) == null){
                            System.out.println("Error! Not divisible!");
                            System.out.println("Please reenter your polynomial.");
                            p2 = new Poly();
                            input.nextLine();
                        }
                        else break;
                    }
                    break;
                }
                catch(Exception e){
                    System.out.println("Error! Incorrect Format!");
                    System.out.println("Please reenter your polynomial.");
                    input.nextLine();
                }
            }

            System.out.println("p2 = " + p2.toString());
            
            System.out.println();
            System.out.println();
            
            switch (op){
                case "+":
                    ans = p1.add(p2);
                    break;
                case "-":
                    ans = p1.subtract(p2);
                    break;
                case "*":
                    ans = p1.multiply(p2);
                    break;
                case "/":
                    ans = p1.divide(p2);
                    break;
            }
            
            System.out.println(p1.toString() + " " + op +  " " + p2.toString() + " = ");
            System.out.println(ans.toString());
            
            System.out.println();
            
            System.out.println("Current answer is now saved. Just type in ans to reuse.");
            
            System.out.println("___________________________________________________________________________________________");

        }
    }
}

