package BotLoop;

import Core.Config;
import Member.Member;
import Web.API;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoopUpdateMembers {

    public static void loopMembers() {

        for (Member member : Config.members.getMembers()) {
            String githubName = member.getMemberGithubName();
            String request = API.request("https://api.github.com/search/commits?q=author:"+
                            githubName +"&sort=author-date&order=desc&page=1",
                    "application/vnd.github.cloak-preview");

            JsonObject api = (JsonObject) new JsonParser().parse(request);
            String lastUpdate = api.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();

            if (lastUpdate.equals(member.getMemberLastCommit())) {
//                String linesAdded =

                member.setMemberCommits(member.getMemberCommits() + 1);
            }
        }
    }
}
