package gui.complexplane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.ComplexNums;

/**
 * Main GUI of ComplexPlane graph.
 */
public class ComplexPlaneGUI extends JFrame 
{
  private static final long serialVersionUID = 1L;
  private static ComplexNums num;
  /**
   * Handles GUI for ComplexPlane.
   */
  public ComplexPlaneGUI() 
  {
    super("Visualization");

    GraphPanel graphPanel = new GraphPanel();
    getContentPane().add(graphPanel, BorderLayout.CENTER);

    double size = Math.max(num.getIConst(), num.getVal());
    
    if (num == null || size < 9) 
    {
      setSize(910, 940); //Size to be static to keep lines together
    } else 
    {
      
      setSize((int) size * 100 + 510, (int) size * 100 + 540);
    }

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
    setVisible(true);
    
    setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    if (num != null) 
    {
      graphPanel.plot(num.getVal(), num.getIConst());
    } else 
    {
      graphPanel.plot(0, 0); //Default if nothing is plotted
    }
  }
  
  /**
   * Setter to plot.
   * @param n
   */
  public static void setNum(final ComplexNums n) 
  {
    num = n;
  }
  
}



