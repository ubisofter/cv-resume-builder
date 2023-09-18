package app.cvresume.android.models;

public class Education {
    private String degree;
    private String university;
    private String city;
    private String years;
    private String desc;

    public Education(String degree, String university, String city, String years, String desc) {
        this.degree = degree;
        this.university = university;
        this.city = city;
        this.years = years;
        this.desc = desc;
    }

    public String getDegree() {
        return degree;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getUniversity() {
        return university;
    }
    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYears() {
        return years;
    }
    public void setYears(String years) {
        this.years = years;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
