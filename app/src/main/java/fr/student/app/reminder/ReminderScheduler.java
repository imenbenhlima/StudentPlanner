package fr.student.app.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public final class ReminderScheduler {
    private ReminderScheduler() {}

    public static void schedule(Context context, long reminderId, long triggerMillis) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(ReminderReceiver.EXTRA_REMINDER_ID, reminderId);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pi = PendingIntent.getBroadcast(context, (int) reminderId, intent, flags);
        if (am != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerMillis, pi);
            } else {
                am.setExact(AlarmManager.RTC_WAKEUP, triggerMillis, pi);
            }
        }
    }

    public static void cancel(Context context, long reminderId) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pi = PendingIntent.getBroadcast(context, (int) reminderId, intent, flags);
        if (am != null) {
            am.cancel(pi);
        }
        pi.cancel();
    }
}
