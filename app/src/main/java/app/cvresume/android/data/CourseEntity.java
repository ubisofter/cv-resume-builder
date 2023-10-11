package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String courseName;
    public String courseSchool;
    public String coursePeriod;
    public String courseDescription;

    public CourseEntity(String courseName, String courseSchool, String coursePeriod, String courseDescription) {
        this.courseName = courseName;
        this.courseSchool = courseSchool;
        this.coursePeriod = coursePeriod;
        this.courseDescription = courseDescription;
    }
}