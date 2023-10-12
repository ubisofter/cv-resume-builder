package app.cvresume.android.fragments.profile.education;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.EducationEntity;

public class AddEducationFragment extends Fragment {

    private EditText eDegreeET, eUniversityET, eCityET, eDateStartET,  eDateEndET, eDescET;
    private AppCompatButton saveEducationBtn;
    private AppDatabase appDatabase;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private EducationAdapter educationAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void setEducationAdapter(EducationAdapter adapter) {
        educationAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_education, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        eDegreeET = view.findViewById(R.id.eDegreeET);
        eUniversityET = view.findViewById(R.id.eUniversityET);
        eCityET = view.findViewById(R.id.eCityET);
        eDateStartET = view.findViewById(R.id.eDateStartET);
        eDateEndET = view.findViewById(R.id.eDateEndET);
        eDescET = view.findViewById(R.id.eDescET);
        saveEducationBtn = view.findViewById(R.id.saveEducationBtn);

        saveEducationBtn.setOnClickListener(v -> addEducation());

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void addEducation() {
        String eDegree = eDegreeET.getText().toString();
        String eUniversity = eUniversityET.getText().toString();
        String eCity = eCityET.getText().toString();
        String eStart = eDateStartET.getText().toString();
        String eEnd = eDateEndET.getText().toString();
        String eDesc = eDescET.getText().toString();

        if (eDegree.isEmpty() || eUniversity.isEmpty() || eCity.isEmpty() || eStart.isEmpty() || eEnd.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        final EducationEntity educationEntity = new EducationEntity(
                eDegree,
                eUniversity,
                eCity,
                eStart + " - " + eEnd,
                eDesc
        );

        executor.execute(() -> {
            appDatabase.educationDao().insertEducation(educationEntity);

            Log.d("AddEducationFragment", "Education added successfully");

            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    eDegreeET.setText("");
                    eUniversityET.setText("");
                    eCityET.setText("");
                    eDateStartET.setText("");
                    eDateEndET.setText("");
                    eDescET.setText("");

                    if (educationAdapter != null) {
                        educationAdapter.notifyDataSetChanged();
                    }

                    getParentFragmentManager().popBackStack();
                }
            });
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        bnv.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bnv.setVisibility(View.GONE);
    }
}