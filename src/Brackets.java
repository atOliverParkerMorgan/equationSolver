import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Brackets { // this is a lot of code a have to find better solution plus it doesn't even work :(
    private List<String> coefficient;
    private List<String> content;
    private static HashMap<String, Brackets> allBrackets;
    private static List<String> currentBracketSolvedValue;
    private Brackets(List<String> coefficient, List<String> content) {
        this.coefficient = coefficient;
        this.content = content;
    }

    private static List<String> excludeBrackets(final List<String> input, String tag){
        String bracketStart = "";
        List <String> output = new ArrayList<>();

        for (String element: input) {
            if(element.equals(tag) && bracketStart.equals("")){
                bracketStart = element;
                output.add("B");


            }else if(bracketStart.equals("")){
                output.add(element);
            }
            else if(element.equals(bracketStart)){
                bracketStart = "";
            }
        }
        return output;
    }
    private static List<String> excludeAllBracketsAndCoefficient(final List<String> input, String firstTag){
        List<String> output = new ArrayList<>();
        List<Integer> doNotAdd = new ArrayList<>();
        List<String> fixedInput = excludeBrackets(input, firstTag);


        for (int i = 0; i < fixedInput.size(); i++) {

            if (fixedInput.get(i).equals("B")) {
                int index = 1;
                while ((i-index>=0&&(index % 2 == 0||(index % 2 == 1 && fixedInput.get(i-index).equals("*")||
                        fixedInput.get(i-index).equals("/")||fixedInput.get(i-index).equals("//")||fixedInput.get(i-index).equals("**"))))) {

                    doNotAdd.add(i-index);
                    index++;
                }
                int index2 = 1;
                while ((i+index2<fixedInput.size()&&(index2 % 2 == 0||(index2 % 2 == 1 && fixedInput.get(i+index2).equals("*")||
                        fixedInput.get(i+index2).equals("/")||fixedInput.get(i+index2).equals("//")||fixedInput.get(i+index2).equals("**"))))) {
                    doNotAdd.add(i+index2);
                    index2++;
                }
            }

        }
        for (int i = 0; i <fixedInput.size() ; i++) {
            if(!doNotAdd.contains(i)&&!fixedInput.get(i).equals("B")){
                output.add(fixedInput.get(i));
            }
        }
        return output;

    }


    private static List<String> getCoefficient(final List<String> input, String tag){
        List<String> coefficient = new ArrayList<>();
        List<String> fixedInput = excludeBrackets(input, tag);


        for (int i = 0; i < fixedInput.size(); i++) {

            if (fixedInput.get(i).equals("B")) {
                int index = 1;
                while ((i-index>=0&&(index % 2 == 0||(index % 2 == 1 && fixedInput.get(i-index).equals("*")||
                        fixedInput.get(i-index).equals("/")||fixedInput.get(i-index).equals("//")||fixedInput.get(i-index).equals("**"))))) {

                    coefficient.add(fixedInput.get(i-index));
                    index++;
                }
                int index2 = 1;
                while ((i+index2<fixedInput.size()&&(index2 % 2 == 0||(index2 % 2 == 1 && fixedInput.get(i+index2).equals("*")||
                        fixedInput.get(i+index2).equals("/")||fixedInput.get(i+index2).equals("//")||fixedInput.get(i+index2).equals("**"))))) {
                    coefficient.add(fixedInput.get(i+index2));
                    index2++;
                }
                break;
            }

        }
        return coefficient.size()==0? null: coefficient;
    }

     private static List<String> getBracketContentWithoutCoefficientToNextBracket(final List<String> input, String tag){
        int lastIndex = Integer.parseInt(tag.substring(tag.indexOf("F")+1))+1;
        String tagIn = tag.substring(0,tag.indexOf("F"))+"F"+lastIndex;
        boolean inBracket = false;
        List<String> content = new ArrayList<>();
        List<Integer> indexToNotAdd = new ArrayList<>();
        List<String> contentWithoutCoefficient = new ArrayList<>();

        for (String s: excludeBrackets(input,tagIn)){
            if(s.equals(tag)){
                inBracket = !inBracket;
            }
            else if(inBracket){
                content.add(s);
            }
        }
        int baseIndex = content.indexOf("B");
        if(baseIndex!=-1) {
            indexToNotAdd.add(baseIndex);
            if (baseIndex != 0) {
                int i = 1;
                while (baseIndex - i!=0&&i%2==0||(i%2==1&&(content.get(baseIndex - i).contains("*")||content.get(baseIndex - i).contains("/")))) {
                    indexToNotAdd.add(baseIndex-i);
                    i++;
                }
            }if (baseIndex+1 != content.size()) {
                int i = 1;
                while (baseIndex + i + 1!=content.size()&&i%2==0||(i%2==1&&(content.get(baseIndex + i).contains("*")||content.get(baseIndex + i).contains("/")))) {
                    indexToNotAdd.add(baseIndex+i);
                    i++;
                }
            }
            for (int i = 0; i < content.size(); i++) {
                if(indexToNotAdd.indexOf(i)==-1){
                    contentWithoutCoefficient.add(content.get(i));
                }
            }
            return contentWithoutCoefficient;
        }else{
           return content;
        }

    }

   static List<String> solveBrackets(final List<String> input, int index1, int index2, boolean ending, List<String> beforeCoefficient) {
       if (index1 == 0) { currentBracketSolvedValue = new ArrayList<>(); }
       // defining tags
       final String tag = index1 + "F" + index2;
       final String tagIn = index1 + "F" + (index2+1);
       // save guard for no brackets
       if(ending) {
           List<String> output;
           int i = 0;
           do{
               output = excludeAllBracketsAndCoefficient(input, i+"F0");
               i++;
           }
           while (output.contains(i+"F0"));
           System.out.println(currentBracketSolvedValue);
           if (currentBracketSolvedValue != null) {
               output.addAll(currentBracketSolvedValue);
           }
           return output;
       }

       List<String> coefficient;
       if (index1 == 0) { allBrackets = addAllBracketsToMap(input, index1 + 1, index2); }
       coefficient = allBrackets.get(tag).coefficient;
       if (beforeCoefficient != null) {
           coefficient.addAll(beforeCoefficient);
       }
       List<String> content = allBrackets.get(tag).content;

       if (input.contains(tagIn)) {
           index2++;
       } else if (input.contains(tag)) {
           index1++;
           beforeCoefficient = null;
       }

       if (coefficient == null) {
           return solveBrackets(input, index1, index2, !input.contains(index1 + "F" + (index2 + 1)) && !input.contains((index1 + 1) + "F" + index2), null);

       } else {
           // formatting and creating minor adjustments
           coefficient.remove(0);
           if (beforeCoefficient != null) {
               coefficient.addAll(beforeCoefficient);
           }
           List<Polynomial> solved = Calculator.multiplyBrackets(Polynomial.createPolynomials(content), Polynomial.createPolynomials(coefficient));
           // adding extra before solved variables
           for (Polynomial p : solved) {
               currentBracketSolvedValue.addAll(p.stringValue);
           }


           coefficient.add(0, "*");
           return solveBrackets(input, index1, index2, !input.contains(tagIn) && !input.contains((index1 + 1) + "F" + index2), coefficient);

       }


   }

    private static HashMap<String,Brackets> addAllBracketsToMap(final List<String> input, int index1, int index2) {
        // do not include tag index1+"F"+index2

        HashMap<String,Brackets> allBracketsInformation = new HashMap<>();
        for (String element: input) {
            if(element.equals(index1+"F"+index2)){break;}
            if(element.contains("F")){
                allBracketsInformation.put(element,new Brackets(getCoefficient(input,element),
                        getBracketContentWithoutCoefficientToNextBracket(input,element)));
            }
        }
        return allBracketsInformation;

    }


}
