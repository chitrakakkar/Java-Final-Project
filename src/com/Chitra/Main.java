package com.Chitra;

import java.sql.*;

public class Main
{
    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";

    private static final String DB_NAME = "coffeeshop";
    private static final String USER = "root";
    private static final String PASS = "password";

    public final static String Coffee_Table_Name = "Drink_Name_Price";
    public final static String PK_Column = "Drink_ID";

    public final static String DrinkName_Column = "Drink_Name";
    public final static String Price_Column = "Drink_Price";
    public final static String Quantity_Column = "Drink_Quantity";

    public final static String Total_Price = "Total_Price";


    private static CoffeeDataModel coffeeDataModel;
    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    static PreparedStatement preparedStatement = null;
    public static void main(String[] args)
    {
        if (!CreateTable()) {
            System.exit(0);
        }

        if (!loadAllCoffeeData()) {
            System.exit(0);
        }
        CoffeeGuiForm CoffeeGui = new CoffeeGuiForm(coffeeDataModel);
//        GridLayOutDemo gridGui = new GridLayOutDemo();
//        gridGui.addComponentsToPane(gridGui.getContentPane());
//        gridGui.pack();
//        gridGui.setVisible(true);
//    }
    }
    public static boolean CreateTable()
    {
        try
        {
            try
            {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            }
            catch (ClassNotFoundException cnfe)
            {
                System.out.println("No database found, Try again");
                return false;
            }

            conn = DriverManager.getConnection(DB_CONNECTION_URL+ DB_NAME,USER,PASS);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            if(!CoffeeTableExists())
            {
                String CreateCoffeeTableSQL = "CREATE TABLE " + Coffee_Table_Name + "(" + PK_Column + " int NOT NULL AUTO_INCREMENT, " + DrinkName_Column + " varchar(100), " + Price_Column + " DOUBLE , " + Quantity_Column + " int," + Total_Price + " DOUBLE , PRIMARY KEY(" + PK_Column + "))";
                statement.execute(CreateCoffeeTableSQL);
                System.out.println("Create Coffee Data table");

                String addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column+" , " + Quantity_Column + " , "+ Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Mocha");
                preparedStatement.setString(2,"3.25");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                 addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Latte");
                preparedStatement.setString(2,"2.25");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Espresso");
                preparedStatement.setString(2,"3.25");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Tea");
                preparedStatement.setString(2,"2.25");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Cappuccino");
                preparedStatement.setString(2,"3.60");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Americano");
                preparedStatement.setString(2,"4.25");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Cold Press");
                preparedStatement.setString(2,"2.25");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Herbal Tea");
                preparedStatement.setString(2,"3.65");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
                addDataSQL = "INSERT INTO " + Coffee_Table_Name + "(" + DrinkName_Column + " , "+ Price_Column +" , "+ Quantity_Column + " , " + Total_Price + " )" + " VALUES (?,?,?,?)";
                preparedStatement = conn.prepareStatement(addDataSQL);
                preparedStatement.setString(1,"Black Tea");
                preparedStatement.setString(2,"3.95");
                preparedStatement.setString(3,"0");
                preparedStatement.setString(4,"0");
                preparedStatement.execute();
            }
            return true;
        }
        catch (SQLException se)
        {
            se.printStackTrace();
            System.out.println("Error creating the table");
            return false;
        }

    }
    public static boolean loadAllCoffeeData()
    {
        try
        {
            String getAllData = "SELECT * FROM " + Coffee_Table_Name;
            rs = statement.executeQuery(getAllData);
            if (coffeeDataModel == null)
            {
                coffeeDataModel = new CoffeeDataModel(rs); // new tableModel created
            }
            else
            {
                coffeeDataModel.updateResultSet(rs);
            }
            return true;
        }
        catch (Exception e)
        {
            System.out.println("Error loading or reloading data");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    private static boolean CoffeeTableExists() throws SQLException
    {
        String checkTablePresentQuery = " SHOW TABLES LIKE '" + Coffee_Table_Name + "'";
        ResultSet tablesRS = statement.executeQuery(checkTablePresentQuery);
        if (tablesRS.next())
        {    //If ResultSet has a next row, it has at least one row... that must be our table
            return true;
        }
        return false;
    }
    public static void shutdown()
    {
        try
        {
            if (rs != null)
            {
                rs.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se)
        {
            se.printStackTrace();
        }

        try {
            if (statement != null)
            {
                statement.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se)
        {
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }
        try
        {
            if (conn != null)
            {
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }
    }
}
