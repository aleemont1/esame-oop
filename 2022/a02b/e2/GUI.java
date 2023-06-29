package a02b.e2;

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

        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);

        ActionListener check = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (logic.checkDiagonals() && logic.isOver()) {
                    refresh();
                    logic = new LogicImpl(size);
                } else {
                    refresh();
                }
            }

        };

        JButton checkButton = new JButton("check > restart");
        checkButton.addActionListener(check);
        main.add(BorderLayout.SOUTH, checkButton);
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                var position = cells.get(button);
                logic.hit(position.getX(), position.getY());
                refresh();
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<>(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void refresh() {
        for (final var c : cells.entrySet()) {
            c.getKey().setText(logic.isStar(c.getValue().getX(), c.getValue().getY()) ? "*" : " ");
            c.getKey().setEnabled(logic.isEnabled(c.getValue().getX(), c.getValue().getY()));
        }
    }
}
