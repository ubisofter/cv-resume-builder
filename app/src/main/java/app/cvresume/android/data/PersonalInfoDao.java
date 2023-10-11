package app.cvresume.android.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PersonalInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonalInfo(PersonalInfoEntity personalInfo);

    @Update
    void updatePersonalInfo(PersonalInfoEntity personalInfo);

    @Delete
    void deletePersonalInfo(PersonalInfoEntity personalInfo);

    @Query("SELECT * FROM personal_info LIMIT 1")
    PersonalInfoEntity getPersonalInfo();
}
