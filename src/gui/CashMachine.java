package gui;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

/**
 * The main class for the CashMachine application.
 */
public class CashMachine implements Runnable {

  /**
   * The entry point of the application (which is executed in the
   * main thread of execution).
   *
   * @param args The command-line arguments
   * @throws InterruptedException if the thread is interrupted
   * @throws InvocationTargetException if an exception is thrown during invocation
   */
  public static void main(final String[] args) 
      throws InterruptedException, InvocationTargetException {
    SwingUtilities.invokeAndWait(new CashMachine());
  }

  /**
   * The code that is executed in the event dispatch thread.
   */
  public void run() {
    // Show language selector dialog first
    LanguageSelector languageSelector = new LanguageSelector(null);
    LanguageSelector.Language selectedLanguage = languageSelector.showDialog();

    // If user closed the dialog without selecting a language, default to English
    if (selectedLanguage == null) {
      selectedLanguage = LanguageSelector.Language.ENGLISH;
    }

    // Set the selected language in the LanguageManager
    LanguageManager.setLanguage(selectedLanguage);

    // Create and show the main calculator window
    PINPadWindow window = new PINPadWindow();
    window.setTitle("RimpleX");        
    window.setVisible(true);
  }
}