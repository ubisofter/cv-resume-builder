package app.cvresume.android.models;

public class PersonalInfo {
    private String city, birthDate, famStatus, citizenship, eduGrade, personalQualities, profSkills, about;
    private Boolean children, gender, medBook, army, driveCat;

    public PersonalInfo(String city, String birthDate, String famStatus, Boolean children,
                        String citizenship, Boolean gender, String eduGrade, Boolean army, Boolean medBook,
                        Boolean driveCat, String personalQualities, String profSkills, String about) {
        this.city = city;
        this.birthDate = birthDate;
        this.famStatus = famStatus;
        this.children = children;
        this.citizenship = citizenship;
        this.gender = gender;
        this.eduGrade = eduGrade;
        this.medBook = medBook;
        this.driveCat = driveCat;
        this.personalQualities = personalQualities;
        this.profSkills = profSkills;
        this.about = about;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFamStatus() {
        return famStatus;
    }
    public void setFamStatus(String famStatus) {
        this.famStatus = famStatus;
    }

    public Boolean getChildren() {
        return children;
    }
    public void setChildren(Boolean children) {
        this.children = children;
    }

    public String getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Boolean getGender() {
        return gender;
    }
    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEduGrade() {
        return eduGrade;
    }
    public void setEduGrade(String eduGrade) {
        this.eduGrade = eduGrade;
    }

    public Boolean getArmy() {
        return army;
    }
    public void setArmy(Boolean army) {
        this.army = army;
    }

    public Boolean getDriveCat() {
        return driveCat;
    }
    public void setDriveCat(Boolean driveCat) {
        this.driveCat = driveCat;
    }

    public String getPersonalQualities() {
        return personalQualities;
    }
    public void setPersonalQualities(String personalQualities) {
        this.personalQualities = personalQualities;
    }

    public String getProfSkills() {
        return profSkills;
    }
    public void setProfSkills(String profSkills) {
        this.profSkills = profSkills;
    }

    public String getAbout() {
        return about;
    }
    public void setAbout(String about) {
        this.about = about;
    }

    public Boolean getMedBook() {
        return medBook;
    }
    public void setMedBook(Boolean medBook) {
        this.medBook = medBook;
    }
}
