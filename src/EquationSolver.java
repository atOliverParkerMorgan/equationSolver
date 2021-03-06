import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class EquationSolver extends Analyzer{
    private double solvedRight;

    EquationSolver(String leftSide, String rightSide){
        super(leftSide, rightSide);
        this.solvedRight = 0;
    }
    void solve(){
        sortL = Brackets.solveBrackets(sortL,0,0,!sortL.contains("0F0"),null);
        sortR = Brackets.solveBrackets(sortR,0,0,!sortR.contains("0F0"),null);
        System.out.println(sortL);
        printEquation();
        List<Polynomial> polynomialsLeft = Polynomial.createPolynomials(sortL);
        List<Polynomial> polynomialsRight = Polynomial.createPolynomials(sortR);
        // adding polynomials up
        double[] totalsR =  Calculator.addUpPolynomials(polynomialsRight);
        double[] totalsL =  Calculator.addUpPolynomials(polynomialsLeft);

        double finalVarCoefficient;
        double totalR = totalsR[0];
        double totalRVar = totalsR[1];

        double totalL = totalsL[0];
        double totalLVar = totalsL[1];


        finalVarCoefficient = totalLVar - totalRVar;
        System.out.println("Answer:");
        // Debug System.out.println("solvedRight: "+totalR+"\nsolvedLeft: "+totalL+"\nVar total left: "+totalLVar+"\nVar total right: "+totalRVar);
        if(finalVarCoefficient == 0){
            solvedRight = totalR;

            System.out.println(totalL +" = "+solvedRight);
            if(solvedRight == totalL){
                String x1 =  inputLeft.contains("x")||inputRight.contains("x")?"if x is a Real Number ":"";
                String x2 = inputLeft.contains("x")||inputRight.contains("x")? " (when x ∈ (-∞; ∞))": "";
                System.out.println(x1+"the equation is always true"+x2);
            }else{
                String x1 =  inputLeft.contains("x")||inputRight.contains("x")?"if x is a Real Number ":"";
                String x2 = inputLeft.contains("x")||inputRight.contains("x")? " (is true when x ∈ {})": "";
                System.out.println(x1+"the equation hasn't got an answer"+x2);
            }
        }else {
            solvedRight = (totalR - totalL) / finalVarCoefficient;
            System.out.println("x = "+fixOperators(Double.toString(solvedRight)));
        }
        System.out.println("\n");

        // the variable will be default on the left side
        test();
    }

    private void test(){
        List<String> sortLTest = sortList(replaceVars(inputLeft,solvedRight));
        List<String> sortRTest = sortList(replaceVars(inputRight,solvedRight));
        sortLTest = Brackets.solveBrackets(sortLTest,0,0,!sortLTest.contains("0F0"),null);
        sortRTest = Brackets.solveBrackets(sortRTest,0,0,!sortRTest.contains("0F0"),null);
        List<Polynomial> polynomialsLTest = Polynomial.createPolynomials(sortLTest);
        List<Polynomial> polynomialsRTest = Polynomial.createPolynomials(sortRTest);
        double totalTestL = Calculator.addUpPolynomials(polynomialsLTest)[0];
        double totalTestR = Calculator.addUpPolynomials(polynomialsRTest)[0];
        System.out.println("Test Calculation:");
        System.out.println(totalTestL + " = " + totalTestR);
        System.out.println("the test is: " + (totalTestL == totalTestR));
        if(totalTestL!=totalTestR){
            System.out.println("Alert the error margin is: "+Math.abs(totalTestL-totalTestR)+" if this margin is small it could be caused by the nature of storing very long numbers in doubles (in computer form)");
        }
        System.out.println("\n");

    }

    private String replaceVars(String input, double var){
        StringBuilder sort = new StringBuilder();
        for (int i = 0; i <input.length() ; i++) {
            char c = input.charAt(i);
            if(c=='x'&&i==0){
                sort.append(var);
            }else {

                if(c=='x' && Character.isDigit(input.charAt(i - 1))){
                    sort.append("*").append(var);
                }else if(c == 'x'){
                    sort.append(var);
                }else {
                    sort.append(c);
                }
            }
        }
        // checking -- => +
        return fixOperators(sort.toString());
    }
    static String fixOperators(String input){
        return input.replaceAll("--", "+").replaceAll("-\\+","-" ).
                replaceAll("\\+-","-").replaceAll("\\+\\+","+");
    }
}
