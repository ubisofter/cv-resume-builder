package app.cvresume.android.fragments.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.PersonalInfoDao;
import app.cvresume.android.data.PersonalInfoEntity;

public class PersonalFragment extends Fragment {

    private AppDatabase appDatabase;
    private EditText cityET, dayET, monthET, yearET, famStatusET, citizenshipET, eduGradeET, personalQualitiesET, profSkillsET, aboutET;
    private CheckBox childrenCB, armyCB, genderMCB, genderFCB, driveCatCB, medBookCB;
    private Boolean children, gender, medBook;
    private PersonalInfoDao personalInfoDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppCompatButton savePersonalBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        cityET = view.findViewById(R.id.cityET);
        dayET = view.findViewById(R.id.dayET);
        monthET = view.findViewById(R.id.monthET);
        yearET = view.findViewById(R.id.yearET);
        famStatusET = view.findViewById(R.id.famStatusET);
        childrenCB = view.findViewById(R.id.childrenCB);
        citizenshipET = view.findViewById(R.id.citizenshipET);
        genderMCB = view.findViewById(R.id.genderMCB);
        genderFCB = view.findViewById(R.id.genderFCB);
        eduGradeET = view.findViewById(R.id.eduGradeET);
        armyCB = view.findViewById(R.id.armyCB);
        driveCatCB = view.findViewById(R.id.driveCatCB);
        personalQualitiesET = view.findViewById(R.id.personalQualitiesET);
        profSkillsET = view.findViewById(R.id.profSkillsET);
        aboutET = view.findViewById(R.id.aboutET);
        medBookCB = view.findViewById(R.id.medBookCB);

        savePersonalBtn = view.findViewById(R.id.savePersonalBtn);

        personalInfoDao = AppDatabase.getInstance(requireContext()).personalInfoDao();
        savePersonalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResumeData();
            }
        });

        loadResumeData();
        manageGender();

        return view;
    }

    private void saveResumeData() {
        PersonalInfoEntity personalInfo = new PersonalInfoEntity();
        personalInfo.setCity(cityET.getText().toString());
        personalInfo.setBirthDate(dayET.getText() + "." + monthET.getText() + "." + yearET.getText());
        personalInfo.setFamStatus(famStatusET.getText().toString());
        personalInfo.setChildren(childrenCB.isChecked());
        personalInfo.setCitizenship(citizenshipET.getText().toString());
        personalInfo.setGender(getGender());
        personalInfo.setEduGrade(eduGradeET.getText().toString());
        personalInfo.setArmy(armyCB.isChecked());
        personalInfo.setDriveCat(driveCatCB.isChecked());
        personalInfo.setPersonalQualities(personalQualitiesET.getText().toString());
        personalInfo.setProfSkills(profSkillsET.getText().toString());
        personalInfo.setMedBook(medBookCB.isChecked());
        personalInfo.setAbout(aboutET.getText().toString());

        String birthDate = dayET.getText().toString() + "." + monthET.getText().toString() + "." + yearET.getText().toString();

        if (personalInfo.getCity().isEmpty() || birthDate.isEmpty() || personalInfo.getFamStatus().isEmpty()
                || personalInfo.getCitizenship().isEmpty() || personalInfo.getEduGrade().isEmpty()
                || personalInfo.getPersonalQualities().isEmpty() || personalInfo.getProfSkills().isEmpty()
                || personalInfo.getAbout().isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все обязательные поля", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            personalInfoDao.insertPersonalInfo(personalInfo);

            Log.d("PersonalFragment", "PersonalInfoEntity added successfully");
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getParentFragmentManager().popBackStack();
                }
            });
        });
    }

    private void loadResumeData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                PersonalInfoEntity personalInfo = personalInfoDao.getPersonalInfo();
                if (personalInfo != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cityET.setText(personalInfo.getCity());
                            String birthDate = personalInfo.getBirthDate();
                            String[] dateParts = birthDate.split("\\.");
                            if (dateParts.length == 3) {
                                dayET.setText(dateParts[0]);
                                monthET.setText(dateParts[1]);
                                yearET.setText(dateParts[2]);
                            }
                            famStatusET.setText(personalInfo.getFamStatus());
                            childrenCB.setChecked(personalInfo.isChildren());
                            citizenshipET.setText(personalInfo.getCitizenship());
                            genderFCB.setChecked(personalInfo.isGender());
                            genderMCB.setChecked(!personalInfo.isGender());
                            eduGradeET.setText(personalInfo.getEduGrade());
                            armyCB.setChecked(personalInfo.isArmy());
                            driveCatCB.setChecked(personalInfo.isDriveCat());
                            personalQualitiesET.setText(personalInfo.getPersonalQualities());
                            profSkillsET.setText(personalInfo.getProfSkills());
                            medBookCB.setChecked(personalInfo.isMedBook());
                            aboutET.setText(personalInfo.getAbout());
                        }
                    });
                }
            }
        });
    }

    private void manageGender(){
        if (genderMCB.isChecked()){
            genderFCB.setChecked(false);
        } else if (genderFCB.isChecked()) {
            genderMCB.setChecked(false);
        } else {
            genderFCB.setChecked(false);
            genderMCB.setChecked(true);
        }
    }

    private Boolean getGender(){
        if (genderMCB.isChecked()){
            genderFCB.setChecked(false);
            gender = false;
        } else if (genderFCB.isChecked()) {
            genderMCB.setChecked(false);
            gender = true;
        }
        return gender;
    }
}