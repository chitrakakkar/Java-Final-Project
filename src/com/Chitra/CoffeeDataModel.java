package com.Chitra;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chitrakakkar on 4/29/16.
 */
public class CoffeeDataModel extends AbstractTableModel

{
    ResultSet resultSet;
    int numberOfRows;
    int numberOfColumns;
    public static Double FinalSum =0.0;
    Double totalPrice =0.0;

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
    public  Double getFinalSum()
    {
        return FinalSum;
    }

    public  void setFinalSum(Double finalSum)
    {
        FinalSum = totalPrice + FinalSum ;
        System.out.println("Final Sum = " + FinalSum);
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
            if(columnIndex ==4)
            {
                Double price = Double.parseDouble( getValueAt(rowIndex,2).toString());
                Integer quantity = Integer.parseInt(getValueAt(rowIndex,3).toString());
                Double TotalPrice = price * quantity;
                totalPrice = new Double(TotalPrice);
                return totalPrice.toString();
            }
            else
            {
                try{
                Object o = resultSet.getObject(columnIndex + 1);
                return o.toString();
                }
                catch (NullPointerException np)
                {
                    return "";
                }
            }
        }
        catch (SQLException se)
        {
            System.out.println(se);
            return se.toString();

        }
//      try {
//            //move cursor by one row
//          resultSet.absolute(rowIndex + 1);
//           Object o = resultSet.getObject(columnIndex + 1);
//           return o.toString();
//       } catch (SQLException se) {
//            System.out.println(se);
//           //se.printStackTrace();
//           return se.toString();
//      }
   }
    // deletes the row selected in GUI using predefined method called deleteRow
    // On the resultSet
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
    public void setValueAt(Object newValue, int row, int col) {
        //Make sure newValue is a positive number
        Double newQuantity;

        try {
            newQuantity = Double.parseDouble(newValue.toString());
            if (newQuantity < 0.0)
            {
                throw new NumberFormatException("Time Taken  must be a postive double number");
            }
        } catch (NumberFormatException ne) {
            //Error dialog box. First argument is the parent GUI component, which is only used to center the
            // dialog box over that component. We don't have a reference to any GUI components here
            // but are allowed to use null - this means the dialog box will show in the center of your screen.
            JOptionPane.showMessageDialog(null, "Try entering a positive number");
            //return prevents the following database update code happening...
            return;
        }
        try
        {
            resultSet.absolute(row + 1);
            resultSet.updateDouble(Main.Quantity_Column, newQuantity);
            //System.out.println("Updated the Quantity");
            resultSet.updateRow();
            fireTableDataChanged();
            //TotalPriceText.setText(getSum().toString());
        }
        catch (SQLException se)
        {
            System.out.println("Error updating the time" + se);
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
        else if(col ==2)
        {

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

    // up vote
    //down vote
    //table.getSelectedRow() will get selected row.

        //table.getSelectedColumns() will get selected columns.

    //getValueAt(rowIndex, columnIndex) will give the value present at the selected row for each column.

    //if (jTable1.getCellEditor() == null) {
    //System.out.println("Not Edited");

} //else {

        //System.out.println(jTable1.getValueAt(jTable1.getSelectedRow(),jTable1.getSelectedColumn()));
       // }

//}
