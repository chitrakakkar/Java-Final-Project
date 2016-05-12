package com.Chitra;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;


/**
 * Created by chitrakakkar on 4/23/16.
 * This class handles the GUI-> all the buttons and actions associated with each button
 * It does have one connection to the database to create a table again in the database.
 */
public class CoffeeGuiForm extends JFrame implements WindowListener
{
    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";

    private static final String DB_NAME = "coffeeshop";
    private static final String USER = "root";
    private static final String PASS = "password";
    private static java.sql.Statement statement = null;

    public final static String Report_Table_Name = "Sale_Report";
    public final static String PK_Column = "Sales_ID";

    public final static String DrinkName_Column = "Drink_Name";
    public final static String Sale_Per_Drink = "Total_Price";
    public final static String Date_Of_Sale_Column = "Date_OF_Sale";
    private JScrollPane scrollPane1;
    private JButton writeINToReportTableButton;
    private JPasswordField AdminPassword;
    private JTextField ADDDRINKTEXT;
    private JTextField ADDPRICETEXT;
    private JButton addButton;
    private JButton deleteButton;
    private JTextField DateText;
    private JTextField SearchText;
    private JButton extractDataButton;
    private JButton writeToaFileButton;
    private JTable DrinkTable;
    private JLabel tagLabel;
    private JPanel rootPanel;
    private JButton activateButton;
    private JLabel AdminLabel;
    private JLabel addADrinkLabel;
    private JLabel addAPriceLabel;
    private JLabel searchStringLabel;
    private JButton quitAdminSectionButton;
    private JButton quitButton;
    private JTextField TotalPriceText;
    private JLabel TotalPriceLabel;
    private JButton calCulateFinalPriceButton;
    private JButton resetButton;
    private JTextField VatText;
    private JTextField FinalSumText;
    private JButton calculateTotalSumButton;
    private JTable ReportTable;
    private JScrollPane Summary;
    private JList InvoiceList;
    private JButton chartButton;
    private JList<Object> SaleReportList;


    private static ReportTableModel reportTableModel;

    private DefaultListModel<String> listModel;
    CoffeeDataModel coffeeDataModel;
    Connection conn = null;

    LinkedList<String> SelectedDrinks = new LinkedList<String>();


    static PreparedStatement preparedStatement = null;
    static ResultSet res = null;
    private long VatValue = 0;

