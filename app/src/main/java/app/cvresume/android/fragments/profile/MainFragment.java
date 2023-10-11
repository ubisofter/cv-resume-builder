package app.cvresume.android.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import app.cvresume.android.data.EducationEntity;
import app.cvresume.android.data.MainInfoDao;
import app.cvresume.android.data.MainInfoEntity;
import app.cvresume.android.models.MainInfo;

public class MainFragment extends Fragment {

    private AppDatabase appDatabase;
    private EditText surnameET, nameET, secondNameET, emailET, phoneNumET, resumeTitleET, busyET, salaryET, scheduleET;
    private MainInfoDao mainInfoDao;
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppCompatButton saveMainBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_info, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        // Находим элементы для ввода
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

        saveMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResumeData();
            }
        });

        loadResumeData();

        return view;
    }

    private void saveResumeData() {
        MainInfoEntity mainInfo = new MainInfoEntity();
        mainInfo.setName(nameET.getText().toString());
        mainInfo.setSurname(surnameET.getText().toString());
        mainInfo.setSecondName(secondNameET.getText().toString());
        mainInfo.setEmail(emailET.getText().toString());
        mainInfo.setPhoneNum(phoneNumET.getText().toString());
        mainInfo.setResumeTitle(resumeTitleET.getText().toString());
        mainInfo.setBusy(busyET.getText().toString());
        mainInfo.setSalary(salaryET.getText().toString());
        mainInfo.setSchedule(scheduleET.getText().toString());

        String mName = nameET.getText().toString();
        String mSurname = surnameET.getText().toString();
        String mSecondName = secondNameET.getText().toString();
        String mEmail = emailET.getText().toString();
        String mPhoneNum = phoneNumET.getText().toString();
        String mResumeTitle = resumeTitleET.getText().toString();
        String mBusy = busyET.getText().toString();
        String mSalary = salaryET.getText().toString();
        String mSchedule = scheduleET.getText().toString();

        if (mName.isEmpty() || mSurname.isEmpty() || mSecondName.isEmpty() || mEmail.isEmpty() || mPhoneNum.isEmpty()
                || mResumeTitle.isEmpty() || mBusy.isEmpty() || mSalary.isEmpty() || mSchedule.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                mainInfoDao.insertMainInfo(mainInfo);
                Log.d("MainFragment", "MainInfoEntity added successfully");
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getParentFragmentManager().popBackStack();
                    }
                });
            }
        });
    }

    private void loadResumeData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MainInfoEntity mainInfo = mainInfoDao.getMainInfo();
                if (mainInfo != null) {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nameET.setText(mainInfo.getName());
                            surnameET.setText(mainInfo.getSurname());
                            secondNameET.setText(mainInfo.getSecondName());
                            emailET.setText(mainInfo.getEmail());
                            phoneNumET.setText(mainInfo.getPhoneNum());
                            resumeTitleET.setText(mainInfo.getResumeTitle());
                            busyET.setText(mainInfo.getBusy());
                            salaryET.setText(mainInfo.getSalary());
                            scheduleET.setText(mainInfo.getSchedule());
                        }
                    });
                }
            }
        });
    }
}