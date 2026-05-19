package fr.student.app.ui.timetable;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.ActivityListBinding;
import fr.student.app.databinding.DialogTimetableBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.CourseEntity;
import fr.student.app.db.TimetableSlotEntity;
import fr.student.app.prefs.AppPreferences;
import fr.student.app.util.DayLabels;

public class TimetableActivity extends AppCompatActivity implements TimetableAdapter.Listener {

    private ActivityListBinding binding;
    private AppDatabase db;
    private AppPreferences prefs;
    private TimetableAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_timetable);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        StudentApplication app = (StudentApplication) getApplication();
        db = app.getDatabase();
        prefs = app.getPreferences();

        adapter = new TimetableAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        binding.fab.setOnClickListener(v -> showDialog(null));
        reload();
    }

    private void reload() {
        long sem = prefs.ensureDefaultSemester(db);
        List<TimetableSlotEntity> slots = db.timetableDao().getForSemester(sem);
        List<TimetableAdapter.Row> rows = new ArrayList<>();
        for (TimetableSlotEntity s : slots) {
            CourseEntity c = db.courseDao().getById(s.courseId);
            rows.add(new TimetableAdapter.Row(s, c != null ? c.name : "?"));
        }
        adapter.setItems(rows);
    }

    private void showDialog(@Nullable TimetableSlotEntity existing) {
        DialogTimetableBinding d = DialogTimetableBinding.inflate(LayoutInflater.from(this));
        long sem = prefs.ensureDefaultSemester(db);
        List<CourseEntity> courses = db.courseDao().getForSemester(sem);
        if (courses.isEmpty()) {
            Toast.makeText(this, R.string.empty_courses, Toast.LENGTH_LONG).show();
            return;
        }
        List<String> labels = new ArrayList<>();
        for (CourseEntity c : courses) labels.add(c.name);
        d.spinnerCourse.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, labels));

        int[] days = DayLabels.allDays();
        String[] dayLabels = new String[days.length];
        for (int i = 0; i < days.length; i++) {
            dayLabels[i] = DayLabels.label(this, days[i]);
        }
        d.spinnerDay.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dayLabels));

        final int[] startMin = {8 * 60};
        final int[] endMin = {10 * 60};

        if (existing != null) {
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).id == existing.courseId) {
                    d.spinnerCourse.setSelection(i);
                    break;
                }
            }
            for (int i = 0; i < days.length; i++) {
                if (days[i] == existing.dayOfWeek) {
                    d.spinnerDay.setSelection(i);
                    break;
                }
            }
            startMin[0] = existing.startMinuteOfDay;
            endMin[0] = existing.endMinuteOfDay;
        }

        d.btnStart.setText(formatMin(startMin[0]));
        d.btnEnd.setText(formatMin(endMin[0]));

        d.btnStart.setOnClickListener(v -> pickTime(startMin, d.btnStart));
        d.btnEnd.setOnClickListener(v -> pickTime(endMin, d.btnEnd));

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add : R.string.edit)
                .setView(d.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    int ci = d.spinnerCourse.getSelectedItemPosition();
                    int di = d.spinnerDay.getSelectedItemPosition();
                    if (ci < 0 || ci >= courses.size()) return;
                    long courseId = courses.get(ci).id;
                    int day = days[di];
                    if (endMin[0] <= startMin[0]) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (existing == null) {
                        TimetableSlotEntity s = new TimetableSlotEntity(sem, courseId, day, startMin[0], endMin[0]);
                        db.timetableDao().insert(s);
                    } else {
                        existing.courseId = courseId;
                        existing.dayOfWeek = day;
                        existing.startMinuteOfDay = startMin[0];
                        existing.endMinuteOfDay = endMin[0];
                        db.timetableDao().update(existing);
                    }
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void pickTime(int[] holder, com.google.android.material.button.MaterialButton btn) {
        int h = holder[0] / 60;
        int m = holder[0] % 60;
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            holder[0] = hourOfDay * 60 + minute;
            btn.setText(formatMin(holder[0]));
        }, h, m, true).show();
    }

    private static String formatMin(int min) {
        return String.format("%02d:%02d", min / 60, min % 60);
    }

    @Override
    public void onEdit(TimetableSlotEntity slot) {
        showDialog(slot);
    }

    @Override
    public void onDelete(TimetableSlotEntity slot) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    db.timetableDao().delete(slot);
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
