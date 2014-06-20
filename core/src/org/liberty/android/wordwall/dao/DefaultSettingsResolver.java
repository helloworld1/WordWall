package org.liberty.android.wordwall.dao;

/**
 * Dummy settings provider that gives the default settings.
 */
public class DefaultSettingsResolver implements SettingsResolver {
    public String getBackgroundImage() {
        return "images/red_with_normal.etc1";
    }
}

