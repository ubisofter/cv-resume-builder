package app.cvresume.android.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.fragments.profile.experience.AddExperienceFragment;
import app.cvresume.android.fragments.profile.experience.ExperienceAdapter;
import app.cvresume.android.models.Experience;

public class ExperienceFragment extends Fragment {

    private List<Experience> experienceList = new ArrayList<>(); // Список образований
    private RecyclerView experienceRV;
    private ExperienceAdapter experienceAdapter;
    private AppCompatButton addExperienceBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String experienceListJson;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience, container, false);

        // Находим элементы для ввода
        addExperienceBtn = view.findViewById(R.id.addExperienceBtn);

        experienceRV = view.findViewById(R.id.experienceRV);
        setupRecyclerView();

        addExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddExperienceFragment();
            }
        });

        loadExperienceData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Опыт работы");

        return view;
    }

    private void setupRecyclerView() {
        experienceRV.setLayoutManager(new LinearLayoutManager(getContext()));
        experienceAdapter = new ExperienceAdapter(experienceList);
        experienceRV.setAdapter(experienceAdapter);
    }

    private void openAddExperienceFragment() {
        // Создаем и отображаем фрагмент для ввода образования
        AddExperienceFragment addExperienceFragment = new AddExperienceFragment();
        addExperienceFragment.setExperienceAdapter(experienceAdapter); // Передаем адаптер в AddEducationFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addExperienceFragment);
        transaction.addToBackStack(null); // Добавляем фрагмент в стек, чтобы можно было вернуться к EducationFragment
        transaction.commit();
    }

    // Метод для удаления образования по индексу
    private void removeExperience(int position) {
        if (position >= 0 && position < experienceList.size()) {
            experienceList.remove(position);
            experienceAdapter.notifyDataSetChanged(); // Обновить адаптер
            saveExperienceData(); // Сохраняем образования после удаления
        }
    }

    // Метод для сохранения данных в SharedPreferences
    private void saveExperienceData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Преобразуем список образований в строку JSON
        Gson gson = new Gson();
        experienceListJson = gson.toJson(experienceList);

        // Сохраняем строку JSON в SharedPreferences
        editor.putString("experience_list", experienceListJson);
        editor.apply();
    }

    // Метод для загрузки сохраненных образований из SharedPreferences
    private void loadExperienceData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);

        // Получаем строку JSON с образованиями
        experienceListJson = sharedPreferences.getString("experience_list", "");

        if (!experienceListJson.isEmpty()) {
            // Если строка JSON не пустая, преобразуем её обратно в список Education
            Gson gson = new Gson();
            Type experienceListType = new TypeToken<List<Experience>>() {}.getType();
            List<Experience> loadedExperienceList = gson.fromJson(experienceListJson, experienceListType);

            // Очищаем существующий список и добавляем загруженные образования
            experienceList.clear();
            experienceList.addAll(loadedExperienceList);

            // Обновляем адаптер после загрузки данных
            experienceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveExperienceData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveExperienceData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveExperienceData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExperienceData();
        activity.getSupportActionBar().setTitle("Опыт работы");
    }
}
