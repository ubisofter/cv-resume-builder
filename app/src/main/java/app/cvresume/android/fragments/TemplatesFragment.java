package app.cvresume.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.adapters.TemplatesAdapter;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.CourseEntity;
import app.cvresume.android.data.EducationEntity;
import app.cvresume.android.data.ExperienceEntity;
import app.cvresume.android.data.LangEntity;
import app.cvresume.android.data.MainInfoEntity;
import app.cvresume.android.data.PersonalInfoEntity;
import app.cvresume.android.data.SkillEntity;

public class TemplatesFragment extends Fragment {

    private RecyclerView templatesRecyclerView;
    AppDatabase appDatabase;
    MainInfoEntity mainInfo;
    PersonalInfoEntity personalInfo;
    LiveData<List<ExperienceEntity>> allExperiencesLiveData;
    LiveData<List<EducationEntity>> allEducationsLiveData;
    LiveData<List<CourseEntity>> allCoursesLiveData;
    LiveData<List<LangEntity>> allLangsLiveData;
    LiveData<List<SkillEntity>> allSkillsLiveData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_templates, container, false);

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            appDatabase = AppDatabase.getInstance(requireContext());
            mainInfo = appDatabase.mainInfoDao().getMainInfo();
            personalInfo = appDatabase.personalInfoDao().getPersonalInfo();

            allExperiencesLiveData = appDatabase.experienceDao().getAllExperiences();
            allEducationsLiveData = appDatabase.educationDao().getAllEducations();
            allCoursesLiveData = appDatabase.courseDao().getAllCourses();
            allLangsLiveData = appDatabase.langDao().getAllLangs();
            allSkillsLiveData = appDatabase.skillDao().getAllSkills();
        });

        templatesRecyclerView = view.findViewById(R.id.templatesRV);
        templatesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        TemplatesAdapter adapter = new TemplatesAdapter(requireContext(), new TemplatesAdapter.OnTemplateClickListener() {
            @Override
            public void onTemplateClick(int position) {
                openPreviewFragment(position);
            }
        });

        templatesRecyclerView.setAdapter(adapter);

        return view;
    }

    private void openPreviewFragment(int position) {
        Bundle args = new Bundle();
        args.putInt("templatePosition", position);

        args.putString("surname", mainInfo.getSurname());
        args.putString("name", mainInfo.getName());
        args.putString("secondName", mainInfo.getSecondName());
        args.putString("email", mainInfo.getEmail());
        args.putString("phoneNum", mainInfo.getPhoneNum());
        args.putString("resumeTitle", mainInfo.getResumeTitle());
        args.putString("busy", mainInfo.getBusy());
        args.putString("salary", mainInfo.getSalary());
        args.putString("schedule", mainInfo.getSchedule());

        args.putString("city", personalInfo.getCity());
        args.putString("birthDate", personalInfo.getBirthDate());
        args.putString("famStatus", personalInfo.getFamStatus());
        args.putBoolean("children", personalInfo.isChildren());
        args.putString("citizenship", personalInfo.getCitizenship());
        args.putBoolean("gender", personalInfo.isGender());
        args.putString("eduGrade", personalInfo.getEduGrade());
        args.putBoolean("army", personalInfo.isArmy());
        args.putBoolean("driveCat", personalInfo.isDriveCat());
        args.putString("personalQualities", personalInfo.getPersonalQualities());
        args.putString("profSkills", personalInfo.getProfSkills());
        args.putBoolean("medBook", personalInfo.isMedBook());
        args.putString("about", personalInfo.getAbout());


        allExperiencesLiveData.observe(getViewLifecycleOwner(), experienceEntities -> {
            ArrayList<String> experienceDataList = new ArrayList<>();
            for (ExperienceEntity entity : experienceEntities) {
                String experienceData = entity.id + "\n"
                        + entity.experienceEmployer + "\n"
                        + entity.experiencePosition + "\n"
                        + entity.experienceDuration + "\n"
                        + entity.experienceWorkcity + "\n"
                        + entity.experienceWorkdesc + "\n";
                experienceDataList.add(experienceData);
            }
            args.putStringArrayList("experienceDataList", experienceDataList);
        });


        allEducationsLiveData.observe(getViewLifecycleOwner(), educationEntities -> {
            ArrayList<String> educationDataList = new ArrayList<>();
            for (EducationEntity entity : educationEntities) {
                String educationData = entity.id + "\n"
                        + entity.educationDegree + "\n"
                        + entity.educationUniversity + "\n"
                        + entity.educationYears + "\n"
                        + entity.educationYears + "\n"
                        + entity.educationDesc + "\n";
                educationDataList.add(educationData);
            }
            args.putStringArrayList("educationDataList", educationDataList);
        });


        allCoursesLiveData.observe(getViewLifecycleOwner(), courseEntities -> {
            ArrayList<String> courseDataList = new ArrayList<>();
            for (CourseEntity entity : courseEntities) {
                String courseData = entity.id + "\n"
                        + entity.courseName + "\n"
                        + entity.courseSchool + "\n"
                        + entity.coursePeriod + "\n"
                        + entity.courseDescription + "\n";
                courseDataList.add(courseData);
            }
            args.putStringArrayList("courseDataList", courseDataList);
        });


        allLangsLiveData.observe(getViewLifecycleOwner(), langEntities -> {
            ArrayList<String> langDataList = new ArrayList<>();
            for (LangEntity entity : langEntities) {
                String langData = entity.id + "\n"
                        + entity.langLang + "\n"
                        + entity.langLvl + "\n"
                        + entity.langDesc + "\n";
                langDataList.add(langData);
            }
            args.putStringArrayList("langDataList", langDataList);
        });


        allSkillsLiveData.observe(getViewLifecycleOwner(), skillEntities -> {
            ArrayList<String> skillDataList = new ArrayList<>();
            for (SkillEntity entity : skillEntities) {
                String skillData = entity.id + "\n"
                        + entity.skillSkill + "\n"
                        + entity.skillLvl + "\n"
                        + entity.skillDesc;
                skillDataList.add(skillData);
            }
            args.putStringArrayList("skillDataList", skillDataList);
        });

        PreviewFragment previewFragment = PreviewFragment.newInstance(args);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, previewFragment)
                .addToBackStack(null)
                .commit();

//        Log.d("NAME DATA", Objects.requireNonNull(args.getString("name")));
//        Log.d("CHILDREN DATA", Objects.requireNonNull(String.valueOf(args.getBoolean("children"))));
//        Log.d("EXPERIENCE DATA", Objects.requireNonNull(args.getStringArrayList("experienceDataList").toString()));
    }

}