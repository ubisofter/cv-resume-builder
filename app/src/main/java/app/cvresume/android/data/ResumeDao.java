package app.cvresume.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ResumeDao {
    @Insert
    void insertResume(ResumeEntity course);

    @Update
    void updateResume(ResumeEntity course);

    @Delete
    void deleteResume(ResumeEntity course);

    @Query("SELECT * FROM courses")
    LiveData<List<ResumeEntity>> getAllResumes();
}
