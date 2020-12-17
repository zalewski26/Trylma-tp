import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class BoardClient {
    JFrame frame = new JFrame("Trylma");
    JLabel label = new JLabel("...");
    BoardPanel panel;
    int[] currentCircle = new int[2];
    Socket socket;
    Scanner input;
    PrintWriter output;

    public BoardClient() throws Exception {
        socket = new Socket("localhost", 59090);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);

        panel = new BoardPanel();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int row = 0; row < panel.getBoard().length; row++){
                    for (int column = 0; column < panel.getBoard()[row].length; column++){
                        if (panel.getBoard()[row][column].contains(e.getX(), e.getY())){
                            currentCircle[0] = row;
                            currentCircle[1] = column;
                            output.println("CLICK " + row + " " + column);
                        }
                    }
                }
            }
        });

        frame.getContentPane().add(label, BorderLayout.SOUTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }

    public void play() throws Exception {
        try{
            var response = input.nextLine();
            var id = Integer.parseInt(response);
            frame.setTitle("Game: Player " + id);

            while (input.hasNextLine()){
                response = input.nextLine();

                if (response.startsWith("VALID_MARK")){
                    panel.mark(currentCircle[0], currentCircle[1], true);
                }
                else if (response.startsWith("VALID_MOVE")) {
                    panel.makeMove(currentCircle[0], currentCircle[1]);
                    label.setText("Good move, now wait!");
                }
                else if (response.startsWith("OTHER_MARK")){
                    String str = response.substring(11);
                    String[] arr = str.split("\\s+");
                    panel.mark(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), false);
                }
                else if (response.startsWith("OTHER_MOVE")){
                    String str = response.substring(11);
                    String[] arr = str.split("\\s+");
                    panel.makeMove(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                    label.setText("Opponent moved, your turn!");
                }
                else if (response.startsWith("MESSAGE")) {
                    label.setText(response.substring(8));
                }
                else if (response.startsWith("OTHER_PLAYER_LEFT")) {
                    JOptionPane.showMessageDialog(frame, "Other player left");
                    break;
                }
            }
            output.println("QUIT");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            socket.close();
            frame.dispose();
        }
    }

    public static void main(String[] args){
        try {
            BoardClient client = new BoardClient();
            client.frame.setSize(700,900);
            client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            client.frame.setVisible(true);
            client.play();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
