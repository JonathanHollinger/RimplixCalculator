package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utilities.Check;
import utilities.arithmetic.Evaluator;
import utilities.arithmetic.Parser;
import utilities.arithmetic.Token;

/**
 * Display panel for the calculator.
 */
public class Display extends JPanel implements ActionListener {
  /** Serial version UID. */
  private static final long serialVersionUID = 1L;
    
  /** Problem being processed. */
  private String problem;
    
  /** Top-left: completed expression so far. */
  private String expression;
    
  /** String for only parenthesis for check. */
  private String parentheses;
    
  /** Bottom-right: current input. */
  private String contents;
    
  /** Labels for expression display. */
  private JLabel expressionLabel;
    
  /** Labels for input display. */
  private JLabel inputLabel;
    
  /** Flag indicating if an expression was evaluated. */
  private boolean evaluatedExpression = false;

  // Constants for buttons
  /** Clear button constant. */
  private static final String CLEAR = "C";
    
  /** Reset button constant. */
  private static final String RESET = "R";
    
  /** Imaginary constant button. */
  private static final String I = "i";
    
  /** Backspace button constant. */
  private static final String ERASE_TO_THE_LEFT = "←";
    
  /** Sign toggle button constant. */
  private static final String SIGN_TOGGLE = "±";
    
  /** Left parenthesis button constant. */
  private static final String LEFT_P = "(";
    
  /** Right parenthesis button constant. */
  private static final String RIGHT_P = ")";
    
  /** Equals button constant. */
  private static final String EQUALS = "=";

  /**
   * Default constructor.
   */
  public Display() {
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

    // label for current bottom right - use translated string
    inputLabel = new JLabel(LanguageManager.getEnterComplexNumberText(), 
                SwingConstants.RIGHT);
    inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
    inputLabel.setForeground(Color.GRAY);

    add(expressionLabel, BorderLayout.NORTH);
    add(inputLabel, BorderLayout.SOUTH);
  }

  @Override
    public void actionPerformed(ActionEvent ae) {
    String ac = ae.getActionCommand();

    switch (ac) {
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
      case LEFT_P -> {
        // Allow multiple sets of parentheses for nested expressions
        if (evaluatedExpression) {
          evaluatedExpression = false;
          problem = "";
          expression = "";
        }
        problem += LEFT_P;
        parentheses += LEFT_P;
        contents += LEFT_P;
      }
      case RIGHT_P -> {
        // Only add closing parenthesis if there's a matching opening one
        if (countChar(parentheses, '(') > countChar(parentheses, ')')) {
          problem += RIGHT_P;
          contents += RIGHT_P;
          parentheses += RIGHT_P;

          // After adding a closing parenthesis, check if the parentheses are
          // balanced for the current expression (not necessarily all parentheses)
          if (areParenthesesBalanced(contents)) {
            // Remove all layers of parentheses
            while (contents.startsWith("(") && contents.endsWith(")")
                                && areParenthesesBalanced(
                                        contents.substring(1, contents.length() - 1))) {
              contents = contents.substring(1, contents.length() - 1);
            }
          }
        }
      }
      case ERASE_TO_THE_LEFT -> {
        if (!contents.isEmpty()) {
          char lastChar = contents.charAt(contents.length() - 1);
          contents = contents.substring(0, contents.length() - 1);

          // Also update parentheses tracking if deleting a parenthesis
          if (lastChar == '(' || lastChar == ')') {
            parentheses = parentheses.substring(0, parentheses.length() - 1);
          }
        }
      }
      case SIGN_TOGGLE -> toggleSign();
      case "." -> {
        if (canAddDecimalPoint()) {
          contents += ".";
        }
      }
      case I -> {
        problem += I;
        contents += I;
            }
            case EQUALS -> {
                // Only evaluate if all parentheses are balanced
                if (areParenthesesBalanced(parentheses)) {
                    BufferedReader buf = new BufferedReader(new StringReader(problem));
                    List<Token> tokens = new ArrayList<>();

                    try {
                        tokens = Parser.parse(buf);
                    } catch (Exception e) {
                        System.err.print("Parsing Failed");
                    }
                    Evaluator evaluator = new Evaluator(tokens);
                    expression += contents + " " + ac + " " + evaluator.result().toString();
                    evaluatedExpression = true;
                    contents = "";
                    problem = evaluator.result().toString();
                }
            }
            default -> {
                if (ac.length() == 1 && Character.isDigit(ac.charAt(0))) {
                    if (evaluatedExpression) {
                        evaluatedExpression = false;
                        expression = "";
                        problem += "";
                    }
                    problem += ac;
                    contents += ac;
                } else if ((ac.equals("+") || ac.equals("-") || ac.equals("x")
                        || ac.equals("÷")) && evaluatedExpression) {
                    expression = problem + " " + ac;
                    problem += ac;
                    contents = "";
                    evaluatedExpression = false;
                } else if ((ac.equals("+") || ac.equals("-") || ac.equals("x")
                        || ac.equals("÷")) && Check.isValid(parentheses)) {
                    if (!contents.isEmpty()) {
                        problem += ac;
                        expression += contents + " " + ac + " ";
                        contents = "";
                        parentheses = "";
                    }
                } else {
                    contents += ac; // fallback
                    problem += ac;
                }
            }
        }

        updateDisplay();
    }

