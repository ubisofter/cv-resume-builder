package app.cvresume.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDao {
    @Insert
    void insertCourse(CourseEntity course);

    @Update
    void updateCourse(CourseEntity course);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("SELECT * FROM courses")
    LiveData<List<CourseEntity>> getAllCourses();
    @Query("SELECT * FROM courses")
    CourseEntity getCourse();

    @Query("SELECT COUNT(*) FROM courses")
    int countCourses();
}