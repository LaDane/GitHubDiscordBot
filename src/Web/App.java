package Web;

import BotChannel.AllChannels;
import Core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class App {
    private String TOKEN;

    public App(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public String getTOKEN() {return TOKEN;}


    public void serializeAppSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/App.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.app, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deserializeAppSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/App.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.app = gson.fromJson(reader, App.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}