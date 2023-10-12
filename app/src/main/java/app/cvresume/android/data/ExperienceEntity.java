package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "experiences")
public class ExperienceEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public int progress;
    public String experiencePosition;
    public String experienceEmployer;
    public String experienceWorkcity;
    public String experienceDuration;
    public String experienceWorkdesc;

    public ExperienceEntity(String experiencePosition, String experienceEmployer, String experienceWorkcity, String experienceDuration, String experienceWorkdesc) {
        this.experiencePosition = experiencePosition;
        this.experienceEmployer = experienceEmployer;
        this.experienceWorkcity = experienceWorkcity;
        this.experienceDuration = experienceDuration;
        this.experienceWorkdesc = experienceWorkdesc;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }
}