    //constructor to get the data from coffee data model
    // so its goes like SQl->ResultSet-> CoffeeModel->GUI;
    CoffeeGuiForm(final CoffeeDataModel coffeeDataModel)
    {
        this.coffeeDataModel = coffeeDataModel; // gets info for the Jtable-> DrinkTable
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        setContentPane(rootPanel);
        pack();
        setTitle("Coffee_Sale");
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DrinkTable.setGridColor(Color.black);
        DrinkTable.setModel(coffeeDataModel);
        UnHighLightAdminSection(); // admin section Un-highlighted
        DateText.setText(dateFormat.format(date));
        DateText.setEditable(false);
        getContentPane().setBackground(Color.PINK);
        coffeeDataModel.findColumn("Drink_Quantity");
        activateButton.addActionListener(e ->
        {
            HighLightAdminSection();
            AdminPassword.setText("");

        });

        quitAdminSectionButton.addActionListener(e ->
        {
            UnHighLightAdminSection();
            ReportTable.setVisible(coffeeDataModel.adminMode);
        });
        //calculates the final price for each drink

        calCulateFinalPriceButton.addActionListener(e ->
        {
            SelectedDrinks.clear(); // clears the ordered drinks in the list
            listModel = new DefaultListModel<>();

            VatValue = Math.round(0.15 * getSum());
            TotalPriceText.setText(getSum().toString());
            VatText.setText((Long.toString(VatValue)));
            int rowCount = DrinkTable.getRowCount();
            //SelectedDrinks.add("Drink Name : " + "|" + " Drink Price ");
            for (int i = 0; i < rowCount; i++) {

                String dName = DrinkTable.getValueAt(i, 1).toString();
                Double dTotalPrice = Double.parseDouble(DrinkTable.getValueAt(i, 4).toString());
               if ((dTotalPrice != 0) && (!listModel.contains(dName))) {
//                    System.out.println("Drink Name : " + "|" + " Drink Price ")
                    SelectedDrinks.add(dName + "|" + dTotalPrice.toString());
                }
            }
            for (String st : SelectedDrinks
                    ) {
                listModel.addElement(st);
            }
            InvoiceList.setModel(listModel);
        });

        // calculates the total sum
        calculateTotalSumButton.addActionListener(e1 ->
        {
            long TotalSum = Math.round(VatValue + getSum());
            FinalSumText.setText(Long.toString(TotalSum));

        });
        // resets everything on this button click
        resetButton.addActionListener(e -> {

            ResetData();


        });
        // creates a new table called Sale_report table
        writeINToReportTableButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    //String Driver = "com.mysql.jdbc.Driver";
                    Connection conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
                    statement = conn.createStatement();
                    if (!ReportTableExists()) {
                        String CreateReportTable = " CREATE TABLE " + Report_Table_Name + "(" + PK_Column + " int NOT NULL AUTO_INCREMENT, " + DrinkName_Column + " varchar(100), " + Sale_Per_Drink + " DOUBLE ," + Date_Of_Sale_Column + " varchar(300), PRIMARY KEY(" + PK_Column + "))";
                        statement.execute(CreateReportTable);
                        System.out.println(" Report Table Created");
                    }
                    //Validation-check
                    if (FinalSumText.equals("") || TotalPriceText.equals("") || listModel == null) {
                        JOptionPane.showMessageDialog(rootPane, "No data to write into the report_Table");
                        return;
                    } else
                    {
                        int rowCount = DrinkTable.getRowCount();
                        String dName = "";
                        String timeOFSale = "";
                        Double Sales;
                        for (int i = 0; i < rowCount; i++) {
                            Sales = Double.parseDouble(DrinkTable.getValueAt(i, 4).toString());
                            if (Sales != 0.0) {
                                dName = DrinkTable.getValueAt(i, 1).toString();
                                timeOFSale = DateText.getText();
                                System.out.println("I am drink name " + dName + "I am the Price " + Sales + " i am the time " + timeOFSale);
                                // SQL to insert the value into the table
                                String addDataSQL = "INSERT INTO " + Report_Table_Name + "(" + DrinkName_Column + " , " + Sale_Per_Drink + " , " + Date_Of_Sale_Column + " )" + " VALUES (?,?,?)";
                                preparedStatement = conn.prepareStatement(addDataSQL);
                                preparedStatement.setString(1, dName);
                                preparedStatement.setString(2, Sales.toString());
                                preparedStatement.setString(3, timeOFSale);
                                preparedStatement.execute();
                            }
                        }
                    }
                } catch (SQLException se) {
                    System.out.println(se);
                }
            }
        });
        // add a new row to the drink table
        addButton.addActionListener(e ->
        {
            String drinkName = ADDDRINKTEXT.getText();
            Double Price = 0.0;
            if (drinkName == null || drinkName.trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a name for the drink");
                return;
            }
            //Validation-check
            try
            {
                Price = Double.parseDouble(ADDPRICETEXT.getText());

                if (Price.toString() == null || Price.toString().trim().equals(""))
                {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a price for the drink");
                    return;
                }
                if (Price < 0)
                {
                    throw new NumberFormatException("Price needs to be a positive number");

                }
            } catch (NumberFormatException ne)
            {
                JOptionPane.showMessageDialog(rootPane,
                        "please enter a valid price");
                return;
            }
            // inserts the row
            boolean insertRow = coffeeDataModel.insertRow(drinkName, Price, 0, 0.0);
            JOptionPane.showMessageDialog(rootPane, drinkName + " added successfully @ price " + Price);
            ADDDRINKTEXT.setText("");
            ADDPRICETEXT.setText("");
            if (!insertRow)
            {
                JOptionPane.showMessageDialog(rootPane, "Error adding new drink");
            }
        });
        // deletes a row from table
        deleteButton.addActionListener(e ->
        {
            boolean deleted = false;

            int currentRow = DrinkTable.getSelectedRow();
            if (currentRow == -1)
            {      // -1 means no row is selected. Display error message.
                JOptionPane.showMessageDialog(rootPane, "Please choose a drink to delete");
                return;
            }
            int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the row ?", " Warning", JOptionPane.YES_NO_OPTION);
            if(reply == JOptionPane.YES_OPTION)
            {
                deleted = coffeeDataModel.deleteRow(currentRow);
                JOptionPane.showMessageDialog(rootPane, " row " + currentRow + "th has been deleted");
            }
            else
            {
                return;
            }

            if (deleted)
            {
                Main.loadAllCoffeeData();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error deleting row");
            }

        });
        // extracts the view from sale_ report table with date as hard-coded;
        // can change it to any string, little tweaking needed in the sql command using prep statement
        extractDataButton.addActionListener(e ->
        {
            coffeeDataModel.adminMode = true;
            ReportTable.setVisible(coffeeDataModel.adminMode);
            //listModel = new DefaultListModel<Object>();
            try {

                String searchText = SearchText.getText(); // gives you the search date in this case
                //validation-check
                if (searchText == null || searchText.trim().equals("")
                        ) {
                    JOptionPane.showMessageDialog(rootPane, "Search box is empty !!! please enter a string to search");
                    SearchText.grabFocus();
                    return;

                }
                System.out.println("I am search " + searchText);
                conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
                statement = conn.createStatement();
                // command to get you the data from DB;
                String addSql = "select * from Sale_Report where Date_OF_Sale" + " = " + "'" + searchText + "'";
                //String addSql = " select * from Sale_Report where '?' = searchText ";
                res = statement.executeQuery(addSql);
                if (reportTableModel == null) {
                    reportTableModel = new ReportTableModel(res);

                } else {
                    reportTableModel.updateResultSet(res);

                }
                ReportTable.setModel(reportTableModel);
                ReportTable.setGridColor(Color.black);

            } catch (SQLException se) {
                System.out.println("I am the error " + se);
                System.out.println("Error extracting data !!!");
                return;
            }
        });
        // quits the windows
        quitButton.addActionListener(e ->
        {
            System.exit(0);

        });
        // writes the extracted view to a file from report table
        writeToaFileButton.addActionListener(e ->
        {
            if (reportTableModel == null) {
                JOptionPane.showMessageDialog(rootPane, " No data TO write !!! Please extract some data for your table");
                SearchText.grabFocus();
            } else {
                WriteIntoAFile.WriteIntoAFile(ReportTable);

            }

        });
        // this displays a chart for every day sale on a different jframe.
        chartButton.addActionListener(e ->
        {
            // a hashMap to get the X and Y co-ordinates
            HashMap<String, Double> ChartValues = new HashMap<String, Double>();
            LinkedList<String> drinkName = new LinkedList<String>();
            int rowsCount = ReportTable.getRowCount();
            for (int i = 0; i < rowsCount; i++)
            {
                drinkName.add(ReportTable.getValueAt(i, 1).toString()); // adds all the drinks

            }
            // gets the total price for all the drinks in the report table
            // if you change the table name to drink table,
            // it will show the graph for all the drinks till date
            // for all drinks-> change the line-341 and line 344 to drinkTable;
            try
            {
                for (String st : drinkName
                        ) {

                   // a query to get the total sum for each drink;
                    String sql = "SELECT SUM(Total_Price) AS overallPrice FROM Sale_Report WHERE Drink_Name = ?";
                    conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
                    preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setString(1, st);
                    ResultSet resultSet_DrinkName = preparedStatement.executeQuery();

                    // gets you the value for each drink
                    while (resultSet_DrinkName.next())
                    {
                        Double value = resultSet_DrinkName.getDouble("overallPrice");
                        ChartValues.put(st, value);

                    }
                }
                // draws the second frame
                System.out.println(" I am the chart value " + ChartValues);
                JFrame frame = new JFrame();
                final int FRAME_WIDTH = 800;
                final int FRAME_HEIGHT = 500;

                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                frame.setTitle("BarChart for sales till  " + SearchText.getText());
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setBackground(Color.GRAY);
                BarChartComponent component = new BarChartComponent(ChartValues);
                frame.add(component);
                frame.setVisible(true);
            } catch (SQLException se)
            {
                System.out.println("Error " + se);
            }


        });
    }

    // to record changes in the table dynamically-> not using it in this project

    //        DrinkTable.getModel().addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent e) {
