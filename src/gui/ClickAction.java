package gui;

import java.awt.event.*;
import javax.swing.*;

/**
 * Responds to click actions.
 */
public class ClickAction extends AbstractAction
{
  private static final long serialVersionUID = 1L;
  private JButton       button;


  public ClickAction(JButton button)
  {
    super();
    this.button = button;
  }


  @Override
  public void actionPerformed(ActionEvent e)
  {
    button.grabFocus();
    button.doClick(50);
  }
}