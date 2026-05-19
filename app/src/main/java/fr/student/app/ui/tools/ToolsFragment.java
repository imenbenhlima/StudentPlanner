package fr.student.app.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.student.app.R;
import fr.student.app.databinding.FragmentToolsBinding;
import fr.student.app.databinding.ItemToolCardBinding;
import fr.student.app.ui.assignments.AssignmentsActivity;
import fr.student.app.ui.courses.CoursesActivity;
import fr.student.app.ui.grades.GradesActivity;
import fr.student.app.ui.notes.NotesActivity;
import fr.student.app.ui.reminders.RemindersActivity;
import fr.student.app.ui.timetable.TimetableActivity;
import fr.student.app.ui.timer.TimerActivity;

public class ToolsFragment extends Fragment {

    private FragmentToolsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentToolsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addTool(R.string.tool_courses, R.drawable.ic_tool_course, CoursesActivity.class);
        addTool(R.string.tool_timetable, R.drawable.ic_tool_schedule, TimetableActivity.class);
        addTool(R.string.tool_assignments, R.drawable.ic_tool_assignment, AssignmentsActivity.class);
        addTool(R.string.tool_notes, R.drawable.ic_tool_note, NotesActivity.class);
        addTool(R.string.tool_grades, R.drawable.ic_tool_grade, GradesActivity.class);
        addTool(R.string.tool_timer, R.drawable.ic_tool_timer, TimerActivity.class);
        addTool(R.string.tool_reminders, R.drawable.ic_tool_reminder, RemindersActivity.class);
    }

    private void addTool(int titleRes, int iconRes, Class<?> activityClass) {
        ItemToolCardBinding card = ItemToolCardBinding.inflate(getLayoutInflater(), binding.toolsContainer, false);
        card.toolTitle.setText(titleRes);
        card.toolIcon.setImageResource(iconRes);
        card.getRoot().setOnClickListener(v -> startActivity(new Intent(requireContext(), activityClass)));
        binding.toolsContainer.addView(card.getRoot());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
