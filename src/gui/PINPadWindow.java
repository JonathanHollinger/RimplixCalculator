package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * A window containing a PIN entry pad.
 */
public class PINPadWindow extends JFrame {

  /** Serial version UID. */
  private static final long serialVersionUID = 1L;

  /**
     * Default Constructor.
     */
  public PINPadWindow() {
    super();
    setupLayout();
    pack();
    setResizable(true);
    setSize(300, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
     * Set up the window layout.
     */
  private void setupLayout() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    // Use translated menu labels from LanguageManager
    JMenu fileMenu = new JMenu(LanguageManager.getFileMenuText());
    JMenu viewMenu = new JMenu(LanguageManager.getViewMenuText());
    JMenu helpMenu = new JMenu(LanguageManager.getHelpMenuText());
    JMenuItem exitItem = new JMenuItem(LanguageManager.getExitItemText());
    JMenuItem aboutItem = new JMenuItem(LanguageManager.getAboutMenuText());
    JMenuItem helpItem = new JMenuItem(LanguageManager.getHelpMenuText());
    helpItem.addActionListener(e -> runHelp());
    JMenuItem cPlane = new JMenuItem(LanguageManager.getComplexPlaneText());
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

    ImageIcon img = new ImageIcon("src/media/iconRimplex.png");
    setIconImage(img.getImage());
  }

  /**
     * Opens the help file in the default browser.
     */
  private void runHelp() {
    File file = new File("src/media/help.html");
    try {
      Desktop.getDesktop().browse(file.toURI());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}