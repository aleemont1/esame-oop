package a04.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final Map<JButton, Pair<Integer,Integer>> cells = new HashMap<>();
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
                System.out.println("click on: " + position);
                var move = logic.move(position.getX(), position.getY());
                System.out.println(move);
                System.out.println("King in: " + logic.getKing());
                System.out.println("Rook in: " + logic.getRook());
                refresh();
                if(logic.isOver()) {
                    final var end = move ? "VITTORIA" : "SCONFITTA";
                    System.out.println(end);
                    logic = new LogicImpl(size);
                    refresh();
                }
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
        refresh();
    }

    private void refresh() {
        for (final var c : cells.entrySet()) {
            final var pos = c.getValue();
            final var bt = c.getKey();
            if (pos.equals(logic.getRook())) {
                bt.setText("R");
            } else if (pos.equals(logic.getKing())) {
                bt.setText("K");
            } else {
                bt.setText(" ");
            }
        }
    }
}
