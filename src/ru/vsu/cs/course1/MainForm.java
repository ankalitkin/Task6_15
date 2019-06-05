package ru.vsu.cs.course1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;


public class MainForm {
    public static final String FORM_TITLE = "Task 5_15 by @kalitkin_a_v";
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JButton exitButton;
    private JButton insertButton;
    private JButton findButton;
    private JButton deleteButton;
    private JButton insertAndDeleteButton;

    public static void main(String[] args) {
        FormUtils.prepare();
        MainForm mainForm = new MainForm();

        JFrame frame = new JFrame(FORM_TITLE);
        frame.setContentPane(mainForm.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FormUtils.processFormAfter(mainForm.rootPanel, frame, true);
    }

    MainForm() {
        DefaultXYDataset xy = new DefaultXYDataset();
        double[][] doubles = {{0, 1, 4}, {0, 1, 2}};
        xy.addSeries("График 1", doubles);
        JFreeChart chart = ChartFactory.createXYLineChart("My chart", "Ось абсцисс", "Ось ординат", xy);
        ChartPanel chartPanel = new ChartPanel(chart);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(chartPanel);
    }
}
