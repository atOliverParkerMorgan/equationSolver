import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }
    void rootAndSquareRoot(List<String> equationSide){
        calculatingSide(equationSide,"**");
        calculatingSide(equationSide,"//"); }

    void multiplyAndDivideLogic(List<String> equationSide){
        calculatingSide(equationSide,"*");
        calculatingSide(equationSide,"/");
    }
    void addAndSubtractLogic(List<String> equationSide){
        calculatingSide(equationSide,"+");
        calculatingSide(equationSide,"-");
    }

    private void calculatingSide(List<String> equationSide, String operator){
        while(equationSide.contains(operator)) {
            for (int i = 0; i < equationSide.size(); i++) {
                if (equationSide.get(i).equals(operator) && Character.isDigit(equationSide.get(i - 1).charAt(0)) && Character.isDigit(equationSide.get(i + 1).charAt(0))) {
                    double x = Calculator.doAction(operator,equationSide.get(i - 1), equationSide.get(i + 1));
                    equationSide.set(i, Double.toString(x));
                    equationSide.remove(equationSide.get(i - 1));
                    equationSide.remove(equationSide.get(i));
                    break;

                }
            }
        }
    }

    public void printEquation(){System.out.println( Arrays.toString(sortL.toArray())+" = "+Arrays.toString(sortR.toArray()));}

    private void brackets(){
    }

}