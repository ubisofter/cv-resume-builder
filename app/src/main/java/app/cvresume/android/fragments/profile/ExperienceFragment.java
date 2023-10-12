package app.cvresume.android.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.ExperienceEntity;
import app.cvresume.android.fragments.profile.experience.AddExperienceFragment;
import app.cvresume.android.fragments.profile.experience.ExperienceAdapter;

public class ExperienceFragment extends Fragment {

    private List<ExperienceEntity> experienceList = new ArrayList<>();
    private RecyclerView experienceRV;
    private ExperienceAdapter experienceAdapter;
    private ConstraintLayout addExperienceBtn;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        addExperienceBtn = view.findViewById(R.id.addExperienceBtn);

        experienceRV = view.findViewById(R.id.experienceRV);
        setupRecyclerView();

        addExperienceBtn.setOnClickListener(v -> openAddExperienceFragment());

        loadExperienceData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void setupRecyclerView() {
        experienceRV.setLayoutManager(new LinearLayoutManager(getContext()));
        experienceAdapter = new ExperienceAdapter(experienceList, appDatabase);
        experienceRV.setAdapter(experienceAdapter);
    }

    private void openAddExperienceFragment() {
        AddExperienceFragment addExperienceFragment = new AddExperienceFragment();
        addExperienceFragment.setExperienceAdapter(experienceAdapter);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addExperienceFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadExperienceData() {
        appDatabase.experienceDao().getAllExperiences().observe(getViewLifecycleOwner(), experiences -> {
            experienceList.clear();
            experienceList.addAll(experiences);
            experienceAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onStop() {
        super.onStop();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadExperienceData();
    }
}
