package fr.student.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

import fr.student.app.db.AppDatabase;
import fr.student.app.prefs.AppPreferences;

public class StudentApplication extends Application {

    private AppDatabase database;
    private AppPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        Locale.setDefault(Locale.FRANCE);
        preferences = new AppPreferences(this);
        applyTheme(preferences.getThemeMode());
        database = AppDatabase.getInstance(this);
        preferences.ensureDefaultSemester(database);
    }

    public static void applyTheme(int mode) {
        switch (mode) {
            case AppPreferences.THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case AppPreferences.THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public AppPreferences getPreferences() {
        return preferences;
    }
}
