package Member;

import Core.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class Members {
    private ArrayList<Member> members = new ArrayList<>();

    public Members(ArrayList<Member> members) {
        this.members = members;
    }

    public ArrayList<Member> getMembers() {return members;}
    public void addToMembers(Member member) {members.add(member);}
    public void removeFromMembers(Member member) {members.remove(member);}


    public void serializeMembersSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/Members.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.members, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeMembersSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/Members.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.members = gson.fromJson(reader, Members.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }
}
