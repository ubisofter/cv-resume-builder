package app.cvresume.android.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import app.cvresume.android.data.EducationEntity;
import app.cvresume.android.fragments.profile.education.AddEducationFragment;
import app.cvresume.android.fragments.profile.education.EducationAdapter;

public class EducationFragment extends Fragment {

    private List<EducationEntity> educationList = new ArrayList<>();
    private RecyclerView educationRV;
    private EducationAdapter educationAdapter;
    private ConstraintLayout addEducationBtn;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        addEducationBtn = view.findViewById(R.id.addEducationBtn);

        educationRV = view.findViewById(R.id.educationRV);
        setupRecyclerView();

        addEducationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEducationFragment();
            }
        });

        loadEducationData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void setupRecyclerView() {
        educationRV.setLayoutManager(new LinearLayoutManager(getContext()));
        educationAdapter = new EducationAdapter(educationList, appDatabase);
        educationRV.setAdapter(educationAdapter);
    }

    private void openAddEducationFragment() {
        AddEducationFragment addEducationFragment = new AddEducationFragment();
        addEducationFragment.setEducationAdapter(educationAdapter);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addEducationFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadEducationData() {
        appDatabase.educationDao().getAllEducations().observe(getViewLifecycleOwner(), new Observer<List<EducationEntity>>() {
            @Override
            public void onChanged(List<EducationEntity> educations) {
                educationList.clear();
                educationList.addAll(educations);
                educationAdapter.notifyDataSetChanged();
            }
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
        loadEducationData();
    }
}
