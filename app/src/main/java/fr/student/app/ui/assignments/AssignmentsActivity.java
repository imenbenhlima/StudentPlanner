package fr.student.app.ui.assignments;

import android.app.DatePickerDialog;
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
import java.util.Locale;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.ActivityListBinding;
import fr.student.app.databinding.DialogAssignmentBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.AssignmentEntity;
import fr.student.app.db.CourseEntity;
import fr.student.app.prefs.AppPreferences;

public class AssignmentsActivity extends AppCompatActivity implements AssignmentAdapter.Listener {

    private ActivityListBinding binding;
    private AppDatabase db;
    private AppPreferences prefs;
    private AssignmentAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_assignments);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        StudentApplication app = (StudentApplication) getApplication();
        db = app.getDatabase();
        prefs = app.getPreferences();

        adapter = new AssignmentAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        binding.fab.setOnClickListener(v -> showDialog(null));
        reload();
    }

    private void reload() {
        long sem = prefs.ensureDefaultSemester(db);
        List<AssignmentEntity> list = db.assignmentDao().getForSemester(sem);
        List<AssignmentAdapter.Row> rows = new ArrayList<>();
        for (AssignmentEntity a : list) {
            CourseEntity c = db.courseDao().getById(a.courseId);
            rows.add(new AssignmentAdapter.Row(a, c != null ? c.name : "?"));
        }
        adapter.setItems(rows);
    }

    private void showDialog(@Nullable AssignmentEntity existing) {
        DialogAssignmentBinding d = DialogAssignmentBinding.inflate(LayoutInflater.from(this));
        long sem = prefs.ensureDefaultSemester(db);
        List<CourseEntity> courses = db.courseDao().getForSemester(sem);
        if (courses.isEmpty()) {
            Toast.makeText(this, R.string.empty_courses, Toast.LENGTH_LONG).show();
            return;
        }
        List<String> labels = new ArrayList<>();
        for (CourseEntity c : courses) labels.add(c.name);
        d.spinnerCourse.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, labels));

        final Calendar due = Calendar.getInstance(Locale.FRANCE);
        if (existing != null) {
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).id == existing.courseId) {
                    d.spinnerCourse.setSelection(i);
                    break;
                }
            }
            d.inputTitle.setText(existing.title);
            d.inputDesc.setText(existing.description);
            due.setTimeInMillis(existing.dueDateMillis);
        }
        updateDueLabel(d, due);

        d.btnDue.setOnClickListener(v -> new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            due.set(Calendar.YEAR, year);
            due.set(Calendar.MONTH, month);
            due.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                due.set(Calendar.HOUR_OF_DAY, hourOfDay);
                due.set(Calendar.MINUTE, minute);
                due.set(Calendar.SECOND, 0);
                due.set(Calendar.MILLISECOND, 0);
                updateDueLabel(d, due);
            }, due.get(Calendar.HOUR_OF_DAY), due.get(Calendar.MINUTE), true).show();
        }, due.get(Calendar.YEAR), due.get(Calendar.MONTH), due.get(Calendar.DAY_OF_MONTH)).show());

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add : R.string.edit)
                .setView(d.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String title = str(d.inputTitle.getText());
                    if (title.isEmpty()) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int ci = d.spinnerCourse.getSelectedItemPosition();
                    long courseId = courses.get(ci).id;
                    String desc = str(d.inputDesc.getText());
                    if (existing == null) {
                        db.assignmentDao().insert(new AssignmentEntity(sem, courseId, title, desc, due.getTimeInMillis()));
                    } else {
                        existing.courseId = courseId;
                        existing.title = title;
                        existing.description = desc;
                        existing.dueDateMillis = due.getTimeInMillis();
                        db.assignmentDao().update(existing);
                    }
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private static void updateDueLabel(DialogAssignmentBinding d, Calendar due) {
        java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(
                java.text.DateFormat.MEDIUM, java.text.DateFormat.SHORT, Locale.FRANCE);
        d.btnDue.setText(df.format(due.getTimeInMillis()));
    }

    private static String str(CharSequence s) {
        return s == null ? "" : s.toString().trim();
    }

    @Override
    public void onEdit(AssignmentEntity a) {
        showDialog(a);
    }

    @Override
    public void onDelete(AssignmentEntity a) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    db.assignmentDao().delete(a);
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
