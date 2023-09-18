package app.cvresume.android.fragments.profile.skill;

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
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.fragments.profile.skill.SkillAdapter;
import app.cvresume.android.models.Skill;

public class AddSkillFragment extends Fragment {
    private EditText skillET, skillDescET;
    private StepView skillLvl;
    private Button saveSkillBtn;
    private List<Skill> skillList = new ArrayList<>();
    private SkillAdapter skillAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String skillListJson;
    int sLvl;

    // Метод для установки адаптера
    public void setSkillAdapter(SkillAdapter adapter) {
        skillAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_skill, container, false);

        // Находим элементы для ввода
        skillET = view.findViewById(R.id.skillET);
        skillLvl = view.findViewById(R.id.skillLvl);
        skillDescET = view.findViewById(R.id.skillDescET);
        saveSkillBtn = view.findViewById(R.id.saveSkillBtn);

        // Устанавливаем количество шагов (уровней навыка)
        skillLvl.setStepsNumber(5);

        // Получите массив строк из ресурсов
        String[] skillLevels = getResources().getStringArray(R.array.skill_level);

        skillLvl.getState().steps(Arrays.asList(skillLevels))
                .animationType(StepView.ANIMATION_LINE)
                .commit();

        skillLvl.setOnStepClickListener(step -> {
            switch (step) {
                case 0:
                    skillLvl.go(0,true);
                    sLvl = 0;
                    break;
                case 1:
                    skillLvl.go(1,true);
                    sLvl = 1;
                    break;
                case 2:
                    skillLvl.go(2,true);
                    sLvl = 2;
                    break;
                case 3:
                    skillLvl.go(3,true);
                    sLvl = 3;
                    break;
                case 4:
                    skillLvl.go(4,true);
                    sLvl = 4;
                    break;
            }
        });

        saveSkillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSkill();
                returnToSkillFragment();
            }
        });

        return view;
    }

    private void addSkill() {
        // Получаем данные с полей ввода
        String sSkill = skillET.getText().toString();
        String sDesc = skillDescET.getText().toString();

        // Создаем новый объект Education
        Skill skill = new Skill(sSkill, sLvl, sDesc);

        // Загружаем текущий список образований из SharedPreferences
        loadSkillData();

        // Добавляем новое образование в список
        skillList.add(skill);

        // Сохраняем обновленный список образований в SharedPreferences
        saveSkillData();

        // Очищаем поля ввода
        skillET.setText("");
        skillDescET.setText("");

        // Обновляем адаптер (если он был установлен)
        if (skillAdapter != null) {
            skillAdapter.notifyDataSetChanged();
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
            skillList = gson.fromJson(skillListJson, skillListType);
        }
    }

    private void returnToSkillFragment() {
        getParentFragmentManager().popBackStack();
    }

}