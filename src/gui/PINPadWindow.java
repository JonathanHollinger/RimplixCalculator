package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.TransferHandler;

import gui.complexplane.ComplexPlaneGUI;
import utilities.Engine;

public class PINPadWindow extends JFrame implements Engine
{

  private static final long serialVersionUID = 1L;
  private Display display;
  private DefaultListModel<String> historyListModel;
  private JList<String> historyList;

  public PINPadWindow()
  {
    super();
    setupLayout();
    // pack();
    setResizable(true);
    setSize(400, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("RimpleX");
  }

  private void setupLayout()
  {
    setJMenuBar(createMenuBar());

    CollapsiblePanel historyPanel = new CollapsiblePanel("History");

    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    display = new Display(historyPanel);
    contentPane.add(display, BorderLayout.NORTH);
    display.addHistoryListener(this);

    NumberPad numberPad = new NumberPad(display);
    contentPane.add(numberPad, BorderLayout.CENTER);

    historyListModel = new DefaultListModel<>();
    historyList = new JList<>(historyListModel);
    historyList.setFocusable(true);
    historyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    historyList.setVisibleRowCount(20);
    historyList.setDragEnabled(true);
    historyList.setTransferHandler(new TransferHandler("selectedValue"));

    InputMap inputMap = historyList.getInputMap(JComponent.WHEN_FOCUSED);
    ActionMap actionMap = historyList.getActionMap();

    inputMap.put(KeyStroke.getKeyStroke("ctrl C"), "copyHistoryItem");
    actionMap.put("copyHistoryItem", new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        String selected = historyList.getSelectedValue();
        if (selected != null)
        {
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          System.out.println("copying: " + selected); // <-- Debug
          clipboard.setContents(new java.awt.datatransfer.StringSelection(selected), null);
        }
      }
    });

    inputMap.put(KeyStroke.getKeyStroke("ctrl V"), "pasteHistoryItem");
    actionMap.put("pasteHistoryItem", new AbstractAction()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        try
        {
          System.out.println("Pasting: "); // <-- Debug
          String selected = (String) clipboard.getData(DataFlavor.stringFlavor);
          if (selected != null && !selected.isBlank())
          {
            display.setInputFromHistory(selected.trim());
          }
        }
        catch (UnsupportedFlavorException | IOException ex)
        {
          ex.printStackTrace();
        }
      }
    });

    JScrollPane scrollPane = new JScrollPane(historyList);
    historyPanel.getContentPane().setLayout(new BorderLayout());
    historyPanel.getContentPane().add(scrollPane, BorderLayout.CENTER);
    contentPane.add(historyPanel, BorderLayout.EAST);

    ImageIcon img = new ImageIcon("src/media/iconRimplex.png");
    setIconImage(img.getImage());
  }

  private JMenuBar createMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();

    JMenu fileMenu = new JMenu(LanguageManager.getFileMenuText());
    JMenu viewMenu = new JMenu(LanguageManager.getViewMenuText());
    JMenu helpMenu = new JMenu(LanguageManager.getHelpMenuText());

    JMenuItem exitItem = new JMenuItem(LanguageManager.getExitItemText());
    exitItem.addActionListener(e -> System.exit(0));

    JMenuItem printItem = new JMenuItem("Print History");
    printItem.addActionListener(e -> showPrintDialog());
    fileMenu.add(printItem);

    JMenuItem aboutItem = new JMenuItem(LanguageManager.getAboutMenuText());
    JMenuItem helpItem = new JMenuItem(LanguageManager.getHelpMenuText());
    helpItem.addActionListener(e -> runHelp());

    JMenuItem cPlane = new JMenuItem(LanguageManager.getComplexPlaneText());
    cPlane.addActionListener(e -> runComplexPlane());

    fileMenu.add(exitItem);
    helpMenu.add(aboutItem);
    helpMenu.add(helpItem);
    viewMenu.add(cPlane);

    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);

    return menuBar;
  }

  public void onHistoryUpdated(List<String> history)
  {
    historyListModel.clear();
    for (String entry : history)
    {
      historyListModel.addElement(entry);
    }
  }

  private void runHelp()
  {
    File file = new File("src/media/help.html");
    try
    {
      Desktop.getDesktop().browse(file.toURI());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  private void showPrintDialog()
  {
    JTextArea textArea = new JTextArea(display.getPrintableHistory());
    textArea.setEditable(false);

    try
    {
      boolean printed = textArea.print(null, null, true, null, null, true);
      if (!printed)
      {
        JOptionPane.showMessageDialog(this, "Printing canceled", "Print",
            JOptionPane.INFORMATION_MESSAGE);
      }
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Print failed: " + ex.getMessage(), "Print Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  public void resizeBig()
  {
    Timer resizer = new Timer(2, new ActionListener() {
      int count = 400;

      @Override
      public void actionPerformed(ActionEvent e)
      {
        count++;
        if (count >= 730) {
          return;
        }
        setSize(count, 500);

      }
    });
    resizer.start();
    // setSize(730, 500);
  }

  public void resizeSmall()
  {
    Timer resizer = new Timer(2, new ActionListener() {
      int count = 730;

      @Override
      public void actionPerformed(ActionEvent e)
      {
        count--;
        if (count <= 400) {
          return;
        }
        setSize(count, 500);

      }
    });
    resizer.start();
    // setSize(400, 500);
  }

  public void runComplexPlane()
  {
    new ComplexPlaneGUI();
  }

}
