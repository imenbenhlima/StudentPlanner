package fr.student.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import fr.student.app.R;
import fr.student.app.StudentApplication;
import fr.student.app.databinding.FragmentHomeBinding;
import fr.student.app.db.AppDatabase;
import fr.student.app.db.AssignmentEntity;
import fr.student.app.db.CourseEntity;
import fr.student.app.db.GradeEntity;
import fr.student.app.db.TimetableSlotEntity;
import fr.student.app.prefs.AppPreferences;
import fr.student.app.util.GradeCalculator;
import fr.student.app.util.TimeFormat;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        if (binding == null || getContext() == null) return;
        StudentApplication app = (StudentApplication) requireContext().getApplicationContext();
        AppDatabase db = app.getDatabase();
        AppPreferences prefs = app.getPreferences();
        long semId = prefs.ensureDefaultSemester(db);

        int dow = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);

        List<TimetableSlotEntity> slots = db.timetableDao().getForDay(semId, dow);
        StringBuilder classesText = new StringBuilder();
        if (slots.isEmpty()) {
            classesText.append(getString(R.string.home_no_classes));
        } else {
            for (TimetableSlotEntity s : slots) {
                CourseEntity c = db.courseDao().getById(s.courseId);
                String name = c != null ? c.name : "?";
                classesText.append("• ")
                        .append(name)
                        .append(" — ")
                        .append(TimeFormat.formatMinutes(s.startMinuteOfDay))
                        .append(" – ")
                        .append(TimeFormat.formatMinutes(s.endMinuteOfDay))
                        .append("\n");
            }
        }
        binding.homeClasses.setText(classesText.toString().trim());

        long now = System.currentTimeMillis();
        AssignmentEntity next = db.assignmentDao().getNextDue(semId, now);
        if (next == null) {
            binding.homeAssignment.setText(getString(R.string.home_no_assignments));
        } else {
            CourseEntity cc = db.courseDao().getById(next.courseId);
            String cn = cc != null ? cc.name : "";
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE);
            binding.homeAssignment.setText(getString(R.string.assignment_title) + ": " + next.title + "\n" + cn + "\n" + df.format(next.dueDateMillis));
        }

        List<GradeEntity> grades = db.gradeDao().getForSemester(semId);
        java.util.HashMap<Long, CourseEntity> map = new java.util.HashMap<>();
        for (GradeEntity g : grades) {
            CourseEntity c = db.courseDao().getById(g.courseId);
            if (c != null) map.put(g.courseId, c);
        }
        float avg = GradeCalculator.weightedAverage(grades, map);
        if (!Float.isNaN(avg)) {
            binding.homeAverage.setVisibility(View.VISIBLE);
            binding.homeAverage.setText(getString(R.string.home_average, avg));
        } else {
            binding.homeAverage.setVisibility(View.GONE);
        }

        StringBuilder focus = new StringBuilder();
        boolean any = false;
        for (GradeEntity g : grades) {
            CourseEntity c = map.get(g.courseId);
            if (c != null && g.value < 10f) {
                any = true;
                focus.append("• ").append(c.name).append(" (").append(String.format(Locale.FRANCE, "%.1f", g.value)).append("/20)\n");
            }
        }
        if (!any) {
            binding.homeFocus.setText(getString(R.string.home_no_low_grades));
        } else {
            binding.homeFocus.setText(focus.toString().trim());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
