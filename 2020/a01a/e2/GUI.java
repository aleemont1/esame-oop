package a01a.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();
    private Logic logic;

    public GUI(int size) {
        this.logic = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var button = (JButton) e.getSource();
            var position = cells.get(button);
            if(logic.hit(position.getX(), position.getY())) {
                button.setText(
                    logic.isMarked(position).getX() ?
                        logic.isMarked(position).getY() ?
                        "0" : "X"
                    : " ");
            }
            if(logic.isOver()) {
                System.exit(0);
            }       
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton(" ");
                this.cells.put(jb, new Pair<Integer, Integer>(j, i));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void refresh() {
        for (final var c : cells.entrySet()) {

            c.getKey().setText(
                    logic.isMarked(c.getValue()).getX() ?
                        logic.isMarked(c.getValue()).getY() ?
                        "0" : "X"
                    : " ");
        }
    }

}
