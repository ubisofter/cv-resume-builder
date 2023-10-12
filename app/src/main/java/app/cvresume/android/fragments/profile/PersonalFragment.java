package app.cvresume.android.fragments.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    private double progress, numberOfFieldsFilled;

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

        loadResumeData();
        manageGender();

        savePersonalBtn.setOnClickListener(view1 -> saveResumeData());

        return view;
    }

    private void saveResumeData() {
        final String city = cityET.getText().toString();
        final String birthDate = dayET.getText() + "." + monthET.getText() + "." + yearET.getText();
        final String famStatus = famStatusET.getText().toString();
        final boolean children = childrenCB.isChecked();
        final String citizenship = citizenshipET.getText().toString();
        final boolean gender = getGender();
        final String eduGrade = eduGradeET.getText().toString();
        final boolean army = armyCB.isChecked();
        final boolean driveCat = driveCatCB.isChecked();
        final String personalQualities = personalQualitiesET.getText().toString();
        final String profSkills = profSkillsET.getText().toString();
        final boolean medBook = medBookCB.isChecked();
        final String about = aboutET.getText().toString();

        numberOfFieldsFilled = calculateNumberOfFieldsFilled();

        executor.execute(() -> {
            PersonalInfoEntity personalInfo = personalInfoDao.getPersonalInfo();

            if (personalInfo == null) {
                personalInfo = new PersonalInfoEntity();
            }

            progress = numberOfFieldsFilled / 10 * 100;

            personalInfo.setProgress((int) Math.round(progress));
            personalInfo.setCity(city);
            personalInfo.setBirthDate(birthDate);
            personalInfo.setFamStatus(famStatus);
            personalInfo.setChildren(children);
            personalInfo.setCitizenship(citizenship);
            personalInfo.setGender(gender);
            personalInfo.setEduGrade(eduGrade);
            personalInfo.setArmy(army);
            personalInfo.setDriveCat(driveCat);
            personalInfo.setPersonalQualities(personalQualities);
            personalInfo.setProfSkills(profSkills);
            personalInfo.setMedBook(medBook);
            personalInfo.setAbout(about);

//            if (city.isEmpty() || birthDate.isEmpty() || famStatus.isEmpty()
//                    || citizenship.isEmpty() || eduGrade.isEmpty()
//                    || personalQualities.isEmpty() || profSkills.isEmpty()
//                    || about.isEmpty()) {
//                requireActivity().runOnUiThread(() -> {
//                    Toast.makeText(requireContext(), "Пожалуйста, заполните все обязательные поля", Toast.LENGTH_SHORT).show();
//                });
//                return;
//            }

            personalInfoDao.insertPersonalInfo(personalInfo);

            requireActivity().runOnUiThread(() -> {
                getParentFragmentManager().popBackStack();
            });
        });
    }

    private void loadResumeData() {
        executor.execute(() -> {
            PersonalInfoEntity personalInfo = personalInfoDao.getPersonalInfo();
            if (personalInfo != null) {
                requireActivity().runOnUiThread(() -> {
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
                });
            }
        });
    }

    private void manageGender(){
        genderMCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                genderFCB.setChecked(false);
            }
        });

        genderFCB.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                genderMCB.setChecked(false);
            }
        });
    }

    private Boolean getGender(){
        return !genderMCB.isChecked();
    }

    private long calculateNumberOfFieldsFilled() {
        int filledFields = 0;
        EditText[] editTexts = {cityET, dayET, monthET, yearET, famStatusET, citizenshipET, eduGradeET, personalQualitiesET, profSkillsET, aboutET};
        for (EditText editText : editTexts) {
            if (!editText.getText().toString().isEmpty()) {
                filledFields++;
            }
        }
        return filledFields;
    }
}