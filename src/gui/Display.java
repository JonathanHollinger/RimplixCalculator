package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.List;
import javax.swing.*;

import utilities.Check;
import utilities.Engine;
import utilities.arithmetic.Evaluator;
import utilities.arithmetic.Parser;
import utilities.arithmetic.Token;

public class Display extends JPanel implements ActionListener
{
  private static final long serialVersionUID = 1L;

  private String problem = "";
  private String expression = "";
  private String parentheses = "";
  private String contents = "";
  private boolean evaluatedExpression = false;

  private JLabel expressionLabel;
  private JLabel inputLabel;
  private JLabel imageLabel;

  private static final String CLEAR = "C";
  private static final String RESET = "R";
  private static final String I = "i";
  private static final String ERASE_TO_THE_LEFT = "←";
  private static final String SIGN_TOGGLE = "±";
  private static final String LEFT_P = "(";
  private static final String RIGHT_P = ")";
  private static final String EQUALS = "=";
  private static final String CONJUGATE = "Conj";
  private static final String INVERSE = "Inv";

  private final Map<String, String> history = new LinkedHashMap<>();
  private final List<Engine> historyListeners = new ArrayList<>();
  
  CollapsiblePanel historyPanel;

  public Display(CollapsiblePanel historyPanel)
  {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createEtchedBorder());

    expressionLabel = new JLabel(" ", SwingConstants.LEFT);
    expressionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    expressionLabel.setForeground(Color.DARK_GRAY);

    inputLabel = new JLabel(LanguageManager.getEnterComplexNumberText(), SwingConstants.RIGHT);
    inputLabel.setFont(new Font("Arial", Font.BOLD, 20));
    inputLabel.setForeground(Color.GRAY);
    
    ImageIcon img = new ImageIcon("src/media/logoRimplex.png");
    imageLabel = new JLabel(img, SwingConstants.LEFT);
    
    add(imageLabel, BorderLayout.NORTH);
    add(expressionLabel, BorderLayout.CENTER);
    add(inputLabel, BorderLayout.SOUTH);
    
