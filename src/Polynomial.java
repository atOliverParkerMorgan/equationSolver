import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Polynomial {
    boolean hasVar;
    List<String> stringValue;
    double realValue;

    private Polynomial(List<String> stringValue){

        // on init polynomials multiply into one number
        this.stringValue = Calculator.getOneNumber(stringValue);
        hasVar = this.stringValue.get(0).contains("x");
        for (int i = 0; i <this.stringValue.size() ; i++) {
            this.stringValue.set(i,EquationSolver.fixOperators(this.stringValue.get(i)));
        }

        if (hasVar) {
            realValue = Double.parseDouble(this.stringValue.get(0).replace("x", ""));
        } else {
            realValue = Double.parseDouble(this.stringValue.get(0));
        }


    }

    public void setStringValue(int index,String stringValue) {
        this.stringValue.set(index, stringValue);
    }

    static List<Polynomial> createPolynomials(final List<String> equationSide){
        // creating polynomials from sorted list

        List<Polynomial> polynomialsSide = new ArrayList<>();
        StringBuilder foundPolynomial = new StringBuilder();

        String lastString = "";
        int end = 0;

        for (String s : equationSide) {
            if((lastString.contains("+")||lastString.contains("-")) && (s.contains("+")||s.contains("-"))){
                polynomialsSide.add(new Polynomial(Analyzer.sortList(foundPolynomial.toString())));
                foundPolynomial = new StringBuilder();
            }
            foundPolynomial.append(s);
            end++;

            lastString = s;

            if(end == equationSide.size()){
                polynomialsSide.add(new Polynomial(Analyzer.sortList(foundPolynomial.toString())));
            }

        }
        //for (Polynomial polynomial:polynomialsSide) {
        //    for (int j = 0; j <polynomial.stringValue.size() ; j++) {
        //        polynomial.setStringValue(j,EquationSolver.fixOperators(polynomial.stringValue.get(j)));
        //    }
        //}

        return polynomialsSide;
    }



}
