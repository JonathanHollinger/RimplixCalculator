package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A numeric keypad
 */
public class NumberPad extends    JPanel
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
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DIVIDE, 0), "÷");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, 0), "+");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0), "-");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0), "x");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "R");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0), "=");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), ".");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT_PARENTHESIS, 0), "(");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT_PARENTHESIS, 0), ")");
        
        


        
        
        setupLayout();
    }

    /**
     * Setup and layout this NumberPad
     */
    private void addButton(String text)
    {
        
        JButton button = new JButton(text);
        button.addActionListener(listener);
        button.setFont(BUTTON_FONT);
        ActionMap actionMap = this.getActionMap();
        actionMap.put(text, new ClickAction(button));
        add(button);
    }

    
    private void setupLayout()
    {
        setLayout(new GridLayout(5, 5));
        
        addButton("±");
        addButton("C");
        addButton("←");
        addButton("+");
        addButton("R");
        addButton("7");
        addButton("8");
        addButton("9");
        addButton("-");
        addButton("Inv");
        addButton("4");
        addButton("5");
        addButton("6");
        addButton("x");
        addButton("(");
        addButton("1");
        addButton("2");
        addButton("3");
        addButton("÷");
        addButton(")");
        addButton("0");
        addButton("PHDR");
        addButton("i");
        addButton("=");
        addButton(".");
        
        
    }
}
