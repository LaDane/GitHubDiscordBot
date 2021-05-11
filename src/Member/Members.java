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


    public void serializeDatamatikerClassSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/Members.json");
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(Config.members, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeDatamatikerClassSimple() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File fileName = new File("src/Secrets/Members.json");
        try (Reader reader = new FileReader(fileName)) {
            Config.members = gson.fromJson(reader, Members.class);
        }
        catch (IOException e) {e.printStackTrace();}
    }

//    public static void reloadUserData() {
//        try {
//            // create Gson instance
//            Gson gson = new Gson();
//
//            // create a reader
//            try (Reader reader = new FileReader(new File("src/users.json"))) {
//                // convert JSON string to User object and add to array
////                members.add(gson.fromJson(reader,Member.class));
//                Member mem = gson.fromJson(reader,Member.class);
//                members.add(mem);
//                System.out.println("info: " + members.get(0).getMemberGithubName());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
}
