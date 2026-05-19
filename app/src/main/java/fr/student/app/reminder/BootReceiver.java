package fr.student.app.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fr.student.app.db.AppDatabase;
import fr.student.app.db.ReminderEntity;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
        AppDatabase db = AppDatabase.getInstance(context);
        long now = System.currentTimeMillis();
        for (ReminderEntity r : db.reminderDao().getFuture(now)) {
            ReminderScheduler.schedule(context, r.id, r.triggerMillis);
        }
    }
}
