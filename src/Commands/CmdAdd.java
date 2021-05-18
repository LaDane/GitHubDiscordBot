package Commands;

import Core.Config;
import Member.*;
import Web.API;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.util.function.Consumer;

public class CmdAdd {
    private static String defaultColor = "#0099ff";

    public static void cmdAdd(String msg, String memberID, String channelID) {
        if (!msg.contains(" "))                 // error
            Cmd.sendErrorEmbed("Missing GitHub Name!", null, channelID);

        else if (msg.contains("<@!")) {         // add another user
            String discordID = msg.substring(msg.lastIndexOf("!") + 1).replaceAll(">", "");

            Consumer<?super User> callbackUser = (response) -> addMember(msg.split(" ")[1], discordID, channelID);
            Config.jda.retrieveUserById(discordID).queue(callbackUser);
        }
        else {                                  // add self
            String githubName = msg.split(" ")[1];
            addMember(githubName, memberID, channelID);
        }
    }

    private static void addMember(String githubName, String memberID, String channelID) {
        Member memberCheck = Cmd.getMemberWithID(memberID);
        if (memberCheck != null)
            Cmd.sendErrorEmbed("You already have an account! (" + memberCheck.getMemberGithubName() + ")", memberCheck.getMemberGithubAvatarURL(), channelID);
        else {
            String request1 = API.request("https://api.github.com/users/" + githubName);
            String request2 = API.request("https://api.github.com/search/commits?q=author:" + githubName + "&sort=author-date&order=desc&page=1", "application/vnd.github.cloak-preview");

            JsonObject api1 = (JsonObject) new JsonParser().parse(request1);
            JsonObject api2 = (JsonObject) new JsonParser().parse(request2);

            // Error Handling
            String account;
            try {account = api1.get("login").getAsString();}
            catch (Exception e) {Cmd.sendErrorEmbed(githubName +" Github account does not exist!", null, channelID); return;}
            if (api2.get("items").getAsJsonArray().size() == 0) {
                Cmd.sendErrorEmbed("An error occurred while trying to link Github account!", null, channelID);
                return;
            }

            String membersChannelID = Config.allChannels.getMembersChannel().getChannelID();
            String githubCreatedAt = api1.get("created_at").getAsString()
                    .replaceAll("T", " ").replaceAll("Z","");

            // Create member object
            Member member = new Member(account,
                    api1.get("html_url").getAsString(),
                    api1.get("url").getAsString(),
                    api1.get("avatar_url").getAsString(),
                    api1.get("public_repos").getAsString(),
                    "https://github.com/"+ account +"?tab=repositories",
                    api1.get("followers").getAsString(),
                    "https://github.com/"+ account +"?tab=followers",
                    api1.get("following").getAsString(),
                    "https://github.com/"+ account +"?tab=following",
                    githubCreatedAt,
                    api2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString(),
                    memberID, "", defaultColor,
                    0,0,0,
                    0);

            Config.members.addToMembers(member);
            Config.members.serializeMembersSimple();

            // Send embeds
            EmbedBuilder embed = member.memberEmbed("Added new user "+ member.getMemberGithubName());
            Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();

            Consumer<? super Message> callbackMessage = (response) -> setMemberMessageID(member, response.getId());
            Config.guild.getTextChannelById(membersChannelID).sendMessage(embed.build()).queue(callbackMessage);
            embed.clear();
        }
    }

    private static void setMemberMessageID(Member member, String ID) {
        member.setMemberDiscordMsgID(ID);
        Config.members.serializeMembersSimple();
    }
}
