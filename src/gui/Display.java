package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.List;
import javax.swing.*;

import gui.complexplane.ComplexPlaneGUI;
import utilities.Check;
import utilities.Engine;
import utilities.arithmetic.Evaluator;
import utilities.arithmetic.Parser;
import utilities.arithmetic.Token;
import utilities.ComplexNums;
import utilities.Nums;

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
  private static final String POLAR = "Pol";


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
          problem += ".";
      }
      case I -> appendToProblem(I);
      case EQUALS -> evaluateExpression();
      case CONJUGATE -> conjugate();
      case INVERSE -> invert();
      case POLAR -> toggleMode();
      case "∠" -> appendToProblem("∠");
      case "°" -> appendToProblem("°");
      case ">" -> resizeBig();
      case "<" -> resizeSmall();
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
    evaluatedExpression = false;
  }

  private void resetExpression()
  {
    expression = parentheses = "";
    evaluatedExpression = false;
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
      String originalInput = problem;
      String processedInput = problem;

      String polarRegex = "(\\d+(\\.\\d+)?)(\\s*)∠(\\s*-?\\d+(\\.\\d+)?)(\\s*)°";
      java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(polarRegex);
      java.util.regex.Matcher matcher = pattern.matcher(processedInput);

      StringBuffer sb = new StringBuffer();
      while (matcher.find())
      {
        double r = Double.parseDouble(matcher.group(1));
        double theta = Double.parseDouble(matcher.group(4));
        double thetaRad = Math.toRadians(theta);
        double real = r * Math.cos(thetaRad);
        double imag = r * Math.sin(thetaRad);
        if (Math.abs(real) < 1e-10) real = 0;
        if (Math.abs(imag) < 1e-10) imag = 0;

        String replacement = String.format(Locale.US, "%.6f%s%.6fi",
          real, (imag >= 0 ? "+" : ""), imag);

        matcher.appendReplacement(sb, replacement);
      }
      matcher.appendTail(sb);
      processedInput = sb.toString();

      processedInput = processedInput
        .replaceAll("(?<=\\W|^)-i", "-1i")
        .replaceAll("(?<=\\W|^)\\+i", "+1i")
        .replaceAll("(?<=\\W|^)i", "1i");

      if (!processedInput.contains("i") && processedInput.startsWith("-"))
        processedInput = "0" + processedInput;
      
      if (processedInput.endsWith("."))
    	processedInput = processedInput.substring(0, processedInput.length() - 1);

      if (processedInput.matches(".*[+\\-x÷]$"))
        processedInput = processedInput.substring(0, processedInput.length() - 1);
    	
      List<Token> tokens = Parser.parse(new BufferedReader(new StringReader(processedInput)));
      Evaluator evaluator = new Evaluator(tokens);
      var result = evaluator.result();
      
      if (result instanceof ComplexNums)
      {
        ComplexNums c = (ComplexNums) result;
        double r = c.getVal();
        double i = c.getIConst();

        if (Math.abs(r) < 1e-10) r = 0;
        if (Math.abs(i) < 1e-10) i = 0;

        result = new ComplexNums(r, i);
      }

      String resultStr = isPolarMode
        ? formatAsPolar(result)
        : formatAsRectangular(result);

      String displayKey = originalInput + " = " + resultStr;

      history.put(displayKey, originalInput); // preserve user's input as typed (e.g. 3.5×6)
      for (Engine listener : historyListeners)
        listener.onHistoryUpdated(new ArrayList<>(history.keySet()));

      expression = displayKey;
      ComplexPlaneGUI.setNum(result);

      problem = resultStr;
      contents = "";
      evaluatedExpression = true;
    }
    catch (Exception e)
    {
      System.err.println("Parsing Failed: " + e.getMessage());
    }

    updateDisplay();
  }
  
  private String formatAsRectangular(ComplexNums result)
  {
    double real = result.getVal();
    double imag = result.getIConst();

    if (Math.abs(real) < 1e-10) real = 0;
    if (Math.abs(imag) < 1e-10) imag = 0;

    String realStr = String.format("%.2f", real);
    String imagStr = String.format("%.2f", Math.abs(imag));

    if (imag > 0)
      return real == 0 ? imagStr + "i" : realStr + "+" + imagStr + "i";
    else if (imag < 0)
      return real == 0 ? "-" + imagStr + "i" : realStr + "-" + imagStr + "i";

    return realStr;
  }

  private void handleInput(String ac)
  {
	if (ac.contains("∠") && (!contents.isEmpty() || !problem.isEmpty()))
	{
	  System.err.println("Cannot mix polar input with a rectangular expression.");
	  return;
	}
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

    int lastPlus = contents.lastIndexOf('+');
    int lastMinus = contents.lastIndexOf('-');
    int lastMult = contents.lastIndexOf('x');
    int lastDiv = contents.lastIndexOf('÷');
    int lastAngle = contents.lastIndexOf('∠');
    int lastDegree = contents.lastIndexOf('°');

    int lastOp = Math.max(Math.max(Math.max(lastPlus, lastMinus), Math.max(lastMult, lastDiv)),
                          Math.max(lastAngle, lastDegree));

    String currentToken = contents.substring(lastOp + 1);
    return !currentToken.contains(".");
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

      List<Token> tokens = Parser.parse(new BufferedReader(new StringReader(parsedInput)));
      Evaluator evaluator = new Evaluator(tokens);
      var result = evaluator.result();

      if (result instanceof ComplexNums)
      {
        ComplexNums complex = (ComplexNums) result;
        ComplexNums conj = complex.conjugate();
        String output = conj.formattedString();

        expression = "conj(" + contents + ") = " + output;
        problem = output;
        contents = "";
        evaluatedExpression = true;
      }
      else
      {
        System.err.println("Conjugate Failed: Result not a ComplexNums.");
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

      if (result instanceof ComplexNums)
      {
        ComplexNums complex = (ComplexNums) result;
        ComplexNums inv = complex.invert();
        String output = inv.formattedString();

        expression = "inv(" + contents + ") = " + output;
        problem = output;
        contents = "";
        evaluatedExpression = true;
      }
      else
      {
        System.err.println("Inverse Failed: Result not a ComplexNums.");
      }
    }
    catch (Exception e)
    {
      System.err.println("Inverse Failed: " + e.getMessage());
    }

    updateDisplay();
  }
  
  private boolean isPolarMode = false;
  
  private void toggleMode()
  {
    isPolarMode = !isPolarMode;
    
    if (evaluatedExpression && !problem.isEmpty())
    {
        try
        {
            List<Token> tokens = Parser.parse(new BufferedReader(new StringReader(problem)));
            Evaluator evaluator = new Evaluator(tokens);
            var result = evaluator.result();

            String resultStr = isPolarMode
                ? formatAsPolar(result)
                : formatAsRectangular((ComplexNums) result);

            expression = problem + " = " + resultStr;
        }
        catch (Exception e)
        {
            System.err.println("Error reformatting previous result: " + e.getMessage());
        }
    }
    
    updateDisplay();
  }
  
  private String formatAsPolar(Nums complex)
  {
    if (!(complex instanceof ComplexNums))
      return "Invalid";

    ComplexNums c = (ComplexNums) complex;
    double real = c.getVal();
    double imag = c.getIConst();
    
    if (Math.abs(real) < 1e-10) real = 0;
    if (Math.abs(imag) < 1e-10) imag = 0;

    double r = Math.hypot(real, imag);
    double theta = Math.toDegrees(Math.atan2(imag, real));
    if (theta < 0)
      theta += 360;

    r = Math.round(r * 1000.0) / 1000.0;
    theta = Math.round(theta * 100.0) / 100.0;

    return String.format("%.2f∠%.2f°", r, theta);
  }

  private void updateDisplay()
  {
    expressionLabel.setText(expression.isEmpty() ? " " : expression);

    if (contents.isEmpty())
    {
      inputLabel.setForeground(Color.GRAY);
      inputLabel.setText(LanguageManager.getEnterComplexNumberText());
      return;
    }

    inputLabel.setForeground(Color.BLACK);
    inputLabel.setText(contents.contains("i")
        ? "<html>" + contents.replace("i", "<i>i</i>") + "</html>"
        : contents);
  }
  
  private void resizeBig()
  {
    NumberPad.changeText();
    historyPanel.setCollapsed(!historyPanel.isCollapsed());
    // PINPadWindow.setSize(700, 500);
    for (Engine listener : historyListeners)
    {
      listener.resizeBig();;
    }
  }

  private void resizeSmall()
  {
    NumberPad.changeText();
    historyPanel.setCollapsed(!historyPanel.isCollapsed());
    // PINPadWindow.setSize(380, 500);
    for (Engine listener : historyListeners)
    {
      listener.resizeSmall();
    }
  }
  
}
