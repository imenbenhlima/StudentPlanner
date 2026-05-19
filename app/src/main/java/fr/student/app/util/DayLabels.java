package fr.student.app.util;

import android.content.Context;

import java.util.Calendar;

import fr.student.app.R;

public final class DayLabels {
    private DayLabels() {}

    public static String label(Context context, int calendarDayOfWeek) {
        switch (calendarDayOfWeek) {
            case Calendar.MONDAY:
                return context.getString(R.string.monday);
            case Calendar.TUESDAY:
                return context.getString(R.string.tuesday);
            case Calendar.WEDNESDAY:
                return context.getString(R.string.wednesday);
            case Calendar.THURSDAY:
                return context.getString(R.string.thursday);
            case Calendar.FRIDAY:
                return context.getString(R.string.friday);
            case Calendar.SATURDAY:
                return context.getString(R.string.saturday);
            case Calendar.SUNDAY:
            default:
                return context.getString(R.string.sunday);
        }
    }

    public static int[] allDays() {
        return new int[]{
                Calendar.MONDAY,
                Calendar.TUESDAY,
                Calendar.WEDNESDAY,
                Calendar.THURSDAY,
                Calendar.FRIDAY,
                Calendar.SATURDAY,
                Calendar.SUNDAY
        };
    }
}
