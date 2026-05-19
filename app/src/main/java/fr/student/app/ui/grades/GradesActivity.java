package fr.student.app.ui.grades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.ActivityGradesBinding;
import fr.student.app.databinding.DialogGradeBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.CourseEntity;
import fr.student.app.db.GradeEntity;
import fr.student.app.prefs.AppPreferences;
import fr.student.app.util.GradeCalculator;

public class GradesActivity extends AppCompatActivity implements GradeAdapter.Listener {

    private ActivityGradesBinding binding;
    private AppDatabase db;
    private AppPreferences prefs;
    private GradeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_grades);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        StudentApplication app = (StudentApplication) getApplication();
        db = app.getDatabase();
        prefs = app.getPreferences();

        adapter = new GradeAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        binding.fab.setOnClickListener(v -> showDialog(null));
        binding.btnCalculate.setOnClickListener(v -> updateAverage());
        reload();
    }

    private void reload() {
        long sem = prefs.ensureDefaultSemester(db);
        List<GradeEntity> grades = db.gradeDao().getForSemester(sem);
        List<GradeAdapter.Row> rows = new ArrayList<>();
        Map<Long, CourseEntity> map = new HashMap<>();
        for (GradeEntity g : grades) {
            CourseEntity c = db.courseDao().getById(g.courseId);
            if (c != null) map.put(g.courseId, c);
            rows.add(new GradeAdapter.Row(g, c != null ? c.name : "?"));
        }
        adapter.setItems(rows);
        updateAverageText(grades, map);
    }

    private void updateAverage() {
        long sem = prefs.ensureDefaultSemester(db);
        List<GradeEntity> grades = db.gradeDao().getForSemester(sem);
        Map<Long, CourseEntity> map = new HashMap<>();
        for (GradeEntity g : grades) {
            CourseEntity c = db.courseDao().getById(g.courseId);
            if (c != null) map.put(g.courseId, c);
        }
        updateAverageText(grades, map);
    }

    private void updateAverageText(List<GradeEntity> grades, Map<Long, CourseEntity> map) {
        float avg = GradeCalculator.weightedAverage(grades, map);
        if (Float.isNaN(avg)) {
            binding.averageText.setText(R.string.no_grades);
        } else {
            binding.averageText.setText(getString(R.string.weighted_average, avg));
        }
    }

    private void showDialog(@Nullable GradeEntity existing) {
        DialogGradeBinding d = DialogGradeBinding.inflate(LayoutInflater.from(this));
        long sem = prefs.ensureDefaultSemester(db);
        List<CourseEntity> courses = db.courseDao().getForSemester(sem);
        if (courses.isEmpty()) {
            Toast.makeText(this, R.string.empty_courses, Toast.LENGTH_LONG).show();
            return;
        }
        List<String> labels = new ArrayList<>();
        for (CourseEntity c : courses) labels.add(c.name);
        d.spinnerCourse.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, labels));

        if (existing != null) {
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).id == existing.courseId) {
                    d.spinnerCourse.setSelection(i);
                    break;
                }
            }
            d.inputGrade.setText(String.format(Locale.FRANCE, "%.2f", existing.value));
        }

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add : R.string.edit)
                .setView(d.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String gs = str(d.inputGrade.getText()).replace(',', '.');
                    if (gs.isEmpty()) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    float val;
                    try {
                        val = Float.parseFloat(gs);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (val < 0 || val > 20) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int ci = d.spinnerCourse.getSelectedItemPosition();
                    long courseId = courses.get(ci).id;
                    GradeEntity other = db.gradeDao().getForCourse(sem, courseId);
                    if (existing == null) {
                        if (other != null) {
                            other.value = val;
                            db.gradeDao().update(other);
                        } else {
                            db.gradeDao().insert(new GradeEntity(sem, courseId, val));
                        }
                    } else {
                        if (other != null && other.id != existing.id) {
                            db.gradeDao().delete(other);
                        }
                        existing.courseId = courseId;
                        existing.value = val;
                        db.gradeDao().update(existing);
                    }
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private static String str(CharSequence s) {
        return s == null ? "" : s.toString().trim();
    }

    @Override
    public void onEdit(GradeEntity g) {
        showDialog(g);
    }

    @Override
    public void onDelete(GradeEntity g) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    db.gradeDao().delete(g);
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
