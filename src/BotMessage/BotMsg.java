package BotMessage;

import Core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class BotMsg {
    private String shopID;
    private String leaderboardID;

    private String gaugeChartID;
    private String donutChartID;
    private String pieChartID;
    private String stackBarChartID;
    private String lineChartID;

    public BotMsg() {}

    public String getShopID() {return shopID;}
    public String getLeaderboardID() {return leaderboardID;}
    public String getGaugeChartID() {return gaugeChartID;}
    public String getDonutChartID() {return donutChartID;}
    public String getPieChartID() {return pieChartID;}
    public String getStackBarChartID() {return stackBarChartID;}
    public String getLineChartID() {return lineChartID;}

    public void setShopID(String ID) {shopID = ID;}
    public void setLeaderboardID(String ID) {leaderboardID = ID;}
    public void setGaugeChartID(String ID) {gaugeChartID = ID;}
    public void setDonutChartID(String ID) {donutChartID = ID;}
    public void setPieChartID(String ID) {pieChartID = ID;}
    public void setStackBarChartID(String ID) {stackBarChartID = ID;}
    public void setLineChartID(String ID) {lineChartID = ID;}


    public void serializeBotMessageSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/BotMessage.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.botMsg, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeBotMessageSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/BotMessage.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.botMsg = gson.fromJson(reader, BotMsg.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
