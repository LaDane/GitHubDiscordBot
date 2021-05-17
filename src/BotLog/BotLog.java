package BotLog;

import java.time.LocalDate;

public class BotLog {
    private String logDate;
    private int logCommits;
    private int logLinesAdded;
    private int logLinesRemoved;
    private int logPointsGiven;
    private int logPointsWon;
    private int logPointsLost;
    private int logPointsSpent;
    private int logMessages;
    private int logMessagesBot;

    public BotLog(String logDate, int logCommits, int logLinesAdded, int logLinesRemoved,
                  int logPointsGiven, int logPointsWon, int logPointsLost, int logPointsSpent,
                  int logMessages, int logMessagesBot) {
        this.logDate = logDate;
        this.logCommits = logCommits;
        this.logLinesAdded = logLinesAdded;
        this.logLinesRemoved = logLinesRemoved;
        this.logPointsGiven = logPointsGiven;
        this.logPointsWon = logPointsWon;
        this.logPointsLost = logPointsLost;
        this.logPointsSpent = logPointsSpent;
        this.logMessages = logMessages;
        this.logMessagesBot = logMessagesBot;
    }

    public String getLogDate() {return logDate;}
    public int getLogCommits() {return logCommits;}
    public int getLogLinesAdded() {return logLinesAdded;}
    public int getLogLinesRemoved() {return  logLinesRemoved;}
    public int getLogPointsGiven() {return logPointsGiven;}
    public int getLogPointsWon() {return logPointsWon;}
    public int getLogPointsLost() {return logPointsLost;}
    public int getLogPointsSpent() {return logPointsSpent;}
    public int getLogMessages() {return logMessages;}
    public int getLogMessagesBot() {return logMessagesBot;}

    public void setLogCommits(int commits) {logCommits = commits;}
    public void setLogLinesAdded(int lines) {logLinesAdded = lines;}
    public void setLogLinesRemoved(int lines) {logLinesRemoved = lines;}
    public void setLogPointsGiven(int points) {logPointsGiven = points;}
    public void setLogPointsWon(int points) {logPointsWon = points;}
    public void setLogPointsLost(int points) {logPointsLost = points;}
    public void setLogPointsSpent(int points) {logPointsSpent = points;}
    public void setLogMessages(int amount) {logMessages = amount;}
    public void setLogMessagesBot(int amount) {logMessagesBot = amount;}
}
