package app.cvresume.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import app.cvresume.android.R;
import app.cvresume.android.activities.MainActivity;

public class HomeFragment extends Fragment {

    AppCompatButton createResume;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        createResume = view.findViewById(R.id.createFirst);
        createResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, profileFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getContext(), "Заполните как можно больше данных для вашего резюме", Toast.LENGTH_LONG).show();

                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);

                MenuItem menuItem = bottomNavigationView.getMenu().findItem(R.id.nav_profile);
                menuItem.setChecked(true);
            }
        });

        return view;
    }

}
