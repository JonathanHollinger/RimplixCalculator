package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

import utilities.Check;
import utilities.arithmetic.*;

public class Display extends JPanel implements ActionListener
{
  private static final long serialVersionUID = 1L;
  private String problem;
  private String expression; // Top-left: completed expression so far
  private String parentheses; // String for only parenthesis for check
  private String contents; // Bottom-right: current input
  private JLabel expressionLabel; // Labels for display
  private JLabel inputLabel; // Labels for display
  private boolean evaluatedExpression = false;

  // Constants for buttons
  private static final String CLEAR = "C";
  private static final String RESET = "R";
  private static final String I = "i";
  private static final String ERASE_TO_THE_LEFT = "←";
  private static final String SIGN_TOGGLE = "±";
  private static final String LeftP = "(";
  private static final String RightP = ")";
  private static final String EQUALS = "=";

  public Display()
  {
    problem = "";
    expression = "";
    parentheses = "";
    contents = "";

    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());

    // Label for top left of expression
    expressionLabel = new JLabel(" ", SwingConstants.LEFT);
    expressionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    expressionLabel.setForeground(Color.DARK_GRAY);

    // label for current bottom right
    inputLabel = new JLabel("Enter a complex number", SwingConstants.RIGHT);
    inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
    inputLabel.setForeground(Color.GRAY);

