package app.cvresume.android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import app.cvresume.android.R;
import app.cvresume.android.fragments.splash.SplashFragmentOne;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /** вернуть в релизе **/
//        if (isFirstTime()) {
//            showSplashFragmentOne();
//        } else {
//            startMainActivity();
//        }

        showSplashFragmentOne();
    }

    private boolean isFirstTime() {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getBoolean("firstTime", true);
    }

    private void showSplashFragmentOne() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new SplashFragmentOne())
                .commit();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /** вернуть в релизе **/
//    public void startMainActivityFromSplashFragmentTwo() {
//        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putBoolean("firstTime", false);
//        editor.apply();
//
//        startMainActivity();
//    }
}