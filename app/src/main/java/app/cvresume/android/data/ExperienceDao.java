package app.cvresume.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExperienceDao {
    @Insert
    void insertExperience(ExperienceEntity experience);

    @Update
    void updateExperience(ExperienceEntity experience);

    @Delete
    void deleteExperience(ExperienceEntity experience);

    @Query("SELECT * FROM experiences")
    LiveData<List<ExperienceEntity>> getAllExperiences();
}
