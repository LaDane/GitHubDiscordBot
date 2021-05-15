package Member;

public class RepoStats {
    private String repoLanguage;
    private int repoLanguageAmount;

    public RepoStats(String repoLanguage, int repoLanguageAmount) {
        this.repoLanguage = repoLanguage;
        this.repoLanguageAmount = repoLanguageAmount;
    }

    public String getRepoLanguage() {return repoLanguage;}
    public int getRepoLanguageAmount() {return repoLanguageAmount;}

    public void setRepoLanguageAmount(int amount) {repoLanguageAmount = amount;}
}
