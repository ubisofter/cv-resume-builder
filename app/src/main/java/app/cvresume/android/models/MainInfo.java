package app.cvresume.android.models;

public class MainInfo {

    private String name;
    private String surname;
    private String secondName;
    private String email;
    private String phoneNum;
    private String resumeTitle;
    private String busy;
    private String salary;
    private String schedule;

    public MainInfo(String name, String surname, String secondName, String email, String phoneNum, String resumeTitle, String busy, String salary, String schedule){
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.resumeTitle = resumeTitle;
        this.busy = busy;
        this.salary = salary;
        this.schedule = schedule;
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

    public String getPhoneNum(){ return phoneNum;}
    public void setPhoneNum(String phoneNum) {this.phoneNum = phoneNum;}

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
