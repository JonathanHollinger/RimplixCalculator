package gui;

import java.awt.*;
import javax.swing.*;

/**
 * A window containing a PIN entry pad.
 */
public class PINPadWindow extends JFrame
{
  
    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    /**
     * Default Constructor.
     */
    public PINPadWindow()
    {
        super();
        setupLayout();
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setupLayout()
    {
      setSize(300, 300); 
      setTitle("ATM");        

      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      Display display = new Display();        
      contentPane.add(display, BorderLayout.NORTH);
      
      NumberPad numberPad = new NumberPad(display);
      contentPane.add(numberPad, BorderLayout.CENTER);
    }

}
