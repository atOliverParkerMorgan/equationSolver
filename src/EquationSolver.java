import java.util.ArrayList;
import java.util.List;

class EquationSolver extends Analyzer{
    private List<Polynomial> polynomialsLeft;
    private List<Polynomial> polynomialsRight;
    private double solvedRight;

    EquationSolver(String leftSide, String rightSide){
        super(leftSide, rightSide);
        this.polynomialsLeft = createPolynomials(sortL);
        this.polynomialsRight = createPolynomials(sortR);
        this.solvedRight = 0;
    }
    void solve(){
        this.polynomialsLeft = createPolynomials(sortL);
        this.polynomialsRight = createPolynomials(sortR);
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
        // Debug System.out.println("solvedRight: "+totalR+"\nsolvedLeft: "+totalL+"\n Var total left: "+totalLVar);
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
            System.out.println("x = "+solvedRight);
        }
        System.out.println("\n");

        // the variable will be default on the left side
        test();
    }
    private List<Polynomial> createPolynomials(final List<String> equationSide){
        List<Polynomial> polynomialsSide = new ArrayList<>();
        StringBuilder foundPolynomial = new StringBuilder();

        String lastString = "";
        int end = 0;

        for (String s : equationSide) {
            if((lastString.contains("+")||lastString.contains("-")) && (s.contains("+")||s.contains("-"))){
                polynomialsSide.add(new Polynomial(sortList(foundPolynomial.toString())));
                foundPolynomial = new StringBuilder();
            }
            foundPolynomial.append(s);
            end++;

            lastString = s;

            if(end == equationSide.size()){
                polynomialsSide.add(new Polynomial(sortList(foundPolynomial.toString())));
            }

        }
        return polynomialsSide;
    }

    void test(){

        List<String> sortLTest = sortList(replaceVars(inputLeft,solvedRight));
        List<String> sortRTest = sortList(replaceVars(inputRight,solvedRight));
        List<Polynomial> polynomialsLTest = createPolynomials(sortLTest);
        List<Polynomial> polynomialsRTest = createPolynomials(sortRTest);

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
                if(var>0) {
                    sort.append("+").append(var);
                }else{
                    sort.append("-").append(var);
                }
            }else {

                if(c=='x' && Character.isDigit(input.charAt(i - 1))){
                    if(var>0) {
                        sort.append("*").append("+").append(var);
                    }else{
                        sort.append("-").append(var);
                    }
                }else if(c == 'x'){
                    sort.append(var);
                }else {
                    sort.append(c);
                }
            }
        }
        // checking -- => +
        return sort.toString().replaceAll("--","+");
    }

}
