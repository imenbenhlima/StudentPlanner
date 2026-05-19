package fr.student.app.ui.courses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.student.app.R;
import fr.student.app.databinding.ItemCourseBinding;
import fr.student.app.db.CourseEntity;
import fr.student.app.db.CourseType;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.VH> {

    public interface Listener {
        void onEdit(CourseEntity course);

        void onDelete(CourseEntity course);
    }

    private final List<CourseEntity> items = new ArrayList<>();
    private final Listener listener;

    public CourseAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<CourseEntity> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding b = ItemCourseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VH(b);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        CourseEntity c = items.get(position);
        h.binding.title.setText(c.name);
        String typeLabel = typeLabel(h.itemView, c.type);
        h.binding.subtitle.setText(h.itemView.getContext().getString(R.string.course_coefficient) + ": " + c.coefficient + " · " + typeLabel);
        h.binding.desc.setText(c.description != null && !c.description.isEmpty() ? c.description : "—");
        h.binding.getRoot().setOnClickListener(v -> listener.onEdit(c));
        h.binding.getRoot().setOnLongClickListener(v -> {
            listener.onDelete(c);
            return true;
        });
    }

    private static String typeLabel(View v, String type) {
        if (CourseType.TP.equals(type)) return v.getContext().getString(R.string.type_tp);
        if (CourseType.TD.equals(type)) return v.getContext().getString(R.string.type_td);
        if (CourseType.CI.equals(type)) return v.getContext().getString(R.string.type_ci);
        return v.getContext().getString(R.string.type_cours);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemCourseBinding binding;

        VH(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
