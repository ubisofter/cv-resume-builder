package app.cvresume.android.models;

import java.util.ArrayList;
import java.util.List;

public class Resume {
    private List<PersonalInfo> personalInfoList;
    private List<MainInfo> mainInfoList;
    private List<Experience> experienceList;
    private List<Education> educationList;
    private List<Course> courseList;
    private List<Skill> skillList;
    private List<Lang> langList;

    // Конструктор
    public Resume(List<MainInfo> mainInfoList, List<PersonalInfo> personalInfoList, List<Experience> experienceList, List<Education> educationList, List<Skill> skillList, List<Lang> langList) {
        this.personalInfoList = personalInfoList;
        this.experienceList = experienceList;
        this.educationList = educationList;
        this.skillList = skillList;
        this.langList = langList;
    }

    public Resume() {
        personalInfoList = new ArrayList<>();
        mainInfoList = new ArrayList<>();
        experienceList = new ArrayList<>();
        educationList = new ArrayList<>();
        courseList = new ArrayList<>();
        skillList = new ArrayList<>();
        langList = new ArrayList<>();
    }

    public List<MainInfo> getMainInfoList() {
        return mainInfoList;
    }
    public void setMainInfoList(List<MainInfo> mainInfoList) {
        this.mainInfoList = mainInfoList;
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

    public List<Course> getCourseList() {
        return courseList;
    }
    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
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

    public String toText() {
        StringBuilder builder = new StringBuilder();

        builder.append("Основная информация:\n");
        for (MainInfo mainInfo: mainInfoList) {
            builder.append("Имя: ").append(mainInfo.getName()).append("\n");
            builder.append("Фамилия: ").append(mainInfo.getSurname()).append("\n");
            builder.append("Отчество: ").append(mainInfo.getSecondName()).append("\n");
            builder.append("Email: ").append(mainInfo.getEmail()).append("\n");
            builder.append("Заголовок резюме: ").append(mainInfo.getResumeTitle()).append("\n");
            builder.append("Занятость: ").append(mainInfo.getBusy()).append("\n");
            builder.append("Заработная плата: ").append(mainInfo.getSalary()).append("\n");
            builder.append("График работы: ").append(mainInfo.getSchedule()).append("\n");
            builder.append("\n");
        }

        // Добавляем персональные данные
        builder.append("Личная информация:\n");
        for (PersonalInfo personalInfo : personalInfoList) {
            builder.append("Город проживания: ").append(personalInfo.getCity()).append("\n");
            builder.append("Дата рождения: ").append(personalInfo.getBirthDate()).append("\n");
            builder.append("Семейное положение: ").append(personalInfo.getFamStatus()).append("\n");
            builder.append("Дети: ").append(personalInfo.getChildren()).append("\n");
            builder.append("Гражданство: ").append(personalInfo.getCitizenship()).append("\n");
            builder.append("Пол: ").append(personalInfo.getGender()).append("\n");
            builder.append("Образование: ").append(personalInfo.getEduGrade()).append("\n");
            builder.append("\n");
        }


        // Добавляем опыт работы
        builder.append("Опыт работы:\n");
        for (Experience experience : experienceList) {
            builder.append("Должность: ").append(experience.getPosition()).append("\n");
            builder.append("Работодатель: ").append(experience.getEmployer()).append("\n");
            builder.append("Город: ").append(experience.getWorkcity()).append("\n");
            builder.append("Даты: ").append(experience.getDuration()).append("\n");
            builder.append("Описание: ").append(experience.getWorkdesc()).append("\n");
            builder.append("\n");
        }

        // Добавляем образование
        builder.append("Образование:\n");
        for (Education education : educationList) {
            builder.append("Образование: ").append(education.getDegree()).append("\n");
            builder.append("Учебное заведение: ").append(education.getUniversity()).append("\n");
            builder.append("Город: ").append(education.getCity()).append("\n");
            builder.append("Даты: ").append(education.getYears()).append("\n");
            builder.append("Описание: ").append(education.getDesc()).append("\n");
            builder.append("\n");
        }

        // Добавляем курсы
        builder.append("Курсы:\n");
        for (Course course : courseList) {
            builder.append("Название курса: ").append(course.getCourseName()).append("\n");
            builder.append("Учебное заведение: ").append(course.getCourseSchool()).append("\n");
            builder.append("Период: ").append(course.getCoursePeriod()).append("\n");
            builder.append("Доп.инфо: ").append(course.getCourseDesc()).append("\n");
            builder.append("\n");
        }

        // Добавляем навыки
        builder.append("Навыки:\n");
        for (Skill skill : skillList) {
            builder.append("Навык: ").append(skill.getSkill()).append("\n");
            builder.append("Уровень: ").append(skill.getSkillLevel()).append("\n");
            builder.append("Описание навыка: ").append(skill.getSkillDesc()).append("\n");
            builder.append("\n");
        }

        // Добавляем языки
        builder.append("Языки:\n");
        for (Lang lang : langList) {
            builder.append("Язык: ").append(lang.getLang()).append("\n");
            builder.append("Уровень: ").append(lang.getLangLvl()).append("\n");
            builder.append("Описание языка: ").append(lang.getLangDesc()).append("\n");
            builder.append("\n");
        }

        return builder.toString();
    }
}
