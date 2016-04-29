package com.Chitra;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by chitrakakkar on 4/23/16.
 */
public class CoffeeGuiForm extends JFrame implements WindowListener
{
    private JScrollPane scrollPane1;
    private JCheckBox smallCheckBox;
    private JCheckBox mediumCheckBox;
    private JCheckBox largeCheckBox;
    private JTextField SmallQuantityTextBox;



    private JTextField MediumSizeTextBox;
    private JTextField LargeSizeTextBox;
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
    private JTextField textField1;
    private JButton extractDataButton;
    private JTextArea textArea1;
    private JButton writeToaFileButton;
    private JTable DrinkTable;
    private JLabel tagLabel;
    private JPanel rootPanel;
    private JButton activateButton;

    CoffeeGuiForm ()
    {
        super();
        setContentPane(rootPanel);
        pack();

    }



    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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




}
