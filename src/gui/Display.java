package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Display extends JLabel implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private String contents;    
    private static final String CLEAR = "C";
    private static final String ERASE_TO_THE_LEFT = "←";
    private static final String SIGN_TOGGLE = "±";
    
    public Display()
    {
        super(" ", SwingConstants.RIGHT);
        setBorder(BorderFactory.createEtchedBorder());
        contents = "";
        updateDisplay();
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        String ac = ae.getActionCommand();
        if (ac.equals(CLEAR))
        {
            contents = "";
        }
        else if (ac.equals(ERASE_TO_THE_LEFT)) 
        {
            if (!contents.equals(""))
                contents = contents.substring(0, contents.length()-1);
        }
        else if (ac.equals(SIGN_TOGGLE))
        {
            // Sign toggle functionality
            toggleSign();
        }
        else if (ac.equals("."))
        {
            // EnteringDecimalPoint functionality
            if (canAddDecimalPoint()) {
                contents += ac;
            }
        }
        else if (ac.equals("i"))
        {
            // EnteringImaginaryUnit functionality
            contents += ac;
        }
        else if (ac.length() == 1 && Character.isDigit(ac.charAt(0)))
        {
            // EnteringDigits functionality
            contents += ac;
        }
        else
        {
            // Handle other button presses
            contents += ac;
        }
        updateDisplay();
    }
    
    /**
     * Toggles the sign of the complex number in the current expression
     */
    private void toggleSign()
    {
        if (contents.isEmpty()) {
            return; // Nothing to toggle
        }
        
        // Handle different formats of complex numbers
        if (contents.contains("i")) {
            // Complex number with imaginary part
            
            // Check if the number is in parentheses format (a+bi)
            if (contents.startsWith("(") && contents.endsWith(")")) {
                // Remove parentheses first
                String innerExpression = contents.substring(1, contents.length() - 1);
                contents = "(-" + innerExpression + ")";
                
                // If it starts with a negative sign, remove it to make it positive
                if (contents.equals("(--") || contents.startsWith("(--(")) {
                    contents = contents.substring(0, 1) + contents.substring(3);
                }
            } 
            else {
                // Not in parentheses, handle direct negation
                if (contents.startsWith("-")) {
                    contents = contents.substring(1); // Remove negative sign
                } else {
                    contents = "-" + contents; // Add negative sign
                }
            }
        } 
        else {
            // Real number only
            if (contents.startsWith("-")) {
                contents = contents.substring(1); // Remove negative sign
            } else {
                contents = "-" + contents; // Add negative sign
            }
        }
    }
    
    // Helper method to check if we can add a decimal point
    private boolean canAddDecimalPoint() {
        if (contents.isEmpty()) {
            return true; // Can add decimal to start of empty expression
        }
        
        // Check if we're already in a decimal portion of a number
        int lastOperatorIndex = Math.max(
            Math.max(contents.lastIndexOf('+'), contents.lastIndexOf('-')),
            Math.max(contents.lastIndexOf('x'), contents.lastIndexOf('÷'))
        );
        
        // Get the part of the string after the last operator
        String currentNumberPart = contents.substring(lastOperatorIndex + 1);
        
        // If there's already a decimal point or an 'i' in this part, don't add another decimal
        return !currentNumberPart.contains(".") && !currentNumberPart.contains("i");
    }
    
    private void updateDisplay()
    {
        if (contents.equals("")) 
        {
            setForeground(Color.GRAY);            
            setText("Enter a complex number");
        }
        else 
        {
            setForeground(Color.BLACK);            
            // Format the text - italicize the 'i' for imaginary numbers
            if (contents.contains("i")) {
                // Replace 'i' with italic 'i'
                String formattedText = contents.replace("i", "<i>i</i>");
                setText("<html>" + formattedText + "</html>");
            } else {
                setText(contents);
            }
        }
    }
}
