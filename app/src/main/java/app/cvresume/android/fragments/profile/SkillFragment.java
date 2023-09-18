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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.fragments.profile.skill.AddSkillFragment;
import app.cvresume.android.fragments.profile.skill.SkillAdapter;
import app.cvresume.android.models.Skill;

public class SkillFragment extends Fragment {

    private List<Skill> skillList = new ArrayList<>(); // Список образований
    private RecyclerView skillRV;
    private SkillAdapter skillAdapter;
    private AppCompatButton addSkillBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String skillListJson;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill, container, false);

        // Находим элементы для ввода
        addSkillBtn = view.findViewById(R.id.addSkillBtn);

        skillRV = view.findViewById(R.id.skillRV);
        setupRecyclerView();

        addSkillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddSkillFragment();
            }
        });

        loadSkillData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Навыки");

        return view;
    }

    private void setupRecyclerView() {
        skillRV.setLayoutManager(new LinearLayoutManager(getContext()));
        skillAdapter = new SkillAdapter(skillList);
        skillRV.setAdapter(skillAdapter);
    }

    private void openAddSkillFragment() {
        // Создаем и отображаем фрагмент для ввода образования
        AddSkillFragment addSkillFragment = new AddSkillFragment();
        addSkillFragment.setSkillAdapter(skillAdapter); // Передаем адаптер в AddEducationFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addSkillFragment);
        transaction.addToBackStack(null); // Добавляем фрагмент в стек, чтобы можно было вернуться к EducationFragment
        transaction.commit();
    }

    // Метод для удаления образования по индексу
    private void removeSkill(int position) {
        if (position >= 0 && position < skillList.size()) {
            skillList.remove(position);
            skillAdapter.notifyDataSetChanged(); // Обновить адаптер
            saveSkillData(); // Сохраняем образования после удаления
        }
    }

    // Метод для сохранения данных в SharedPreferences
    private void saveSkillData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Преобразуем список образований в строку JSON
        Gson gson = new Gson();
        skillListJson = gson.toJson(skillList);

        // Сохраняем строку JSON в SharedPreferences
        editor.putString("skill_list", skillListJson);
        editor.apply();
    }

    // Метод для загрузки сохраненных образований из SharedPreferences
    private void loadSkillData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);

        // Получаем строку JSON с образованиями
        skillListJson = sharedPreferences.getString("skill_list", "");

        if (!skillListJson.isEmpty()) {
            // Если строка JSON не пустая, преобразуем её обратно в список Education
            Gson gson = new Gson();
            Type skillListType = new TypeToken<List<Skill>>() {}.getType();
            List<Skill> loadedSkillList = gson.fromJson(skillListJson, skillListType);

            // Очищаем существующий список и добавляем загруженные образования
            skillList.clear();
            skillList.addAll(loadedSkillList);

            // Обновляем адаптер после загрузки данных
            skillAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveSkillData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSkillData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveSkillData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSkillData();
        activity.getSupportActionBar().setTitle("Навыки");
    }
}