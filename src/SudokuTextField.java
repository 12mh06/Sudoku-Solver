import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.PlainDocument;
import java.awt.*;;

public class SudokuTextField extends JTextField {

    public SudokuTextField(){
        Border thinBorder = BorderFactory.createLineBorder(Color.GRAY, 1);
        this.setBorder(thinBorder);
        this.setHorizontalAlignment(JTextField.CENTER);
        this.setFont(new Font("Serif", Font.BOLD, 30));
        this.setForeground(Color.BLUE);
        PlainDocument doc = (PlainDocument) this.getDocument();
        ((PlainDocument) this.getDocument()).setDocumentFilter(new TextFieldFilter());
    }

    private boolean InputIsValid(char c){
        char[] validInputs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        for(char a : validInputs){
            if(a == c){
                System.out.println(true);
                return true;
            }
        }
        System.out.println(false);
        return false;
    }
}
