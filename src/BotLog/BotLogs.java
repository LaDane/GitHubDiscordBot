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


    public void updateBotLogs(int logCommits, int logLinesAdded, int logLinesRemoved) {
        deserializeBotLogsSimple();

        LocalDate today = LocalDate.now();
        BotLog latestBotLog = getBotLog(today);
        if (latestBotLog == null) {
            latestBotLog = new BotLog(LocalDate.now().toString(), logCommits, logLinesAdded, logLinesRemoved);
            Config.botLogs.addToBotLogs(latestBotLog);
        }
        else {
            latestBotLog.setLogCommits(latestBotLog.getLogCommits() + logCommits);
            latestBotLog.setLogLinesAdded(latestBotLog.getLogLinesAdded() + logLinesAdded);
            latestBotLog.setLogLinesRemoved(latestBotLog.getLogLinesRemoved() + logLinesRemoved);
        }
        serializeBotLogsSimple();
    }

    public int[] getBotLogData(LocalDate date) {
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
