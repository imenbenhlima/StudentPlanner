package fr.student.app.ui.assignments;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.student.app.databinding.ItemAssignmentBinding;
import fr.student.app.db.AssignmentEntity;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.VH> {

    public static final class Row {
        public final AssignmentEntity assignment;
        public final String courseName;

        public Row(AssignmentEntity assignment, String courseName) {
            this.assignment = assignment;
            this.courseName = courseName;
        }
    }

    public interface Listener {
        void onEdit(AssignmentEntity a);

        void onDelete(AssignmentEntity a);
    }

    private final List<Row> items = new ArrayList<>();
    private final Listener listener;

    public AssignmentAdapter(Listener listener) {
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
        return new VH(ItemAssignmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Row row = items.get(position);
        AssignmentEntity a = row.assignment;
        h.binding.title.setText(a.title);
        h.binding.subtitle.setText(row.courseName);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE);
        h.binding.desc.setText(df.format(a.dueDateMillis) + (a.description != null && !a.description.isEmpty() ? "\n" + a.description : ""));
        h.binding.getRoot().setOnClickListener(v -> listener.onEdit(a));
        h.binding.getRoot().setOnLongClickListener(v -> {
            listener.onDelete(a);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemAssignmentBinding binding;

        VH(ItemAssignmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
