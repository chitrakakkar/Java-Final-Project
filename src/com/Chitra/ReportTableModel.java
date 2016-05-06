package com.Chitra;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chitrakakkar on 5/6/16.
 */
public class ReportTableModel extends AbstractTableModel
{
    ResultSet resultSet;
    int numberOfRows;
    int numberOfColumns;


    // constructor gets the result set
    // and rowCount and columnCount
    public ReportTableModel(ResultSet rs)
    {
        this.resultSet = rs;
        setup();

    }


//updates the result set

    public void updateResultSet(ResultSet newRS)
    {
        resultSet = newRS;
        setup();

    }

    // counts number of column basis metadata from resultSet and counts the row too
    private void setup() {

        countRows();

        try {
            numberOfColumns = resultSet.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }
    // counts the row basis number of elements present in the ResultSet

    private void countRows()
    {
        numberOfRows =0;
        try
        {
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                numberOfRows++;
            }
            resultSet.beforeFirst();
        }
        catch (SQLException se)
        {
            System.out.println("Error counting rows" + se);
        }
    }
    @Override
    public int getRowCount() {
        countRows();
        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return numberOfColumns;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        try
        {
            resultSet.absolute(rowIndex + 1);
            Object o = resultSet.getObject(columnIndex + 1);
            return o.toString();

        }
        catch (SQLException se)
        {
            System.out.println(se);
            return se.toString();
        }
    }
    @Override
    public String getColumnName(int col)
    {
        //Get from ResultSet metadata, which contains the database column names
        //TODO translate DB column names into something nicer for display, so "YEAR_RELEASED" becomes "Year Released"
        try
        {
            return resultSet.getMetaData().getColumnName(col + 1);
        } catch (SQLException se)
        {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }
}