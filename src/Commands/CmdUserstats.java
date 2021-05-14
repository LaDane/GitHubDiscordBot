package Commands;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.util.ArrayList;
import Chart.*;
import BotLoop.*;
import Chart.MemberBarChart;
import Core.Config;
import Member.Member;
import Web.API;

public class CmdUserstats {
    public static void cmdUserstats(String msg, String memberID, String channelID) {
        String name = "";
        String userPoints = "0 (Not registered)";
        Member member = Cmd.getMemberWithID(memberID);
        try {
            //Message has param
            if (!msg.split(" ")[1].equals("")) {
                name = msg.split(" ")[1];

                //Check if user with github name is in database
                for (Member d : Config.members.getMembers()) {
                    if (d.getMemberGithubName().equalsIgnoreCase(name)) {
                        userPoints = String.valueOf(d.getMemberPoints());
                        member = d;
                    }
                }
            }
        } catch (Exception e) {
            //Message has no param

            //Check if message author has an account
            for (Member d : Config.members.getMembers()) {
                if (d.getMemberDiscordID().equals(memberID)) {
                    name = d.getMemberGithubName();
                    userPoints = String.valueOf(d.getMemberPoints());
                }
            }
        }

        //
        if (name.equals("")) {
            Cmd.sendErrorEmbed("User not found (or missing parameter)",null, channelID);
            return;
        }

        try {
            String request = API.request("https://api.github.com/users/" + name);
            JsonObject api = (JsonObject) new JsonParser().parse(request);
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.decode(member.getMemberColor()))
                    .setTitle("User stats for " + api.get("login").getAsString())
                    .setThumbnail(api.get("avatar_url").getAsString())
                    .addField("Public repositories", "[" + api.get("public_repos").getAsString() + " repositor" + (api.get("public_repos").getAsString().equals("1") ? "y" : "ies") + "](https://github.com/" + api.get("login").getAsString() + "?tab=repositories)", true)
                    .addField("Followers", "[" + api.get("followers").getAsString() + " follower" + (api.get("followers").getAsString().equals("1") ? "" : "s") + "](https://github.com/" + api.get("login").getAsString() + "?tab=followers)", true)
                    .addField("Following", "[Following " + api.get("following").getAsString() + "](https://github.com/" + api.get("login").getAsString() + "?tab=following)", true)
                    .addField("Account created", api.get("created_at").getAsString().replaceAll("T", "   ").replaceAll("Z",""), true)
                    .addField("Points", userPoints, true)
                    .setImage(MemberBarChart.memberBarChart(member));
//                    .setImage(Chart.createMemberBarChart(member,10,20,30,40,50,60,70));
            Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
            embed.clear();
        } catch (Exception e) {
            System.out.println(e);
            Cmd.sendErrorEmbed("GitHub user not found",null, channelID);
        }


        // set image = add image to embed
    }
}