//
//
//                int[] selectedRow = DrinkTable.getSelectedRows();
//                int[] selectedColumns = DrinkTable.getSelectedColumns();
//                String selectedData = "";
//
//
//                for (int i = 0; i < DrinkTable.getRowCount(); i++) {
//                    for (int j = 0; j < selectedColumns.length; j++) {
//                        selectedData = (String) DrinkTable.getValueAt(selectedRow[i], selectedColumns[j]);
//
//
//                    }
//                }
//                System.out.println("Selected: " + selectedData);
//
//            }
//        });
//    }

    //checks if the report table exists, if do-> does not crate another table
    private static boolean ReportTableExists() throws SQLException
    {
        String checkTablePresentQuery = " SHOW TABLES LIKE '" + Report_Table_Name + "'";
        ResultSet tablesRS = statement.executeQuery(checkTablePresentQuery);
        if (tablesRS.next()) {    //If ResultSet has a next row, it has at least one row... that must be our table
            return true;
        }
        return false;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }
    // closes all the connection
    @Override
    public void windowClosing(WindowEvent e) {
        Main.shutdown();
        System.out.println("Closing");

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {


    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
        // method to un-highlight the admin section when the GUI loads up
    public void UnHighLightAdminSection() {

        AdminLabel.setEnabled(false);
        ADDDRINKTEXT.setEnabled(false);
        ADDPRICETEXT.setEnabled(false);
        addADrinkLabel.setEnabled(false);
        addAPriceLabel.setEnabled(false);
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        searchStringLabel.setEnabled(false);
        SearchText.setEnabled(false);
        extractDataButton.setEnabled(false);
        writeToaFileButton.setEnabled(false);
        quitAdminSectionButton.setEnabled(false);
        coffeeDataModel.adminMode = false;
        SearchText.setText("");
        chartButton.setEnabled(false);


    }
    // a method to highlight the admin section when the user enters and matches the password

    public void HighLightAdminSection()
    {
        String password = String.valueOf(AdminPassword.getPassword());
        System.out.println("I am the password " + password);
        if (password.equals("JavaProject")) {

            AdminLabel.setEnabled(true);
            ADDDRINKTEXT.setEnabled(true);
            ADDPRICETEXT.setEnabled(true);
            addADrinkLabel.setEnabled(true);
            addAPriceLabel.setEnabled(true);
            addButton.setEnabled(true);
            deleteButton.setEnabled(true);
            searchStringLabel.setEnabled(true);
            SearchText.setEnabled(true);
            extractDataButton.setEnabled(true);
            writeToaFileButton.setEnabled(true);
            quitAdminSectionButton.setEnabled(true);
            coffeeDataModel.adminMode = true;
            chartButton.setEnabled(true);


        } else
        {
            JOptionPane.showMessageDialog(rootPane, "Wrong Password, try again");
            AdminPassword.grabFocus();
        }
    }
    // a method to get the final price for all the drinks; before the VAT calculation
    public Double getSum() {
        int rowsCount = DrinkTable.getRowCount();
        Double sum = 0.0;
        for (int i = 0; i < rowsCount; i++) {
            sum = sum + Double.parseDouble(DrinkTable.getValueAt(i, 4).toString());
        }
        return sum;
    }

    // resets the data to become ready for the bext order
    public void ResetData()
    {
        for (int j = 0; j < DrinkTable.getRowCount(); j++)
        {
            if (listModel != null || ((!DrinkTable.getValueAt(j, 3).equals(0))))
            {
                FinalSumText.setText("");
                VatText.setText("");
                TotalPriceText.setText("");
                int rowCount = DrinkTable.getRowCount();
                for (int i = 0; i < rowCount; i++)
                {
                    DrinkTable.setValueAt(0, i, 3); // sets the quantity column to 0
                    DrinkTable.setValueAt(0, i, 4); // price to 0;
                }
            }
            else
            {
                JOptionPane.showMessageDialog(rootPane,"Nothing to reset");
            }
        }
        if (listModel != null) {
            listModel.removeAllElements();

        }

        return;
    }
}











