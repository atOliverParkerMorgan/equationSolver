
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

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if(c=='(' || c==')'){
                s.append(c);
                sort.add(s.toString());
                s = new StringBuilder();
            }

            else if(c=='*'||c=='/'){
                if(i+1==input.length()){
                    s.append(c);
                    sort.add(s.toString());
                    break;
                }
                if(input.charAt(i+1)=='*'&&c=='*'||input.charAt(i+1)=='/'&&c=='/'){
                    s.append(c);
                }else {
                    s.append(c);
                    sort.add(s.toString());
                    s = new StringBuilder();
                }
            }

            if(Character.isDigit(c)||c=='x'){
                s.append(c);
                if(i+1==input.length()||!Character.isDigit(input.charAt(i+1))){
                    if(sort.size()==0){
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
        boolean deleteNextBracket = false;

        while (equationSide.contains("(") || equationSide.contains(")")) {
            for (int i1 = 0; i1 < equationSide.size(); i1++) {
                if (equationSide.get(i1).equals("(")) {
                    if(i1==0){
                        deleteNextBracket = true;
                    }else if(equationSide.get(i1-1).equals("+")||equationSide.get(i1-1).equals("-")){
                        deleteNextBracket = true;
                    }

                    for (int i2 = 0; i2 < equationSide.size(); i2++) {
                        if (equationSide.get(i2).equals(")")) {
                            if(i2==equationSide.size()-1&&deleteNextBracket){
                                equationSide.remove(i1);
                                equationSide.remove(i2-1);
                                break;
                            }else if((equationSide.get(i2+1).contains("+")||equationSide.get(i2+1).contains("-")) && deleteNextBracket){
                                equationSide.remove(i1);
                                equationSide.remove(i2-1);
                                break;
                            }
                        }

                    }
                }
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

        while(equationSide.contains(operator)) {
            for (int i = 0; i < equationSide.size(); i++) {

                if (equationSide.get(i).equals(operator) ) {
                    if(Character.isDigit(equationSide.get(i - 1).charAt(1)) && Character.isDigit(equationSide.get(i + 1).charAt(1))) {
                        double x = Calculator.doAction(operator, equationSide.get(i - 1), equationSide.get(i + 1));
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