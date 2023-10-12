package app.cvresume.android.fragments.splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import app.cvresume.android.R;

public class SplashFragmentOne extends Fragment {

    public SplashFragmentOne() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_one, container, false);

        AppCompatButton nextButton = view.findViewById(R.id.splash_one_btn);
        nextButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            transaction.replace(R.id.fragment_container, new SplashFragmentTwo());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return view;
    }
}