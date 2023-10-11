package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "personal_info")
public class PersonalInfoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String city;
    private String birthDate;
    private String famStatus;
    private boolean children;
    private String citizenship;
    private boolean gender;
    private String eduGrade;
    private boolean army;
    private boolean driveCat;
    private String personalQualities;
    private String profSkills;
    private boolean medBook;
    private String about;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEduGrade() {
        return eduGrade;
    }

    public void setEduGrade(String eduGrade) {
        this.eduGrade = eduGrade;
    }

    public boolean isArmy() {
        return army;
    }

    public void setArmy(boolean army) {
        this.army = army;
    }

    public boolean isDriveCat() {
        return driveCat;
    }

    public void setDriveCat(boolean driveCat) {
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

    public boolean isMedBook() {
        return medBook;
    }

    public void setMedBook(boolean medBook) {
        this.medBook = medBook;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}