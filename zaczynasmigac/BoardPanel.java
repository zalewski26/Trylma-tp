import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final Circle[][] circles = new Circle[17][13];
    private Circle currentCircle;
    private Color currentColor;
    private final int[][] board = { {8,8,8,8,8,8,6,8,8,8,8,8,8}, {8,8,8,8,8,6,6,8,8,8,8,8,8}, {8,8,8,8,8,6,6,6,8,8,8,8,8},
            {8,8,8,8,6,6,6,6,8,8,8,8,8}, {5,5,5,5,0,0,0,0,0,4,4,4,4}, {5,5,5,0,0,0,0,0,0,4,4,4,8}, {8,5,5,0,0,0,0,0,0,0,4,4,8},
            {8,5,0,0,0,0,0,0,0,0,4,8,8}, {8,8,0,0,0,0,0,0,0,0,0,8,8}, {8,3,0,0,0,0,0,0,0,0,2,8,8}, {8,3,3,0,0,0,0,0,0,0,2,2,8},
            {3,3,3,0,0,0,0,0,0,2,2,2,8}, {3,3,3,3,0,0,0,0,0,2,2,2,2}, {8,8,8,8,1,1,1,1,8,8,8,8,8}, {8,8,8,8,8,1,1,1,8,8,8,8,8},
            {8,8,8,8,8,1,1,8,8,8,8,8,8}, {8,8,8,8,8,8,1,8,8,8,8,8,8},
    };

    public BoardPanel(){
        setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());

        int diameter = 50;
        for (int row = 0; row < circles.length; row++){
            for (int column = 0; column < circles[row].length; column++){
                if (row % 2 == 0)
                    circles[row][column] = new Circle(column* diameter, row* diameter, diameter, board[row][column]);
                else
                    circles[row][column] = new Circle(column* diameter + diameter /2, row* diameter, diameter, board[row][column]);
            }
        }
        repaint();
    }

    public void mark(int row, int column, boolean tick){
        currentCircle = circles[row][column];
        currentColor = currentCircle.getColor();
        if (tick){
            currentCircle.setColor(Color.CYAN);
            repaint();
        }
    }

    public void makeMove(int row, int column){
        if (currentCircle.getId() != 0)
            currentCircle.setColor(Color.GRAY);
        else
            currentCircle.setColor(Color.DARK_GRAY);
        circles[row][column].setColor(currentColor);
        repaint();
    }

    public Circle[][] getBoard(){
        return circles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < circles.length; row++){
            for (int column = 0; column < circles[row].length; column++){
                if (board[row][column] != 8)
                    circles[row][column].draw(g);
            }
        }
    }
}
