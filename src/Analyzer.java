
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// this method analyzes the user input and refactors it for further calculations
class Analyzer {

    public static final String allowedOperations = "/*-+";
     String inputLeft;
     String inputRight;
     List<String> sortL;
     List<String> sortR;
     List<Polynomial> polynomialsLeft;
     List<Polynomial> polynomialsRight;
     double solvedRight;
     double solvedLeft;


    Analyzer(String inputLeft, String inputRight){
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.solvedRight = 0;
    }

    void order(){
        this.sortR = sortList(inputRight);
        this.sortL = sortList(inputLeft);
        this.polynomialsLeft = createPolynomials(sortL);
        this.polynomialsRight = createPolynomials(sortR);
    }

    void solve(){
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
            solvedLeft = totalL;
            solvedRight = totalR;

            System.out.println(solvedLeft+" = "+solvedRight);
            if(solvedRight == solvedLeft){
                System.out.println("if x is a Real Number the equation is true when x ∈ (-∞; ∞)");
            }else{
                System.out.println("if x is a Real Number the equation hasn't got an answer");
            }
        }else {
            solvedRight = (totalR - totalL) / finalVarCoefficient;
            System.out.println("x = "+solvedRight);
        }
        System.out.println("\n");

        // the variable will be default on the left side

    }

    private List<String> sortList(final String input){
        // this method method sorts the string input into a List separating digits operators and brackets for
        // further processing
        List<String> sort= new ArrayList<>();

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(c=='(' || c==')'){ // adding all brackets
                s.append(c);
                sort.add(s.toString());
                s = new StringBuilder();
            }

            else if(c=='*'||c=='/'){ // adding operators
                if(i+1==input.length()){ // making sure that the for loop is at it's last loop
                    s.append(c);
                    sort.add(s.toString());
                    break;
                }
                if(input.charAt(i+1)=='*'&&c=='*'||input.charAt(i+1)=='/'&&c=='/'){ // checking if operator doesn't equal ** or //
                    s.append(c);
                }else { // else the operator equals * or /
                    s.append(c);
                    sort.add(s.toString());
                    s = new StringBuilder();
                }
            }
           if (c=='.'){s.append(c);}
            // plus and minus is handled for every digit meaning that every digit has a + or - operator before it
           if(Character.isDigit(c)){
                s.append(c);
               System.out.println(sort.toString());
                if(i+1==input.length()||!Character.isDigit(input.charAt(i+1))&&input.charAt(i+1)!='.'){
                    String variable = "";
                    if(i+1!=input.length()) {
                        variable = input.charAt(i + 1) == 'x' ? "x" : "";
                    }
                    if(i==0){ // this element is the first in the list
                        s = new StringBuilder("+" + s.toString());

                    }else if(i==1){
                        if(input.charAt(0)=='-') {
                            s = new StringBuilder("-" + s.toString()+variable);
                        }else{
                            s = new StringBuilder("+" + s.toString()+variable);
                        }
                    }
                    else{
                        if(input.charAt(i-s.toString().length())=='-'){
                            s = new StringBuilder("-"+s.toString()+variable);
                        }else{
                            s = new StringBuilder("+"+s.toString()+variable);
                        }
                    }
                    sort.add(s.toString());
                    s = new StringBuilder();
                }

            }else if(c=='x'){
               if(i==0){
                   s = new StringBuilder("+1x");
                   sort.add(s.toString());
                   s = new StringBuilder();
               }else if(!Character.isDigit(input.charAt(i-1))){
                   if(input.charAt(i-1)=='-') {
                       s = new StringBuilder("-1x");
                       sort.add(s.toString());
                       s = new StringBuilder();
                   }else{
                       s = new StringBuilder("+1x");
                       sort.add(s.toString());
                       s = new StringBuilder();
                   }
               }
           }
        }
        return sort;

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




    public void printEquation(){
        System.out.println("Computed equation:");
        System.out.println( Arrays.toString(sortL.toArray())+" = "+Arrays.toString(sortR.toArray()));
        System.out.println("\n");
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