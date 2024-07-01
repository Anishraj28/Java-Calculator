package Tests;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

// Define a class named "calculator" that extends JPanel and implements ActionListener
public class calculator extends JPanel implements ActionListener {
    // Create a textfield to display output
    JTextField outputLabel;

    // Store operator and operands
    String operand1, operator, operand2;
    ArrayList<String> history; // List to store past calculations

    // Default constructor
    calculator() {
        super();
        operand1 = operator = operand2 = "";
        history = new ArrayList<>(); // Initialize the ArrayList

        // Create a textfield
        outputLabel = new JTextField(16);
        // Set the textfield to non-editable
        outputLabel.setEditable(false);

        // Create number buttons and some operators
        JButton addButton, subtractButton, divideButton, multiplyButton, decimalButton, clearButton, equalButton,
                historyButton;

        this.add(outputLabel);

        // Create number buttons from 0 to 9
        for (int i = 0; i <= 9; i++) {
            JButton button = new JButton("" + i);
            button.addActionListener(this); // Add ActionListener to each number button
            this.add(button);
        }

        // Create buttons for operators, decimal, clear, equal, and history
        clearButton = new JButton("C");
        historyButton = new JButton("H");
        equalButton = new JButton("=");
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        divideButton = new JButton("/");
        multiplyButton = new JButton("*");

        // Create decimal button
        decimalButton = new JButton(".");

        // ActionListener to handle operator buttons
        ActionListener operatorListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!operand1.equals("") && !operand2.equals("")) calculateResult();
                operator = e.getActionCommand();
                updateOutput();
            }
        };

        // Add action listeners to operator buttons
        addButton.addActionListener(operatorListener);
        subtractButton.addActionListener(operatorListener);
        divideButton.addActionListener(operatorListener);
        multiplyButton.addActionListener(operatorListener);

        // Add action listeners to other buttons
        decimalButton.addActionListener(this);
        clearButton.addActionListener(this);
        equalButton.addActionListener(this);
        historyButton.addActionListener(this);

        // Add elements to the panel
        this.add(addButton);
        this.add(subtractButton);
        this.add(multiplyButton);
        this.add(divideButton);
        this.add(decimalButton);
        this.add(clearButton);
        this.add(equalButton);
        this.add(historyButton);

        // Set background color of the panel
        this.setBackground(Color.blue);
    }

    // Method to perform arithmetic operation
    public double performOperation(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "/":
                return operand1 / operand2;
            case "*":
                return operand1 * operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    // Method to calculate result
    private void calculateResult() {
        double result = performOperation(Double.parseDouble(operand1), Double.parseDouble(operand2), operator);

        // Add the calculation to history
        history.add(outputLabel.getText() + " = " + result);

        operand1 = Double.toString(result);
        operator = "";
        operand2 = "";
    }

    // actionPerformed method
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        // If the action command is "H", show history
        if (s.equals("H")) {
            showHistory(new StringBuilder("History: \n"));
            return;
        }

        char input = s.charAt(0);

        // If the value is a number or decimal point
        if ((input >= '0' && input <= '9') || input == '.') {
            // If operator is present, add to second operand, else add to first operand
            if (!operator.equals(""))
                operand2 = operand2 + s;
            else
                operand1 = operand1 + s;
        } else if (input == 'C') {
            // Clear the input
            operand1 = operator = operand2 = "";
        } else if (input == '=' && !operator.equals("")) {
            calculateResult(); // Calculate result when '=' is pressed
        }

        updateOutput(); // Update the output label
    }

    // Method to update output label
    public void updateOutput() {
        outputLabel.setText(operand1 + operator + operand2);
    }

 // Method to show calculation history and calculate total sum
    private void showHistory(StringBuilder historyText) {
        double totalSum = 0.0; // Variable to store the total sum of previous results
        
      //Check if history is empty
        if (!history.isEmpty()) {
        	//Loop through each entry in history
            for (String entry : history) {
            	//Append each entry to historyText
                historyText.append(entry).append("\n");
                
                //Extract the result from each entry and add it to totalSum
                String[] parts = entry.split(" = ");
                double result = Double.parseDouble(parts[1]);
                totalSum += result;
            }
            //Append the total sum to historyText
            historyText.append("Total Sum: ").append(totalSum);
        } else {
        	//Append message if history is empty
            historyText.append("No history available.");
        }

        //Show historyText in a dialog
        JOptionPane.showMessageDialog(this, historyText.toString(), "Calculation History", 
        		JOptionPane.INFORMATION_MESSAGE);
    }

}
