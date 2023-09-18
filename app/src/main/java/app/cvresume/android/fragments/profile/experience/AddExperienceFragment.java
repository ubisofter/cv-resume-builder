package app.cvresume.android.fragments.profile.experience;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.fragments.profile.education.EducationAdapter;
import app.cvresume.android.models.Education;
import app.cvresume.android.models.Experience;

public class AddExperienceFragment extends Fragment {

    private EditText positionET, employerET, experienceCityET, workDateBeginET, workDateEndET, workDescET;
    private AppCompatButton saveExperienceBtn;
    private List<Experience> experienceList = new ArrayList<>(); // Список образований
    private ExperienceAdapter experienceAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String experienceListJson;

    AppCompatActivity activity;
    BottomNavigationView bnv;

    // Метод для установки адаптера
    public void setExperienceAdapter(ExperienceAdapter adapter) {
        experienceAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_experience, container, false);

        // Находим элементы для ввода
        positionET = view.findViewById(R.id.positionET);
        employerET = view.findViewById(R.id.employerET);
        experienceCityET = view.findViewById(R.id.experienceCityET);
        workDateBeginET = view.findViewById(R.id.workDateBeginET);
        workDateEndET = view.findViewById(R.id.workDateEndET);
        workDescET = view.findViewById(R.id.workDescET);
        saveExperienceBtn = view.findViewById(R.id.saveExperienceBtn);

        saveExperienceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExperience();
                returnToExperienceFragment();
            }
        });

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Добавить место работы");

        return view;
    }

    private void addExperience() {
        // Получаем данные с полей ввода
        String wPosition = positionET.getText().toString();
        String wEmployer = employerET.getText().toString();
        String wYears = workDateBeginET.getText().toString() + " - " + workDateEndET.getText().toString();
        String wCity = experienceCityET.getText().toString();
        String wDesc = workDescET.getText().toString();

        // Создаем новый объект Education
        Experience experience = new Experience(wPosition, wEmployer, wCity, wYears, wDesc);

        // Загружаем текущий список образований из SharedPreferences
        loadExperienceData();

        // Добавляем новое образование в список
        experienceList.add(experience);

        // Сохраняем обновленный список образований в SharedPreferences
        saveExperienceData();

        // Очищаем поля ввода
        positionET.setText("");
        employerET.setText("");
        experienceCityET.setText("");
        workDateBeginET.setText("");
        workDateEndET.setText("");
        workDescET.setText("");

        // Обновляем адаптер (если он был установлен)
        if (experienceAdapter != null) {
            experienceAdapter.notifyDataSetChanged();
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
            experienceList = gson.fromJson(experienceListJson, experienceListType);
        }

        Log.d("RESUME INFO",sharedPreferences.getAll().toString());
    }

    private void returnToExperienceFragment() {
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onStop() {
        super.onStop();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);
    }

}