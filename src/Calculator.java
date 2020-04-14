import java.util.List;

public class Calculator {
    static List<String> getOneNumber(List<String> polynomial){
        calculate(polynomial,"**");
        calculate(polynomial,"//");
        calculate(polynomial,"*");
        calculate(polynomial,"/");

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

    static void calculate(List<String> expression, String operator){
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

    static String add(String num1, String num2){
        if(num1.contains("x") && num2.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) + Double.parseDouble(num2.replace("x", "")))+"x";
        }
        return Double.toString(Double.parseDouble(num1) + Double.parseDouble(num2));
    }
    static String subtract(String num1, String num2){
        if(num1.contains("x") && num2.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) - Double.parseDouble(num2.replace("x", "")))+"x";
        }
        return Double.toString(Double.parseDouble(num1) - Double.parseDouble(num2));
    }
    static String multiply(String num1, String num2){
        if(num1.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) * Double.parseDouble(num2))+"x";
        }else if(num2.contains("x")){
            return (Double.parseDouble(num1) * Double.parseDouble(num2.replace("x","")))+"x";
        }

        return Double.toString(Double.parseDouble(num1) * Double.parseDouble(num2));
    }
    static String divide(String num1, String num2){
        if(num1.contains("x")){
            return (Double.parseDouble(num1.replace("x", "")) / Double.parseDouble(num2))+"x";
        }else if(num2.contains("x")){
            return ( Double.parseDouble(num1) / Double.parseDouble(num2.replace("x","")))+"x";
        }
        return Double.toString(Double.parseDouble(num1) / Double.parseDouble(num2));
    }

    static String squareRoot(String num1, String num2){
        return Double.toString(Math.pow(Double.parseDouble(num2),  1/Double.parseDouble(num1)));
    }
    static String root(String num1, String num2){
        return Double.toString(Math.pow(Double.parseDouble(num1),  Double.parseDouble(num2)));
    }

    static String doAction(String operation, String num1, String num2){
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
