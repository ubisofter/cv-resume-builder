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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.CourseEntity;
import app.cvresume.android.data.ExperienceEntity;
import app.cvresume.android.fragments.profile.course.CourseAdapter;
import app.cvresume.android.fragments.profile.education.EducationAdapter;
import app.cvresume.android.models.Education;
import app.cvresume.android.models.Experience;

public class AddExperienceFragment extends Fragment {

    private EditText positionET, employerET, experienceCityET, workDateBeginET, workDateEndET, workDescET;
    private AppCompatButton saveExperienceBtn;
    private AppDatabase appDatabase;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private ExperienceAdapter experienceAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void setExperienceAdapter(ExperienceAdapter adapter) {
        experienceAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_experience, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        positionET = view.findViewById(R.id.positionET);
        employerET = view.findViewById(R.id.employerET);
        experienceCityET = view.findViewById(R.id.experienceCityET);
        workDateBeginET = view.findViewById(R.id.workDateBeginET);
        workDateEndET = view.findViewById(R.id.workDateEndET);
        workDescET = view.findViewById(R.id.workDescET);
        saveExperienceBtn = view.findViewById(R.id.saveExperienceBtn);

        saveExperienceBtn.setOnClickListener(v -> addExperience());

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void addExperience() {
        String wPosition = positionET.getText().toString();
        String wEmployer = employerET.getText().toString();
        String wStart = workDateBeginET.getText().toString();
        String wEnd = workDateEndET.getText().toString();
        String wCity = experienceCityET.getText().toString();
        String wDesc = workDescET.getText().toString();

        if (wPosition.isEmpty() || wEmployer.isEmpty() || wStart.isEmpty() || wEnd.isEmpty() || wCity.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        final ExperienceEntity experienceEntity = new ExperienceEntity(
                wPosition,
                wEmployer,
                wCity,
                wStart + " - " + wEnd,
                wDesc
        );

        executor.execute(() -> {
            appDatabase.experienceDao().insertExperience(experienceEntity);

            Log.d("AddExperienceFragment", "Experience added successfully");

            requireActivity().runOnUiThread(() -> {
                positionET.setText("");
                employerET.setText("");
                experienceCityET.setText("");
                workDateBeginET.setText("");
                workDateEndET.setText("");
                workDescET.setText("");

                if (experienceAdapter != null) {
                    experienceAdapter.notifyDataSetChanged();
                }

                getParentFragmentManager().popBackStack();
            });
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bnv.setVisibility(View.GONE);
    }
}

