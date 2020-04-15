
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
        this.sortR = sortList(inputRight);
        this.sortL = sortList(inputLeft);
    }
    List<String> sortList(final String input){
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
          // handling dot
           if (c=='.'){s.append(c);}

            // plus and minus is handled for every digit meaning that every digit has a + or - operator before it
           else if(Character.isDigit(c)){
                s.append(c);
             //  System.out.println(sort.toString());
                if(i+1==input.length()||!Character.isDigit(input.charAt(i+1))&&input.charAt(i+1)!='.'){
                    String variable = "";
                    if(i+1!=input.length()) {
                        variable = input.charAt(i + 1) == 'x' ? "x" : "";
                    }
                    if(i==0||(i - s.toString().length())<0){ // this element is the first in the list
                        s = new StringBuilder("+" + s.toString());

                    }else if(i==1){
                        if(input.charAt(0)=='-') {
                            s = new StringBuilder("-" + s.toString()+variable);
                        }else{
                            s = new StringBuilder("+" + s.toString()+variable);
                        }
                    }
                    else{
                        if (input.charAt(i - s.toString().length()) == '-') {
                            s = new StringBuilder("-" + s.toString() + variable);
                        } else {
                            s = new StringBuilder("+" + s.toString() + variable);
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


    public void printEquation(){
        System.out.println("Computed equation:");
        System.out.println( Arrays.toString(sortL.toArray())+" = "+Arrays.toString(sortR.toArray()));
        System.out.println("\n");
    }

}