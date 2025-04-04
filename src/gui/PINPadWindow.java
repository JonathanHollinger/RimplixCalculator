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
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setupLayout()
    {
      // ImageIcon img = new ImageIcon("media/iconRmplex.png");
      // setIconImage(img.getImage());
      
      setSize(600, 500); 
      setTitle("RimpleX");
      

      Container contentPane = getContentPane();
      contentPane.setLayout(new BorderLayout());

      Display display = new Display();        
      contentPane.add(display, BorderLayout.NORTH);
      
      NumberPad numberPad = new NumberPad(display);
      contentPane.add(numberPad, BorderLayout.CENTER);
    }

}
