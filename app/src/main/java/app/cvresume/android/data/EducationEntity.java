package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "educations")
public class EducationEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public int progress;
    public String educationDegree;
    public String educationUniversity;
    public String educationCity;
    public String educationYears;
    public String educationDesc;

    public EducationEntity(String educationDegree, String educationUniversity, String educationCity, String educationYears, String educationDesc) {
        this.educationDegree = educationDegree;
        this.educationUniversity = educationUniversity;
        this.educationCity = educationCity;
        this.educationYears = educationYears;
        this.educationDesc = educationDesc;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
}