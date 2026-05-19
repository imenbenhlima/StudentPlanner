package fr.student.app.reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import fr.student.app.MainActivity;
import fr.student.app.R;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.ReminderEntity;

public class ReminderReceiver extends BroadcastReceiver {
    public static final String EXTRA_REMINDER_ID = "reminder_id";
    private static final String CHANNEL_ID = "reminders_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(EXTRA_REMINDER_ID, -1);
        if (id < 0) return;

        AppDatabase db = AppDatabase.getInstance(context);
        ReminderEntity r = db.reminderDao().getById(id);
        if (r == null) return;

        ensureChannel(context);

        Intent open = new Intent(context, MainActivity.class);
        open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(
                context,
                (int) id,
                open,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder b = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_simple)
                .setContentTitle(context.getString(R.string.tool_reminders))
                .setContentText(r.text)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) {
            nm.notify((int) id, b.build());
        }
    }

    private static void ensureChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.notification_channel),
                    NotificationManager.IMPORTANCE_HIGH
            );
            ch.setDescription(context.getString(R.string.notification_channel_desc));
            NotificationManager nm = context.getSystemService(NotificationManager.class);
            if (nm != null) {
                nm.createNotificationChannel(ch);
            }
        }
    }
}
