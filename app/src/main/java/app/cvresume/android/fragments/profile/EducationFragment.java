package app.cvresume.android.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.List;
import app.cvresume.android.R;
import app.cvresume.android.fragments.profile.education.AddEducationFragment;
import app.cvresume.android.fragments.profile.education.EducationAdapter;
import app.cvresume.android.models.Education;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class EducationFragment extends Fragment {

    private List<Education> educationList = new ArrayList<>();
    private RecyclerView educationRV;
    private EducationAdapter educationAdapter;
    private AppCompatButton addEducationBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String educationListJson;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        // Находим элементы для ввода
        addEducationBtn = view.findViewById(R.id.addEducationBtn);

        educationRV = view.findViewById(R.id.educationRV);
        setupRecyclerView();

        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEducationFragment();
            }
        });

        loadEducationData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Образование");

        return view;
    }

    private void setupRecyclerView() {
        educationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        educationAdapter = new EducationAdapter(educationList);
        educationRV.setAdapter(educationAdapter);
    }

    private void openAddEducationFragment() {
        // Создаем и отображаем фрагмент для ввода образования
        AddEducationFragment addEducationFragment = new AddEducationFragment();
        addEducationFragment.setEducationAdapter(educationAdapter); // Передаем адаптер в AddEducationFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addEducationFragment);
        transaction.addToBackStack(null); // Добавляем фрагмент в стек, чтобы можно было вернуться к EducationFragment
        transaction.commit();
    }

    // Метод для удаления образования по индексу
    private void removeEducation(int position) {
        if (position >= 0 && position < educationList.size()) {
            educationList.remove(position);
            educationAdapter.notifyDataSetChanged(); // Обновить адаптер
            saveEducationData(); // Сохраняем образования после удаления
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
            List<Education> loadedEducationList = gson.fromJson(educationListJson, educationListType);

            // Очищаем существующий список и добавляем загруженные образования
            educationList.clear();
            educationList.addAll(loadedEducationList);

            // Обновляем адаптер после загрузки данных
            educationAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveEducationData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveEducationData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveEducationData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadEducationData();
        activity.getSupportActionBar().setTitle("Образование");
    }
}