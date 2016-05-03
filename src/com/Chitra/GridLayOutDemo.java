package com.Chitra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by chitrakakkar on 4/29/16.
 */
public class GridLayOutDemo extends JFrame
{
    static final String gapList[] = {"0", "10", "15", "20"};
    final static int maxGap = 10;

    GridLayout experimentLayout = new GridLayout(0,2);

    public GridLayOutDemo()
    {
        super();
        setResizable(false);
    }



    public void addComponentsToPane(final Container pane) {
        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 3));

        //Set up components preferred size
        JButton b = new JButton("Just fake button");
        Dimension buttonSize = b.getPreferredSize();
        compsToExperiment.setPreferredSize(new Dimension((int) (buttonSize.getWidth() * 2.5) + maxGap,
                (int) (buttonSize.getHeight() * 3.5) + maxGap * 2));

        //Add buttons to experiment with Grid Layout
        compsToExperiment.add(new JButton("Coffee"));
        compsToExperiment.add(new JButton("Button 2"));
        compsToExperiment.add(new JButton("Button 3"));
        compsToExperiment.add(new JButton("Long-Named Button 4"));
        compsToExperiment.add(new JButton("5"));

        //Add controls to set up horizontal and vertical gaps

        pane.add(compsToExperiment, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH);



    }

}


