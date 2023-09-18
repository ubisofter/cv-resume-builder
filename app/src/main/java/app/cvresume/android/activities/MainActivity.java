package app.cvresume.android.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import app.cvresume.android.R;
import app.cvresume.android.fragments.HomeFragment;
import app.cvresume.android.fragments.MoreFragment;
import app.cvresume.android.fragments.ProfileFragment;
import app.cvresume.android.fragments.TemplatesFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    HomeFragment homeFragment;
    TemplatesFragment templatesFragment;
    ProfileFragment profileFragment;
    MoreFragment moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadToolbar();
        loadBottomNavigation();

        // Загрузка HomeFragment по умолчанию
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    // Метод для загрузки фрагмента
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void loadToolbar(){
        // Настройка Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Конструктор резюме"); // Задайте свой заголовок
    }

    private void loadBottomNavigation() {
        // Инициализация BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                homeFragment = new HomeFragment();
                templatesFragment = new TemplatesFragment();
                profileFragment = new ProfileFragment();
                moreFragment = new MoreFragment();

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        toolbar.setTitle("Конструктор резюме");
                        loadFragment(homeFragment);
                        return true;
                    case R.id.nav_templates:
                        toolbar.setTitle("Шаблоны");
                        loadFragment(templatesFragment);
                        return true;
                    case R.id.nav_profile:
                        toolbar.setTitle("Профиль");
                        loadFragment(profileFragment);
                        return true;
                    case R.id.nav_more:
                        toolbar.setTitle("Настройки");
                        loadFragment(moreFragment);
                        return true;
                }
                return false;
            }
        });
    }
}