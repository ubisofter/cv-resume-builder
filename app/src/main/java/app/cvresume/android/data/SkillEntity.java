package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "skills")
public class SkillEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String skillSkill;
    public int skillLvl;
    public String skillDesc;

    public SkillEntity(String skillSkill, int skillLvl, String skillDesc) {
        this.skillSkill = skillSkill;
        this.skillLvl = skillLvl;
        this.skillDesc = skillDesc;
    }
}
