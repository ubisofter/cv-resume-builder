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
import app.cvresume.android.fragments.profile.hobby.AddHobbyFragment;
import app.cvresume.android.fragments.profile.hobby.HobbyAdapter;
import app.cvresume.android.models.Hobby;

public class HobbyFragment extends Fragment {

    private List<Hobby> hobbyList = new ArrayList<>(); // Список образований
    private RecyclerView hobbyRV;
    private HobbyAdapter hobbyAdapter;
    private AppCompatButton addHobbyBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String hobbyListJson;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hobby, container, false);

        // Находим элементы для ввода
        addHobbyBtn = view.findViewById(R.id.addHobbyBtn);

        hobbyRV = view.findViewById(R.id.hobbyRV);
        setupRecyclerView();

        addHobbyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddHobbyFragment();
            }
        });

        loadHobbyData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        bnv.setVisibility(View.GONE);

        activity.getSupportActionBar().setTitle("Хобби");

        return view;
    }

    private void setupRecyclerView() {
        hobbyRV.setLayoutManager(new LinearLayoutManager(getContext()));
        hobbyAdapter = new HobbyAdapter(hobbyList);
        hobbyRV.setAdapter(hobbyAdapter);
    }

    private void openAddHobbyFragment() {
        // Создаем и отображаем фрагмент для ввода образования
        AddHobbyFragment addHobbyFragment = new AddHobbyFragment();
        addHobbyFragment.setHobbyAdapter(hobbyAdapter); // Передаем адаптер в AddEducationFragment
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addHobbyFragment);
        transaction.addToBackStack(null); // Добавляем фрагмент в стек, чтобы можно было вернуться к EducationFragment
        transaction.commit();
    }

    // Метод для удаления образования по индексу
    private void removeHobby(int position) {
        if (position >= 0 && position < hobbyList.size()) {
            hobbyList.remove(position);
            hobbyAdapter.notifyDataSetChanged(); // Обновить адаптер
            saveHobbyData(); // Сохраняем образования после удаления
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
            List<Hobby> loadedHobbyList = gson.fromJson(hobbyListJson, hobbyListType);

            // Очищаем существующий список и добавляем загруженные образования
            hobbyList.clear();
            hobbyList.addAll(loadedHobbyList);

            // Обновляем адаптер после загрузки данных
            hobbyAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveHobbyData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveHobbyData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveHobbyData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHobbyData();
        activity.getSupportActionBar().setTitle("Хобби");
    }
}