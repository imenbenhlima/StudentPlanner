package fr.student.app.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.ActivityListBinding;
import fr.student.app.databinding.DialogCourseBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.CourseEntity;
import fr.student.app.db.CourseType;
import fr.student.app.prefs.AppPreferences;

import java.util.Arrays;
import java.util.List;

public class CoursesActivity extends AppCompatActivity implements CourseAdapter.Listener {

    private ActivityListBinding binding;
    private AppDatabase db;
    private AppPreferences prefs;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_courses);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        StudentApplication app = (StudentApplication) getApplication();
        db = app.getDatabase();
        prefs = app.getPreferences();

        adapter = new CourseAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);

        binding.fab.setOnClickListener(v -> showDialog(null));
        reload();
    }

    private void reload() {
        long sem = prefs.ensureDefaultSemester(db);
        List<CourseEntity> list = db.courseDao().getForSemester(sem);
        adapter.setItems(list);
    }

    private void showDialog(@Nullable CourseEntity existing) {
        DialogCourseBinding d = DialogCourseBinding.inflate(LayoutInflater.from(this));
        String[] types = new String[]{
                getString(R.string.type_cours),
                getString(R.string.type_tp),
                getString(R.string.type_td),
                getString(R.string.type_ci)
        };
        List<String> typeCodes = Arrays.asList(CourseType.COURS, CourseType.TP, CourseType.TD, CourseType.CI);
        d.spinnerType.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types));

        if (existing != null) {
            d.inputName.setText(existing.name);
            d.inputCoeff.setText(String.valueOf(existing.coefficient));
            d.inputDesc.setText(existing.description);
            int idx = typeCodes.indexOf(existing.type);
            if (idx >= 0) d.spinnerType.setSelection(idx);
        } else {
            d.spinnerType.setSelection(0);
        }

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add : R.string.edit)
                .setView(d.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String name = str(d.inputName.getText());
                    String coeffStr = str(d.inputCoeff.getText());
                    String desc = str(d.inputDesc.getText());
                    if (name.isEmpty() || coeffStr.isEmpty()) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    float coeff;
                    try {
                        coeff = Float.parseFloat(coeffStr.replace(',', '.'));
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int sel = d.spinnerType.getSelectedItemPosition();
                    String type = typeCodes.get(Math.max(0, Math.min(sel, typeCodes.size() - 1)));
                    long sem = prefs.ensureDefaultSemester(db);
                    if (existing == null) {
                        CourseEntity c = new CourseEntity(sem, name, coeff, desc, type);
                        db.courseDao().insert(c);
                    } else {
                        existing.name = name;
                        existing.coefficient = coeff;
                        existing.description = desc;
                        existing.type = type;
                        db.courseDao().update(existing);
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
    public void onEdit(CourseEntity course) {
        showDialog(course);
    }

    @Override
    public void onDelete(CourseEntity course) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    db.courseDao().delete(course);
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
