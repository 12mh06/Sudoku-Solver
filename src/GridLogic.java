import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class GridLogic {

    private int[][] startShape;
    private final int[][] shape;
    private final SudokuGUI GUI;

    public GridLogic(){
        this.shape = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };

        this.GUI = new SudokuGUI(this);
    }

    private void setShape(int row, int col, int num) {
        int timeBetweenSteps = 0;
        try{
            Thread.sleep(timeBetweenSteps);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        this.shape[row][col] = num;
        String text = Integer.toString(this.shape[row][col]);
        this.GUI.getGrid().getField(row, col).setForeground(Color.RED);
        this.GUI.getGrid().getField(row, col).setText(text);
    }

    public void setEditableFields(boolean editable){
        for(int i = 0; i < this.shape.length; ++i) {
            for (int j = 0; j < this.shape[0].length; ++j) {
                this.GUI.getGrid().getField(i, j).setEditable(editable);

                if (editable) {
                    this.GUI.getGrid().getField(i, j).setForeground(Color.BLUE);
                }
            }
        }
    }

    private void alignGrid(){
        this.startShape = new int[this.shape.length][this.shape[0].length];
        for(int i = 0; i < this.shape.length; ++i) {
            for (int j = 0; j < this.shape[0].length; ++j) {
                SudokuTextField field = this.GUI.getGrid().getField(i, j);
                try {
                    int num = Integer.parseInt(field.getText());
                    this.shape[i][j] = num;
                    this.startShape[i][j] = num;
                } catch (Exception e){
                    this.shape[i][j] = 0;
                    this.startShape[i][j] = 0;
                }

            }
        }
    }

    public void solveSudoku(){
        alignGrid();

        if (checkValidSudoku()){

            setEditableFields(false);
            solveTile(0, 0);

            if(this.isSolved()){
                JOptionPane.showMessageDialog(this.GUI, "solution found");
            }
            else{
                JOptionPane.showMessageDialog(this.GUI, "This Sudoku is unsolvable");
            }
        }
        else{
            JOptionPane.showMessageDialog(this.GUI,
                    "Invalid Grid", "Error", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private boolean solveTile(int row, int col) {
        if(this.isSolved()){
            return true;
        }

        int nextRow = row;
        int nextCol = col;
        if (col < 8){
            ++nextCol;
        }
        else {
            nextCol = 0;
            ++nextRow;
        }

        boolean changeable = (this.startShape[row][col] == 0);
        if (!changeable){
            return this.solveTile(nextRow, nextCol);
        }

        int result = findCompatibleNum(row, col);
        this.setShape(row, col, result);
        if (result == 0){ return false; }

        boolean next = this.solveTile(nextRow, nextCol);
        if (!next){
            solveTile(row, col);
        }

        return next;
    }

    private boolean checkValidSudoku(){
        for (int i = 0; i < this.shape.length; ++i) {
            for (int j = 0; j < this.shape[0].length; ++j) {
                if (!this.isCompatible(i, j, this.shape[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSolved(){
        for (int[] ints : this.shape) {
            for (int j = 0; j < this.shape[0].length; ++j) {
                if (ints[j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int findCompatibleNum(int row, int col){
        //System.out.println(this.shape[row][col] + 1);
        for(int i = this.shape[row][col] + 1; i < 10; ++i){
            if(isCompatible(row, col, i)){
                //System.out.println(i);
                return i;
            }
        }
        return 0;
    }

    private boolean isCompatible(int row, int col, int num){
        if (num == 0){
            return true;
        }

        int remember = this.shape[row][col];
        this.shape[row][col] = 0;

        for (int i : this.shape[row]){
            if(i == num){
                this.shape[row][col] = remember;
                return false;
            }
        }
        for (int[] ints : this.shape) {
            if (ints[col] == num) {
                this.shape[row][col] = remember;
                return false;
            }
        }
        for (int i : getCompartmentArray(row, col)){
            if (i == num){
                this.shape[row][col] = remember;
                return false;
            }
        }
        this.shape[row][col] = remember;
        return true;
    }

    private int[] getCompartmentArray(int row, int col){
        int [] compIndex = getCompartmentIndex(row, col);
        int first = compIndex[0];
        int second = compIndex[1];

        int[] result = new int[9];

        int x = 0;
        for(int i = first * 3; i < 3 + 3 * first; ++i){
            for(int j = second * 3; j < 3 + 3 * second; ++j){
                result[x] = this.shape[i][j];
                ++x;
            }
        }
        return result;
    }

    private int[] getCompartmentIndex(int row, int col){
        int indexFirst = 0;
        if(row > 5){
            indexFirst = 2;
        }
        else if(row > 2){
            indexFirst = 1;
        }

        int indexSecond = 0;
        if(col > 5){
            indexSecond = 2;
        }
        else if(col > 2){
            indexSecond = 1;
        }
        return new int[] {indexFirst, indexSecond};
    }

    public void clearGrid() {
        for (int i = 0; i < this.shape.length; ++i) {
            for (int j = 0; j < this.shape[i].length; ++j) {
                this.shape[i][j] = 0;
                this.GUI.getGrid().getField(i, j).setText(null);
            }
        }
    }
}
