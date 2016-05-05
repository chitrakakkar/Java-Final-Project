package com.Chitra;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.beans.*;
import java.util.LinkedList;
import java.util.Objects;


/**
 * Created by chitrakakkar on 4/23/16.
 */
public class CoffeeGuiForm extends JFrame implements WindowListener {
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
    private JList<Object> SaleReportList;

    private DefaultListModel<Object> listModel;
    CoffeeDataModel coffeeDataModel;

    LinkedList<Object> SalesReportData = new LinkedList<Object>();


    static PreparedStatement preparedStatement = null;
    static ResultSet res = null;
    private Double VatValue = 0.0;


    CoffeeGuiForm(final CoffeeDataModel coffeeDataModel) {
        this.coffeeDataModel = coffeeDataModel;
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
        UnHighLightAdminSection();
        DateText.setText(dateFormat.format(date));
        DateText.setEditable(false);
        getContentPane().setBackground(Color.PINK);
        coffeeDataModel.findColumn("Drink_Quantity");
        activateButton.addActionListener(e ->
        {
            HighLightAdminSection();
            AdminPassword.setText("");

        });

        quitAdminSectionButton.addActionListener(e -> {
            UnHighLightAdminSection();
        });

        calCulateFinalPriceButton.addActionListener(e ->
        {
            VatValue = (0.15 * getSum());


            TotalPriceText.setText(getSum().toString());
            VatText.setText(VatValue.toString());
        });

        calculateTotalSumButton.addActionListener(e1 ->
        {
            Double TotalSum = VatValue + getSum();
            FinalSumText.setText(TotalSum.toString());

        });

        resetButton.addActionListener(e -> {

            ResetData();

        });
        writeINToReportTableButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    String Driver = "com.mysql.jdbc.Driver";
                    //Class.forName(Driver);
                    Connection conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
                    statement = conn.createStatement();
                    if (!ReportTableExists()) {
                        String CreateReportTable = " CREATE TABLE " + Report_Table_Name + "(" + PK_Column + " int NOT NULL AUTO_INCREMENT, " + DrinkName_Column + " varchar(100), " + Sale_Per_Drink + " DOUBLE ," + Date_Of_Sale_Column + " varchar(300), PRIMARY KEY(" + PK_Column + "))";
                        statement.execute(CreateReportTable);
                        System.out.println(" Report Table Created");
                    }

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


                            String addDataSQL = "INSERT INTO " + Report_Table_Name + "(" + DrinkName_Column + " , " + Sale_Per_Drink + " , " + Date_Of_Sale_Column + " )" + " VALUES (?,?,?)";
                            preparedStatement = conn.prepareStatement(addDataSQL);
                            preparedStatement.setString(1, dName);
                            preparedStatement.setString(2, Sales.toString());
                            preparedStatement.setString(3, timeOFSale);
                            preparedStatement.execute();
                        }
                    }
                } catch (SQLException se) {
                    System.out.println(se);
                }
            }
        });

        addButton.addActionListener(e ->
        {
            String drinkName = ADDDRINKTEXT.getText();
            Double Price = 0.0;
            if (drinkName == null || drinkName.trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Please enter a name for the drink");
                return;

            }
            try {
                Price = Double.parseDouble(ADDPRICETEXT.getText());

                if (Price.toString() == null || Price.toString().trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a price for the drink");
                    return;

                }
                if (Price < 0) {
                    throw new NumberFormatException("Price needs to be a positive number");

                }
            } catch (NumberFormatException ne) {
                JOptionPane.showMessageDialog(rootPane,
                        "please enter a valid value");
                return;
            }
            boolean insertRow = coffeeDataModel.insertRow(drinkName, Price, 0, 0.0);
            ADDDRINKTEXT.setText("");
            ADDPRICETEXT.setText("");
            if (!insertRow) {
                JOptionPane.showMessageDialog(rootPane, "Error adding new drink");

            }
        });

        deleteButton.addActionListener(e ->
        {
            int currentRow = DrinkTable.getSelectedRow();
            if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                JOptionPane.showMessageDialog(rootPane, "Please choose a drink to delete");
            }
            boolean deleted = coffeeDataModel.deleteRow(currentRow);
            if (deleted) {
                Main.loadAllCoffeeData();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Error deleting row");
            }

        });

        extractDataButton.addActionListener(e ->
        {
            listModel = new DefaultListModel<Object>();
            try {
                String searchText = SearchText.getText();
                System.out.println("I am search " + searchText);
                Connection conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
                statement = conn.createStatement();
                //String addSql = "SELECT * FROM " + Report_Table_Name + " WHERE " + Date_Of_Sale_Column + " = " + searchText;
                //String addSql = "select * from Sale_Report where Date_OF_Sale = '2016/05/03'";
                String addSql = "select * from Sale_Report where Date_OF_Sale" + " = " + "'" + searchText + "'";
                res = statement.executeQuery(addSql);

                CoffeeDataModel newTable = new CoffeeDataModel(res);

                while (res.next()) {
                    SalesReportData.add(res.getString(1).trim());
                    SalesReportData.add(res.getString(2).trim());
                    SalesReportData.add(res.getString(3).trim());
                    SalesReportData.add(res.getString(4).trim());

                }
                for (Object st : SalesReportData) {
                    listModel.addElement(st);
                }

                SaleReportList.setModel(listModel);
            } catch (SQLException se) {
                System.out.println("I am the error " + se);
                System.out.println("Error extracting data !!!");
                return;
            }
        });

    }

    private static boolean ReportTableExists() throws SQLException {
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

    public void UnHighLightAdminSection()
    {

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


    }

    public void HighLightAdminSection() {
        String password = String.valueOf(AdminPassword.getPassword());
        System.out.println("I am the password " + password);
        if (password.equals("JavaProject"))
        {

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

        } else {
            JOptionPane.showMessageDialog(rootPane, "Wrong Password, try again");
            AdminPassword.grabFocus();
        }
    }

    public Double getSum() {
        int rowsCount = DrinkTable.getRowCount();
        Double sum = 0.0;
        for (int i = 0; i < rowsCount; i++) {
            sum = sum + Double.parseDouble(DrinkTable.getValueAt(i, 4).toString());
        }
        return sum;
    }

    public void ResetData() {
        FinalSumText.setText("");
        VatText.setText("");
        TotalPriceText.setText("");
        int rowCount = DrinkTable.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            DrinkTable.setValueAt(0, i, 3);
            DrinkTable.setValueAt(0, i, 4);

            // ((CoffeeDataModel)DrinkTable.getModel()).
        }

    }


}











