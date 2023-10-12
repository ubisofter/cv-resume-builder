package app.cvresume.android.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import app.cvresume.android.R;
import app.cvresume.android.data.ExperienceEntity;
import app.cvresume.android.data.MainInfoEntity;

public class PreviewFragment extends Fragment {

    public static PreviewFragment newInstance(Bundle args) {
        PreviewFragment fragment = new PreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview, container, false);

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt("templatePosition");
            String name = args.getString("name");
            boolean hasChildren = args.getBoolean("children");
            ArrayList<String> experienceDataList = args.getStringArrayList("experienceDataList");

            Log.d("position", "position = "+position);
            Log.d("name", "name = "+name);
            Log.d("children", "children = "+hasChildren);
            Log.d("experienceDataList", "experienceDataList = "+experienceDataList);

            TextView posTV = view.findViewById(R.id.pPosTV);
            posTV.setText("Position: " + position);
            TextView nameTV = view.findViewById(R.id.pNameTV);
            nameTV.setText("Name: " + name);
            TextView childTV = view.findViewById(R.id.pChildTV);
            childTV.setText("Name: " + hasChildren);
            TextView experienceTV = view.findViewById(R.id.pExperienceTV);
            experienceTV.setText("Name: " + experienceDataList);

            // Добавьте код для отображения других данных
        }

        return view;
    }
}
