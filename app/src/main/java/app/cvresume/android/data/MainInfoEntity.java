package app.cvresume.android.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "main_info")
public class MainInfoEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public int progress;
    public String name;
    public String surname;
    public String secondName;
    public String email;
    public String phoneNum;
    public String resumeTitle;
    public String busy;
    public String salary;
    public String schedule;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }
    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getResumeTitle() {
        return resumeTitle;
    }
    public void setResumeTitle(String resumeTitle) {
        this.resumeTitle = resumeTitle;
    }

    public String getBusy() {
        return busy;
    }
    public void setBusy(String busy) {
        this.busy = busy;
    }

    public String getSalary() {
        return salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}