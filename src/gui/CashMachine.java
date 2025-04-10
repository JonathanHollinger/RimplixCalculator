package gui;

import java.lang.reflect.*;
import javax.swing.*;

/**
 * The main class for the CashMachine application.
 */
public class CashMachine implements Runnable
{
    /**
     * The entry point of the application (which is executed in the
     * main thread of execution).
     *
     * @param args   The command-line arguments
     */
    public static void main(String[] args) throws InterruptedException, InvocationTargetException
    {
      SwingUtilities.invokeAndWait(new CashMachine());
    }
    
    /**
     * The code that is executed in the event dispatch thread.
     */
    public void run()
    {
      PINPadWindow window = new PINPadWindow();
      window.setTitle("RimpleX");        
      window.setVisible(true);
    }
}
