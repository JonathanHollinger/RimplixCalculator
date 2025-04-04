package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Display extends    JLabel
                     implements ActionListener
{
    private static final long serialVersionUID = 1L;

    private String contents;    

    private static final String     CLEAR = "C";
    private static final String     ERASE_TO_THE_LEFT = "\u232B";    

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
            setText("Enter your PIN");
        }
        else if (ac.equals(ERASE_TO_THE_LEFT)) 
        {
            if (!contents.equals(""))
                contents = contents.substring(0, contents.length()-1);
        }
        else
        {
            contents += ac;
        }
        updateDisplay();
    }
    
    private void updateDisplay()
    {
        if (contents.equals("")) 
        {
            setForeground(Color.GRAY);            
            setText("Enter your PIN");
        }
        else 
        {
            String    asterisks = "";
            
            for (int i=0; i<contents.length(); i++) asterisks += "*";
            setForeground(Color.RED);            
            setText(asterisks);
        }
    }
}