package dev.jlkeesh.httpserver.config;

import java.util.ResourceBundle;

public class SettingsConfig {
    private static ResourceBundle settings = ResourceBundle.getBundle("application");

    public static String get(String key) {
        return settings.getString(key);
    }
}
