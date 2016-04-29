package com.Chitra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chitrakakkar on 4/23/16.
 */
public class CoffeeGuiForm extends JFrame implements WindowListener
{
    private JScrollPane scrollPane1;
    private JTextField SmallQuantityTextBox;


    private JCheckBox cookiesCheckBox;
    private JCheckBox cupCakesCheckBox;
    private JCheckBox iceCreamCheckBox;
    private JCheckBox candyCheckBox;
    private JCheckBox chipsCheckBox;
    private JTextField SumTextField;
    private JTextField VATTextField;
    private JTextField TotalSumText;
    private JList list1;
    private JButton writeINToReportTableButton;
    private JPasswordField AdminPassword;
    private JTextField ADDDRINKTEXT;
    private JTextField ADDPRICETEXT;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField DateText;
    private JTextField SearchText;
    private JButton extractDataButton;
    private JTextArea reportTextField;
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

    CoffeeGuiForm (final CoffeeDataModel coffeeDataModel)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        setContentPane(rootPanel);
        pack();
        setTitle("Rubik_Solver");
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500,500);
        DrinkTable.setGridColor(Color.black);
        DrinkTable.setModel(coffeeDataModel);
        UnHighLightAdminSection();
        DateText.setText(dateFormat.format(date));
        DateText.setEditable(false);
        getContentPane().setBackground(Color.PINK);


        activateButton.addActionListener(e ->
        {

            HighLightAdminSection();
        });

        quitAdminSectionButton.addActionListener(e -> {
            UnHighLightAdminSection();
        });




    }



    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e)
    {
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

            //AdminPassword.setEnabled(false);
            AdminLabel.setEnabled(false);
            ADDDRINKTEXT.setEnabled(false);
            ADDPRICETEXT.setEnabled(false);
            addADrinkLabel.setEnabled(false);
            addAPriceLabel.setEnabled(false);
            addButton.setEnabled(false);
            deleteButton.setEnabled(false);
            updateButton.setEnabled(false);
            searchStringLabel.setEnabled(false);
            SearchText.setEnabled(false);
            extractDataButton.setEnabled(false);
            writeToaFileButton.setEnabled(false);
            reportTextField.setEnabled(false);
            quitAdminSectionButton.setEnabled(false);

    }

    public void HighLightAdminSection() {
        String password = String.valueOf(AdminPassword.getPassword());
        System.out.println("I am the password " + password);
        if (password.equals( "JavaProject"))
        {
            AdminLabel.setEnabled(true);
            ADDDRINKTEXT.setEnabled(true);
            ADDPRICETEXT.setEnabled(true);
            addADrinkLabel.setEnabled(true);
            addAPriceLabel.setEnabled(true);
            addButton.setEnabled(true);
            deleteButton.setEnabled(true);
            updateButton.setEnabled(true);
            searchStringLabel.setEnabled(true);
            SearchText.setEnabled(true);
            extractDataButton.setEnabled(true);
            writeToaFileButton.setEnabled(true);
            reportTextField.setEnabled(true);
            quitAdminSectionButton.setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(rootPane, "Wrong Password, try again");
            AdminPassword.grabFocus();
        }
    }




}
