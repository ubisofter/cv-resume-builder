package app.cvresume.android.models;

public class PersonalInfo {

    private String name;
    private String surname;
    private String email;
    private String resumeTitle;
    private String city;
    private String address;
    private String placeOfBirth;
    private String phoneNumber;
    private String citizenship;
    private String website;
    private String linkedin;
    private String customField;

    public PersonalInfo(String name, String surname, String email, String resumeTitle, String city, String address, String placeOfBirth, String phoneNumber, String citizenship, String website, String linkedin, String customField) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.resumeTitle = resumeTitle;
        this.city = city;
        this.address = address;
        this.placeOfBirth = placeOfBirth;
        this.phoneNumber = phoneNumber;
        this.citizenship = citizenship;
        this.website = website;
        this.linkedin = linkedin;
        this.customField = customField;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getResumeTitle() {
        return resumeTitle;
    }
    public void setResumeTitle(String resumeTitle) {
        this.resumeTitle = resumeTitle;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLinkedin() {
        return linkedin;
    }
    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getCustomField() {
        return customField;
    }
    public void setCustomField(String customField) {
        this.customField = customField;
    }
}
