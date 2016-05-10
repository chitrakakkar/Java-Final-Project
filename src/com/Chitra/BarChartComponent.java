package com.Chitra;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

/**
 * Created by chitrakakkar on 5/9/16.
 */
public class BarChartComponent extends JComponent
{
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
