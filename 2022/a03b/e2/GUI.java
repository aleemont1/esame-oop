package a03b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

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
                if (!logic.isOver()) {
                    logic.move(position.getX(), position.getY());
                    refresh();
                } else {
                    logic = new LogicImpl(size);
                    refresh();
                }
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
        refresh();
    }

    private void refresh() {
        for (final var c : cells.entrySet()) {
            final var x = c.getValue().getX();
            final var y = c.getValue().getY();
            c.getKey().setText(logic.isComputer(x, y) ? "o"
                    : logic.isPawn(x, y) ? "*" : " ");
        }
    }
}
