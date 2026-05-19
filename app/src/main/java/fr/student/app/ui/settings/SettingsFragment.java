package fr.student.app.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButtonToggleGroup;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.FragmentSettingsBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.SemesterEntity;
import fr.student.app.prefs.AppPreferences;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private AppPreferences prefs;
    private AppDatabase db;
    private List<SemesterEntity> semesters = new ArrayList<>();
    private boolean spinnerProgrammatic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StudentApplication app = (StudentApplication) requireContext().getApplicationContext();
        prefs = app.getPreferences();
        db = app.getDatabase();

        int mode = prefs.getThemeMode();
        if (mode == AppPreferences.THEME_LIGHT) {
            binding.themeToggle.check(R.id.btn_theme_light);
        } else if (mode == AppPreferences.THEME_DARK) {
            binding.themeToggle.check(R.id.btn_theme_dark);
        } else {
            binding.themeToggle.check(R.id.btn_theme_system);
        }

        binding.themeToggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (!isChecked) return;
                int m = AppPreferences.THEME_SYSTEM;
                if (checkedId == R.id.btn_theme_light) m = AppPreferences.THEME_LIGHT;
                else if (checkedId == R.id.btn_theme_dark) m = AppPreferences.THEME_DARK;
                prefs.setThemeMode(m);
                StudentApplication.applyTheme(m);
            }
        });

        binding.btnNewSemester.setOnClickListener(v -> showNewSemesterDialog());
        loadSemesters();
    }

    private void loadSemesters() {
        semesters = db.semesterDao().getAll();
        if (semesters.isEmpty()) {
            prefs.ensureDefaultSemester(db);
            semesters = db.semesterDao().getAll();
        }
        List<String> labels = new ArrayList<>();
        for (SemesterEntity s : semesters) {
            labels.add(s.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, labels);
        binding.semesterSpinner.setAdapter(adapter);
        long current = prefs.getCurrentSemesterId();
        int pos = 0;
        for (int i = 0; i < semesters.size(); i++) {
            if (semesters.get(i).id == current) {
                pos = i;
                break;
            }
        }
        spinnerProgrammatic = true;
        binding.semesterSpinner.setSelection(pos);
        spinnerProgrammatic = false;

        binding.semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerProgrammatic) return;
                SemesterEntity s = semesters.get(position);
                prefs.setCurrentSemesterId(s.id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void showNewSemesterDialog() {
        EditText input = new EditText(requireContext());
        input.setHint(R.string.semester_name_hint);
        input.setMinLines(2);
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.settings_new_semester)
                .setView(input)
                .setPositiveButton(R.string.save, (d, w) -> {
                    String name = input.getText() != null ? input.getText().toString().trim() : "";
                    if (name.isEmpty()) {
                        Toast.makeText(requireContext(), R.string.error_fill_fields, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    long nid = db.semesterDao().insert(new SemesterEntity(name));
                    prefs.setCurrentSemesterId(nid);
                    loadSemesters();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
