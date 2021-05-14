package BotLog;

import java.time.LocalDate;

public class BotLog {
    private String logDate;
    private int logCommits;
    private int logLinesAdded;
    private int logLinesRemoved;

    public BotLog(String logDate, int logCommits, int logLinesAdded, int logLinesRemoved) {
        this.logDate = logDate;
        this.logCommits = logCommits;
        this.logLinesAdded = logLinesAdded;
        this.logLinesRemoved = logLinesRemoved;
    }

    public String getLogDate() {return logDate;}
    public int getLogCommits() {return logCommits;}
    public int getLogLinesAdded() {return logLinesAdded;}
    public int getLogLinesRemoved() {return  logLinesRemoved;}

    public void setLogCommits(int commits) {logCommits = commits;}
    public void setLogLinesAdded(int lines) {logLinesAdded = lines;}
    public void setLogLinesRemoved(int lines) {logLinesRemoved = lines;}
}
