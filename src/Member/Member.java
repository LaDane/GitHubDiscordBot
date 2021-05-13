package Member;

import Core.Config;
import com.google.gson.JsonObject;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;

public class Member {
    private String memberGithubName;
    private String memberGithubURL;
    private String memberGithubAvatarURL;
    private String memberGithubPublicRepos;
    private String memberGithubPublicReposURL;
    private String memberGithubFollowers;
    private String memberGithubFollowersURL;
    private String memberGithubFollowing;
    private String memberGithubFollowingURL;
    private String memberGithubCreatedAt;
    private String memberLastCommit;
    private String memberDiscordID;
    private String memberDiscordMsgID;
    private String memberColor;
    private int memberPoints;
    private int memberCommits;
    private int memberLinesAdded;
    private int memberLinesRemoved;
    private ArrayList<String> memberItemsOwned;

    public Member(String memberGithubName, String memberGithubURL, String memberAvatarURL,
                  String memberGithubPublicRepos, String memberGithubPublicReposURL,
                  String memberGithubFollowers, String memberGithubFollowersURL,
                  String memberGithubFollowing, String memberGithubFollowingURL,
                  String memberGithubCreatedAt, String memberLastCommit, String memberDiscordID,
                  String memberDiscordMsgID, String memberColor,
                  int memberPoints, int memberCommits, int memberLinesAdded,
                  int memberLinesRemoved, ArrayList<String> memberItemsOwned) {
        this.memberGithubName = memberGithubName;
        this.memberGithubURL = memberGithubURL;
        this.memberGithubAvatarURL = memberAvatarURL;
        this.memberGithubPublicRepos = memberGithubPublicRepos;
        this.memberGithubPublicReposURL = memberGithubPublicReposURL;
        this.memberGithubFollowers = memberGithubFollowers;
        this.memberGithubFollowersURL = memberGithubFollowersURL;
        this.memberGithubFollowing = memberGithubFollowing;
        this.memberGithubFollowingURL = memberGithubFollowingURL;
        this.memberGithubCreatedAt = memberGithubCreatedAt;
        this.memberLastCommit = memberLastCommit;
        this.memberDiscordID = memberDiscordID;
        this.memberDiscordMsgID = memberDiscordMsgID;
        this.memberColor = memberColor;
        this.memberPoints = memberPoints;
        this.memberCommits = memberCommits;
        this.memberLinesAdded = memberLinesAdded;
        this.memberLinesRemoved = memberLinesRemoved;
        this.memberItemsOwned = memberItemsOwned;
    }

    public String getMemberGithubName() {return memberGithubName;}
    public String getMemberGithubURL() {return memberGithubURL;}
    public String getMemberGithubAvatarURL() {return memberGithubAvatarURL;}
    public String getMemberGithubPublicRepos() {return memberGithubPublicRepos;}
    public String getMemberGithubPublicReposURL() {return memberGithubPublicReposURL;}
    public String getMemberGithubFollowers() {return memberGithubFollowers;}
    public String getMemberGithubFollowersURL() {return memberGithubFollowersURL;}
    public String getMemberGithubFollowing() {return memberGithubFollowing;}
    public String getMemberGithubFollowingURL() {return memberGithubFollowingURL;}
    public String getMemberDiscordID() {return memberDiscordID;}
    public String getMemberDiscordMsgID() {return memberDiscordMsgID;}
    public String getMemberColor() {return memberColor;}
    public String getMemberLastCommit() {return memberLastCommit;}
    public int getMemberPoints() {return memberPoints;}
    public int getMemberCommits() {return memberCommits;}
    public int getMemberLinesAdded() {return memberLinesAdded;}
    public int getMemberLinesRemoved() {return memberLinesRemoved;}
    public ArrayList<String> getMemberItemsOwned() {return memberItemsOwned;}

    public void setMemberDiscordMsgID(String ID) {memberDiscordID = ID;}
    public void setMemberEmbedColor(String color) {memberColor = color;}
    public void setMemberPoints(int points) {memberPoints = points;}
    public void setMemberCommits(int commits) {memberCommits = commits;}
    public void setMemberLinesAdded(int lines) {memberLinesAdded = lines;}

    public void addMemberItemOwned(String itemName) {memberItemsOwned.add(itemName);}


    public EmbedBuilder memberEmbed(String title) {
        EmbedBuilder embed = new EmbedBuilder().setColor(Color.decode(memberColor));
        embed.setTitle(title);
        embed.setThumbnail(memberGithubAvatarURL);
        embed.addField("Public repositories",
                "["+ memberGithubPublicRepos +" repositor"+ (memberGithubPublicRepos.equals("1") ? "y" : "ies") +"]("+
                        memberGithubPublicReposURL +")", true);
        embed.addField("Followers",
                "["+ memberGithubFollowers +" follower"+ (memberGithubFollowers.equals("1") ? "" : "s")+ "]("+
                        memberGithubFollowersURL +")", true);
        embed.addField("Following", "[Following "+ memberGithubFollowing +"]("+ memberGithubFollowingURL +")", true);
        embed.addField("Points", memberPoints +" point"+ (memberPoints == 1 ? "" : "s"),true);
        embed.addField("Commits", memberCommits +" commit"+ (memberCommits == 1 ? "" : "s"), true);
        embed.addField("\u200B", "\u200B", true); // space
        embed.addField("Lines added", memberLinesAdded +" line"+ (memberLinesAdded == 1 ? "" : "s"), true);
        embed.addField("Lines removed", memberLinesRemoved +" line"+ (memberLinesRemoved == 1 ? "" : "s"), true);
        embed.addField("\u200B", "\u200B", true); // space
        embed.setFooter("Account created "+ memberGithubCreatedAt);
        return embed;
    }
}
