package app.cvresume.android.fragments.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import app.cvresume.android.R;
import app.cvresume.android.models.PersonalInfo;

public class PersonalFragment extends Fragment {

    private TextInputEditText nameET,surnameET,resumeTitleET,emailET,cityET,addressET,placeOfBirthET;
    private TextInputEditText phoneNumberET,citizenshipET,websiteET,linkedinET,customFieldET;

    private PersonalInfo personalInfo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);

        // Находим элементы для ввода
        nameET = view.findViewById(R.id.nameET);
        surnameET = view.findViewById(R.id.surnameET);
        emailET = view.findViewById(R.id.emailET);
        resumeTitleET = view.findViewById(R.id.resumeTitleET);
        cityET = view.findViewById(R.id.cityET);
        addressET = view.findViewById(R.id.addressET);
        placeOfBirthET = view.findViewById(R.id.placeOfBirthET);
        phoneNumberET = view.findViewById(R.id.phoneNumberET);
        citizenshipET = view.findViewById(R.id.citizenshipET);
        websiteET = view.findViewById(R.id.websiteET);
        linkedinET = view.findViewById(R.id.linkedinET);
        customFieldET = view.findViewById(R.id.customFieldET);

        sharedPreferences = requireActivity().getSharedPreferences("resume_data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Всегда инициализируйте personalInfo
        personalInfo = new PersonalInfo("", "", "", "", "", "", "", "", "", "", "", "");

        loadResumeData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveResumeData();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveResumeData();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveResumeData();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadResumeData();
    }

    private void saveResumeData() {

        personalInfo.setName(nameET.getText().toString());
        personalInfo.setSurname(surnameET.getText().toString());
        personalInfo.setEmail(emailET.getText().toString());
        personalInfo.setResumeTitle(resumeTitleET.getText().toString());
        personalInfo.setCity(cityET.getText().toString());
        personalInfo.setAddress(addressET.getText().toString());
        personalInfo.setPlaceOfBirth(placeOfBirthET.getText().toString());
        personalInfo.setPhoneNumber(phoneNumberET.getText().toString());
        personalInfo.setCitizenship(citizenshipET.getText().toString());
        personalInfo.setWebsite(websiteET.getText().toString());
        personalInfo.setLinkedin(linkedinET.getText().toString());
        personalInfo.setCustomField(customFieldET.getText().toString());

        editor.putString("name", personalInfo.getName());
        editor.putString("surname", personalInfo.getSurname());
        editor.putString("email", personalInfo.getEmail());
        editor.putString("resume_title", personalInfo.getResumeTitle());
        editor.putString("city", personalInfo.getCity());
        editor.putString("address", personalInfo.getAddress());
        editor.putString("place_of_birth", personalInfo.getPlaceOfBirth());
        editor.putString("phone_number", personalInfo.getPhoneNumber());
        editor.putString("citizenship", personalInfo.getCitizenship());
        editor.putString("website", personalInfo.getWebsite());
        editor.putString("linked_in", personalInfo.getLinkedin());
        editor.putString("custom_field", personalInfo.getCustomField());

        editor.apply();
    }

    private void loadResumeData() {

        personalInfo.setName(sharedPreferences.getString("name", ""));
        personalInfo.setSurname(sharedPreferences.getString("surname", ""));
        personalInfo.setEmail(sharedPreferences.getString("email", ""));
        personalInfo.setResumeTitle(sharedPreferences.getString("resume_title", ""));
        personalInfo.setCity(sharedPreferences.getString("city", ""));
        personalInfo.setAddress(sharedPreferences.getString("address", ""));
        personalInfo.setPlaceOfBirth(sharedPreferences.getString("place_of_birth", ""));
        personalInfo.setPhoneNumber(sharedPreferences.getString("phone_number", ""));
        personalInfo.setCitizenship(sharedPreferences.getString("citizenship", ""));
        personalInfo.setWebsite(sharedPreferences.getString("website", ""));
        personalInfo.setLinkedin(sharedPreferences.getString("linked_in", ""));
        personalInfo.setCustomField(sharedPreferences.getString("custom_field", ""));

        // Заполнение полей ввода данными из resume
        nameET.setText(personalInfo.getName());
        surnameET.setText(personalInfo.getSurname());
        emailET.setText(personalInfo.getEmail());
        resumeTitleET.setText(personalInfo.getResumeTitle());
        cityET.setText(personalInfo.getCity());
        addressET.setText(personalInfo.getAddress());
        placeOfBirthET.setText(personalInfo.getPlaceOfBirth());
        phoneNumberET.setText(personalInfo.getPhoneNumber());
        citizenshipET.setText(personalInfo.getCitizenship());
        websiteET.setText(personalInfo.getWebsite());
        linkedinET.setText(personalInfo.getLinkedin());
        customFieldET.setText(personalInfo.getCustomField());

    }
}