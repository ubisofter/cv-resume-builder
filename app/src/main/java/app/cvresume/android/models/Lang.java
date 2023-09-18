package app.cvresume.android.models;

public class Lang {
    private String lang, langDesc;
    private int langLvl;

    public Lang(String lang, int langLvl, String langDesc) {
        this.lang = lang;
        this.langLvl = langLvl;
        this.langDesc = langDesc;
    }

    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getLangLvl() {
        return langLvl;
    }
    public void setLangLvl(int langLvl) {
        this.langLvl = langLvl;
    }

    public String getLangDesc() {
        return langDesc;
    }
    public void setLangDesc(String langDesc) {
        this.langDesc = langDesc;
    }
}
