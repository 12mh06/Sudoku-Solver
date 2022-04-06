import javax.swing.*;

public class AnswerWorker extends SwingWorker<Void, Void> {

    private final GridLogic g;
    private final SudokuGUI gui;

    public AnswerWorker(GridLogic g, SudokuGUI gui){
        this.g = g;
        this.gui = gui;
    }

    @Override
    protected Void doInBackground() {
        g.solveSudoku();
        return null;
    }

    @Override
    protected void done() {
        gui.setVisible(true);
    }
}
