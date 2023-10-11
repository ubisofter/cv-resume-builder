package app.cvresume.android.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import app.cvresume.android.data.SkillEntity;
import app.cvresume.android.fragments.profile.skill.AddSkillFragment;
import app.cvresume.android.fragments.profile.skill.SkillAdapter;

public class SkillFragment extends Fragment {

    private List<SkillEntity> skillList = new ArrayList<>();
    private RecyclerView skillRV;
    private SkillAdapter skillAdapter;
    private ConstraintLayout addSkillBtn;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skill, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        addSkillBtn = view.findViewById(R.id.addSkillBtn);

        skillRV = view.findViewById(R.id.skillRV);
        setupRecyclerView();

        addSkillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddSkillFragment();
            }
        });

        loadSkillData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void setupRecyclerView() {
        skillRV.setLayoutManager(new LinearLayoutManager(getContext()));
        skillAdapter = new SkillAdapter(skillList, appDatabase);
        skillRV.setAdapter(skillAdapter);
    }

    private void openAddSkillFragment() {
        AddSkillFragment addSkillFragment = new AddSkillFragment();
        addSkillFragment.setSkillAdapter(skillAdapter);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addSkillFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadSkillData() {
        appDatabase.skillDao().getAllSkills().observe(getViewLifecycleOwner(), new Observer<List<SkillEntity>>() {
            @Override
            public void onChanged(List<SkillEntity> skills) {
                skillList.clear();
                skillList.addAll(skills);
                skillAdapter.notifyDataSetChanged();
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
        loadSkillData();
    }
}