package app.cvresume.android.fragments.profile.skill;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.LangEntity;
import app.cvresume.android.data.SkillEntity;
import app.cvresume.android.fragments.profile.lang.LangAdapter;
import app.cvresume.android.fragments.profile.skill.SkillAdapter;
import app.cvresume.android.models.Skill;

public class AddSkillFragment extends Fragment {

    private EditText skillET, skillDescET;
    private StepView skillLvl;
    int sLvl;
    private AppCompatButton saveSkillBtn;
    private AppDatabase appDatabase;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private SkillAdapter skillAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void setSkillAdapter(SkillAdapter adapter) {
        skillAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_skill, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        skillET = view.findViewById(R.id.skillET);
        skillLvl = view.findViewById(R.id.skillLvl);
        skillDescET = view.findViewById(R.id.skillDescET);
        saveSkillBtn = view.findViewById(R.id.saveSkillBtn);

        skillLvl.setStepsNumber(5);
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

        saveSkillBtn.setOnClickListener(v -> addSkill());

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);
        return view;
    }

    private void addSkill() {

        String lSkillET = skillET.getText().toString();
        int lSkillLvl = sLvl;
        String lSkillDescET = skillDescET.getText().toString();

        if (lSkillET.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        final SkillEntity skillEntity = new SkillEntity(
                lSkillET,
                lSkillLvl,
                lSkillDescET
        );
        executor.execute(() -> {
            appDatabase.skillDao().insertSkill(skillEntity);

            Log.d("AddSkillFragment", "Skill added successfully");

            requireActivity().runOnUiThread(() -> {
                skillET.setText("");
                skillDescET.setText("");

                if (skillAdapter != null) {
                    skillAdapter.notifyDataSetChanged();
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