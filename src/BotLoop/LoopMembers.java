package BotLoop;

import BotChannel.BotChannel;
import Commands.CmdRemove;
import Core.Config;
import Member.*;
import Web.API;
import Chart.MemberBarChart;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class LoopMembers {

    public static void loopMembers(BotChannel membersChannel, BotChannel commitsCommandsChannel) {

        // deserialize must be done outside of for loop in order to not replace current config.members member
//        Config.members.deserializeMembersSimple();            // we dont need to load data here

        for (Member member : Config.members.getMembers()) {
//            Config.members.deserializeMembersSimple();
//            try {Thread.sleep(15 * 1000L);}
//            catch (InterruptedException e) {Thread.currentThread().interrupt();}
            System.out.println("\tCurrent member = "+ member.getMemberGithubName());

            String githubName = member.getMemberGithubName();
            String request1 = API.request("https://api.github.com/users/"+ githubName);
            String request2 = API.request("https://api.github.com/search/commits?q=author:"+
                            githubName +"&sort=author-date&order=desc&page=1",
                    "application/vnd.github.cloak-preview");
            String requestRepo = API.request("https://api.github.com/users/"+ githubName +"/repos");
//            System.out.println(request2);

            JsonObject api1 = (JsonObject) new JsonParser().parse(request1);
            JsonObject api2 = (JsonObject) new JsonParser().parse(request2);
            JsonArray apiRepo = (JsonArray) new JsonParser().parse(requestRepo);

            // API error handling
            if (api1 == null) break;
            if (/*api1.get("items").getAsJsonArray().size() == 0 || */api2.get("items").getAsJsonArray().size() == 0) {
                System.out.println("ERROR: "+ member.getMemberGithubName() +" items array is equal to 0!");
                continue;
            }
            try {
                if (api1.get("message").getAsString().contains("API rate limit exceeded") ||
                        api2.get("message").getAsString().contains("API rate limit exceeded")) {
                    System.out.println("RATE LIMITED! Sleeping for 3 minutes");
                    try {Thread.sleep(180 * 1000L);}
                    catch (InterruptedException e) {Thread.currentThread().interrupt();}
                    return;
                }
            } catch (Exception e) {}
            try {api1.get("login").getAsString();}
            catch (Exception e) {
                System.out.println("Error: Member Github account is no longer reachable!");
//                CmdRemove.removeMember();     // TODO: FINISH -> GET GITHUB ACCOUNT NAME + SOMETHING ELSE
                continue;
            }

            // Update repos / followers / following / avatar url
            String lastUpdate = api2.get("items").getAsJsonArray().get(0).getAsJsonObject().get("url").getAsString();

            member.setMemberGithubAvatarURL(api1.get("avatar_url").getAsString());
            member.setMemberGithubPublicRepos(api1.get("public_repos").getAsString());
            member.setMemberGithubFollowers(api1.get("followers").getAsString());
            member.setMemberGithubFollowing(api1.get("following").getAsString());

            // Member repo stats
            member.resetMemberRepoStats();
            if (apiRepo.size() != 0) {
                for (int i = 0; i < apiRepo.size(); i++) {
                    try {
                        String repoLanguage = apiRepo.get(i).getAsJsonObject().get("language").getAsString();
                        member.updateMemberRepoStats(repoLanguage);
                    } catch (Exception e) {
                        System.out.println("ERROR: Failed to get a programming language from "+ member.getMemberGithubName() +" repos!");
                    }
                }
            }

            // New commit detected!
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

                // Date and time formatting
                LocalDate date = LocalDate.parse(commitDate.substring(0, commitDate.indexOf(" ")).replaceAll(" ", ""));
                LocalTime time = LocalTime.parse(commitDate.substring(commitDate.indexOf(" ")).replaceAll(" ", ""));
                LocalDateTime newDateTime = LocalDateTime.of(date, time).plusHours(2);
                if (newDateTime.isBefore(LocalDateTime.now().minusHours(1))) {
                    System.out.println("ERROR: API call for "+ member.getMemberGithubName() +" is over an hour old. Continuing without updating!");
                    continue;
                }
                date = newDateTime.toLocalDate();
                time = newDateTime.toLocalTime();

                // Update member stats
                member.setMemberLastCommit(lastUpdate);
                member.setMemberPoints(member.getMemberPoints() + Integer.parseInt(additions));
                member.setMemberCommits(member.getMemberCommits() + 1);
                member.setMemberLinesAdded(member.getMemberLinesAdded() + Integer.parseInt(additions));
                member.setMemberLinesRemoved(member.getMemberLinesRemoved() + Integer.parseInt(deletions));
//                Config.members.serializeMembersSimple();

                // Update bot logs
                Config.botLogs.updateBotLogs(1, Integer.parseInt(additions), Integer.parseInt(deletions),Integer.parseInt(additions),0,0,0,0,0);

                // Create commit embed
                EmbedBuilder embed = new EmbedBuilder().setColor(Color.decode(member.getMemberColor()));
                embed.setThumbnail(member.getMemberGithubAvatarURL());
//                embed.setTitle("New [commit]("+ commitHtml +") by ["+ member.getMemberGithubName() +"]("+ authorHtml +")");
                embed.setTitle("Commit Detected!");
                embed.addField(repoName, "**New [commit]("+ commitHtml +") by ["+ member.getMemberGithubName() +"]("+ authorHtml +")**"+
                        "\n**Commit message:** "+ commitMsg +"\n**Date:** "+ date +" "+ time, false);
                embed.addField("Changes", "\n**{+}** "+ additions +" line"+ (additions.equals("1") ? "" : "s") +" added ("+
                        additions +" point"+ (additions.equals("1") ? "" : "s") +" added)"+
                        "\n**{-}** "+ deletions +" line"+ (deletions.equals("1") ? "" : "s") +" removed", false);
                Config.guild.getTextChannelById(commitsCommandsChannel.getChannelID()).sendMessage(embed.build()).queue();
                embed.clear();
            }
            // Save member data to json
            Config.members.serializeMembersSimple();

            // Edit member embed
            member.editMemberEmbed(membersChannel);

        }
    }
}

