package fr.student.app.prefs;

import android.content.Context;
import android.content.SharedPreferences;

public final class AppPreferences {
    private static final String PREFS = "student_prefs";
    private static final String KEY_SEMESTER_ID = "current_semester_id";
    private static final String KEY_THEME = "theme_mode";
    public static final int THEME_LIGHT = 0;
    public static final int THEME_DARK = 1;
    public static final int THEME_SYSTEM = 2;

    private final SharedPreferences prefs;

    public AppPreferences(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public long getCurrentSemesterId() {
        return prefs.getLong(KEY_SEMESTER_ID, -1L);
    }

    public void setCurrentSemesterId(long id) {
        prefs.edit().putLong(KEY_SEMESTER_ID, id).apply();
    }

    public int getThemeMode() {
        return prefs.getInt(KEY_THEME, THEME_SYSTEM);
    }

    public void setThemeMode(int mode) {
        prefs.edit().putInt(KEY_THEME, mode).apply();
    }

    /** Ensures a default semester exists; returns current semester id. */
    public long ensureDefaultSemester(fr.student.app.db.AppDatabase db) {
        long id = getCurrentSemesterId();
        if (id > 0 && db.semesterDao().getById(id) != null) {
            return id;
        }
        fr.student.app.db.SemesterEntity s = new fr.student.app.db.SemesterEntity("Année 1 — Semestre 1");
        long newId = db.semesterDao().insert(s);
        setCurrentSemesterId(newId);
        return newId;
    }
}
