import javax.swing.*;
import java.awt.*;

public class SudokuGrid extends JPanel {

    public SudokuGrid(){
        super(new GridLayout(3, 3));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                SudokuCompartment compartment = new SudokuCompartment();
                compartment.setName(Integer.toString(i) + Integer.toString(j));

                this.add(compartment);
            }
        }
    }

    public SudokuTextField getField(int row, int col){

        int compFirst = 1;
        if(row < 3){
            compFirst = 0;
        }
        if(row > 5){
            compFirst = 2;
        }
        int compSecond = 1;
        if(col < 3){
            compSecond = 0;
        }
        if(col > 5){
            compSecond = 2;
        }
        String compName = Integer.toString(compFirst) + Integer.toString(compSecond);

        SudokuCompartment comp = null;
        for(Component c : this.getComponents()){
            if(c instanceof SudokuCompartment && c.getName().equals(compName)){
                comp = (SudokuCompartment) c;
            }
        }

        row -= compFirst * 3;
        col -= compSecond * 3;

        SudokuTextField field = null;
        String fieldName = Integer.toString(row) + Integer.toString(col);
        for(Component c : comp.getComponents()) {
            if (c instanceof SudokuTextField && c.getName().equals(fieldName)) {
                field = (SudokuTextField) c;
            }
        }
        return field;
    }
}
