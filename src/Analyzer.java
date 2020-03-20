
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
    Analyzer(String inputLeft, String inputRight){
        this.inputLeft = inputLeft;
        this.inputRight = inputRight;
        this.sortL = new ArrayList<>(){};
        this.sortR = new ArrayList<>(){};
    }

    void order(){
        sortList(inputLeft,sortL);
        sortList(inputRight,sortR);
    }
    public void solve(){
        order();

        // left side
        printEquation();
        rootAndSquareRoot(sortL);
        multiplyAndDivideLogic(sortL);
        addAndSubtractLogic(sortL);


        // right side
        brackets(sortR);
        rootAndSquareRoot(sortR);
        multiplyAndDivideLogic(sortR);
        addAndSubtractLogic(sortR);
        printEquation();
    }


    private void sortList(String input,List<String> sort){
        // this method method sorts the string input into a List separating digits operators and brackets for
        // further processing


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

            // plus and minus is handled for every digit meaning that every digit has a + or - operator before it

            if(Character.isDigit(c)||c=='x'){
                s.append(c);
                if(i+1==input.length()||!Character.isDigit(input.charAt(i+1))){
                    if(sort.size()==0){ // this element is the first in the list
                        if(i==1){
                            if( input.charAt(i-1)=='-'){
                                s = new StringBuilder("-"+s.toString());
                            }else {
                                s = new StringBuilder("+" + s.toString());
                            }
                        }else {
                            s = new StringBuilder("+" + s.toString());
                        }
                    }else{
                        if(input.charAt(i-s.toString().length())=='-'){
                            s = new StringBuilder("-"+s.toString());
                        }else{
                            s = new StringBuilder("+"+s.toString());
                        }
                    }
                    sort.add(s.toString());
                    s = new StringBuilder();
                }

            }
        }

    }

    private void brackets(List<String> equationSide) {

        // find the most inner bracket
        // count the number of ( brackets
        int bracketCount = 0;
        for (String element: equationSide) {
            if(element.equals("(")){
                bracketCount ++ ;
            }
        }
        while(bracketCount>0) {
            List<String> mostInnerBracket = new ArrayList<>();
            int currentBracketCount = bracketCount;
            // find the most inner bracket
            int index = 0;
            int index2;

            for (String element : equationSide) {
                if (currentBracketCount == 1 && element.equals("(")) {
                    index2 = index+1;
                    while (!equationSide.get(index2).equals(")")) {
                        mostInnerBracket.add(equationSide.get(index2));
                        index2++;
                    }
                    bracketCount--;
                    System.out.println(Arrays.toString(mostInnerBracket.toArray()));
                    break;


                } else if (element.equals("(")) {
                    currentBracketCount--;
                }
                index++;
            }
            rootAndSquareRoot(mostInnerBracket);
            multiplyAndDivideLogic(mostInnerBracket);
            addAndSubtractLogic(mostInnerBracket);


            int removeIndex = index;
            while (!equationSide.get(removeIndex).equals(")")) {
                equationSide.remove(removeIndex);
            }
            equationSide.remove(removeIndex);

            // replace the brackets with the solved version
            int i2 = 0;
            for (int i = index; i < mostInnerBracket.size()+index; i++) {
                equationSide.add(i,mostInnerBracket.get(i2));
                i2++;
            }


        }
    }

    private void rootAndSquareRoot(List<String> equationSide){
        calculatingSide(equationSide,"**");
        calculatingSide(equationSide,"//"); }

    private void multiplyAndDivideLogic(List<String> equationSide){
        calculatingSide(equationSide,"*");
        calculatingSide(equationSide,"/");
    }
    private void addAndSubtractLogic(List<String> equationSide){
        boolean foundFirstNumber = false;
        String stringValueTotal = "";
        for (int i = 0; i < equationSide.size(); i++) {
            if(!foundFirstNumber&&Character.isDigit(equationSide.get(i).charAt(1))){
                stringValueTotal = equationSide.get(i);
                foundFirstNumber = true;
                equationSide.remove(equationSide.get(i));
                i--;
            }else if(Character.isDigit(equationSide.get(i).charAt(1))){
                double x = Calculator.add(stringValueTotal,equationSide.get(i));

                if(x>=0 ){
                    if(!Double.toString(Calculator.add(stringValueTotal,equationSide.get(i))).contains("+")){
                        stringValueTotal = "+"+ Calculator.add(stringValueTotal, equationSide.get(i));
                    }else{
                        stringValueTotal = Double.toString(Calculator.add(stringValueTotal, equationSide.get(i)));
                    }
                }else if(x<0){
                    if(!Double.toString(Calculator.add(stringValueTotal,equationSide.get(i))).contains("-")){
                        stringValueTotal = "-" + Calculator.add(stringValueTotal, equationSide.get(i));
                    }else{
                        stringValueTotal = Double.toString(Calculator.add(stringValueTotal, equationSide.get(i)));
                    }

                }

                equationSide.remove(equationSide.get(i));
                i--;
            }
        }
        equationSide.add(stringValueTotal);

    }

    private void calculatingSide(List<String> equationSide, String operator){
        // count the number of x variables
        int varCount = 1;
        int operatorCount = 0;
        // initial values can't equal so while loop can start
        // repeat loop until there are no operators in the list or the number of operators equals the number of
        // variables x
        while(equationSide.contains(operator)&&varCount!=operatorCount) {
            varCount = 0;
            operatorCount = 0;
            for (String element:equationSide) {
                if(element.equals("+x")||element.equals("-x")){
                    varCount++;
                }if(element.equals(operator)){
                    operatorCount++;
                }
            }


            for (int i = 0; i < equationSide.size(); i++) {

                if (equationSide.get(i).equals(operator) ) {
                    if(Character.isDigit(equationSide.get(i - 1).charAt(1)) && Character.isDigit(equationSide.get(i + 1).charAt(1))) {
                        double x = Calculator.doAction(operator, equationSide.get(i - 1), equationSide.get(i + 1));
                        // adding +; - logic
                        if (x >= 0) {
                            if (!Double.toString(x).contains("+")) {
                                equationSide.set(i, '+' + Double.toString(x));
                            } else {
                                equationSide.set(i, Double.toString(x));
                            }
                        } else {
                            if (!Double.toString(x).contains("-")) {
                                equationSide.set(i, '-' + Double.toString(x));
                            } else {
                                equationSide.set(i, Double.toString(x));
                            }

                        }

                        equationSide.remove(equationSide.get(i - 1));
                        equationSide.remove(equationSide.get(i));
                        break;
                    }

                }
            }
        }
    }

    public void printEquation(){System.out.println( Arrays.toString(sortL.toArray())+" = "+Arrays.toString(sortR.toArray()));}


}