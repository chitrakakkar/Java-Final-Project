package com.Chitra;

import com.sun.tools.javac.util.Name;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chitrakakkar on 5/6/16.
 */
public class WriteIntoAFile
{
    private static String ReportFile = "Sale_PerDay_Report.txt";
    static void WriteIntoAFile(JTable reportTable)
    {
        int rowCount = reportTable.getRowCount();
        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter(ReportFile,true));
            writer.write(String.format("%3s %15s %6s %12s \n", "ID", "Name","Price", "Date"));
            String test = "";
            for(int i =0;i<rowCount;i++)
            {
                //test =  String.format("%3s %15s %6s %12s \r\n", "ID", "Name","Price", "Date");
                List<String> list = new LinkedList<>();
                for (int j = 0; j < reportTable.getColumnCount(); j++)
                {
                    list.add(reportTable.getValueAt(i, j).toString());
                    //writer.write((reportTable.getValueAt(i, j).toString()));
                }
                test = test + String.format("%3s %15s %6s %12s ", list.get(0),list.get(1),list.get(2),list.get(3))+ "\n";
            }
            writer.write(test);
            writer.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Error writing data");
        }

    }

}
