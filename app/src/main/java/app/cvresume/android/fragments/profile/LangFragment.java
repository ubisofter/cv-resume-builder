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
import app.cvresume.android.fragments.profile.lang.AddLangFragment;
import app.cvresume.android.fragments.profile.lang.LangAdapter;
import app.cvresume.android.models.Lang;

public class LangFragment extends Fragment {

    private List<Lang> langList = new ArrayList<>(); // Список образований
    private RecyclerView langRV;
    private LangAdapter langAdapter;
    private AppCompatButton addLangBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String langListJson;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lang, container, false);

        // Находим элементы для ввода
        addLangBtn = view.findViewById(R.id.addLangBtn);

        langRV = view.findViewById(R.id.langRV);
        setupRecyclerView();

        addLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddLangFragment();
            }
        });

        loadLangData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Языки");

        return view;
    }

    private void setupRecyclerView() {
        langRV.setLayoutManager(new LinearLayoutManager(getContext()));
        langAdapter = new LangAdapter(langList);
        langRV.setAdapter(langAdapter);
    }

    private void openAddLangFragment() {
        // Создаем и отображаем фрагмент для ввода образования
        AddLangFragment addLangFragment = new AddLangFragment();
        addLangFragment.setLangAdapter(langAdapter); // Передаем адаптер в AddEducationFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addLangFragment);
        transaction.addToBackStack(null); // Добавляем фрагмент в стек, чтобы можно было вернуться к EducationFragment
        transaction.commit();
    }

    // Метод для удаления образования по индексу
    private void removeLang(int position) {
        if (position >= 0 && position < langList.size()) {
            langList.remove(position);
            langAdapter.notifyDataSetChanged(); // Обновить адаптер
            saveLangData(); // Сохраняем образования после удаления
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
            List<Lang> loadedLangList = gson.fromJson(langListJson, langListType);

            // Очищаем существующий список и добавляем загруженные образования
            langList.clear();
            langList.addAll(loadedLangList);

            // Обновляем адаптер после загрузки данных
            langAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveLangData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveLangData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveLangData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLangData();
        activity.getSupportActionBar().setTitle("Языки");
    }
}