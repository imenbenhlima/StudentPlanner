package fr.student.app.ui.timetable;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.student.app.databinding.ItemTimetableBinding;
import fr.student.app.db.TimetableSlotEntity;
import fr.student.app.util.DayLabels;
import fr.student.app.util.TimeFormat;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.VH> {

    public static final class Row {
        public final TimetableSlotEntity slot;
        public final String courseName;

        public Row(TimetableSlotEntity slot, String courseName) {
            this.slot = slot;
            this.courseName = courseName;
        }
    }

    public interface Listener {
        void onEdit(TimetableSlotEntity slot);

        void onDelete(TimetableSlotEntity slot);
    }

    private final List<Row> items = new ArrayList<>();
    private final Listener listener;

    public TimetableAdapter(Listener listener) {
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
        return new VH(ItemTimetableBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Row row = items.get(position);
        TimetableSlotEntity s = row.slot;
        h.binding.title.setText(row.courseName);
        String day = DayLabels.label(h.binding.getRoot().getContext(), s.dayOfWeek);
        String time = TimeFormat.formatMinutes(s.startMinuteOfDay) + " – " + TimeFormat.formatMinutes(s.endMinuteOfDay);
        h.binding.subtitle.setText(day + " · " + time);
        h.binding.getRoot().setOnClickListener(v -> listener.onEdit(s));
        h.binding.getRoot().setOnLongClickListener(v -> {
            listener.onDelete(s);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemTimetableBinding binding;

        VH(ItemTimetableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
