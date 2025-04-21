package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A numeric keypad
 */
public class NumberPad extends JPanel
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static final Font BUTTON_FONT = new Font("DejaVu Sans", Font.PLAIN, 12);

  ActionListener listener;

  /**
   * Default Constructor
   */
  public NumberPad(ActionListener listener)
  {
    super();

    this.listener = listener;

    // 0 means it needs no modifier, the shift down means it works when shift is held down.
    InputMap inputMap  = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0), "0");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), "1");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0), "2");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0), "3");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0), "4");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0), "5");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_6, 0), "6");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0), "7");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_8, 0), "8");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, 0), "9");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0), "i");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "←");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "C");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0), "÷");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.SHIFT_DOWN_MASK), "+");
    // inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SLASH, InputEvent.SHIFT_DOWN_MASK), "+");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "-");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "x");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "R");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "=");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), ".");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_9, InputEvent.SHIFT_DOWN_MASK), "(");
    // inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BRACELEFT, InputEvent.SHIFT_DOWN_MASK), "(");
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.SHIFT_DOWN_MASK), ")");
    // inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BRACERIGHT, InputEvent.SHIFT_DOWN_MASK), "(");
    setupLayout();
  }

  /**
   * Setup and layout this NumberPad
   */
  private void addButton(String text, int x, int y)
  {

    JButton button = new JButton(text);
    button.addActionListener(listener);
    button.setFont(BUTTON_FONT);
    ActionMap actionMap = this.getActionMap();
    actionMap.put(text, new ClickAction(button));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = x;
    c.gridy = y;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.gridwidth = 1;
    c.gridheight = 1;
    add(button, c);

  }

  private void addWideButton(String text, int x, int y)
  {

    JButton button = new JButton(text);
    button.addActionListener(listener);
    button.setFont(BUTTON_FONT);
    ActionMap actionMap = this.getActionMap();
    actionMap.put(text, new ClickAction(button));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = x;
    c.gridy = y;
    c.gridwidth = 2;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.fill = GridBagConstraints.BOTH;
    c.gridwidth = 2;
    c.gridheight = 1;
    add(button, c);

  }

  private void setupLayout()
  {

    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    addButton("±", 0, 0);
    addButton("C", 1, 0);
    addButton("←", 2, 0);
    addButton("+", 3, 0);
    addButton("R", 4, 0);
    addButton("7", 0, 1);
    addButton("8", 1, 1);
    addButton("9", 2, 1);
    addButton("-", 3, 1);
    // addButton("Inv", 4, 1);
    addButton("4", 0, 2);
    addButton("5", 1, 2);
    addButton("6", 2, 2);
    addButton("x", 3, 2);
    addButton("(", 4, 2);
    addButton("1", 0, 3);
    addButton("2", 1, 3);
    addButton("3", 2, 3);
    addButton("÷", 3, 3);
    addButton(")", 4, 3);
    addWideButton("0", 0, 4);
    addButton("i", 2, 4);
    addButton("=", 3, 4);
    addButton(".", 4, 4);

  }
}
