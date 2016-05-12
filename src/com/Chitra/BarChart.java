package com.Chitra;
import java.awt.*;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * Created by chitrakakkar on 5/9/16.
 * Reference : - https://gist.github.com/jimmykurian/1856973
 * http://stackoverflow.com/questions/8693342/drawing-a-simple-line-graph-in-java
 * I got the main idea from the stack-over flow and followed the above mentioned github address to
 * draw the chart
 */

public class BarChart
{
    private int width;
    private int height;
    HashMap<String,Double> Sale_Report = new HashMap<>();
    int BorderGap =30; // gap for bar from x-axis and y-axis

    // constructor to get width and HEight
    public BarChart(int aWidth, int aHeight )
    {
        width = aWidth;
        height = aHeight;

    }
    // a method to add data to the hashMap

    public void add(String dname, Double sale)
    {
        Sale_Report.put(dname,sale);
    }
    // this rotates the y-axis title to an angle so that it stays vertical.
    public void drawRotate(Graphics2D g2, double x, double y, int angle, String text)
    {
        g2.translate((float) x, (float) y);
        g2.rotate(Math.toRadians(angle));
        g2.drawString(text, 0, 0);
        g2.rotate(-Math.toRadians(angle));
        g2.translate(-(float) x, -(float) y);
    }

    // draws the barGraph
    public void draw(Graphics2D g2)
    {
        int i = 0;
        double maxSale = 100; // reference for sale for each drink;

//        for (String DrinkName : Sale_Report.keySet())
//            if(maxSale < Sale_Report.get(DrinkName))
//                maxSale = Sale_Report.get(DrinkName);

        int xwidth = width - 2* BorderGap;
        int yheight = height - 2* BorderGap;

        int xleft = BorderGap; // the bar starts from here

        for (String DrinkName : Sale_Report.keySet())
        {
            // gives points to draw the rectangle
            int xright = (xwidth * (i + 1) / Sale_Report.size()-1) + BorderGap;
            int barWidth = (xwidth)/ Sale_Report.size()-1;
            int barHeight = (int) Math.round(yheight * Sale_Report.get(DrinkName) / maxSale); // to normalize the drink values

                // rectangle class object to draw the Bar
            Rectangle bar =
                    new Rectangle(xleft, yheight - barHeight+BorderGap,
                            barWidth, barHeight);

            Font font = new Font("Arial", Font.PLAIN, 11);


            //FontMetrics metrics = g2.getFontMetrics(font);

            g2.setFont(font);
            g2.setColor(Color.BLUE);
            // to fill the rectangle
            g2.fill3DRect(xleft, yheight - barHeight+BorderGap,barWidth, barHeight,true);
            g2.drawString(DrinkName, (xleft+barWidth/2),(yheight+ (BorderGap + 10 )));
            drawRotate(g2, (BorderGap/2), (yheight/2 ), 270, "TotalSales in  Hundreds ($) ");
           // g2.drawString("TotalSales in  Hundreds" , (BorderGap/2),(yheight/2 ));
            g2.draw(bar);
            xleft = xright;
            i++;
        }

    }
}



