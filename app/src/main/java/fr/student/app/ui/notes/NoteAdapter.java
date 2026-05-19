package fr.student.app.ui.notes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.student.app.databinding.ItemNoteBinding;
import fr.student.app.db.CourseNoteEntity;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.VH> {

    public static final class Row {
        public final CourseNoteEntity note;
        public final String courseLabel;

        public Row(CourseNoteEntity note, String courseLabel) {
            this.note = note;
            this.courseLabel = courseLabel;
        }
    }

    public interface Listener {
        void onEdit(CourseNoteEntity n);

        void onDelete(CourseNoteEntity n);
    }

    private final List<Row> items = new ArrayList<>();
    private final Listener listener;

    public NoteAdapter(Listener listener) {
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
        return new VH(ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Row row = items.get(position);
        h.binding.title.setText(row.note.title);
        h.binding.subtitle.setText(row.courseLabel);
        h.binding.content.setText(row.note.content);
        h.binding.getRoot().setOnClickListener(v -> listener.onEdit(row.note));
        h.binding.getRoot().setOnLongClickListener(v -> {
            listener.onDelete(row.note);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemNoteBinding binding;

        VH(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
