package app.cvresume.android.fragments.profile.education;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.models.Education;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class AddEducationFragment extends Fragment {

    private TextInputEditText degreeET, universityET, educationCityET, dateBeginET, dateEndET, descET;
    private AppCompatButton saveEducationBtn;
    private List<Education> educationList = new ArrayList<>(); // Список образований
    private EducationAdapter educationAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String educationListJson;
    AppCompatActivity activity;
    BottomNavigationView bnv;

    // Метод для установки адаптера
    public void setEducationAdapter(EducationAdapter adapter) {
        educationAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_education, container, false);

        // Находим элементы для ввода
        degreeET = view.findViewById(R.id.degreeET);
        universityET = view.findViewById(R.id.universityET);
        educationCityET = view.findViewById(R.id.educationCityET);
        dateBeginET = view.findViewById(R.id.dateBeginET);
        dateEndET = view.findViewById(R.id.dateEndET);
        descET = view.findViewById(R.id.descET);
        saveEducationBtn = view.findViewById(R.id.saveEducationBtn);

        saveEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEducation();
                returnToEducationFragment();
            }
        });

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Добавить образование");

        return view;
    }

    private void addEducation() {
        // Получаем данные с полей ввода
        String degree = degreeET.getText().toString();
        String university = universityET.getText().toString();
        String years = dateBeginET.getText().toString() + " - " + dateEndET.getText().toString();
        String city = educationCityET.getText().toString();
        String desc = descET.getText().toString();

        // Создаем новый объект Education
        Education education = new Education(degree, university, city, years, desc);

        // Загружаем текущий список образований из SharedPreferences
        loadEducationData();

        // Добавляем новое образование в список
        educationList.add(education);

        // Сохраняем обновленный список образований в SharedPreferences
        saveEducationData();

        // Очищаем поля ввода
        degreeET.setText("");
        universityET.setText("");
        educationCityET.setText("");
        dateBeginET.setText("");
        dateEndET.setText("");
        descET.setText("");

        // Обновляем адаптер (если он был установлен)
        if (educationAdapter != null) {
            educationAdapter.notifyDataSetChanged();
        }
    }

    // Метод для сохранения данных в SharedPreferences
    private void saveEducationData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Преобразуем список образований в строку JSON
        Gson gson = new Gson();
        educationListJson = gson.toJson(educationList);

        // Сохраняем строку JSON в SharedPreferences
        editor.putString("education_list", educationListJson);
        editor.apply();
    }

    // Метод для загрузки сохраненных образований из SharedPreferences
    private void loadEducationData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);

        // Получаем строку JSON с образованиями
        educationListJson = sharedPreferences.getString("education_list", "");

        if (!educationListJson.isEmpty()) {
            // Если строка JSON не пустая, преобразуем её обратно в список Education
            Gson gson = new Gson();
            Type educationListType = new TypeToken<List<Education>>() {}.getType();
            educationList = gson.fromJson(educationListJson, educationListType);
        }
    }

    private void returnToEducationFragment() {
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