package app.cvresume.android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LangDao {
    @Insert
    void insertLang(LangEntity lang);

    @Update
    void updateLang(LangEntity lang);

    @Delete
    void deleteLang(LangEntity lang);

    @Query("SELECT * FROM langs")
    LiveData<List<LangEntity>> getAllLangs();
}
