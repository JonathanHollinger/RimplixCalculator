package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import utilities.Check;

public class Display extends JPanel implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private String expression; // Top-left: completed expression so far
  private String parentheses; // String for only parenthesis for check
  private String contents; // Bottom-right: current input
  private JLabel expressionLabel; // Labels for display
  private JLabel inputLabel; // Labels for display

  //Constants for buttons
  private static final String CLEAR = "C";
  private static final String ERASE_TO_THE_LEFT = "←";
  private static final String SIGN_TOGGLE = "±";
  private static final String LeftP = "(";
  private static final String RightP = ")";

  public Display()
  {
    expression = "";
    parentheses = "";
    contents = "";
    
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());
    
    //Label for top left of expression
    expressionLabel = new JLabel(" ", SwingConstants.LEFT);
    expressionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    expressionLabel.setForeground(Color.DARK_GRAY);
    
    //label for current bottom right
    inputLabel = new JLabel("Enter a complex number", SwingConstants.RIGHT);
    inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
    inputLabel.setForeground(Color.GRAY);
    
    add(expressionLabel, BorderLayout.NORTH);
    add(inputLabel, BorderLayout.SOUTH);
  }

  public void actionPerformed(ActionEvent ae)
  {
    String ac = ae.getActionCommand();

    switch (ac) {
        case CLEAR -> {
            contents = "";
            expression = "";
            parentheses = "";
        }
        case RightP -> {
          parentheses += RightP;
          if(Check.isValid(parentheses)) {
            contents = contents.substring(1, contents.length());
          } else {
            parentheses = parentheses.substring(0, parentheses.length() - 1);
          }
      }
        case LeftP -> {
          if(parentheses.length() < 1) {
            parentheses += LeftP;
            contents += LeftP;
          }
      }
        case ERASE_TO_THE_LEFT -> {
            if (!contents.isEmpty()) {
                contents = contents.substring(0, contents.length() - 1);
            }
        }
        case SIGN_TOGGLE -> toggleSign();
        case "." -> {
            if (canAddDecimalPoint()) contents += ".";
        }
        case "i" -> contents += "i";
        default -> {
            if (ac.length() == 1 && Character.isDigit(ac.charAt(0))) {
                contents += ac;
            } else if ((ac.equals("+") || ac.equals("-") || ac.equals("x") || ac.equals("÷")) && Check.isValid(parentheses)) {
                if (!contents.isEmpty()) {
                    expression += contents + " " + ac + " ";
                    contents = "";
                }
            } else {
                contents += ac; // fallback
            }
        }
    }

    updateDisplay();
  }

  /**
   * Toggles the sign of the complex number in the current expression
   */
  private void toggleSign()
  {
    if (contents.isEmpty())
    {
      return; // Nothing to toggle
    }

    // Handle different formats of complex numbers
    if (contents.contains("i"))
    {
      // Complex number with imaginary part

      // Check if the number is in parentheses format (a+bi)
      if (contents.startsWith("(") && contents.endsWith(")"))
      {
        // Remove parentheses first
        String innerExpression = contents.substring(1, contents.length() - 1);
        contents = "(-" + innerExpression + ")";

        // If it starts with a negative sign, remove it to make it positive
        if (contents.equals("(--") || contents.startsWith("(--("))
        {
          contents = contents.substring(0, 1) + contents.substring(3);
        }
      }
      else
      {
        // Not in parentheses, handle direct negation
        if (contents.startsWith("-"))
        {
          contents = contents.substring(1); // Remove negative sign
        }
        else
        {
          contents = "-" + contents; // Add negative sign
        }
      }
    }
    else
    {
      // Real number only
      if (contents.startsWith("-"))
      {
        contents = contents.substring(1); // Remove negative sign
      }
      else
      {
        contents = "-" + contents; // Add negative sign
      }
    }
  }

  // Helper method to check if we can add a decimal point
  private boolean canAddDecimalPoint()
  {
    if (contents.isEmpty())
    {
      return true; // Can add decimal to start of empty expression
    }

    // Check if we're already in a decimal portion of a number
    int lastOperatorIndex = Math.max(Math.max(contents.lastIndexOf('+'), contents.lastIndexOf('-')),
        Math.max(contents.lastIndexOf('x'), contents.lastIndexOf('÷')));

    // Get the part of the string after the last operator
    String currentNumberPart = contents.substring(lastOperatorIndex + 1);

    // If there's already a decimal point or an 'i' in this part, don't add another decimal
    return !currentNumberPart.contains(".") && !currentNumberPart.contains("i");
  }

  private void updateDisplay() {
    // Update top expression label
    expressionLabel.setText(expression.isEmpty() ? " " : expression);

    // Update input label
    if (contents.isEmpty()) {
        inputLabel.setForeground(Color.GRAY);
        inputLabel.setText("Enter a complex number");
    } else {
        inputLabel.setForeground(Color.BLACK);
        if (contents.contains("i")) {
            inputLabel.setText("<html>" + contents.replace("i", "<i>i</i>") + "</html>");
        } else {
            inputLabel.setText(contents);
        }
    }
}
}
