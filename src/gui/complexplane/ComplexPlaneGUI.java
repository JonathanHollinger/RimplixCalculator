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
public class ComplexPlaneGUI extends JFrame {

  private JPanel gridPanel;
  private final int rows = 10;
  private final int cols = 10;

  /**
   * Handles GUI for ComplexPlane.
   */
  public ComplexPlaneGUI() 
  {
    super("Complex Plane Viewer");

    GraphPanel graphPanel = new GraphPanel();
    getContentPane().add(graphPanel, BorderLayout.CENTER);

    setSize(800, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setVisible(true);

    graphPanel.plot(3, 2);     // 3 + 2i
  }

}



