package gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import gui.complexplane.ComplexPlaneGUI;

/**
 * A window containing a PIN entry pad.
 */
public class PINPadWindow extends JFrame
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private static final String EXIT_ITEM = "Exit";
  private static final String FILE_MENU = "File";
  private static final String HELP_MENU = "Help";
  private static final String VIEW_MENU = "View"; 
  private static final String ABOUT_MENU = "About"; 
  private static final String CPLANE_MENU = "Complex Plane"; 


  /**
   * Default Constructor.
   */
  public PINPadWindow()
  {
    super();
    setupLayout();
    pack();
    setResizable(true);
    setSize(300, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void setupLayout()
  {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu fileMenu = new JMenu(FILE_MENU);
    JMenu viewMenu = new JMenu(VIEW_MENU);
    JMenu helpMenu = new JMenu(HELP_MENU);
    JMenuItem exitItem = new JMenuItem(EXIT_ITEM);
    JMenuItem aboutItem = new JMenuItem(ABOUT_MENU);
    JMenuItem helpItem = new JMenuItem(HELP_MENU);
    helpItem.addActionListener(e -> runHelp());
    JMenuItem cPlane = new JMenuItem(CPLANE_MENU);
    cPlane.addActionListener(e -> runComplexPlane());
    
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);
    helpMenu.add(aboutItem);
    helpMenu.add(helpItem);
    viewMenu.add(cPlane);
    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);

    setSize(600, 500);
    setTitle("RimpleX");

    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    Display display = new Display();
    contentPane.add(display, BorderLayout.NORTH);

    NumberPad numberPad = new NumberPad(display);
    contentPane.add(numberPad, BorderLayout.CENTER);
    
    CollapsiblePanel test = new CollapsiblePanel("History");
    contentPane.add(test, BorderLayout.SOUTH);


    ImageIcon img = new ImageIcon("src/media/iconRimplex.png");
    setIconImage(img.getImage());
  }

  private void runHelp() {
    File file = new File("src/media/help.html");
    try
    {
      Desktop.getDesktop().browse(file.toURI());
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void runComplexPlane() {
    new ComplexPlaneGUI();
  }
}
