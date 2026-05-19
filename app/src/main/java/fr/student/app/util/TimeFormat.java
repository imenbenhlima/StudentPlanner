package fr.student.app.util;

public final class TimeFormat {
    private TimeFormat() {}

    public static String formatMinutes(int minuteOfDay) {
        int h = minuteOfDay / 60;
        int m = minuteOfDay % 60;
        return String.format("%02d:%02d", h, m);
    }
}
