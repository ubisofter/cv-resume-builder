package app.cvresume.android.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MainInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMainInfo(MainInfoEntity mainInfo);

    @Update
    void updateMainInfo(MainInfoEntity mainInfo);

    @Delete
    void deleteMainInfo(MainInfoEntity mainInfo);

    @Query("SELECT * FROM main_info LIMIT 1")
    MainInfoEntity getMainInfo();
}
