package app.cvresume.android.models;

import java.util.List;

public class Resume {
    private List<PersonalInfo> personalInfoList;
    private List<Experience> experienceList;
    private List<Education> educationList;
    private List<Skill> skillList;
    private List<Lang> langList;

    // Конструктор
    public Resume(List<PersonalInfo> personalInfoList, List<Experience> experienceList, List<Education> educationList, List<Skill> skillList, List<Lang> langList) {
        this.personalInfoList = personalInfoList;
        this.experienceList = experienceList;
        this.educationList = educationList;
        this.skillList = skillList;
        this.langList = langList;
    }

    public List<PersonalInfo> getPersonalInfoList() {
        return personalInfoList;
    }
    public void setPersonalInfoList(List<PersonalInfo> personalInfoList) {
        this.personalInfoList = personalInfoList;
    }

    public List<Experience> getExperienceList(){
        return experienceList;
    }
    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<Education> getEducationList(){
        return educationList;
    }
    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<Skill> getSkillList(){
        return skillList;
    }
    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public List<Lang> getLangList(){
        return langList;
    }
    public void setLangList(List<Lang> langList) {
        this.langList = langList;
    }

}
