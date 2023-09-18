package app.cvresume.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.cvresume.android.R;
import app.cvresume.android.adapters.ResumeAdapter;
import app.cvresume.android.fragments.profile.EducationFragment;
import app.cvresume.android.fragments.profile.ExperienceFragment;
import app.cvresume.android.fragments.profile.PersonalFragment;

public class ProfileFragment extends Fragment {

    private RecyclerView sectionsRecycler;
    private String[] resumeSections = {"Персональные данные", "Образование", "Опыт работы", "Навыки", "Языки", "Хобби"};
    int[] resumeIcons = {R.drawable.ic_person, R.drawable.ic_edu, R.drawable.ic_exp, R.drawable.ic_skill, R.drawable.ic_language, R.drawable.ic_hobby};

    AppCompatActivity activity;
    BottomNavigationView bnv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Показать кнопку "Назад" в Toolbar
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);

        // Добавьте обработчик нажатия на кнопку "Назад"
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
                activity.getSupportActionBar().setTitle("Профиль");
                requireActivity().onBackPressed();
                bnv.setVisibility(View.VISIBLE);

            }
        });

        sectionsRecycler = view.findViewById(R.id.sectionsRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        sectionsRecycler.setLayoutManager(layoutManager);

        ResumeAdapter adapter = new ResumeAdapter(resumeSections, resumeIcons);
        sectionsRecycler.setAdapter(adapter);

        // Установите обработчик нажатия на элементы списка
        adapter.setOnItemClickListener(new ResumeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Обработка нажатия на элемент списка
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new PersonalFragment();
                        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                        bnv.setVisibility(View.GONE);
                        activity.getSupportActionBar().setTitle(resumeSections[position]);
                        break;
                    case 1:
                        fragment = new EducationFragment();
                        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                        bnv.setVisibility(View.GONE);
                        activity.getSupportActionBar().setTitle(resumeSections[position]);
                        break;
                    case 2:
                        fragment = new ExperienceFragment();
                        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                        bnv.setVisibility(View.GONE);
                        activity.getSupportActionBar().setTitle(resumeSections[position]);
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        bnv.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        bnv.setVisibility(View.VISIBLE);
    }
}