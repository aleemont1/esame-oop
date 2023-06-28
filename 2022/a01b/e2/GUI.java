package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private final Logic logic;

    public GUI(int size) {
        logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                var pos = cells.get(button);
                logic.hit(pos.getX(), pos.getY());
                refresh();
                if (logic.isOver()) {
                    System.exit(0);
               }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton("");
                this.cells.put(jb, new Pair<>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.setVisible(true);
    }

    private void refresh() {
        for (final var c : cells.entrySet()) {
            final var pos = c.getValue();
            final var bt = c.getKey();
            if (logic.getStars().contains(pos)) {
                bt.setText("*");
            } else {
                bt.setText(" ");
            }
        }
    }
}
