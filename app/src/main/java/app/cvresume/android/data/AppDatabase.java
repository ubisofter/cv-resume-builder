package app.cvresume.android.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        //ResumeEntity.class,
        MainInfoEntity.class,
        PersonalInfoEntity.class,
        ExperienceEntity.class,
        EducationEntity.class,
        CourseEntity.class,
        SkillEntity.class,
        LangEntity.class
        },
        version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    //public abstract ResumeDao resumeDao();
    public abstract MainInfoDao mainInfoDao();
    public abstract PersonalInfoDao personalInfoDao();
    public abstract ExperienceDao experienceDao();
    public abstract EducationDao educationDao();
    public abstract CourseDao courseDao();
    public abstract SkillDao skillDao();
    public abstract LangDao langDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app-database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}