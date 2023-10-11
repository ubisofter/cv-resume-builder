package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "educations")
public class EducationEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

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
}