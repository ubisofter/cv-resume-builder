package app.cvresume.android.models;

public class Lang {
    private String language;
    private String proficiency;

    public Lang(String language, String proficiency) {
        this.language = language;
        this.proficiency = proficiency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }
}
