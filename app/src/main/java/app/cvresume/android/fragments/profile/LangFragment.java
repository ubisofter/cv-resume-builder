package app.cvresume.android.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import app.cvresume.android.data.LangEntity;
import app.cvresume.android.fragments.profile.lang.AddLangFragment;
import app.cvresume.android.fragments.profile.lang.LangAdapter;
import app.cvresume.android.fragments.profile.lang.AddLangFragment;
import app.cvresume.android.fragments.profile.lang.LangAdapter;
import app.cvresume.android.models.Lang;

public class LangFragment extends Fragment {

    private List<LangEntity> langList = new ArrayList<>();
    private RecyclerView langRV;
    private LangAdapter langAdapter;
    private ConstraintLayout addLangBtn;
    private AppCompatActivity activity;
    private BottomNavigationView bnv;
    private AppDatabase appDatabase;
    private static final String TAG = "LangFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lang, container, false);

        appDatabase = AppDatabase.getInstance(requireContext());

        addLangBtn = view.findViewById(R.id.addLangBtn);

        langRV = view.findViewById(R.id.langRV);
        setupRecyclerView();

        addLangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddLangFragment();
            }
        });

        loadLangData();

        activity = (AppCompatActivity) requireActivity();
        bnv = activity.findViewById(R.id.bottom_navigation);
        bnv.setVisibility(View.GONE);

        return view;
    }

    private void setupRecyclerView() {
        langRV.setLayoutManager(new LinearLayoutManager(getContext()));
        langAdapter = new LangAdapter(langList, appDatabase);
        langRV.setAdapter(langAdapter);
    }

    private void openAddLangFragment() {
        AddLangFragment addLangFragment = new AddLangFragment();
        addLangFragment.setLangAdapter(langAdapter);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addLangFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadLangData() {
        appDatabase.langDao().getAllLangs().observe(getViewLifecycleOwner(), new Observer<List<LangEntity>>() {
            @Override
            public void onChanged(List<LangEntity> langs) {
                langList.clear();
                langList.addAll(langs);
                langAdapter.notifyDataSetChanged();
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
        loadLangData();
    }
}