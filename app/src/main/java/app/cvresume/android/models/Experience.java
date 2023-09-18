package app.cvresume.android.models;

public class Experience {
    private String position;
    private String employer;
    private String workcity;
    private String duration;
    private String workdesc;

    public Experience(String position, String employer, String workcity, String duration, String workdesc) {
        this.position = position;
        this.employer = employer;
        this.workcity = workcity;
        this.duration = duration;
        this.workdesc = workdesc;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployer() {
        return employer;
    }
    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getWorkcity() {
        return workcity;
    }
    public void setWorkcity(String workcity) {
        this.workcity = workcity;
    }

    public String getWorkdesc() {
        return workdesc;
    }
    public void setWorkdesc(String workdesc) {
        this.workdesc = workdesc;
    }
}