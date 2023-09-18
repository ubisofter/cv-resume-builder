package app.cvresume.android.fragments.profile.lang;

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
import app.cvresume.android.fragments.profile.lang.LangAdapter;
import app.cvresume.android.models.Lang;

public class AddLangFragment extends Fragment {
    private EditText langET, langDescET;
    private StepView langLvl;
    private Button saveLangBtn;
    private List<Lang> langList = new ArrayList<>();
    private LangAdapter langAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String langListJson;
    int sLvl;

    // Метод для установки адаптера
    public void setLangAdapter(LangAdapter adapter) {
        langAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lang, container, false);

        // Находим элементы для ввода
        langET = view.findViewById(R.id.langET);
        langLvl = view.findViewById(R.id.langLvl);
        langDescET = view.findViewById(R.id.langDescET);
        saveLangBtn = view.findViewById(R.id.saveLangBtn);

        // Устанавливаем количество шагов (уровней навыка)
        langLvl.setStepsNumber(5);

        // Получите массив строк из ресурсов
        String[] langLevels = getResources().getStringArray(R.array.lang_level);

        langLvl.getState().steps(Arrays.asList(langLevels))
                .animationType(StepView.ANIMATION_LINE)
                .commit();

        langLvl.setOnStepClickListener(step -> {
            switch (step) {
                case 0:
                    langLvl.go(0,true);
                    sLvl = 0;
                    break;
                case 1:
                    langLvl.go(1,true);
                    sLvl = 1;
                    break;
                case 2:
                    langLvl.go(2,true);
                    sLvl = 2;
                    break;
                case 3:
                    langLvl.go(3,true);
                    sLvl = 3;
                    break;
                case 4:
                    langLvl.go(4,true);
                    sLvl = 4;
                    break;
            }
        });

        saveLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLang();
                returnToLangFragment();
            }
        });

        return view;
    }

    private void addLang() {
        // Получаем данные с полей ввода
        String sLang = langET.getText().toString();
        String sDesc = langDescET.getText().toString();

        // Создаем новый объект Education
        Lang lang = new Lang(sLang, sLvl, sDesc);

        // Загружаем текущий список образований из SharedPreferences
        loadLangData();

        // Добавляем новое образование в список
        langList.add(lang);

        // Сохраняем обновленный список образований в SharedPreferences
        saveLangData();

        // Очищаем поля ввода
        langET.setText("");
        langDescET.setText("");

        // Обновляем адаптер (если он был установлен)
        if (langAdapter != null) {
            langAdapter.notifyDataSetChanged();
        }
    }

    // Метод для сохранения данных в SharedPreferences
    private void saveLangData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Преобразуем список образований в строку JSON
        Gson gson = new Gson();
        langListJson = gson.toJson(langList);

        // Сохраняем строку JSON в SharedPreferences
        editor.putString("lang_list", langListJson);
        editor.apply();
    }

    // Метод для загрузки сохраненных образований из SharedPreferences
    private void loadLangData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);

        // Получаем строку JSON с образованиями
        langListJson = sharedPreferences.getString("lang_list", "");

        if (!langListJson.isEmpty()) {
            // Если строка JSON не пустая, преобразуем её обратно в список Education
            Gson gson = new Gson();
            Type langListType = new TypeToken<List<Lang>>() {}.getType();
            langList = gson.fromJson(langListJson, langListType);
        }
    }

    private void returnToLangFragment() {
        getParentFragmentManager().popBackStack();
    }

}