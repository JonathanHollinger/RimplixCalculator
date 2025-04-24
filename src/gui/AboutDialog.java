package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * About dialog window for the application.
 */
public class AboutDialog extends JDialog {

  /** Serial version UID. */
  private static final long serialVersionUID = 1L;

  /** Translation map for "About" title. */
  private static final Map<LanguageSelector.Language, String> ABOUT_TITLE = new HashMap<>();

  /** Translation map for description. */
  private static final Map<LanguageSelector.Language, String> DESCRIPTION = new HashMap<>();

  /** Translation map for "developed by" text. */
  private static final Map<LanguageSelector.Language, String> DEVELOPED_BY = new HashMap<>();

  /** Translation map for "Close" button. */
  private static final Map<LanguageSelector.Language, String> CLOSE_BUTTON = new HashMap<>();

  // Initialize translations
  static {
    // "About" title
    ABOUT_TITLE.put(LanguageSelector.Language.ENGLISH, "About");
    ABOUT_TITLE.put(LanguageSelector.Language.SPANISH, "Acerca de");
    ABOUT_TITLE.put(LanguageSelector.Language.FRENCH, "À propos");

    // Description
    DESCRIPTION.put(LanguageSelector.Language.ENGLISH, 
        "Rimplex is a modern, easy-to-use calculator that works with real, "
            + "imaginary, and complex numbers.");
    DESCRIPTION.put(LanguageSelector.Language.SPANISH, 
        "Rimplex es una calculadora moderna y fácil de usar que funciona con números "
            + "reales, imaginarios y complejos.");
    DESCRIPTION.put(LanguageSelector.Language.FRENCH, 
        "Rimplex est une calculatrice moderne et facile à utiliser qui fonctionne avec "
            + "des nombres réels, imaginaires et complexes.");

    // "Developed by" text
    DEVELOPED_BY.put(LanguageSelector.Language.ENGLISH, 
        "It is a product of Sagacious Media that was developed by:");
    DEVELOPED_BY.put(LanguageSelector.Language.SPANISH, 
        "Es un producto de Sagacious Media que fue desarrollado por:");
    DEVELOPED_BY.put(LanguageSelector.Language.FRENCH, 
        "C'est un produit de Sagacious Media qui a été développé par:");

    // "Close" button
    CLOSE_BUTTON.put(LanguageSelector.Language.ENGLISH, "Close");
    CLOSE_BUTTON.put(LanguageSelector.Language.SPANISH, "Cerrar");
    CLOSE_BUTTON.put(LanguageSelector.Language.FRENCH, "Fermer");
  }

  /**
   * Constructor for the About dialog.
   * 
   * @param parent The parent frame
   */
  public AboutDialog(Frame parent) {
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
    // Get the current language
    LanguageSelector.Language currentLanguage = LanguageManager.getLanguage();

    // Set the dialog title
    setTitle(ABOUT_TITLE.get(currentLanguage));

    // Main panel
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    panel.setBackground(Color.LIGHT_GRAY);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.anchor = GridBagConstraints.CENTER;

    // Add title
    JLabel titleLabel = new JLabel(ABOUT_TITLE.get(currentLanguage));
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(titleLabel, gbc);

    // Add logo (X with circle)
    try {
      // This is a placeholder. You may need to create or use an existing logo image
      ImageIcon icon = new ImageIcon("src/media/iconRimplex.png");
      Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
      JLabel logoLabel = new JLabel(new ImageIcon(image));
      logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
      gbc.gridy = 1;
      panel.add(logoLabel, gbc);
    } catch (Exception e) {
      // If image can't be loaded, use a text placeholder
      JLabel logoLabel = new JLabel("X");
      logoLabel.setFont(new Font("Arial", Font.BOLD, 72));
      logoLabel.setForeground(new Color(139, 0, 0)); // Dark red
      logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
      gbc.gridy = 1;
      panel.add(logoLabel, gbc);
    }

    // Add version
    JLabel versionLabel = new JLabel("Rimplex v1.0");
    versionLabel.setFont(new Font("Arial", Font.BOLD, 16));
    versionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridy = 2;
    panel.add(versionLabel, gbc);

    // Add description
    JLabel descriptionLabel = new JLabel("<html><div style='text-align: center;'>" 
        + DESCRIPTION.get(currentLanguage) + "</div></html>");
    descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridy = 3;
    gbc.insets = new Insets(15, 5, 5, 5);
    panel.add(descriptionLabel, gbc);

    // Add developer info
    JLabel developerInfoLabel = new JLabel("<html><div style='text-align: center;'>" 
        + DEVELOPED_BY.get(currentLanguage) + "</div></html>");
    developerInfoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    developerInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    gbc.gridy = 4;
    gbc.insets = new Insets(5, 5, 15, 5);
    panel.add(developerInfoLabel, gbc);

    // Add close button
    JButton closeButton = new JButton(CLOSE_BUTTON.get(currentLanguage));
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    gbc.gridy = 5;
    gbc.fill = GridBagConstraints.NONE;
    panel.add(closeButton, gbc);

    // Set panel size
    panel.setPreferredSize(new Dimension(450, 350));

    getContentPane().add(panel, BorderLayout.CENTER);
  }

  /**
   * Show the about dialog.
   */
  public void showDialog() {
    setVisible(true);
  }
}