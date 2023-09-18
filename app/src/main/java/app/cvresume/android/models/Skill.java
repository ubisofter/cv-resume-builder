package app.cvresume.android.models;

public class Skill {
    private String skill;
    private int skillLevel;
    private String skillDesc;

    public Skill(String skill, int skillLevel, String skillDesc) {
        this.skill = skill;
        this.skillLevel = skillLevel;
        this.skillDesc = skillDesc;
    }

    public String getSkill() {
        return skill;
    }
    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getSkillLevel() {
        return skillLevel;
    }
    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillDesc() {
        return skillDesc;
    }
    public void setSkillDesc(String skillDesc) {
        this.skillDesc = skillDesc;
    }
}