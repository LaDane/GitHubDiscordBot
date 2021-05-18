package Member;

import Core.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Member {
    private String memberGithubName;
    private String memberGithubURL;
    private String memberGithubApiURL;
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
    private ArrayList<RepoStats> memberRepoStats = new ArrayList<RepoStats>();
    private ArrayList<MemberLog> memberLogs = new ArrayList<MemberLog>();

    public Member(String memberGithubName, String memberGithubURL, String memberGithubApiURL,
                  String memberAvatarURL, String memberGithubPublicRepos, String memberGithubPublicReposURL,
                  String memberGithubFollowers, String memberGithubFollowersURL,
                  String memberGithubFollowing, String memberGithubFollowingURL,
                  String memberGithubCreatedAt, String memberLastCommit, String memberDiscordID,
                  String memberDiscordMsgID, String memberColor, int memberPoints,
                  int memberCommits, int memberLinesAdded, int memberLinesRemoved) {
        this.memberGithubName = memberGithubName;
        this.memberGithubURL = memberGithubURL;
        this.memberGithubApiURL = memberGithubApiURL;
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
    }

    public String getMemberGithubName() {return memberGithubName;}
//    public String getMemberGithubURL() {return memberGithubURL;}
//    public String getMemberGithubApiURL() {return memberGithubApiURL;}
    public String getMemberGithubAvatarURL() {return memberGithubAvatarURL;}
//    public String getMemberGithubPublicRepos() {return memberGithubPublicRepos;}
//    public String getMemberGithubPublicReposURL() {return memberGithubPublicReposURL;}
//    public String getMemberGithubFollowers() {return memberGithubFollowers;}
//    public String getMemberGithubFollowersURL() {return memberGithubFollowersURL;}
//    public String getMemberGithubFollowing() {return memberGithubFollowing;}
//    public String getMemberGithubFollowingURL() {return memberGithubFollowingURL;}
    public String getMemberDiscordID() {return memberDiscordID;}
    public String getMemberDiscordMsgID() {return memberDiscordMsgID;}
    public String getMemberColor() {return memberColor;}
    public String getMemberLastCommit() {return memberLastCommit;}
    public int getMemberPoints() {return memberPoints;}
    public int getMemberCommits() {return memberCommits;}
    public int getMemberLinesAdded() {return memberLinesAdded;}
    public int getMemberLinesRemoved() {return memberLinesRemoved;}
    public ArrayList<RepoStats> getMemberRepoStats() {return memberRepoStats;}
//    public ArrayList<MemberLog> getMemberLogs() {return memberLogs;}

    public void setMemberGithubAvatarURL(String url) {memberGithubAvatarURL = url;}
    public void setMemberGithubPublicRepos(String repos) {memberGithubPublicRepos = repos;}
    public void setMemberGithubFollowers(String followers) {memberGithubFollowers = followers;}
    public void setMemberGithubFollowing(String following) {memberGithubFollowing = following;}
    public void setMemberDiscordMsgID(String ID) {memberDiscordMsgID = ID;}
    public void setMemberLastCommit(String commit) {memberLastCommit = commit;}
    public void setMemberColor(String color) {memberColor = color;}
    public void setMemberPoints(int points) {memberPoints = points;}
    public void setMemberCommits(int commits) {memberCommits = commits;}
    public void setMemberLinesAdded(int lines) {memberLinesAdded = lines;}
    public void setMemberLinesRemoved(int lines) {memberLinesRemoved = lines;}

    public void addToMemberRepoStats(RepoStats stats) {memberRepoStats.add(stats);}
    public void resetMemberRepoStats() {memberRepoStats.clear();}

    public void addToMemberLogs(MemberLog log) {memberLogs.add(log);}

    public void updateMemberRepoStats(String repoLanguage) {
        boolean languageExistsInRepoStats = false;
        for (RepoStats repoStats : memberRepoStats) {
            if (repoStats.getRepoLanguage().matches(repoLanguage)) {
                repoStats.setRepoLanguageAmount(repoStats.getRepoLanguageAmount() + 1);
                languageExistsInRepoStats = true;
            }
        }
        if (!languageExistsInRepoStats) {
            RepoStats repoStats = new RepoStats(repoLanguage, 1);
            addToMemberRepoStats(repoStats);
        }
    }

    public void updateMemberLogs(int commits, int linesAdd, int linesRem,
                                 int pointsGiven, int pointsWon, int pointsLost, int pointsSpent) {
        LocalDate today = LocalDate.now();
        MemberLog latestMemberLog = getMemberLog(today);
        if (latestMemberLog == null) {
            latestMemberLog = new MemberLog(today.toString(), commits, linesAdd, linesRem,
                    pointsGiven, pointsWon, pointsLost, pointsSpent);
            addToMemberLogs(latestMemberLog);
        } else {
            latestMemberLog.setMemberLogCommits(latestMemberLog.getMemberLogCommits() + commits);
            latestMemberLog.setMemberLogLinesAdded(latestMemberLog.getMemberLogLinesAdded() + linesAdd);
            latestMemberLog.setMemberLogLinesRemoved(latestMemberLog.getMemberLogLinesRemoved() + linesRem);

            latestMemberLog.setMemberLogPointsGiven(latestMemberLog.getMemberLogPointsGiven() + pointsGiven);
            latestMemberLog.setMemberLogPointsWon(latestMemberLog.getMemberLogPointsWon() + pointsWon);
            latestMemberLog.setMemberLogPointsLost(latestMemberLog.getMemberLogPointsLost() + pointsLost);
            latestMemberLog.setMemberLogPointsSpent(latestMemberLog.getMemberLogPointsSpent() + pointsSpent);
        }
    }

    public int[] getMemberLogCommitsLines(LocalDate date) {
        MemberLog latestMemberLog = getMemberLog(date);
        if (latestMemberLog == null)
            return new int[3];
        else {
            int logCommits = latestMemberLog.getMemberLogCommits();
            int logLinesAdded = latestMemberLog.getMemberLogLinesAdded();
            int logLinesRemoved = latestMemberLog.getMemberLogLinesRemoved();
            return new int[]{logCommits, logLinesAdded, logLinesRemoved};
        }
    }

    public int[] getMemberLogPoints(LocalDate date) {
        MemberLog latestMemberLog = getMemberLog(date);
        if (latestMemberLog == null)
            return new int[4];
        else {
            int pointsGiven = latestMemberLog.getMemberLogPointsGiven();
            int pointsWon = latestMemberLog.getMemberLogPointsWon();
            int pointsLost = latestMemberLog.getMemberLogPointsLost();
            int pointsSpent = latestMemberLog.getMemberLogPointsSpent();
            return new int[]{pointsGiven, pointsWon, pointsLost, pointsSpent};
        }
    }

    private MemberLog getMemberLog(LocalDate date) {
        for (MemberLog log : memberLogs) {
            if (log.getMemberLogDate().equals(date.toString()))
                return log;
        }
        return null;
    }

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
        embed.addField("\u200B", "[Account]("+ memberGithubURL +") created "+ memberGithubCreatedAt, true);
        return embed;
    }

    public void editMemberEmbed() {
        TextChannel mChannel = Config.guild.getTextChannelById(Config.allChannels.getMembersChannel().getChannelID());
        if (mChannel == null) {System.out.println("ERROR: Members channel does not exist!"); return;}

        EmbedBuilder memberEmbed = memberEmbed(memberGithubName);
        mChannel.retrieveMessageById(memberDiscordMsgID).queue((message) -> {
            message.editMessage(memberEmbed.build()).queue();
        }, (failure) -> {
            if (failure instanceof ErrorResponseException) {
                System.out.println("ERROR: Members embed message does not exist in Members channel!");
            }
        });
    }
}
