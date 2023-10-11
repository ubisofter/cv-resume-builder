package app.cvresume.android.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.cvresume.android.R;
import app.cvresume.android.adapters.ResumeAdapter;
import app.cvresume.android.fragments.profile.CourseFragment;
import app.cvresume.android.fragments.profile.EducationFragment;
import app.cvresume.android.fragments.profile.ExperienceFragment;
import app.cvresume.android.fragments.profile.LangFragment;
import app.cvresume.android.fragments.profile.MainFragment;
import app.cvresume.android.fragments.profile.PersonalFragment;
import app.cvresume.android.fragments.profile.SkillFragment;

public class ProfileFragment extends Fragment {

    private RecyclerView sectionsRecycler;
    private String[] resumeSections;
    int[] resumeIcons = {R.drawable.ic_main_info, R.drawable.ic_personal_info, R.drawable.ic_exp, R.drawable.ic_edu, R.drawable.ic_cou, R.drawable.ic_lang, R.drawable.ic_skill};

    AppCompatActivity activity;
    BottomNavigationView bnv;
    SharedPreferences sharedPreferences;
    AppCompatButton selectTemplateButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);

        sectionsRecycler = view.findViewById(R.id.sectionsRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        sectionsRecycler.setLayoutManager(layoutManager);

        resumeSections = getResources().getStringArray(R.array.resume_sections);

        ResumeAdapter adapter = new ResumeAdapter(resumeSections, resumeIcons);
        sectionsRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new ResumeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Обработка нажатия на элемент списка
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new MainFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                    case 1:
                        fragment = new PersonalFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                    case 2:
                        fragment = new ExperienceFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                    case 3:
                        fragment = new EducationFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                    case 4:
                        fragment = new CourseFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                    case 5:
                        fragment = new LangFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                    case 6:
                        fragment = new SkillFragment();
                        bnv.setVisibility(View.GONE);
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        selectTemplateButton = view.findViewById(R.id.selectTemplateButton);
        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        selectTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new TemplatesFragment());
                transaction.addToBackStack(null);
                transaction.commit();
                bnv.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bnv.setVisibility(View.VISIBLE);
    }

}