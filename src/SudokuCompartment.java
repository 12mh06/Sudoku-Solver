import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class SudokuCompartment extends JLabel {

    public SudokuCompartment(){
        this.setLayout(new GridLayout(3, 3, 0, 0));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        this.setBorder(border);

        for(int i = 0; i < 3; ++i){
            for(int j = 0; j < 3; ++j){
                SudokuTextField field = new SudokuTextField();
                field.setName(Integer.toString(i) + Integer.toString(j));
                //field.setText(field.getName());
                this.add(field);
            }
        }
    }
}
