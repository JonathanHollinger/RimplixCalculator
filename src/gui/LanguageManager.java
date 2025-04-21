package gui;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages translations for the application.
 */
public final class LanguageManager {
    
    /**
     * Private constructor to prevent instantiation.
     */
    private LanguageManager() {
        // Utility class should not be instantiated
    }
    
    /** Current selected language. */
    private static LanguageSelector.Language currentLanguage = LanguageSelector.Language.ENGLISH;
    
    /** Translation map for "Enter a complex number" text. */
    private static final Map<LanguageSelector.Language, String> ENTER_COMPLEX_NUMBER = new HashMap<>();
    
    /** Translation map for "File" menu text. */
    private static final Map<LanguageSelector.Language, String> FILE_MENU = new HashMap<>();
    
    /** Translation map for "View" menu text. */
    private static final Map<LanguageSelector.Language, String> VIEW_MENU = new HashMap<>();
    
    /** Translation map for "Help" menu text. */
    private static final Map<LanguageSelector.Language, String> HELP_MENU = new HashMap<>();
    
    /** Translation map for "About" menu text. */
    private static final Map<LanguageSelector.Language, String> ABOUT_MENU = new HashMap<>();
    
    /** Translation map for "Exit" menu text. */
    private static final Map<LanguageSelector.Language, String> EXIT_ITEM = new HashMap<>();
    
    /** Translation map for "Complex Plane" menu text. */
    private static final Map<LanguageSelector.Language, String> COMPLEX_PLANE = new HashMap<>();
    
    // Initialize translations
    static {
        // "Enter a complex number" prompt
        ENTER_COMPLEX_NUMBER.put(LanguageSelector.Language.ENGLISH, "Enter a complex number");
        ENTER_COMPLEX_NUMBER.put(LanguageSelector.Language.SPANISH, "Introduzca un número complejo");
        ENTER_COMPLEX_NUMBER.put(LanguageSelector.Language.FRENCH, "Entrez un nombre complexe");
        
        // Menu items
        FILE_MENU.put(LanguageSelector.Language.ENGLISH, "File");
        FILE_MENU.put(LanguageSelector.Language.SPANISH, "Archivo");
        FILE_MENU.put(LanguageSelector.Language.FRENCH, "Fichier");
        
        VIEW_MENU.put(LanguageSelector.Language.ENGLISH, "View");
        VIEW_MENU.put(LanguageSelector.Language.SPANISH, "Vista");
        VIEW_MENU.put(LanguageSelector.Language.FRENCH, "Affichage");
        
        HELP_MENU.put(LanguageSelector.Language.ENGLISH, "Help");
        HELP_MENU.put(LanguageSelector.Language.SPANISH, "Ayuda");
        HELP_MENU.put(LanguageSelector.Language.FRENCH, "Aide");
        
        ABOUT_MENU.put(LanguageSelector.Language.ENGLISH, "About");
        ABOUT_MENU.put(LanguageSelector.Language.SPANISH, "Acerca de");
        ABOUT_MENU.put(LanguageSelector.Language.FRENCH, "À propos");
        
        EXIT_ITEM.put(LanguageSelector.Language.ENGLISH, "Exit");
        EXIT_ITEM.put(LanguageSelector.Language.SPANISH, "Salir");
        EXIT_ITEM.put(LanguageSelector.Language.FRENCH, "Quitter");
        
        COMPLEX_PLANE.put(LanguageSelector.Language.ENGLISH, "Complex Plane");
        COMPLEX_PLANE.put(LanguageSelector.Language.SPANISH, "Plano Complejo");
        COMPLEX_PLANE.put(LanguageSelector.Language.FRENCH, "Plan Complexe");
    }
    
    /**
     * Set the current language for the application.
     * 
     * @param language The language to set
     */
    public static void setLanguage(LanguageSelector.Language language) {
        currentLanguage = language;
    }
    
    /**
     * Get the current language.
     * 
     * @return The current language
     */
    public static LanguageSelector.Language getLanguage() {
        return currentLanguage;
    }
    
    /**
     * Get the translated text for "Enter a complex number".
     * 
     * @return Translated text
     */
    public static String getEnterComplexNumberText() {
        return ENTER_COMPLEX_NUMBER.get(currentLanguage);
    }
    
    /**
     * Get the translated text for "File" menu.
     * 
     * @return Translated text
     */
    public static String getFileMenuText() {
        return FILE_MENU.get(currentLanguage);
    }
    
    /**
     * Get the translated text for "View" menu.
     * 
     * @return Translated text
     */
    public static String getViewMenuText() {
        return VIEW_MENU.get(currentLanguage);
    }
    
    /**
     * Get the translated text for "Help" menu.
     * 
     * @return Translated text
     */
    public static String getHelpMenuText() {
        return HELP_MENU.get(currentLanguage);
    }
    
    /**
     * Get the translated text for "About" menu item.
     * 
     * @return Translated text
     */
    public static String getAboutMenuText() {
        return ABOUT_MENU.get(currentLanguage);
    }
    
    /**
     * Get the translated text for "Exit" menu item.
     * 
     * @return Translated text
     */
    public static String getExitItemText() {
        return EXIT_ITEM.get(currentLanguage);
    }
    
    /**
     * Get the translated text for "Complex Plane" menu item.
     * 
     * @return Translated text
     */
    public static String getComplexPlaneText() {
        return COMPLEX_PLANE.get(currentLanguage);
    }
}