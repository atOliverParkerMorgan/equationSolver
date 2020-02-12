import java.awt.geom.Rectangle2D;
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
    private void sortList(String input,List<String> sort){
        StringBuilder current = new StringBuilder();
        int i;
        for (i = 0; i<input.length() ; i++) {
            if(Character.isDigit(input.charAt(i))||input.charAt(i)=='x'){
                current.append(input.charAt(i));

            }else {
                sort.add(current.toString());
                current = new StringBuilder();
                int x = 0;
                do{
                    current.append(input.charAt(i+x));
                    x++;
                }while (allowedOperations.contains(Character.toString(input.charAt(i+x))));
                i+=x-1;
                sort.add(current.toString());
                current = new StringBuilder();
            }
        }
        sort.add(current.toString());
        for (int j = 0; j < sort.size(); j++) {
            if(Character.isDigit(sort.get(j).charAt(0))||sort.get(j).charAt(0)=='x'){
                if(j==0){
                    sort.set(j, '+' + sort.get(j));

                }else {
                    if(sort.get(j-1).equals("-")||sort.get(j-1).equals("+")) {
                        sort.set(j, sort.get(j-1) + sort.get(j));
                        sort.remove(j-1);
                        j--;
                    }else {
                        sort.set(j, "+" + sort.get(j));
                    }
                }
            }
        }

    }
    void rootAndSquareRoot(List<String> equationSide){
        calculatingSide(equationSide,"**");
        calculatingSide(equationSide,"//"); }

    void multiplyAndDivideLogic(List<String> equationSide){
        calculatingSide(equationSide,"*");
        calculatingSide(equationSide,"/");
    }
    void addAndSubtractLogic(List<String> equationSide){
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

                if(x>=0){
                    stringValueTotal = "+" + (Calculator.add(stringValueTotal,equationSide.get(i)));
                }else{
                    stringValueTotal = "-" + (Calculator.add(stringValueTotal,equationSide.get(i)));
                }

                equationSide.remove(equationSide.get(i));
                i--;
            }
        }
        equationSide.add(stringValueTotal);

    }

    private void calculatingSide(List<String> equationSide, String operator){
        int end = 0;
        while(equationSide.contains(operator)) {
            for (int i = 0; i < equationSide.size(); i++) {
                if (equationSide.get(i).equals(operator) && Character.isDigit(equationSide.get(i - 1).charAt(1)) && Character.isDigit(equationSide.get(i + 1).charAt(1))) {
                    double x = Calculator.doAction(operator,equationSide.get(i - 1), equationSide.get(i + 1));
                    if(x>=0){
                        equationSide.set(i, '+'+Double.toString(x));
                    }else {
                        equationSide.set(i, '-'+Double.toString(x));
                    }

                    equationSide.remove(equationSide.get(i - 1));
                    equationSide.remove(equationSide.get(i));
                    break;

                }
            }
            if(end == 1000){
                break;
            }
            end++;
        }
    }

    public void printEquation(){System.out.println( Arrays.toString(sortL.toArray())+" = "+Arrays.toString(sortR.toArray()));}

    private void brackets(){


    }

}