package app.cvresume.android.models;

public class Course {

    String cName;
    String cSchool;
    String cPeriod;
    String cDesc;

    public Course (String cName, String cSchool, String cPeriod, String cDesc){
        this.cName = cName;
        this.cSchool = cSchool;
        this.cPeriod = cPeriod;
        this.cDesc = cDesc;
    }

    public String getCourseName(){
        return cName;
    }
    public void setCourseName(String cName){
        this.cName = cName;
    }

    public String getCourseSchool(){
        return cSchool;
    }
    public void setCourseSchool(String cSchool){
        this.cSchool = cSchool;
    }

    public String getCoursePeriod(){
        return cPeriod;
    }
    public void setCoursePeriod(String cPeriod){
        this.cPeriod = cPeriod;
    }

    public String getCourseDesc() {
        return cDesc;
    }
    public void setCourseDesc(String cDesc) {
        this.cDesc = cDesc;
    }
}
