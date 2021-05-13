package Commands;

import Core.Config;
import Member.Member;
import Web.API;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;

public class CmdRecent {
    private static int defaultDays = 30;

    public static void cmdRecent(String msg, String memberID, String channelID) {
        if (!msg.contains(" ")) {                // display msg author recent commits over default days
            Member member = Cmd.getMemberWithID(memberID);
            displayRecent(member, defaultDays, channelID);
        }
        else if (msg.contains("<@!")) {
            String stripped = msg.replaceAll("/recent ", "");
            int days = defaultDays;
            if (stripped.contains(" ")) days = Integer.parseInt(stripped.split(" ")[1]);
            int finalDays = days;
            String discordID = stripped.split(" ")[0]
                .substring(stripped.lastIndexOf("!") + 1)
                .replaceAll(">", "");

            Consumer<?super User> callbackUser = (response) -> displayRecent(Cmd.getMemberWithID(response.getId()), finalDays, channelID);
            Config.jda.retrieveUserById(discordID).queue(callbackUser);
        }
        else if (msg.split(" ")[1].matches(".*\\d.*")) {
            int days = Integer.parseInt(msg.split(" ")[1]);
            if (days > defaultDays) days = defaultDays;
            Member member = Cmd.getMemberWithID(memberID);
            displayRecent(member, days, channelID);
        }
    }

    private static void displayRecent(Member member, int days, String channelID) {
        if (member == null) {Cmd.sendErrorEmbed("Account does not exist!", null, channelID); return;}
        String githubName = member.getMemberGithubName();
        String request = API.request("https://api.github.com/search/commits?q=author:"+
                githubName +"&sort=author-date&order=desc&page=1",
                "application/vnd.github.cloak-preview");
        JsonObject api = (JsonObject) new JsonParser().parse(request);
        LocalDate stopDate = LocalDate.now().minusDays(days);

        EmbedBuilder embed = new EmbedBuilder().setColor(Color.decode(member.getMemberColor()));
        embed.setTitle("Commits by "+ githubName +" within the last "+ days +" day"+ (days != 1 ? "s" : ""));
        embed.setThumbnail(member.getMemberGithubAvatarURL());

        for (int i = 0; i < api.get("items").getAsJsonArray().size(); i++) {
            JsonObject object = api.get("items").getAsJsonArray().get(i).getAsJsonObject();
            String dateTime = object.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString();
            String date = dateTime.split("T")[0];
            if (LocalDate.parse(date).isBefore(stopDate))
                break;

            String time = dateTime.substring(dateTime.lastIndexOf("T") + 1).substring(0, 5);
            String repoName = object.get("repository").getAsJsonObject().get("full_name").getAsString();
            String message = object.get("commit").getAsJsonObject().get("message").getAsString();
            embed.addField("\u200B", "**Repo:** "+ repoName +"\n**Message**: "+ message +"\n**Date**: "+ date +"   **Time**: "+ time, false);
        }
        Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
        embed.clear();
    }
}
