package app.cvresume.android.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.CourseDao;
import app.cvresume.android.fragments.HomeFragment;
import app.cvresume.android.fragments.MoreFragment;
import app.cvresume.android.fragments.ProfileFragment;
import app.cvresume.android.fragments.TemplatesFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    TemplatesFragment templatesFragment;
    ProfileFragment profileFragment;
    MoreFragment moreFragment;
    private CourseDao courseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-database").build();
        courseDao = appDatabase.courseDao();

        loadToolbar();
        loadBottomNavigation();

        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void loadToolbar(){
        /** вернуть в релизе **/
    }

    private void loadBottomNavigation() {
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
                        loadFragment(homeFragment);
                        return true;
                    case R.id.nav_templates:
                        loadFragment(templatesFragment);
                        return true;
                    case R.id.nav_profile:
                        loadFragment(profileFragment);
                        return true;
                    case R.id.nav_more:
                        loadFragment(moreFragment);
                        return true;
                }
                return false;
            }
        });
    }
}