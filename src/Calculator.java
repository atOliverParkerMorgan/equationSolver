import java.util.ArrayList;
import java.util.List;

class Calculator {
    static List<String> getOneNumber(List<String> polynomial){
        calculate(polynomial,"//");
        calculate(polynomial,"**");
        calculate(polynomial,"/");
        calculate(polynomial,"*");


        return polynomial;
    }

    static double[] addUpPolynomials(List<Polynomial> polynomials){
        double total = 0;
        double totalVar = 0;

        for (Polynomial pR:polynomials) {
            if (!pR.hasVar) {
                total += pR.realValue;
            } else {
                totalVar += pR.realValue;
            }
        }
        return new double[]{total, totalVar};
    }

    strictfp static List<Polynomial> multiplyBrackets(List<Polynomial> polynomials1, List<Polynomial> polynomials2){
        double[] t1 = addUpPolynomials(polynomials1);
        double[] t2 = addUpPolynomials(polynomials2);
        String x1 = t1[1]==0?"0":"x";
        String x2 = t2[1]==0?"0":"x";
        // String xPowered = x1.equals("x") && x2.equals("x")?"**2":"";

        String varTotal1 = t1[1]==0?"1":Double.toString(t1[1]);
        String varTotal2 = t2[1]==0?"1":Double.toString(t2[1]);
        String total1 = t1[0]==0?"1":Double.toString(t1[0]);
        String total2 = t2[0]==0?"1":Double.toString(t2[0]);

        // multiplication with in brackets
        // (vT1+t1)*(vT2+t2) = vT1*vT2+vT1*t2+t1*vT2+t1*t2;
        // varTotal1 * varTotal2 + varTotal1 * total2 + total1 * varTotal2 + total1 * total2
        List<String> output = new ArrayList<>();

        output.add(varTotal1); output.add("*"); output.add(x1); output.add("*");
        output.add(varTotal2); output.add("*"); output.add(x2);

        output.add(varTotal1); output.add("*"); output.add(x1); output.add("*");
        output.add(total2);

        output.add(total1); output.add("*");
        output.add(varTotal2); output.add("*");output.add(x2);

        output.add(total1); output.add("*");
        output.add(total2);

        return Polynomial.createPolynomials(output);
    }

    private static void calculate(List<String> expression, String operator){
        while(expression.contains(operator)) {
            for (int i = 0; i < expression.size(); i++) {

                if (expression.get(i).equals(operator) ) {
                    if(Character.isDigit(expression.get(i - 1).charAt(1)) && Character.isDigit(expression.get(i + 1).charAt(1))) {
                        String stringX = Calculator.doAction(operator, expression.get(i - 1), expression.get(i + 1));
                        double x;
                        final String output;
                        if(stringX.contains("x")){
                            x = Double.parseDouble(stringX.replace("x",""));
                            output = x + "x";

                        }else {
                            x = Double.parseDouble(stringX);
                            output = Double.toString(x);
                        }

                        if (x >= 0) {
                            if (!Double.toString(x).contains("+")) {

                                expression.set(i, '+' + output);
                            } else {

                                expression.set(i,output);
                            }
                        } else {
                            if (!Double.toString(x).contains("-")) {
                                expression.set(i, '-' + output);
                            } else {
                                expression.set(i, output);
                            }

                        }

                        expression.remove(expression.get(i - 1));
                        expression.remove(expression.get(i));
                        break;

                    }


                }
            }
        }
    }
    strictfp private static String add(String num1, String num2){
        if(num1.contains("x") && num2.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) + Double.parseDouble(num2.replace("x", "")))+"x";
        }
        return Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2));
    }
    strictfp private static String subtract(String num1, String num2){
        if(num1.contains("x") && num2.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) - Double.parseDouble(num2.replace("x", "")))+"x";
        }
        return Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2));
    }
    strictfp private static String multiply(String num1, String num2){
        if(num1.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) * Double.parseDouble(num2))+"x";
        }else if(num2.contains("x")){
            return (Double.parseDouble(num1) * Double.parseDouble(num2.replace("x","")))+"x";
        }
        if(num1.equals("-0.0")||num1.equals("+0.0")){
            return "0.0";
        }else if(num2.equals("-0.0")||num2.equals("+0.0")){
            return "0.0";
        }
        return Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2));
    }
    strictfp private static String divide(String num1, String num2){
        if(num1.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) / Double.parseDouble(num2))+"x";
        }else if(num2.contains("x")){
            return ( Double.parseDouble(num1) / Double.parseDouble(num2.replace("x","")))+"x";
        }
        if(num1.equals("-0.0")||num1.equals("+0.0")){
            return "0.0";
        }else if(num2.equals("-0.0")||num2.equals("+0.0")){
            throw new java.lang.Error("can't divide by zero");
        }

        return Double.toString(Double.parseDouble(num1) / Double.parseDouble(num2));
    }

    strictfp private static String squareRoot(String num1, String num2){
        return Double.toString(Math.pow(Double.parseDouble(num2),  1/Double.parseDouble(num1)));
    }
    strictfp private static String root(String num1, String num2){
        return Double.toString(Math.pow(Double.parseDouble(num1),  Double.parseDouble(num2)));
    }

    private static String doAction(String operation, String num1, String num2){
        switch (operation){
            case "+":
                return add(num1,num2);
            case "-":
                return subtract(num1,num2);
            case "*":
                return multiply(num1,num2);
            case "/":
                return divide(num1,num2);
            case "//":
                return squareRoot(num1,num2);
            case "**":
                return root(num1,num2);
            }
        System.out.println("there is no operator: "+operation);
        return "-1";
    }


}
