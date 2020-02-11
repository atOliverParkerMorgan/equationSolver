public class UI {
    public static void main(String[] args) {
        Analyzer A = new Analyzer("7**2","12+31-3+2");
        A.order();
        A.printEquation();
        A.rootAndSquareRoot(A.sortL);
        A.multiplyAndDivideLogic(A.sortL);
        A.addAndSubtractLogic(A.sortL);
        A.multiplyAndDivideLogic(A.sortR);
        A.addAndSubtractLogic(A.sortR);
        A.printEquation();
       }

}
