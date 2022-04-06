import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class SudokuGUI extends JFrame {

    private final SudokuGrid grid;

    public SudokuGrid getGrid() {
        return grid;
    }

    public SudokuGUI(GridLogic g){
        this.setLayout(new BorderLayout());
        this.setTitle("Sudoku Solver");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        SudokuGrid grid = new SudokuGrid();
        this.grid = grid;
        this.add(grid, BorderLayout.CENTER);

        int verticalGap = 15;
        int horizontalGap = this.getWidth() / 3;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, horizontalGap, verticalGap));
        JButton button = new JButton("Solve");
        SudokuGUI gui = this;

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AnswerWorker(g, gui).execute();
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                g.clearGrid();
                g.setEditableFields(true);
            }
        });

        button.setFocusPainted(false);
        clearButton.setFocusPainted(false);
        buttonPanel.add(button);
        buttonPanel.add(clearButton);
        this.add(buttonPanel, BorderLayout.PAGE_END);

        this.setVisible(true);
    }

}
