package com.kpi.gui;

import com.kpi.City;
import com.kpi.CitySet;
import com.kpi.Path;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Vector;

public class SimulWindow extends JFrame {
    private static final int WINDOW_WIDTH  = 1000;
    private static final int WINDOW_HEIGHT = 1000;

    private final Vector<Path> solutions;
    private JPanel simulPanel;

    public SimulWindow(Vector<Path> solutions) {
        this.solutions = solutions;

        setTitle("Genetic Algorithm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        createSimulPanel();

        add(simulPanel);
    }

    private void createSimulPanel() {
        simulPanel = new JPanel();

        simulPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        simulPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        simulPanel.setBackground(Color.LIGHT_GRAY);

        simulPanel.setLayout(null);
    }

    public void paint (Graphics g) {

        Random random = new Random();
        for (Path path : solutions) {

            int stroke = path == solutions.lastElement() ? 5 : 2;
            ((Graphics2D)g).setStroke(new BasicStroke(stroke));
            g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));

            for (int i = 0; i < path.cities.size(); i++) {
                City city1 = path.cities.elementAt(i);
                City city2 = i == path.cities.size() - 1 ? path.cities.elementAt(0) : path.cities.elementAt(i + 1);

                g.drawLine(city1.getX(), city1.getY(), city2.getX(), city2.getY());
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        int cityNum = solutions.elementAt(0).cities.size();
        CitySet citySet = CitySet.getCitySet(cityNum);
        for (City city : citySet.cities)
            g.drawString(Integer.toString(city.getId()), city.getX(), city.getY());

        int cnt = 1;
        for (Path path : solutions)
            System.out.println("iteration: " + cnt++ + "\n" + path);
    }
}
