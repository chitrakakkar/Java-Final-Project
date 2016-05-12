package com.Chitra;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chitrakakkar on 4/29/16.
 * this class designs the data into table from result set;
 * Gets you the number of rows, columns, and data to be filled in.
 */
public class CoffeeDataModel extends AbstractTableModel

{
    ResultSet resultSet;
    int numberOfRows;


    int numberOfColumns;
    public static Double FinalSum =0.0;
    Double totalPrice =0.0;


    boolean adminMode = false;

    public Double getTotalPrice()
    {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // constructor gets the result set
    // and rowCount and columnCount
    public CoffeeDataModel(ResultSet rs)
    {
        this.resultSet = rs;
        setup();

    }
    //updates the result set

    public  void updateResultSet(ResultSet newRS)
    {
        resultSet = newRS;
        setup();

    }
    // counts number of column basis metadata from resultSet and counts the row too
    private void setup()
    {

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
        numberOfRows = 0;
        try {
            //Move cursor to the start...
            resultSet.beforeFirst();
            // next() method moves the cursor forward one row and returns true if there is another row ahead
            while (resultSet.next())
            {
                numberOfRows++;

            }
            resultSet.beforeFirst();

        } catch (SQLException se) {
            System.out.println("Error counting rows " + se);
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
            if(columnIndex == 4)
            {
                Double price = Double.parseDouble( getValueAt(rowIndex,2).toString());
                Integer quantity = Integer.parseInt(getValueAt(rowIndex,3).toString());
                Double TotalPrice = (double)Math.round(price * quantity);
                totalPrice = new Double(TotalPrice);
                return totalPrice.toString();
            }
            else
            {
                try
                {
                Object o = resultSet.getObject(columnIndex + 1);
                return o.toString();
                }
                catch (NullPointerException np)
                {
                    return " Hello";
                }
            }
        }
        catch (SQLException se)
        {
            System.out.println(se);
            return se.toString();

        }
     }
    public  boolean deleteRow(int row)
    {
        try
        {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //Tell table to redraw itself
            fireTableDataChanged();
            return true;
        }catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }
    // Updates the new value into a row using result set whenever
    // user edits an editable cell which is Time _Taken in this case
    public void setValueAt(Object newValue, int row, int col)
    {
        // quantity column always editable
        if(col == 3)
        {
            //Make sure newValue is a positive number
            Double newQuantity;
            try {
                newQuantity = Double.parseDouble(newValue.toString());
                if (newQuantity < 0.0) {
                    throw new NumberFormatException("Quantity  must be a postive double number");
                }
            } catch (NumberFormatException ne) {
                //Error dialog box. First argument is the parent GUI component, which is only used to center the
                // dialog box over that component. We don't have a reference to any GUI components here
                // but are allowed to use null - this means the dialog box will show in the center of your screen.
                JOptionPane.showMessageDialog(null, "Try entering a positive number for quantity");
                //return prevents the following database update code happening...
                return;
            }
            try {
                resultSet.absolute(row + 1);
                resultSet.updateDouble(Main.Quantity_Column, newQuantity);
                //System.out.println("Updated the Quantity");
                resultSet.updateRow();
                fireTableDataChanged();
                //TotalPriceText.setText(getSum().toString());
            } catch (SQLException se) {
                System.out.println("Error updating the quantity" + se);
            }
        }
        // gives authority to change the price if in admin section
        if(adminMode && col==2)
        {
            Double newPRice;
            try
            {
                newPRice = Double.parseDouble(newValue.toString());
                if(newPRice<0.0)
                {
                    throw new NumberFormatException("Time Taken  must be a postive double number");

                }
            }
            catch (NumberFormatException ne)
            {
                JOptionPane.showMessageDialog(null, "Try entering a positive number for quantity");
                return;
            }
            try
            {
                resultSet.absolute(row + 1);
                resultSet.updateDouble(Main.Price_Column, newPRice);
                resultSet.updateRow();
                fireTableDataChanged();

            }catch (SQLException se)
            {
                System.out.println("Error updating the price" + se);
            }
        }

    }

    // allows to edit the cell(Time taken in this case)
    public boolean isCellEditable(int row, int col)
    {
        // if(col == resultSet.findColumn(Main.Time_Taken)
        if (col == 3)
        {
            return true;
        }
        if(adminMode && col==2)
        {
            return true;
        }
        return false;
    }
    // Inserts the value into a row using result set
    public  boolean insertRow(String dn, Double pc, int Q,Double TP)
    {
        try {
            resultSet.moveToInsertRow();
            resultSet.updateString(Main.DrinkName_Column, dn);
            resultSet.updateDouble(Main.Price_Column,pc);
            resultSet.updateInt(Main.Quantity_Column,Q);
            resultSet.updateDouble(Main.Total_Price,TP);
            resultSet.insertRow();
            fireTableDataChanged();
            return true;
        }
        catch (SQLException e)
        {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
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