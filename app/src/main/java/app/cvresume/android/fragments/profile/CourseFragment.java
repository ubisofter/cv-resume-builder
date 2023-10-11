package app.cvresume.android.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import app.cvresume.android.R;
import app.cvresume.android.data.AppDatabase;
import app.cvresume.android.data.CourseEntity;
import app.cvresume.android.fragments.profile.course.AddCourseFragment;
import app.cvresume.android.fragments.profile.course.CourseAdapter;
import app.cvresume.android.models.Course;

public class CourseFragment extends Fragment {

    private List<CourseEntity> courseList = new ArrayList<>();
    private RecyclerView courseRV;
    private CourseAdapter courseAdapter;
    private ConstraintLayout addCourseBtn;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        // Находим элементы для ввода
        addCourseBtn = view.findViewById(R.id.addCourseBtn);

        courseRV = view.findViewById(R.id.courseRV);
        setupRecyclerView();

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCourseFragment();
            }
        });

        loadCourseData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void setupRecyclerView() {
        courseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        courseAdapter = new CourseAdapter(courseList, appDatabase);
        courseRV.setAdapter(courseAdapter);
    }

    private void openAddCourseFragment() {
        AddCourseFragment addCourseFragment = new AddCourseFragment();
        addCourseFragment.setCourseAdapter(courseAdapter);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addCourseFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadCourseData() {
        appDatabase.courseDao().getAllCourses().observe(getViewLifecycleOwner(), new Observer<List<CourseEntity>>() {
            @Override
            public void onChanged(List<CourseEntity> courses) {
                courseList.clear();
                courseList.addAll(courses);
                courseAdapter.notifyDataSetChanged();
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
        loadCourseData();
    }
}
