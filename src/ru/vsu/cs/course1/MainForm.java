package ru.vsu.cs.course1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainForm {
    private static final String FORM_TITLE = "Task 6_15 by @kalitkin_a_v";
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JButton exitButton;
    private JButton insertButton;
    private JButton findButton;
    private JButton insertAndDeleteButton;
    private JSpinner measurementsSpinner;
    private JSpinner operationsSpinner;
    private JSpinner keyLengthSpinner;
    private JButton deleteButton;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public static void main(String[] args) {
        FormUtils.prepare();
        MainForm mainForm = new MainForm();

        JFrame frame = new JFrame(FORM_TITLE);
        frame.setContentPane(mainForm.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FormUtils.processFormAfter(mainForm.rootPanel, frame, true);
    }

    private int getMeasurementsNumber() {
        return (int) measurementsSpinner.getValue();
    }

    private int getOperationsNumber() {
        return (int) operationsSpinner.getValue();
    }

    private int getKeyLength() {
        return (int) keyLengthSpinner.getValue();
    }

    private void displayChart(DefaultXYDataset xy, String title) {
        chart = ChartFactory.createXYLineChart(String.format("Map performance comparison - %s", title), "Number of elements", "Time, ms", xy);
        chartPanel.setChart(chart);
    }

    MainForm() {
        measurementsSpinner.setModel(new SpinnerNumberModel(100,  1, 10000, 1));
        operationsSpinner.setModel(new SpinnerNumberModel(1000,  1, 10000, 1));
        keyLengthSpinner.setModel(new SpinnerNumberModel(8,  1, 256, 1));
        chartPanel = new ChartPanel(null);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(chartPanel);
        insertButton.addActionListener(e -> testInsert());
        deleteButton.addActionListener(e -> testDelete());
        findButton.addActionListener(e -> testFind());
        insertAndDeleteButton.addActionListener(e -> testInsertAndDelete());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void testInsert() {
        DefaultXYDataset xy = PerfTester.testInsertPerformance(getMeasurementsNumber(), getOperationsNumber(), getKeyLength());
        displayChart(xy, "Insert Operation");
    }

    private void testDelete() {
        DefaultXYDataset xy = PerfTester.testDeletePerformance(getMeasurementsNumber(), getOperationsNumber(), getKeyLength());
        displayChart(xy, "Delete Operation");
    }

    private void testInsertAndDelete() {
        DefaultXYDataset xy = PerfTester.testInsertAndDeletePerformance(getMeasurementsNumber(), getOperationsNumber(), getKeyLength());
        displayChart(xy, "Insert and Delete Operations");
    }

    private void testFind() {
        DefaultXYDataset xy = PerfTester.testFindPerformance(getMeasurementsNumber(), getOperationsNumber(), getKeyLength());
        displayChart(xy, "Find Operation");
    }
}
