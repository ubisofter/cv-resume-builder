package app.cvresume.android.data;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

@Entity(tableName = "resumes")
public class ResumeEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @Embedded
    public MainInfoEntity mainInfo;

    @Embedded
    public PersonalInfoEntity personalInfo;

    @Relation(parentColumn = "id", entityColumn = "resumeId", entity = ExperienceEntity.class)
    public List<ExperienceEntity> experienceList;

    @Relation(parentColumn = "id", entityColumn = "resumeId", entity = EducationEntity.class)
    public List<EducationEntity> educationList;

    @Relation(parentColumn = "id", entityColumn = "resumeId", entity = CourseEntity.class)
    public List<CourseEntity> courseList;

    @Relation(parentColumn = "id", entityColumn = "resumeId", entity = SkillEntity.class)
    public List<SkillEntity> skillList;

    @Relation(parentColumn = "id", entityColumn = "resumeId", entity = LangEntity.class)
    public List<LangEntity> langList;
}