    this.historyPanel = historyPanel;
  }

  @Override
  public void actionPerformed(ActionEvent ae)
  {
    String ac = ae.getActionCommand();

    switch (ac)
    {
      case CLEAR -> clearAll();
      case RESET -> resetExpression();
      case LEFT_P -> addLeftParenthesis();
      case RIGHT_P -> addRightParenthesis();
      case ERASE_TO_THE_LEFT -> backspace();
      case SIGN_TOGGLE -> toggleSign();
      case "." -> {
        if (canAddDecimalPoint())
          contents += ".";
      }
      case I -> appendToProblem(I);
      case EQUALS -> evaluateExpression();
      case CONJUGATE -> conjugate();
      case INVERSE -> invert();
      case ">" -> {
        NumberPad.changeText();
        historyPanel.setCollapsed(!historyPanel.isCollapsed());
        // PINPadWindow.setSize(700, 500);

      }
      case "<" -> {
        NumberPad.changeText();
        historyPanel.setCollapsed(!historyPanel.isCollapsed());
        // PINPadWindow.setSize(380, 500);
      }
      default -> handleInput(ac);
    }
    updateDisplay();
  }

  public String getPrintableHistory()
  {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : history.entrySet())
    {
      sb.append(entry.getKey()).append("\n");
    }
    return sb.toString();
  }

  private void clearAll()
  {
    problem = contents = expression = parentheses = "";
  }

  private void resetExpression()
  {
    expression = parentheses = "";
  }

  private void addLeftParenthesis()
  {
    if (evaluatedExpression)
      clearAll();
    problem += LEFT_P;
    parentheses += LEFT_P;
    contents += LEFT_P;
  }

  private void addRightParenthesis()
  {
    if (countChar(parentheses, '(') > countChar(parentheses, ')'))
    {
      problem += RIGHT_P;
      contents += RIGHT_P;
      parentheses += RIGHT_P;

      if (areParenthesesBalanced(contents))
      {
        while (contents.startsWith("(") && contents.endsWith(")")
            && areParenthesesBalanced(contents.substring(1, contents.length() - 1)))
        {
          contents = contents.substring(1, contents.length() - 1);
        }
      }
    }
  }

  private void backspace()
  {
    if (!contents.isEmpty())
    {
      char lastChar = contents.charAt(contents.length() - 1);
      contents = contents.substring(0, contents.length() - 1);
      if (!problem.isEmpty())
      {
        problem = problem.substring(0, problem.length() - 1);
      }
      if ((lastChar == '(' || lastChar == ')') && !parentheses.isEmpty())
      {
        parentheses = parentheses.substring(0, parentheses.length() - 1);
      }
    }
  }

  private void evaluateExpression()
  {
    if (!areParenthesesBalanced(parentheses))
      return;
    try
    {
      List<Token> tokens = Parser.parse(new BufferedReader(new StringReader(problem)));
      Evaluator evaluator = new Evaluator(tokens);
      String result = evaluator.result().toString();
      String displayKey = expression + contents + " = " + result;

      history.put(displayKey, problem);
      for (Engine listener : historyListeners)
      {
        listener.onHistoryUpdated(new ArrayList<>(history.keySet()));
      }

      expression = displayKey;
      contents = "";
      problem = result;
      evaluatedExpression = true;
    }
    catch (Exception e)
    {
      System.err.println("Parsing Failed: " + e.getMessage());
    }
  }

  private void handleInput(String ac)
  {
    if (ac.length() == 1 && Character.isDigit(ac.charAt(0)))
    {
      if (evaluatedExpression)
        clearAll();
      appendToProblem(ac);
    }
    else if (("+-x÷".contains(ac)) && evaluatedExpression)
    {
      expression = problem + " " + ac;
      appendToProblem(ac);
      contents = "";
      evaluatedExpression = false;
    }
    else if (("+-x÷".contains(ac)) && Check.isValid(parentheses))
    {
      if (!contents.isEmpty())
      {
        appendToProblem(ac);
        expression += contents + " " + ac + " ";
        contents = parentheses = "";
      }
    }
    else
    {
      appendToProblem(ac);
    }
  }

  private void appendToProblem(String s)
  {
    problem += s;
    contents += s;
  }

  public void setInputFromHistory(String displayKey)
  {
    String storedProblem = history.get(displayKey);
    String trimmedKey = displayKey.contains("=") ? displayKey.split("=")[0].trim()
        : displayKey.trim();
    this.problem = storedProblem != null ? storedProblem : trimmedKey;
    this.contents = trimmedKey;
    this.expression = "";
    this.evaluatedExpression = false;
    updateDisplay();
  }

  public void addHistoryListener(Engine listener)
  {
    historyListeners.add(listener);
  }

  private boolean areParenthesesBalanced(String str)
  {
    int balance = 0;
    for (char c : str.toCharArray())
    {
      if (c == '(')
        balance++;
      if (c == ')')
        balance--;
      if (balance < 0)
        return false;
    }
    return balance == 0;
  }

  private int countChar(String str, char c)
  {
    int count = 0;
    for (char ch : str.toCharArray())
      if (ch == c)
        count++;
    return count;
  }

  private boolean canAddDecimalPoint()
  {
    if (contents.isEmpty())
      return true;
    int lastOp = Math.max(Math.max(contents.lastIndexOf('+'), contents.lastIndexOf('-')),
        Math.max(contents.lastIndexOf('x'), contents.lastIndexOf('÷')));
    String numberPart = contents.substring(lastOp + 1);
    return !numberPart.contains(".") && !numberPart.contains(I);
  }

  private void toggleSign()
  {
    if (contents.isEmpty() || contents.equals("0"))
      return;
    if (contents.contains(I))
    {
      if (contents.startsWith("(") && contents.endsWith(")"))
      {
        String inner = contents.substring(1, contents.length() - 1);
        contents = "(-" + inner + ")";
        if (contents.startsWith("(--"))
          contents = "(" + contents.substring(3);
      }
      else
      {
        contents = contents.startsWith("-") ? contents.substring(1) : "-" + contents;
      }
    }
    else
    {
      contents = contents.startsWith("-") ? contents.substring(1) : "-" + contents;
    }
  }
  
  private void conjugate()
  {
    if (contents.isEmpty())
      return;
    try
    {
      String parsedInput = contents
        .replaceAll("(?<=\\W|^)i", "1i")
        .replaceAll("\\+i", "+1i")
        .replaceAll("-i", "-1i");

      if (parsedInput.startsWith("-"))
        parsedInput = "0" + parsedInput;

      parsedInput = "(" + parsedInput + ")";

      System.out.println("Normalized input: " + parsedInput);

      List<Token> tokens = Parser.parse(new BufferedReader(new StringReader(parsedInput)));
      Evaluator evaluator = new Evaluator(tokens);
      var result = evaluator.result();

      if (result != null)
      {
        String output = result.conjugate().toString();
        expression = "conj(" + contents + ") = " + output;
        problem = output;
        contents = "";
        evaluatedExpression = true;
      }
      else
      {
        System.err.println("Conjugate Failed: Evaluator returned null result.");
      }
    }
    catch (Exception e)
    {
      System.err.println("Conjugate Failed: " + e.getMessage());
    }

    updateDisplay();
  }
  
  private void invert()
  {
    if (contents.isEmpty())
      return;
    try
    {
      String parsedInput = contents
        .replaceAll("(?<=\\W|^)i", "1i")
        .replaceAll("\\+i", "+1i")
        .replaceAll("-i", "-1i");

      if (parsedInput.startsWith("-"))
        parsedInput = "0" + parsedInput;

      parsedInput = "(" + parsedInput + ")";

      List<Token> tokens = Parser.parse(new BufferedReader(new StringReader(parsedInput)));
      Evaluator evaluator = new Evaluator(tokens);
      var result = evaluator.result();

      if (result != null)
      {
        String output = result.invert().toString();
        expression = "inv(" + contents + ") = " + output;
        problem = output;
        contents = "";
        evaluatedExpression = true;
      }
      else
      {
        System.err.println("Inverse Failed: Evaluator returned null result.");
      }
    }
    catch (Exception e)
    {
      System.err.println("Inverse Failed: " + e.getMessage());
    }

    updateDisplay();
  }

  private void updateDisplay()
  {
    expressionLabel.setText(expression.isEmpty() ? " " : expression);
    if (contents.isEmpty())
    {
      inputLabel.setForeground(Color.GRAY);
      inputLabel.setText(LanguageManager.getEnterComplexNumberText());
    }
    else
    {
      inputLabel.setForeground(Color.BLACK);
      inputLabel
          .setText(contents.contains("i") ? "<html>" + contents.replace("i", "<i>i</i>") + "</html>"
              : contents);
    }
  }
}
