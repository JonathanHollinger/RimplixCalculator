package gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A dialog window for selecting the application language.
 */
public class LanguageSelector extends JDialog {
    
  /** Serial version UID. */
  private static final long serialVersionUID = 1L;
    
  /**
     * Available languages enum.
     */
  public enum Language {
        /** English language. */
        ENGLISH, 
        /** Spanish language. */
        SPANISH, 
        /** French language. */
        FRENCH
  }
    
  /** Selected language (default to English). */
  private Language selectedLanguage = Language.ENGLISH;
    
  /** Translation maps for title text. */
  private static final Map<Language, String> TITLE_TEXT = new HashMap<>();
    
  /** Translation maps for confirm button text. */
  private static final Map<Language, String> CONFIRM_TEXT = new HashMap<>();
    
  /** Title label display. */
  private JLabel titleLabel;
    
  /** Confirm button. */
  private JButton confirmButton;
    
  /** Language combo box for selection. */
  private JComboBox<String> languageComboBox;
    
  /** Flag to track if the dialog was confirmed. */
  private boolean confirmed = false;
    
  // Initialize translations
  static {
    // Title translations
    TITLE_TEXT.put(Language.ENGLISH, "Select your language");
    TITLE_TEXT.put(Language.SPANISH, "Seleccione su idioma");
    TITLE_TEXT.put(Language.FRENCH, "Sélectionnez votre langue");
        
    // Confirm button translations
    CONFIRM_TEXT.put(Language.ENGLISH, "Confirm");
    CONFIRM_TEXT.put(Language.SPANISH, "Confirmar");
    CONFIRM_TEXT.put(Language.FRENCH, "Confirmer");
  }
    
  /**
   * Constructor for the language selector dialog.
   * 
   * @param parent The parent frame
   * 
   */
  public LanguageSelector(Frame parent) {
    super(parent, true); // Modal dialog
    setupLayout();
    pack();
    setResizable(false);
    setLocationRelativeTo(parent); // Center dialog on parent
  }
    
  /**
   * Set up the dialog layout.
   */
  private void setupLayout() {
    setTitle("RimpleX");
        
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
        
    // Title label
    titleLabel = new JLabel(TITLE_TEXT.get(Language.ENGLISH));
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(titleLabel, gbc);
        
    // Language dropdown
    String[] languages = {"English", "Español", "Français"};
    languageComboBox = new JComboBox<>(languages);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    panel.add(languageComboBox, gbc);
        
    // Add listener to update UI text when language is changed
    languageComboBox.addActionListener(e -> {
      int selectedIndex = languageComboBox.getSelectedIndex();
      switch (selectedIndex) {
        case 0:
          selectedLanguage = Language.ENGLISH;
          break;
        case 1:
          selectedLanguage = Language.SPANISH;
          break;
        case 2:
          selectedLanguage = Language.FRENCH;
          break;
        default:
          selectedLanguage = Language.ENGLISH;
          break;
      }
      updateUiText();
    });
        
    // Confirm button
    confirmButton = new JButton(CONFIRM_TEXT.get(Language.ENGLISH));
    confirmButton.addActionListener(e -> {
      confirmed = true;
      dispose();
    });
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.insets = new Insets(15, 5, 5, 5);
    panel.add(confirmButton, gbc);
        
    getContentPane().add(panel);
  }
    
  /**
   * Update the UI text based on the selected language.
   */
  private void updateUiText() {
    titleLabel.setText(TITLE_TEXT.get(selectedLanguage));
    confirmButton.setText(CONFIRM_TEXT.get(selectedLanguage));
  }
    
  /**
  * Get the selected language.
     * 
     * @return The selected language
     */
  public Language getSelectedLanguage() {
    return selectedLanguage;
  }
    
  /**
     * Show the dialog and return the selected language.
     * 
     * @return The selected language, or null if dialog was closed without confirming
     */
  public Language showDialog() {
    setVisible(true);
    // Dialog is modal, so execution will block until it's closed
    return confirmed ? selectedLanguage : null;
  }
}