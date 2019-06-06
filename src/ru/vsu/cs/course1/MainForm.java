package ru.vsu.cs.course1;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ru.vsu.cs.course1.PerfTester;


public class MainForm {
    private static final String FORM_TITLE = "Task 6_15 by @kalitkin_a_v";
    private JPanel rootPanel;
    private JPanel drawPanel;
    private JButton exitButton;
    private JButton insertButton;
    private JButton findButton;
    private JButton deleteButton;
    private JButton insertAndDeleteButton;
    private DefaultXYDataset xy;
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

    MainForm() {
        chartPanel = new ChartPanel(null);
        drawPanel.setLayout(new GridLayout());
        drawPanel.add(chartPanel);
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xy = new DefaultXYDataset();
                int iterations = 1000;
                int timesPerIteration = 1000;
                double[][] doubles = PerfTester.millisXY(PerfTester.testDummy(iterations, timesPerIteration), timesPerIteration);
                xy.addSeries("График 1", doubles);
                chart = ChartFactory.createXYLineChart("Тест мпроизводительности", "Количество элементов", "Время, мс", xy);
                chartPanel.setChart(chart);
            }
        });
    }
}