    /**
     * Checks if the current expression enclosed in parentheses is complete (has
     * balanced parentheses for the immediate expression).
     * 
     * @return true if the expression is complete, false otherwise
     */
    private boolean isCurrentExpressionComplete() {
        // Count open and close parentheses
        int openCount = 0;
        int closeCount = 0;

        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) == '(') {
                openCount++;
            } else if (contents.charAt(i) == ')') {
                closeCount++;
                // If we've balanced the outermost parentheses
                if (openCount == closeCount) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Counts occurrences of a character in a string.
     * 
     * @param str the string to search
     * @param c the character to count
     * @return the count of character occurrences
     */
    private int countChar(String str, char c) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if parentheses in a string are balanced.
     * 
     * @param str the string to check
     * @return true if parentheses are balanced, false otherwise
     */
    private boolean areParenthesesBalanced(String str) {
        int balance = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                balance++;
            } else if (str.charAt(i) == ')') {
                balance--;
            }
            if (balance < 0) {
                return false; // Closing without opening
            }
        }
        return balance == 0; // All opened parentheses are closed
    }

    /**
     * Removes outermost matching parentheses from a string.
     * E.g., "(3+4i)" becomes "3+4i".
     * 
     * @param str the string to process
     * @return the string with outermost parentheses stripped
     */
    private String stripOutermostParentheses(String str) {
        // Find the outermost matching pair of parentheses
        int openingIndex = -1;
        int closingIndex = -1;
        int balance = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                if (openingIndex == -1) {
                    openingIndex = i;
                }
                balance++;
            } else if (str.charAt(i) == ')') {
                balance--;
                if (balance == 0 && openingIndex != -1) {
                    closingIndex = i;
                    break;
                }
            }
        }

        if (openingIndex == -1 || closingIndex == -1) {
            return str; // No matching pair found
        }

        // Return the content without the outermost parentheses
        return str.substring(0, openingIndex)
                + str.substring(openingIndex + 1, closingIndex)
                + str.substring(closingIndex + 1);
    }

    /**
     * Toggles the sign of the complex number in the current expression.
     */
    private void toggleSign() {
        if (contents.isEmpty()) {
            return; // Nothing to toggle
        }

        // Skip toggling for zero
        if (contents.equals("0")) {
            return; // Don't toggle the sign of zero
        }

        // Handle different formats of complex numbers
        if (contents.contains(I)) {
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
            } else {
                // Not in parentheses, handle direct negation
                if (contents.startsWith("-")) {
                    contents = contents.substring(1); // Remove negative sign
                } else {
                    contents = "-" + contents; // Add negative sign
                }
            }
        } else {
            // Real number only
            if (contents.startsWith("-")) {
                contents = contents.substring(1); // Remove negative sign
            } else {
                contents = "-" + contents; // Add negative sign
            }
        }
    }

    /**
     * Helper method to check if a decimal point can be added.
     * 
     * @return true if a decimal point can be added, false otherwise
     */
    private boolean canAddDecimalPoint() {
        if (contents.isEmpty()) {
            return true; // Can add decimal to start of empty expression
        }

        // Check if we're already in a decimal portion of a number
        int lastOperatorIndex = Math.max(
                Math.max(contents.lastIndexOf('+'), contents.lastIndexOf('-')),
                Math.max(contents.lastIndexOf('x'), contents.lastIndexOf('÷')));

        // Get the part of the string after the last operator
        String currentNumberPart = contents.substring(lastOperatorIndex + 1);

        // If there's already a decimal point or an 'i' in this part, don't add another decimal
        return !currentNumberPart.contains(".") && !currentNumberPart.contains(I);
    }

    /**
     * Updates the display with current values.
     */
    private void updateDisplay() {
        // Update top expression label
        expressionLabel.setText(expression.isEmpty() ? " " : expression);

        // Update input label with the appropriate translated text when empty
        if (contents.isEmpty()) {
            inputLabel.setForeground(Color.GRAY);
            inputLabel.setText(LanguageManager.getEnterComplexNumberText());
        } else {
            inputLabel.setForeground(Color.BLACK);
            if (contents.contains("i")) {
                inputLabel.setText(
                        "<html>" + contents.replace("i", "<i>i</i>") + "</html>");
            } else {
                inputLabel.setText(contents);
            }
        }
    }
}