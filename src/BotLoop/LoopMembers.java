package BotLoop;

import BotChannel.BotChannel;
import Commands.CmdRemove;
import Core.Config;
import Member.Member;
import Web.API;
import Chart.MemberBarChart;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.awt.*;

public class LoopMembers {

    public static void loopMembers(BotChannel membersChannel, BotChannel leaderboardChannel, BotChannel commitsCommandsChannel) {

        for (Member member : Config.members.getMembers()) {
            try {Thread.sleep(3 * 1000L);}
            catch (InterruptedException e) {Thread.currentThread().interrupt();}
            System.out.println("Current member = "+ member.getMemberGithubName());

            String githubName = member.getMemberGithubName();
            String request1 = API.request("https://api.github.com/users/" + githubName);
            String request2 = API.request("https://api.github.com/search/commits?q=author:"+
                            githubName +"&sort=author-date&order=desc&page=1",
                    "application/vnd.github.cloak-preview");

            JsonObject api1 = (JsonObject) new JsonParser().parse(request1);
            JsonObject api2 = (JsonObject) new JsonParser().parse(request2);
            System.out.println(api1);

            // API error handling
            if (api1 == null) {
                System.out.println("HERE");
                break;
            }

            try {api1.get("login").getAsString();}
            catch (Exception e) {
                System.out.println("Member Github account is no longer reachable!");
                e.printStackTrace();
                System.out.println(e);
                break;
//                CmdRemove.removeMember();
            }
            if (api2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString() == null)
                break;
            String lastUpdate = api2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();

            member.setMemberGithubPublicRepos(api1.get("public_repos").getAsString());
            member.setMemberGithubFollowers(api1.get("followers").getAsString());
            member.setMemberGithubFollowing(api1.get("following").getAsString());
            Config.members.serializeMembersSimple();

            if (!lastUpdate.equals(member.getMemberLastCommit())) {
                String request3 = API.request(lastUpdate);
                JsonObject api3 = (JsonObject) new JsonParser().parse(request3);

                // Extract data
                String repoName = api2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("repository").getAsJsonObject().get("full_name").getAsString();
                String commitMsg = api3.get("commit").getAsJsonObject().get("message").getAsString();
                String commitDate = api3.get("commit").getAsJsonObject().get("committer").getAsJsonObject().get("date").getAsString();
                commitDate = commitDate.replaceAll("T", " ").replaceAll("Z","");
                String commitHtml = api3.get("html_url").getAsString();
                String authorHtml = api3.get("author").getAsJsonObject().get("html_url").getAsString();
                String additions = api3.get("stats").getAsJsonObject().get("additions").getAsString();
                String deletions = api3.get("stats").getAsJsonObject().get("deletions").getAsString();

                // Update member stats
                member.setMemberLastCommit(lastUpdate);
                member.setMemberPoints(member.getMemberPoints() + Integer.parseInt(additions));
                member.setMemberCommits(member.getMemberCommits() + 1);
                member.setMemberLinesAdded(member.getMemberLinesAdded() + Integer.parseInt(additions));
                member.setMemberLinesRemoved(member.getMemberLinesRemoved() + Integer.parseInt(deletions));
                Config.members.serializeMembersSimple();

                // Update bot logs
                Config.botLogs.updateBotLogs(1, Integer.parseInt(additions), Integer.parseInt(deletions));

                // Create commit embed
                EmbedBuilder embed = new EmbedBuilder().setColor(Color.decode(member.getMemberColor()));
                embed.setThumbnail(member.getMemberGithubAvatarURL());
//                embed.setTitle("New [commit]("+ commitHtml +") by ["+ member.getMemberGithubName() +"]("+ authorHtml +")");
                embed.setTitle("Commit Detected!");
                embed.addField(repoName, "**New [commit]("+ commitHtml +") by ["+ member.getMemberGithubName() +"]("+ authorHtml +")**"+
                        "\n**Commit message:** "+ commitMsg +"\n**Date:** "+ commitDate, false);
                embed.addField("Changes", "\n**{+}** "+ additions +" line"+ (additions.equals("1") ? "" : "s") +" added ("+
                        additions +" point"+ (additions.equals("1") ? "" : "s") +" added)"+
                        "\n**{-}** "+ deletions +" line"+ (deletions.equals("1") ? "" : "s") +" removed", false);
                Config.guild.getTextChannelById(commitsCommandsChannel.getChannelID()).sendMessage(embed.build()).queue();
                embed.clear();
            }

            // Edit member embed
            member.editMemberEmbed(membersChannel);
        }
    }
}

