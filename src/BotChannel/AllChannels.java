package BotChannel;

import Core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class AllChannels {
    private ArrayList<BotChannel> allChannels = new ArrayList<BotChannel>();

    public AllChannels(ArrayList<BotChannel> allChannels) {
        this.allChannels = allChannels;
    }

    public ArrayList<BotChannel> getAllChannels() {return allChannels;}
    public void addToAllChannels(BotChannel channel) {allChannels.add(channel);}


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
