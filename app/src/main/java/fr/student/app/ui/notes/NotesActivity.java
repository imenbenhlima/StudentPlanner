package fr.student.app.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.ActivityListBinding;
import fr.student.app.databinding.DialogNoteBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.CourseEntity;
import fr.student.app.db.CourseNoteEntity;
import fr.student.app.prefs.AppPreferences;

public class NotesActivity extends AppCompatActivity implements NoteAdapter.Listener {

    private ActivityListBinding binding;
    private AppDatabase db;
    private AppPreferences prefs;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_notes);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        StudentApplication app = (StudentApplication) getApplication();
        db = app.getDatabase();
        prefs = app.getPreferences();

        adapter = new NoteAdapter(this);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        binding.fab.setOnClickListener(v -> showDialog(null));
        reload();
    }

    private void reload() {
        long sem = prefs.ensureDefaultSemester(db);
        List<CourseNoteEntity> list = db.noteDao().getForSemester(sem);
        List<NoteAdapter.Row> rows = new ArrayList<>();
        for (CourseNoteEntity n : list) {
            String label;
            if (n.courseId == null) {
                label = getString(R.string.no_course);
            } else {
                CourseEntity c = db.courseDao().getById(n.courseId);
                label = c != null ? c.name : "?";
            }
            rows.add(new NoteAdapter.Row(n, label));
        }
        adapter.setItems(rows);
    }

    private void showDialog(@Nullable CourseNoteEntity existing) {
        DialogNoteBinding d = DialogNoteBinding.inflate(LayoutInflater.from(this));
        long sem = prefs.ensureDefaultSemester(db);
        List<CourseEntity> courses = db.courseDao().getForSemester(sem);
        List<String> labels = new ArrayList<>();
        labels.add(getString(R.string.no_course));
        for (CourseEntity c : courses) labels.add(c.name);
        d.spinnerCourse.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, labels));

        if (existing != null) {
            d.inputTitle.setText(existing.title);
            d.inputContent.setText(existing.content);
            if (existing.courseId == null) {
                d.spinnerCourse.setSelection(0);
            } else {
                for (int i = 0; i < courses.size(); i++) {
                    if (courses.get(i).id == existing.courseId) {
                        d.spinnerCourse.setSelection(i + 1);
                        break;
                    }
                }
            }
        }

        new AlertDialog.Builder(this)
                .setTitle(existing == null ? R.string.add : R.string.edit)
                .setView(d.getRoot())
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    String title = str(d.inputTitle.getText());
                    String content = str(d.inputContent.getText());
                    if (title.isEmpty()) {
                        Toast.makeText(this, R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int sel = d.spinnerCourse.getSelectedItemPosition();
                    Long courseId = null;
                    if (sel > 0 && sel - 1 < courses.size()) {
                        courseId = courses.get(sel - 1).id;
                    }
                    if (existing == null) {
                        db.noteDao().insert(new CourseNoteEntity(sem, courseId, title, content, System.currentTimeMillis()));
                    } else {
                        existing.courseId = courseId;
                        existing.title = title;
                        existing.content = content;
                        db.noteDao().update(existing);
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
    public void onEdit(CourseNoteEntity n) {
        showDialog(n);
    }

    @Override
    public void onDelete(CourseNoteEntity n) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.delete, (d, w) -> {
                    db.noteDao().delete(n);
                    reload();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
