package fr.student.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

import fr.student.app.R;
import fr.student.app.databinding.ActivityMainBinding;
import fr.student.app.ui.home.HomeFragment;
import fr.student.app.ui.settings.SettingsFragment;
import fr.student.app.ui.tools.ToolsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            binding.bottomNav.setSelectedItemId(R.id.nav_home);
        }

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                Fragment f;
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    f = new HomeFragment();
                } else if (id == R.id.nav_tools) {
                    f = new ToolsFragment();
                } else if (id == R.id.nav_settings) {
                    f = new SettingsFragment();
                } else {
                    return false;
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, f)
                        .commit();
                return true;
            }
        });
    }
}
