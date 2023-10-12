package app.cvresume.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SkillDao {
    @Insert
    void insertSkill(SkillEntity skill);

    @Update
    void updateSkill(SkillEntity skill);

    @Delete
    void deleteSkill(SkillEntity skill);

    @Query("SELECT * FROM skills")
    LiveData<List<SkillEntity>> getAllSkills();
    @Query("SELECT * FROM skills")
    SkillEntity getSkill();

    @Query("SELECT COUNT(*) FROM skills")
    int countSkills();
}