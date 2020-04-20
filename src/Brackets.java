import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brackets {

    static List<String> excludeBrackets(final List<String> input, String tag){
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


    static List<String> getCoefficient(final List<String> input, String tag){
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

    static List<String> getBracketContentWithoutCoefficientToNextBracket(final List<String> input, String tag, String tagIn){
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

    static List<String> solveBrackets(final List<String> input, int index1, int index2, boolean ending) {

        System.out.println(index1 + "F" + index2);
        System.out.println(input);


        List<String> content = getBracketContentWithoutCoefficientToNextBracket(input, index1 + "F" + index2, index1 + "F" + (index2 + 1));
        List<String> coefficient = getCoefficient(input, index1 + "F" + index2);
        List<String> output = removeBracketAndCoefficient(input,index1 + "F" + index2,index1 + "F" + (index2 + 1));
        System.out.println(output);

        if(input.contains(index1 + "F" + (index2+1))){
            index2++;
        }else if(input.contains((index1+1) + "F" + index2)){
            index1++;
        }

        if(coefficient==null){
            output.addAll(content);

            if(ending){
                System.out.println("finished");
                return output;
            }else {
                return solveBrackets(output, index1, index2, !input.contains(index1 + "F" + (index2 + 1)) && !input.contains((index1 + 1) + "F" + index2));
            }
        }else {
            System.out.println("multiplied");
            // creating minor adjustments
            coefficient.remove(0);

            List<Polynomial> solved = Calculator.multiplyBrackets(Polynomial.createPolynomials(content), Polynomial.createPolynomials(coefficient));

            // adding extra before solved variables
            for (Polynomial p: solved) {
                output.addAll(p.stringValue);
            }

            if(ending){
                System.out.println("finished");
                System.out.println(output);
                return output;
            }else {
                return solveBrackets(output, index1, index2, !input.contains(index1 + "F" + (index2 + 1)) && !input.contains((index1 + 1) + "F" + index2));
            }
        }

    }

    static List<String> removeBracketAndCoefficient(final List<String> input, String tag, String tagIn){

        List<String> helper = new ArrayList<>();
        List<String> output = new ArrayList<>();
        List<Integer> indexToNotAdd = new ArrayList<>();
        List<String> firstCoefficient = new ArrayList<>();
        int firstBracketIndex = 0;

        boolean afterLast = false;
        boolean isANum = false;

        boolean removeFirstBracket = false;
        boolean isInSecondBracket = false;

        int index = 0;
        for (String s: input) {
            if(s.equals(tagIn)){
                if(!isInSecondBracket && 0<=index){
                    int i = 1;
                    boolean nextAdd = false;
                    while ((0<=index - i&& input.get(index - i).equals("*")||input.get(index - i).equals("/"))||nextAdd){
                        firstCoefficient.add(input.get(index - i));
                        nextAdd = !nextAdd;
                        i++;
                    }
                }
                helper.add(s);
                isInSecondBracket = !isInSecondBracket;
                if(!isInSecondBracket && input.size()!=index+1){
                    int i = 1;
                    boolean nextAdd = false;
                    while ((input.size()!=index+1&& input.get(index + i).equals("*")||input.get(index + i).equals("/"))||nextAdd){
                        helper.add(input.get(index + i));
                        nextAdd = !nextAdd;
                        i++;
                    }
                }
            }
            else if(s.equals(tag)){

                if(index!=0 && !removeFirstBracket) {
                    int i = 1;
                    indexToNotAdd.add(index);
                    boolean nextAdd = false;
                    while (index - i >= 0 && (input.get(index - i).contains("*") || input.get(index - i).contains("/"))||nextAdd) {
                        indexToNotAdd.add(index - i);
                        i++;
                        nextAdd = !nextAdd;
                    }
                }else if((index+1)!=input.size()){
                    int i = 1;
                    indexToNotAdd.add(index);
                    boolean nextAdd = false;
                    while (index + i < input.size() && (input.get(index + i).contains("*") || input.get(index + i).contains("/"))||nextAdd) {
                        indexToNotAdd.add(index + i);
                        i++;
                        nextAdd = !nextAdd;
                    }
                    afterLast = true;
                }
                removeFirstBracket = !removeFirstBracket;
            }

            if((((!removeFirstBracket||isInSecondBracket)))&&!s.equals(tag)&&!afterLast){
                helper.add(s);
            }
            if(((index+1)!=input.size()&&afterLast)){
                if(isANum||(input.get(index+1).contains("*")||input.get(index+1).contains("/"))){
                    isANum = !isANum;
                }else {
                    afterLast = false;
                }
            }

            index++;

        }

        for (int i = 0; i < helper.size(); i++) {
            if(indexToNotAdd.indexOf(i)==-1){
                output.add(helper.get(i));
            }
        }

        for (int j = firstCoefficient.size(); j > 0; j--) {
            output.add(output.indexOf(tagIn),firstCoefficient.get(j-1));
        }

        return output;
    }
}
