package a01b.e2;

import javax.swing.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final List<JButton> cells = new ArrayList<>();
    private final Logic logic;

    public GUI(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final JButton jb = new JButton("");
                this.cells.add(jb);
                panel.add(jb);
            }
        }

        logic = new LogicImpl(size, cells, 0);
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                var button = (JButton) e.getSource();
                logic.isHit(button);
                if (logic.isOver()) {
                    System.exit(0);
                }
            }
        };

        for (final var c : cells) {
            c.addActionListener(al);
        }

        this.setVisible(true);
    }
}
