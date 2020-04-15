import java.util.ArrayList;
import java.util.List;

class Polynomial {
    boolean hasVar;
    private List<String> stringValue;
    double realValue;

    Polynomial(List<String> stringValue){
        if(stringValue.contains("+0")||stringValue.contains("-0")){
            this.stringValue = new ArrayList<>();
            this.stringValue.add("+0");
        }else {
            this.stringValue = Calculator.getOneNumber(stringValue);
            hasVar = this.stringValue.get(0).contains("x");

            if(hasVar){
                realValue = Double.parseDouble(this.stringValue.get(0).replace("x",""));
            }else{
                realValue = Double.parseDouble(this.stringValue.get(0));
            }
        }
    }



}
