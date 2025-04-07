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
  private static final String EXIT_ITEM = "Exit";
  private static final String FILE_MENU = "File";
  private static final String HELP_MENU = "Help";
  private static final String VIEW_MENU = "View"; 
  private static final String ABOUT_MENU = "About"; 
  

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
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu fileMenu = new JMenu(FILE_MENU);
    JMenu viewMenu = new JMenu(VIEW_MENU);
    JMenu helpMenu = new JMenu(HELP_MENU);
    JMenuItem exitItem = new JMenuItem(EXIT_ITEM);
    JMenuItem aboutItem = new JMenuItem(ABOUT_MENU);
    JMenuItem helpItem = new JMenuItem(HELP_MENU);
    exitItem.addActionListener(e -> System.exit(0));
    fileMenu.add(exitItem);
    helpMenu.add(aboutItem);
    helpMenu.add(helpItem);
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
  }

}
