package PLang;

public class PLang {
    private String lang;
    private String color;

    public PLang(String programmingLanguage, String languageHEX) {
        this.lang = programmingLanguage;
        this.color = languageHEX;
    }

    public String getLang() {return lang;}
    public String getColor() {return color;}
}
