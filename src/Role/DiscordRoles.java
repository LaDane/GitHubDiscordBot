package Role;

import Core.Config;
import Member.Members;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class DiscordRoles {
    private ArrayList<DiscordRole> discordRoles = new ArrayList<>();

    public DiscordRoles(ArrayList<DiscordRole> discordRoles) {
        this.discordRoles = discordRoles;
    }

    public ArrayList<DiscordRole> getDiscordRoles() {return discordRoles;}
    public void addToDiscordRoles(DiscordRole discordRole) {discordRoles.add(discordRole);}


    public void serializeDiscordRolesSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/DiscordRoles.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.discordRoles, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deserializeDiscordRolesSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/DiscordRoles.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.discordRoles = gson.fromJson(reader, DiscordRoles.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
