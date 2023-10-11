package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "langs")
public class LangEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String langLang;
    public int langLvl;
    public String langDesc;

    public LangEntity(String langLang, int langLvl, String langDesc) {
        this.langLang = langLang;
        this.langLvl = langLvl;
        this.langDesc = langDesc;
    }
}
