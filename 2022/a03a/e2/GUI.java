package a03a.e2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI extends JFrame {

    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logic logic;

    public GUI(int size) {
        logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                var position = cells.get(button);
                int x = position.getX();
                int y = position.getY();
                logic.move(x, y);
                refreshAll();
                Optional<Boolean> isOver = logic.isOver();
                if (isOver.isPresent()) {
                    if (isOver.get()) {
                        System.out.println("Computer wins");
                    } else {
                        System.out.println("Player wins");
                    }
                    logic = new LogicImpl(size);
                    refreshAll();
                }
            }
        };

        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                final JButton jb = new JButton(" ");
                final var pos = new Pair<>(i, j);
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        refreshAll();
        this.setVisible(true);
    }

    private void refreshAll() {
        for (final var entry : cells.entrySet()) {
            JButton button = entry.getKey();
            Pair<Integer, Integer> position = entry.getValue();
            if (!logic.getPlayerPos().equals(logic.getCompPos())) {
                if (position.equals(logic.getPlayerPos())) {
                    button.setText("P");
                } else if (position.equals(logic.getCompPos())) {
                    button.setText("C");
                } else {
                    button.setText(" ");
                }
            } else {
                button.setText(logic.getTurn() ? "C" : "P");
            }
        }
    }
}
