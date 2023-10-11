package app.cvresume.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EducationDao {
    @Insert
    void insertEducation(EducationEntity education);

    @Update
    void updateEducation(EducationEntity education);

    @Delete
    void deleteEducation(EducationEntity education);

    @Query("SELECT * FROM educations")
    LiveData<List<EducationEntity>> getAllEducations();
}