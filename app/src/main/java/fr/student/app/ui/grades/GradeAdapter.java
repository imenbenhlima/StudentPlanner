package fr.student.app.ui.grades;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.student.app.databinding.ItemGradeBinding;
import fr.student.app.db.GradeEntity;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.VH> {

    public static final class Row {
        public final GradeEntity grade;
        public final String courseName;

        public Row(GradeEntity grade, String courseName) {
            this.grade = grade;
            this.courseName = courseName;
        }
    }

    public interface Listener {
        void onEdit(GradeEntity g);

        void onDelete(GradeEntity g);
    }

    private final List<Row> items = new ArrayList<>();
    private final Listener listener;

    public GradeAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<Row> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemGradeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Row row = items.get(position);
        h.binding.title.setText(row.courseName);
        h.binding.gradeValue.setText(String.format(Locale.FRANCE, "%.2f / 20", row.grade.value));
        h.binding.getRoot().setOnClickListener(v -> listener.onEdit(row.grade));
        h.binding.getRoot().setOnLongClickListener(v -> {
            listener.onDelete(row.grade);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemGradeBinding binding;

        VH(ItemGradeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
