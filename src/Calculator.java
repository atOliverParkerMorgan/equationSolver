public class Calculator {

    static double add(String num1, String num2){
        return Double.parseDouble(num1) + Double.parseDouble(num2);
    }
    static double subtract(String num1, String num2){
        return Double.parseDouble(num1) - Double.parseDouble(num2);
    }
    static double multiply(String num1, String num2){
        return Double.parseDouble(num1) * Double.parseDouble(num2);
    }
    static double divide(String num1, String num2){
        return Double.parseDouble(num1) / Double.parseDouble(num2);
    }

    static double squareRoot(String num1, String num2){
        return Math.pow(Double.parseDouble(num2),  1/Double.parseDouble(num1));
    }
    static double root(String num1, String num2){
        return Math.pow(Double.parseDouble(num1),  Double.parseDouble(num2));
    }

    static double doAction(String operation, String num1, String num2){
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
        return -1;
    }


}
