import javax.swing.*;	
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[4][4];
    private String[] symbols = {"A", "A", "B", "B", "C", "C", "D", "D", "E", "E", "F", "F", "G", "G", "H", "H"};
    private String firstSymbol = null;
    private JButton firstButton = null;
    private int moves = 0;
    private int pairsFound = 0;

    public MemoryGame() {
        setTitle("Memory Game");
        setSize(400, 400);
        setLayout(new GridLayout(4, 4));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<String> symbolList = new ArrayList<>();
        Collections.addAll(symbolList, symbols);
        Collections.shuffle(symbolList);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));
                buttons[i][j].setActionCommand(symbolList.get(i * 4 + j));
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String symbol = clickedButton.getActionCommand();

        if (firstButton == null) {
            firstButton = clickedButton;
            firstSymbol = symbol;
            firstButton.setText(symbol);
            firstButton.setEnabled(false);
        } else {
            moves++;
            clickedButton.setText(symbol);
            clickedButton.setEnabled(false);
            if (symbol.equals(firstSymbol)) {
                pairsFound++;
                firstButton = null;
                firstSymbol = null;
                if (pairsFound == 8) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You won in " + moves + " moves.");
                }
            } else {
                Timer timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        firstButton.setText("");
                        firstButton.setEnabled(true);
                        clickedButton.setText("");
                        clickedButton.setEnabled(true);
                        firstButton = null;
                        firstSymbol = null;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MemoryGame().setVisible(true);
            }
        });
    }
}

