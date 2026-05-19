package fr.student.app.ui.reminders;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.ActivityListBinding;
import fr.student.app.databinding.DialogReminderBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.ReminderEntity;
import fr.student.app.reminder.ReminderScheduler;

public class RemindersActivity extends AppCompatActivity implements ReminderAdapter.Listener {

    private ActivityListBinding binding;
    private AppDatabase db;
    private ReminderAdapter adapter;

    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (!granted) {
                    Toast.makeText(this, R.string.notification_channel_desc, Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_reminders);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        db = ((StudentApplication) getApplication()).getDatabase();
        adapter = new ReminderAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        binding.fab.setOnClickListener(v -> {
            ensureNotificationPermission();
            showDialog(null);
        });
        reload();
    }

    private void ensureNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void reload() {
        List<ReminderEntity> list = db.reminderDao().getAll();
        adapter.setItems(list);
    }

    private void showDialog(@Nullable ReminderEntity existing) {
        DialogReminderBinding d = DialogReminderBinding.inflate(LayoutInflater.from(this));
        final Calendar when = Calendar.getInstance(Locale.FRANCE);
        if (existing != null) {
            d.inputText.setText(existing.text);
            when.setTimeInMillis(existing.triggerMillis);
        } else {
            when.add(Calendar.HOUR_OF_DAY, 1);
        }
        updateLabel(d, when);

        d.btnDatetime.setOnClickListener(v -> new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            when.set(Calendar.YEAR, year);
            when.set(Calendar.MONTH, month);
            when.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                when.set(Calendar.HOUR_OF_DAY, hourOfDay);
                when.set(Calendar.MINUTE, minute);
                when.set(Calendar.SECOND, 0);
                when.set(Calendar.MILLISECOND, 0);
                updateLabel(d, when);
            }, when.get(Calendar.HOUR_OF_DAY), when.get(Calendar.MINUTE), true).show();
        }, when.get(Calendar.YEAR), when.get(Calendar.MONTH), when.get(Calendar.DAY_OF_MONTH)).show());

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add : R.string.edit)
                .setView(d.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String text = str(d.inputText.getText());
                    if (text.isEmpty()) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    long ms = when.getTimeInMillis();
                    if (ms <= System.currentTimeMillis()) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (existing == null) {
                        long id = db.reminderDao().insert(new ReminderEntity(text, ms));
                        ReminderScheduler.schedule(this, id, ms);
                    } else {
                        ReminderScheduler.cancel(this, existing.id);
                        existing.text = text;
                        existing.triggerMillis = ms;
                        db.reminderDao().update(existing);
                        ReminderScheduler.schedule(this, existing.id, ms);
                    }
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private static void updateLabel(DialogReminderBinding d, Calendar when) {
        java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(
                java.text.DateFormat.MEDIUM, java.text.DateFormat.SHORT, Locale.FRANCE);
        d.btnDatetime.setText(df.format(when.getTimeInMillis()));
    }

    private static String str(CharSequence s) {
        return s == null ? "" : s.toString().trim();
    }

    @Override
    public void onEdit(ReminderEntity r) {
        ensureNotificationPermission();
        showDialog(r);
    }

    @Override
    public void onDelete(ReminderEntity r) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    ReminderScheduler.cancel(this, r.id);
                    db.reminderDao().delete(r);
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
