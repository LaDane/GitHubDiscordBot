package BotChannel;

import Core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class AllChannels {
    private BotChannel membersChannel;
    private BotChannel leaderboardChannel;
    private BotChannel commitsCommandsChannel;
    private BotChannel graphsChannel;

    public AllChannels() {}

    public BotChannel getMembersChannel() {return membersChannel;}
    public BotChannel getLeaderboardChannel() {return leaderboardChannel;}
    public BotChannel getCommitsCommandsChannel() {return commitsCommandsChannel;}
    public BotChannel getGraphsChannel() {return graphsChannel;}

    public void setMembersChannel(String ID, String name) {membersChannel = new BotChannel(ID, name);}
    public void setLeaderboardChannel(String ID, String name) {leaderboardChannel = new BotChannel(ID, name);}
    public void setCommitsCommandsChannel(String ID, String name) {commitsCommandsChannel = new BotChannel(ID,name);}
    public void setGraphsChannel(String ID, String name) {graphsChannel = new BotChannel(ID, name);}


    public void serializeAllChannelsSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/AllChannels.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.allChannels, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeAllChannelsSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/AllChannels.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.allChannels = gson.fromJson(reader, AllChannels.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
