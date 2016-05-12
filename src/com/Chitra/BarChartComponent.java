package com.Chitra;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

/**
 * Created by chitrakakkar on 5/9/16.
 * This gives the X and Y co-ordinates to the BarChart class
 *To draw the rectangle(BAR)
 * JComponent class gives methods like getHeights() and getWidth()
 * Returns the current width/Height of this component.*/

public class BarChartComponent extends JComponent
{
    // a hashmap to get the x and y value together
    HashMap<String,Double> ChartValues;
    String dname = "";
    Double price = 0.0;

    public BarChartComponent(HashMap<String,Double> Values)
    {
        this.ChartValues= Values;

    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        // creating a BarChart object to add components to it.
        BarChart c = new BarChart(getWidth(), getHeight());
        for (String k:ChartValues.keySet()
             ) {

            dname = k;
            price = ChartValues.get(dname);
            //System.out.println("I am the drink name " + dname + "Price " + price);
            c.add(dname,price);


        }
        c.draw(g2);
    }

}
