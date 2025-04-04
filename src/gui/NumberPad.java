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

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "\u232B");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0), "C");
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
        setLayout(new GridLayout(4, 3));
        
        for (int i=1; i<=9; i++) addButton(String.format("%1d", i)); // this works?
        addButton("\u232B");
        addButton("0");
        addButton("C");
        addButton("i");
    }
}
