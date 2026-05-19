package fr.student.app.ui.reminders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.student.app.databinding.ItemReminderBinding;
import fr.student.app.db.ReminderEntity;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.VH> {

    public interface Listener {
        void onEdit(ReminderEntity r);

        void onDelete(ReminderEntity r);
    }

    private final List<ReminderEntity> items = new ArrayList<>();
    private final Listener listener;

    public ReminderAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<ReminderEntity> list) {
        items.clear();
        if (list != null) items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemReminderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        ReminderEntity r = items.get(position);
        h.binding.text.setText(r.text);
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRANCE);
        h.binding.time.setText(df.format(r.triggerMillis));
        h.binding.getRoot().setOnClickListener(v -> listener.onEdit(r));
        h.binding.getRoot().setOnLongClickListener(v -> {
            listener.onDelete(r);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final ItemReminderBinding binding;

        VH(ItemReminderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
