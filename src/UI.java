import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends Frame implements ActionListener{
    private Label instructions;
    private TextField leftSideOfEquation;
    private TextField rightSideOfEquation;
    private Button btnCount;

    // Constructor to setup GUI components and event handlers
    public UI () {
        //setLayout(new FlowLayout());
        EquationSolver E = new EquationSolver("1+2(1+2(1+2(1+2)1+2)*3*22+2)*1*1","x+1");
        E.solve();

       // input of the left side of the equation
       // leftSideOfEquation = new TextField( "3*x", 10);
       // leftSideOfEquation.setEditable(true);
       // add(leftSideOfEquation);
       // equals sign
       // instructions = new Label(" = ");
       // add(instructions);

       // input of the right side of the equation
       // rightSideOfEquation = new TextField( "1", 10);
       // rightSideOfEquation.setEditable(true);
       // add(rightSideOfEquation);
       // btnCount = new Button("Solve");
       // add(btnCount);
       // btnCount.addActionListener(this);
       // setTitle("EquationSolver");
       // setSize(250, 100);
       // setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        Analyzer A = new Analyzer(leftSideOfEquation.getText(),rightSideOfEquation.getText());

    }

    public static void main(String[] args) {UI app = new UI();}
}
