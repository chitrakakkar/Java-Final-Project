package com.Chitra;

import com.sun.tools.javac.util.Name;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

            BufferedWriter writer = new BufferedWriter(new FileWriter(ReportFile,false));
            for(int i =0;i<rowCount;i++)
            {
                for (int j = 0; j < reportTable.getColumnCount(); j++)
                {
                    writer.write((reportTable.getValueAt(i, j).toString())+"\n");
                }
            }
            writer.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Error writing data");
        }

    }

}
