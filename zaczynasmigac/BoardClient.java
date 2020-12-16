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
    boolean marked = false;

    public BoardClient() throws Exception {
        socket = new Socket("localhost", 59090);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream(), true);

        panel = new BoardPanel();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < 13; i++){
                    for (int j = 0; j < 17; j++){
                        if (panel.getBoard()[i][j].contains(e.getX(), e.getY())){
                            if (marked){
                                currentCircle[0] = i;
                                currentCircle[1] = j;
                                output.println("MOVE " + i + " " + j);
                            }
                            else{
                                currentCircle[0] = i;
                                currentCircle[1] = j;
                                output.println("MARK " + i + " " + j);
                            }
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
            var id = Integer.parseInt(String.valueOf(response.charAt(8)));
            var opponentId = id == 1 ? 2 : 1;
            frame.setTitle("Game: Player " + id);

            while (input.hasNextLine()){
                response = input.nextLine();

                if (response.startsWith("VALID_MARK")){
                    label.setText("Valid mark, please wait");
                    marked = true;
                    panel.mark(currentCircle[0], currentCircle[1]);
                }
                else if (response.startsWith("VALID_MOVE")) {
                    label.setText("Valid move, please wait");
                    panel.makeMove(currentCircle[0], currentCircle[1]);
                    marked = false;
                }
                else if (response.startsWith("OPPONENT_MOVED")) {
                    String str = response.substring(15);
                    String[] arr = str.split("\\s+");
                    panel.oppMove(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), opponentId);
                    label.setText("Opponent moved, your turn");
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
