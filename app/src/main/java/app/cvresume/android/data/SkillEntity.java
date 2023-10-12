package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "skills")
public class SkillEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public int progress;
    public String skillSkill;
    public int skillLvl;
    public String skillDesc;

    public SkillEntity(String skillSkill, int skillLvl, String skillDesc) {
        this.skillSkill = skillSkill;
        this.skillLvl = skillLvl;
        this.skillDesc = skillDesc;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
