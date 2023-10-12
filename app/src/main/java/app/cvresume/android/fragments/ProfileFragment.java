package app.cvresume.android.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.adapters.ProfileAdapter;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.CourseDao;
import app.cvresume.android.data.EducationDao;
import app.cvresume.android.data.ExperienceDao;
import app.cvresume.android.data.LangDao;
import app.cvresume.android.data.MainInfoDao;
import app.cvresume.android.data.MainInfoEntity;
import app.cvresume.android.data.PersonalInfoDao;
import app.cvresume.android.data.PersonalInfoEntity;
import app.cvresume.android.data.SkillDao;
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
    int[] sectionsProgress;
    int[] resumeIcons = {R.drawable.ic_main_info, R.drawable.ic_personal_info, R.drawable.ic_exp, R.drawable.ic_edu, R.drawable.ic_cou, R.drawable.ic_lang, R.drawable.ic_skill};

    AppCompatActivity activity;
    BottomNavigationView bnv;
    AppCompatButton selectTemplateButton;
    MainInfoDao mInfoDao; PersonalInfoDao pInfoDao; ExperienceDao exDao; EducationDao edDao; CourseDao cDao; SkillDao sDao; LangDao lDao;
    MainInfoEntity mInfo; PersonalInfoEntity pInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppDatabase appDatabase = AppDatabase.getInstance(requireContext());
        mInfoDao = appDatabase.mainInfoDao();
        pInfoDao = appDatabase.personalInfoDao();
        exDao = appDatabase.experienceDao();
        edDao = appDatabase.educationDao();
        cDao = appDatabase.courseDao();
        sDao = appDatabase.skillDao();
        lDao = appDatabase.langDao();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);

        Executors.newSingleThreadExecutor().execute(() -> {
            mInfo = mInfoDao.getMainInfo();
            int progressMI = mInfo != null ? mInfo.getProgress() : 0;
            Log.d("progressMI", String.valueOf(progressMI));

            pInfo = pInfoDao.getPersonalInfo();
            int progressPI = pInfo != null ? pInfo.getProgress() : 0;
            Log.d("progressPI", String.valueOf(progressPI));

            int exInt = exDao.countExperiences();
            double progressEx = exInt * 50;
            Log.d("progressEx", String.valueOf((int) Math.round(progressEx)));

            int edInt = edDao.countEducations();
            double progressEd = edInt * 34;
            Log.d("progressEd", String.valueOf((int) Math.round(progressEd)));

            int cInt = cDao.countCourses();
            double progressC = cInt * 50;
            Log.d("progressC", String.valueOf((int) Math.round(progressC)));

            int lInt = lDao.countLangs();
            double progressL = lInt * 50;
            Log.d("progressL", String.valueOf((int) Math.round(progressL)));

            int sInt = sDao.countSkills();
            double progressS = sInt * 50;
            Log.d("progressS", String.valueOf((int) Math.round(progressS)));

            requireActivity().runOnUiThread(() -> {
                sectionsRecycler = view.findViewById(R.id.sectionsRecycler);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                sectionsRecycler.setLayoutManager(layoutManager);

                resumeSections = getResources().getStringArray(R.array.resume_sections);
                sectionsProgress = new int[]{progressMI, progressPI, (int) Math.round(progressEx), (int) Math.round(progressEd), (int) Math.round(progressC), (int) Math.round(progressL), (int) Math.round(progressS)};

                ProfileAdapter adapter = new ProfileAdapter(resumeSections, resumeIcons, sectionsProgress);
                sectionsRecycler.setAdapter(adapter);

                adapter.setOnItemClickListener(position -> {
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
                });
            });
        });

        selectTemplateButton = view.findViewById(R.id.selectTemplateButton);
        selectTemplateButton.setOnClickListener(v -> {
            TemplatesFragment templatesFragment = new TemplatesFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, templatesFragment);
            transaction.addToBackStack(null);
            transaction.commit();

            Toast.makeText(getContext(), "Выберите шаблон из списка", Toast.LENGTH_LONG).show();

            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

            MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.nav_templates);
            menuItem.setChecked(true);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bnv.setVisibility(View.VISIBLE);
    }

}