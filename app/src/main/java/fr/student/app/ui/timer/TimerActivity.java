package fr.student.app.ui.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

import fr.student.app.R;
import fr.student.app.databinding.ActivityTimerBinding;

public class TimerActivity extends AppCompatActivity {

    private ActivityTimerBinding binding;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable tick = new Runnable() {
        @Override
        public void run() {
            refresh();
            handler.postDelayed(this, 1000L);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.tool_timer);
        }
        binding.toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
        handler.post(tick);
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(tick);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(tick);
        super.onDestroy();
    }

    private void refresh() {
        Calendar now = Calendar.getInstance(Locale.FRANCE);
        long nowMs = now.getTimeInMillis();

        Calendar summer = targetMayFirst(now);
        binding.countdownSummer.setText(formatDiff(nowMs, summer.getTimeInMillis()));

        Calendar rentree = targetSeptember12(now);
        binding.countdownSchool.setText(formatDiff(nowMs, rentree.getTimeInMillis()));
    }

    /** Next 1st May at 00:00 (local). */
    private static Calendar targetMayFirst(Calendar reference) {
        Calendar c = Calendar.getInstance(reference.getTimeZone(), Locale.FRANCE);
        c.set(Calendar.MONTH, Calendar.MAY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        if (c.getTimeInMillis() <= reference.getTimeInMillis()) {
            c.add(Calendar.YEAR, 1);
        }
        return c;
    }

    /** Next 12 September at 08:00 (local) — rentrée. */
    private static Calendar targetSeptember12(Calendar reference) {
        Calendar c = Calendar.getInstance(reference.getTimeZone(), Locale.FRANCE);
        c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c.set(Calendar.DAY_OF_MONTH, 12);
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        if (c.getTimeInMillis() <= reference.getTimeInMillis()) {
            c.add(Calendar.YEAR, 1);
        }
        return c;
    }

    private String formatDiff(long nowMs, long targetMs) {
        long diff = targetMs - nowMs;
        if (diff <= 0) {
            return getString(R.string.timer_passed);
        }
        long sec = diff / 1000;
        long d = sec / 86400;
        long h = (sec % 86400) / 3600;
        long m = (sec % 3600) / 60;
        long s = sec % 60;
        return getString(R.string.timer_days, (int) d) + " · "
                + getString(R.string.timer_hours, (int) h) + " · "
                + getString(R.string.timer_minutes, (int) m) + " · "
                + getString(R.string.timer_seconds, (int) s);
    }
}
