package app.cvresume.android.fragments.profile.lang;

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
import com.shuhart.stepview.StepView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.ExperienceEntity;
import app.cvresume.android.data.LangEntity;
import app.cvresume.android.fragments.profile.experience.ExperienceAdapter;
import app.cvresume.android.fragments.profile.lang.LangAdapter;
import app.cvresume.android.models.Lang;

public class AddLangFragment extends Fragment {

    private EditText langET, langDescET;
    private StepView langLvl;
    int sLvl;
    private AppCompatButton saveLangBtn;
    private AppDatabase appDatabase;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private LangAdapter langAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void setLangAdapter(LangAdapter adapter) {
        langAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_lang, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        langET = view.findViewById(R.id.langET);
        langLvl = view.findViewById(R.id.langLvl);
        langDescET = view.findViewById(R.id.langDescET);
        saveLangBtn = view.findViewById(R.id.saveLangBtn);

        langLvl.setStepsNumber(5);

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
            }
        });

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void addLang() {

        String lLangET = langET.getText().toString();
        int lLangLvl = sLvl;
        String lLangDescET = langDescET.getText().toString();

        if (lLangET.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        final LangEntity langEntity = new LangEntity(
                lLangET,
                lLangLvl,
                lLangDescET
        );

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.langDao().insertLang(langEntity);

                Log.d("AddLangFragment", "Lang added successfully");

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        langET.setText("");
                        langDescET.setText("");

                        if (langAdapter != null) {
                            langAdapter.notifyDataSetChanged();
                        }

                        getParentFragmentManager().popBackStack();
                    }
                });
            }
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