package app.cvresume.android.fragments.profile.hobby;

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
import app.cvresume.android.fragments.profile.hobby.HobbyAdapter;
import app.cvresume.android.models.Hobby;

public class AddHobbyFragment extends Fragment {
    private EditText hobbyET;
    private Button saveHobbyBtn;
    private List<Hobby> hobbyList = new ArrayList<>();
    private HobbyAdapter hobbyAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String hobbyListJson;

    // Метод для установки адаптера
    public void setHobbyAdapter(HobbyAdapter adapter) {
        hobbyAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_hobby, container, false);

        // Находим элементы для ввода
        hobbyET = view.findViewById(R.id.hobbyET);
        saveHobbyBtn = view.findViewById(R.id.saveHobbyBtn);

        saveHobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHobby();
                returnToHobbyFragment();
            }
        });

        return view;
    }

    private void addHobby() {
        // Получаем данные с полей ввода
        String sHobby = hobbyET.getText().toString();

        // Создаем новый объект Education
        Hobby hobby = new Hobby(sHobby);

        // Загружаем текущий список образований из SharedPreferences
        loadHobbyData();

        // Добавляем новое образование в список
        hobbyList.add(hobby);

        // Сохраняем обновленный список образований в SharedPreferences
        saveHobbyData();

        // Очищаем поля ввода
        hobbyET.setText("");

        // Обновляем адаптер (если он был установлен)
        if (hobbyAdapter != null) {
            hobbyAdapter.notifyDataSetChanged();
        }
    }

    // Метод для сохранения данных в SharedPreferences
    private void saveHobbyData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Преобразуем список образований в строку JSON
        Gson gson = new Gson();
        hobbyListJson = gson.toJson(hobbyList);

        // Сохраняем строку JSON в SharedPreferences
        editor.putString("hobby_list", hobbyListJson);
        editor.apply();
    }

    // Метод для загрузки сохраненных образований из SharedPreferences
    private void loadHobbyData() {
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);

        // Получаем строку JSON с образованиями
        hobbyListJson = sharedPreferences.getString("hobby_list", "");

        if (!hobbyListJson.isEmpty()) {
            // Если строка JSON не пустая, преобразуем её обратно в список Education
            Gson gson = new Gson();
            Type hobbyListType = new TypeToken<List<Hobby>>() {}.getType();
            hobbyList = gson.fromJson(hobbyListJson, hobbyListType);
        }
    }

    private void returnToHobbyFragment() {
        getParentFragmentManager().popBackStack();
    }

}