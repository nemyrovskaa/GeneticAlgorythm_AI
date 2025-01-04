package com.kpi.gui;

import com.kpi.Path;
import com.kpi.TravelSolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SetUpWindow extends JFrame implements ActionListener {
    private static final int WINDOW_WIDTH  = 800;
    private static final int WINDOW_HEIGHT = 500;
    private static final int TEXT_FIELD_WIDTH = 150;
    private static final int BUTTON_WIDTH  = 100;
    private static final int ITEM_HEIGHT = 30;

    private JPanel setUpPanel;
    private JTextField cityNumField, iterNumField, mutPercField;

    public SetUpWindow() {
        setTitle("Genetic Algorithm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);

        createSetUpPanel();

        this.add(setUpPanel);
    }

    private void createSetUpPanel() {
        setUpPanel = new JPanel();
        setUpPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setUpPanel.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        setUpPanel.setBackground(Color.LIGHT_GRAY);

        //------------------------TITLE------------------------
        JLabel title = new JLabel("Genetic Algorithm Simulation", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        title.setBounds((WINDOW_WIDTH - 300)/2, 50, 300, 50);
        setUpPanel.add(title);

        //-----------------------LABELS-----------------------
        JLabel cityNumLabel = new JLabel("Number of cities:");
        cityNumLabel.setBounds(150, 150, 150, ITEM_HEIGHT);
        setUpPanel.add(cityNumLabel);

        JLabel iterNumLabel = new JLabel("Number of iterations:");
        iterNumLabel.setBounds(150, 200, 150, ITEM_HEIGHT);
        setUpPanel.add(iterNumLabel);

        JLabel mutPercLabel = new JLabel("Mutation percentage:");
        mutPercLabel.setBounds(150, 250, 150, ITEM_HEIGHT);
        setUpPanel.add(mutPercLabel);

        //-----------------------TEXT-FIELDS-----------------------
        cityNumField = new JTextField();
        cityNumField.setBounds((WINDOW_WIDTH - TEXT_FIELD_WIDTH) / 2, 150, TEXT_FIELD_WIDTH, ITEM_HEIGHT);
        cityNumField.setBackground(Color.WHITE);
        setUpPanel.add(cityNumField);

        iterNumField = new JTextField();
        iterNumField.setBounds((WINDOW_WIDTH - TEXT_FIELD_WIDTH) / 2, 200, TEXT_FIELD_WIDTH, ITEM_HEIGHT);
        iterNumField.setBackground(Color.WHITE);
        setUpPanel.add(iterNumField);

        mutPercField = new JTextField();
        mutPercField.setBounds((WINDOW_WIDTH - TEXT_FIELD_WIDTH) / 2, 250, TEXT_FIELD_WIDTH, ITEM_HEIGHT);
        mutPercField.setBackground(Color.WHITE);
        setUpPanel.add(mutPercField);

        //-----------------------START-BUTTON-----------------------
        JButton startButton = new JButton("Start");
        startButton.setBackground(Color.WHITE);
        startButton.setBounds((WINDOW_WIDTH - BUTTON_WIDTH)/2, 350, BUTTON_WIDTH, ITEM_HEIGHT);
        startButton.setHorizontalTextPosition(JButton.CENTER);
        startButton.setVerticalTextPosition(JButton.CENTER);
        startButton.setFocusable(false);
        startButton.setActionCommand("startGASimulation");
        startButton.addActionListener(this);
        setUpPanel.add(startButton);

        setUpPanel.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("startGASimulation")) {
            int cityNum, iterNum, mutPrec;
            try {
                cityNum = Integer.parseInt(cityNumField.getText());
                iterNum = Integer.parseInt(iterNumField.getText());
                mutPrec = Integer.parseInt(mutPercField.getText());

            } catch (NumberFormatException numberFormatException) {
                JOptionPane.showMessageDialog(null,
                        "Fill all the fields, please.",
                        "Filling the form error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.dispose();
            TravelSolution travelSolution = new TravelSolution(cityNum, iterNum, mutPrec);
            Vector<Path> solutions = travelSolution.findSolution();
            SimulWindow simulWindow = new SimulWindow(solutions);
        }
    }
}