    add(expressionLabel, BorderLayout.NORTH);
    add(inputLabel, BorderLayout.SOUTH);

  }

  public void actionPerformed(ActionEvent ae)
  {
    String ac = ae.getActionCommand();

    switch (ac)
    {
      case CLEAR -> {
        problem = "";
        contents = "";
        expression = "";
        parentheses = "";
      }
      case RESET -> {
        expression = "";
        parentheses = "";
      }
      case LeftP -> {
        // Allow multiple sets of parentheses for nested expressions
        if(evaluatedExpression) {
          evaluatedExpression = false;
          problem = "";
          expression = "";
        }
        problem += LeftP;
        parentheses += LeftP;
        contents += LeftP;
      }
      case RightP -> {
        // Only add closing parenthesis if there's a matching opening one
        if (countChar(parentheses, '(') > countChar(parentheses, ')'))
        {
          problem += RightP;
          contents += RightP;
          parentheses += RightP;

          // After adding a closing parenthesis, check if the parentheses are balanced
          // for the current expression (not necessarily all parentheses)
          if (areParenthesesBalanced(contents))
          {
            // Remove all layers of parentheses
            while (contents.startsWith("(") && contents.endsWith(")")
                && areParenthesesBalanced(contents.substring(1, contents.length() - 1)))
            {
              contents = contents.substring(1, contents.length() - 1);
            }
          }
        }
      }
      case ERASE_TO_THE_LEFT -> {
        if (!contents.isEmpty())
        {
          char lastChar = contents.charAt(contents.length() - 1);
          contents = contents.substring(0, contents.length() - 1);

          // Also update parentheses tracking if deleting a parenthesis
          if (lastChar == '(' || lastChar == ')')
          {
            parentheses = parentheses.substring(0, parentheses.length() - 1);
          }
        }
      }
      case SIGN_TOGGLE -> toggleSign();
      case "." -> {
        if (canAddDecimalPoint())
          contents += ".";
      }
      case I -> {
        problem += I;
        contents += I;
      }
      case EQUALS -> {
        // Only evaluate if all parentheses are balanced
        if (areParenthesesBalanced(parentheses))
        {
          try
          {
            BufferedReader toParse = new BufferedReader(new StringReader(problem));
            Evaluator eval = new Evaluator(Parser.parse(toParse));
            expression += " " + contents + EQUALS + " " + problem + EQUALS + eval.result();
            problem = eval.result().toString();
            evaluatedExpression = true;
            contents = "";
          }
          catch (IOException e)
          {
            e.printStackTrace(); // or handle it more gracefully
            expression += "Error: Unable to evaluate expression.";
          }
        }
      }
      default -> {
        if (ac.length() == 1 && Character.isDigit(ac.charAt(0)))
        {
          if(evaluatedExpression) {
            evaluatedExpression = false;
            expression = "";
            problem += "";
          }
          problem += ac;
          contents += ac;
        } else if ((ac.equals("+") || ac.equals("-") || ac.equals("x") || ac.equals("÷"))
            && evaluatedExpression) {
          expression = problem + " " + ac;
          problem += ac;
          contents = "";
          evaluatedExpression = false;
        }
        else if ((ac.equals("+") || ac.equals("-") || ac.equals("x") || ac.equals("÷"))
            && Check.isValid(parentheses))
        {
          if (!contents.isEmpty())
          {
            problem += ac;
            expression += contents + " " + ac + " ";
            contents = "";
            parentheses = "";
          }
        }
        else
        {
          contents += ac; // fallback
          problem += ac;
        }
      }
    }

    updateDisplay();
  }

  /**
   * Checks if the current expression enclosed in parentheses is complete (has balanced parentheses
   * for the immediate expression)
   */
  private boolean isCurrentExpressionComplete()
  {
    // Count open and close parentheses
    int openCount = 0;
    int closeCount = 0;

    for (int i = 0; i < contents.length(); i++)
    {
      if (contents.charAt(i) == '(')
      {
        openCount++;
      }
      else if (contents.charAt(i) == ')')
      {
        closeCount++;
        // If we've balanced the outermost parentheses
        if (openCount == closeCount)
        {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Counts occurrences of a character in a string
   */
  private int countChar(String str, char c)
  {
    int count = 0;
    for (int i = 0; i < str.length(); i++)
    {
      if (str.charAt(i) == c)
      {
        count++;
      }
    }
    return count;
  }

  /**
   * Checks if parentheses in a string are balanced
   */
  private boolean areParenthesesBalanced(String str)
  {
    int balance = 0;
    for (int i = 0; i < str.length(); i++)
    {
      if (str.charAt(i) == '(')
      {
        balance++;
      }
      else if (str.charAt(i) == ')')
      {
        balance--;
      }
      if (balance < 0)
      {
        return false; // Closing without opening
      }
    }
    return balance == 0; // All opened parentheses are closed
  }

  /**
   * Removes outermost matching parentheses from a string E.g., "(3+4i)" becomes "3+4i"
   */
  private String stripOutermostParentheses(String str)
  {
    // Find the outermost matching pair of parentheses
    int openingIndex = -1;
    int closingIndex = -1;
    int balance = 0;

    for (int i = 0; i < str.length(); i++)
    {
      if (str.charAt(i) == '(')
      {
        if (openingIndex == -1)
        {
          openingIndex = i;
        }
        balance++;
      }
      else if (str.charAt(i) == ')')
      {
        balance--;
        if (balance == 0 && openingIndex != -1)
        {
          closingIndex = i;
          break;
        }
      }
    }

    if (openingIndex == -1 || closingIndex == -1)
    {
      return str; // No matching pair found
    }

    // Return the content without the outermost parentheses
    return str.substring(0, openingIndex) + str.substring(openingIndex + 1, closingIndex)
        + str.substring(closingIndex + 1);
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

    // Skip toggling for zero
    if (contents.equals("0"))
    {
      return; // Don't toggle the sign of zero
    }

    // Handle different formats of complex numbers
    if (contents.contains(I))
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
    return !currentNumberPart.contains(".") && !currentNumberPart.contains(I);
  }

  private void updateDisplay()
  {
    // Update top expression label
    expressionLabel.setText(expression.isEmpty() ? " " : expression);

    // Update input label
    if (contents.isEmpty())
    {
      inputLabel.setForeground(Color.GRAY);
      inputLabel.setText("Enter a complex number");
    }
    else
    {
      inputLabel.setForeground(Color.BLACK);
      if (contents.contains("i"))
      {
        inputLabel.setText("<html>" + contents.replace("i", "<i>i</i>") + "</html>");
      }
      else
      {
        inputLabel.setText(contents);
      }
    }
  }
}
