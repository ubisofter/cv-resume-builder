package app.cvresume.android.fragments.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.MainInfoDao;
import app.cvresume.android.data.MainInfoEntity;

public class MainFragment extends Fragment {

    private AppDatabase appDatabase;
    private EditText surnameET, nameET, secondNameET, emailET, phoneNumET, resumeTitleET, busyET, salaryET, scheduleET;
    private MainInfoDao mainInfoDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppCompatButton saveMainBtn;
    private double progress, numberOfFieldsFilled;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_info, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        surnameET = view.findViewById(R.id.surnameET);
        nameET = view.findViewById(R.id.nameET);
        secondNameET = view.findViewById(R.id.secondNameET);
        emailET = view.findViewById(R.id.emailET);
        phoneNumET = view.findViewById(R.id.phoneNumET);
        resumeTitleET = view.findViewById(R.id.resumeTitleET);
        busyET = view.findViewById(R.id.busyET);
        salaryET = view.findViewById(R.id.salaryET);
        scheduleET = view.findViewById(R.id.scheduleET);

        saveMainBtn = view.findViewById(R.id.saveMainBtn);

        mainInfoDao = AppDatabase.getInstance(requireContext()).mainInfoDao();

        saveMainBtn.setOnClickListener(view1 -> saveResumeData());

        loadResumeData();

        return view;
    }

    private void saveResumeData() {
        final String name = nameET.getText().toString();
        final String surname = surnameET.getText().toString();
        final String secondName = secondNameET.getText().toString();
        final String email = emailET.getText().toString();
        final String phoneNum = phoneNumET.getText().toString();
        final String resumeTitle = resumeTitleET.getText().toString();
        final String busy = busyET.getText().toString();
        final String salary = salaryET.getText().toString();
        final String schedule = scheduleET.getText().toString();

        numberOfFieldsFilled = calculateNumberOfFieldsFilled();

//        if (name.isEmpty() || surname.isEmpty() || secondName.isEmpty() || email.isEmpty() || phoneNum.isEmpty()
//                || resumeTitle.isEmpty() || busy.isEmpty() || salary.isEmpty() || schedule.isEmpty()) {
//            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
//            return;
//        }

        executor.execute(() -> {
            MainInfoEntity mainInfo = mainInfoDao.getMainInfo();

            if (mainInfo == null) {
                mainInfo = new MainInfoEntity();
            }

            progress = numberOfFieldsFilled / 9 * 100;

            mainInfo.setProgress((int) Math.round(progress));
            mainInfo.setName(name);
            mainInfo.setSurname(surname);
            mainInfo.setSecondName(secondName);
            mainInfo.setEmail(email);
            mainInfo.setPhoneNum(phoneNum);
            mainInfo.setResumeTitle(resumeTitle);
            mainInfo.setBusy(busy);
            mainInfo.setSalary(salary);
            mainInfo.setSchedule(schedule);

            mainInfoDao.insertMainInfo(mainInfo);

            requireActivity().runOnUiThread(() -> {
                getParentFragmentManager().popBackStack();
            });
        });
    }

    private void loadResumeData() {
        executor.execute(() -> {
            MainInfoEntity mainInfo = mainInfoDao.getMainInfo();
            if (mainInfo != null) {
                requireActivity().runOnUiThread(() -> {
                    nameET.setText(mainInfo.getName());
                    surnameET.setText(mainInfo.getSurname());
                    secondNameET.setText(mainInfo.getSecondName());
                    emailET.setText(mainInfo.getEmail());
                    phoneNumET.setText(mainInfo.getPhoneNum());
                    resumeTitleET.setText(mainInfo.getResumeTitle());
                    busyET.setText(mainInfo.getBusy());
                    salaryET.setText(mainInfo.getSalary());
                    scheduleET.setText(mainInfo.getSchedule());
                });
            }
        });
    }

    private long calculateNumberOfFieldsFilled() {
        int filledFields = 0;
        EditText[] editTexts = {nameET, surnameET, secondNameET, emailET, phoneNumET, resumeTitleET, busyET, salaryET, scheduleET};
        for (EditText editText : editTexts) {
            if (!editText.getText().toString().isEmpty()) {
                filledFields++;
            }
        }
        Log.d("filledFields", String.valueOf(filledFields));
        return filledFields;
    }
}