package app.cvresume.android.fragments.profile.course;

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
import app.cvresume.android.data.CourseEntity;

public class AddCourseFragment extends Fragment {

    private EditText cNameET, cSchoolET, cStartDateET, cEndDateET, cDescET;
    private AppCompatButton saveCourseBtn;
    private AppDatabase appDatabase;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private CourseAdapter courseAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();

    public void setCourseAdapter(CourseAdapter adapter) {
        courseAdapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        cNameET = view.findViewById(R.id.cNameET);
        cSchoolET = view.findViewById(R.id.cSchoolET);
        cStartDateET = view.findViewById(R.id.cStartDateET);
        cEndDateET = view.findViewById(R.id.cEndDateET);
        cDescET = view.findViewById(R.id.cDescET);
        saveCourseBtn = view.findViewById(R.id.saveCourseBtn);

        saveCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void addCourse() {
        String cName = cNameET.getText().toString();
        String cSchool = cSchoolET.getText().toString();
        String cStartDate = cStartDateET.getText().toString();
        String cEndDate = cEndDateET.getText().toString();
        String cDesc = cDescET.getText().toString();

        if (cName.isEmpty() || cSchool.isEmpty() || cStartDate.isEmpty() || cEndDate.isEmpty() || cDesc.isEmpty()) {
            Toast.makeText(requireContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        final CourseEntity courseEntity = new CourseEntity(
                cName,
                cSchool,
                cStartDate + " - " + cEndDate,
                cDesc
        );
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.courseDao().insertCourse(courseEntity);

                Log.d("AddCourseFragment", "Course added successfully");

                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cNameET.setText("");
                        cSchoolET.setText("");
                        cStartDateET.setText("");
                        cEndDateET.setText("");
                        cDescET.setText("");

                        if (courseAdapter != null) {
                            courseAdapter.notifyDataSetChanged();
                        }

                        getParentFragmentManager().popBackStack();
                    }
                });
            }
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