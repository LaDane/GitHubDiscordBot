package BotLog;

import Core.Config;
import Member.Members;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BotLogs {
    private ArrayList<BotLog> botLogs = new ArrayList<BotLog>();

    public BotLogs() {}

    public ArrayList<BotLog> getBotLogs() {return botLogs;}
    public void addToBotLogs(BotLog botLog) {botLogs.add(botLog);}
    public void removeFromBotLogs(BotLog botLog) {botLogs.remove(botLog);}


    public void updateBotLogs(int commits, int linesAdd, int linesRem,
                              int pointsGiven, int pointsWon, int pointsLost, int pointsSpent,
                              int msg, int msgBot) {
//        deserializeBotLogsSimple();

        LocalDate today = LocalDate.now();
        BotLog latestBotLog = getBotLog(today);
        if (latestBotLog == null) {
            latestBotLog = new BotLog(LocalDate.now().toString(), commits, linesAdd, linesRem,
                    pointsGiven, pointsWon, pointsLost, pointsSpent, msg, msgBot);

            Config.botLogs.addToBotLogs(latestBotLog);
        }
        else {
            latestBotLog.setLogCommits(latestBotLog.getLogCommits() + commits);
            latestBotLog.setLogLinesAdded(latestBotLog.getLogLinesAdded() + linesAdd);
            latestBotLog.setLogLinesRemoved(latestBotLog.getLogLinesRemoved() + linesRem);

            latestBotLog.setLogPointsGiven(latestBotLog.getLogPointsGiven() + pointsGiven);
            latestBotLog.setLogPointsWon(latestBotLog.getLogPointsWon() + pointsWon);
            latestBotLog.setLogPointsLost(latestBotLog.getLogPointsLost() + pointsLost);
            latestBotLog.setLogPointsSpent(latestBotLog.getLogPointsSpent() + pointsSpent);

            latestBotLog.setLogMessages(latestBotLog.getLogMessages() + msg);
            latestBotLog.setLogMessagesBot(latestBotLog.getLogMessagesBot() + msgBot);
        }
        serializeBotLogsSimple();
    }

    public int[] getBotLogCommitsLines(LocalDate date) {
        BotLog latestBotLog = getBotLog(date);
        if (latestBotLog == null)
            return new int[3];
        else {
            int logCommits = latestBotLog.getLogCommits();
            int logLinesAdded = latestBotLog.getLogLinesAdded();
            int logLinesRemoved = latestBotLog.getLogLinesRemoved();
            return new int[]{logCommits, logLinesAdded, logLinesRemoved};
        }
    }

    public int[] getBotLogPoints(LocalDate date) {
        BotLog latestBotLog = getBotLog(date);
        if (latestBotLog == null)
            return new int[4];
        else {
            int pointsGiven = latestBotLog.getLogPointsGiven();
            int pointsWon = latestBotLog.getLogPointsWon();
            int pointsLost = latestBotLog.getLogPointsLost();
            int pointsSpent = latestBotLog.getLogPointsSpent();
            return new int[]{pointsGiven, pointsWon, pointsLost, pointsSpent};
        }
    }

    public int[] getBotLogMessages(LocalDate date) {
        BotLog latestBotLog = getBotLog(date);
        if (latestBotLog == null)
            return new int[2];
        else {
            int msgAmount = latestBotLog.getLogMessages();
            int msgAmountBot = latestBotLog.getLogMessagesBot();
            return new int[]{msgAmount, msgAmountBot};
        }
    }

    private BotLog getBotLog(LocalDate date) {
        if (Config.botLogs.getBotLogs() != null) {
            for (BotLog log : Config.botLogs.getBotLogs()) {
                if (log.getLogDate().equals(date.toString()))
                    return log;
            }
        }
        return null;
    }


    public void serializeBotLogsSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/BotLogs.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.botLogs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeBotLogsSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/BotLogs.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.botLogs = gson.fromJson(reader, BotLogs.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
