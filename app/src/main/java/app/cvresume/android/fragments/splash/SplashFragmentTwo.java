package app.cvresume.android.fragments.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import app.cvresume.android.R;
import app.cvresume.android.activities.MainActivity;
import app.cvresume.android.activities.SplashActivity;

public class SplashFragmentTwo extends Fragment {

    public SplashFragmentTwo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_two, container, false);
        AppCompatButton createResumeButton = view.findViewById(R.id.splash_two_btn);
        createResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** вернуть в релизе **/
                //((SplashActivity) requireActivity()).startMainActivityFromSplashFragmentTwo();
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                requireActivity().finish();
            }
        });
        return view;
    }
}
