package Member;

import Core.Config;

import java.util.ArrayList;

public class Member {

    /*public Member(Object users) {
        this.users = users;
    }

    public Object getUsers() {
        return users;
    }*/


    private String memberGithubName;
    private String memberGithubURL;
    private String memberGithubAvatarURL;
    private String memberLastCommit;
    private String memberDiscordID;
    private int memberPoints;
    private ArrayList<String> memberItemsOwned;

    public Member(String memberGithubName, String memberGithubURL, String memberAvatarURL, String memberLastCommit, String memberDiscordID, int memberPoints, ArrayList<String> memberItemsOwned) {
        this.memberGithubName = memberGithubName;
        this.memberGithubURL = memberGithubURL;
        this.memberGithubAvatarURL = memberAvatarURL;
        this.memberDiscordID = memberDiscordID;
        this.memberLastCommit = memberLastCommit;
        this.memberPoints = memberPoints;
        this.memberItemsOwned = memberItemsOwned;
    }

    public String getMemberGithubName() {return memberGithubName;}
    public String getMemberGithubURL() {return memberGithubURL;}
    public String getMemberGithubAvatarURL() {return memberGithubAvatarURL;}
    public String getMemberDiscordID() {return memberDiscordID;}
    public String getMemberLastCommit() {return memberLastCommit;}
    public int getMemberPoints() {return memberPoints;}
    public ArrayList<String> getMemberItemsOwned() {return memberItemsOwned;}

    public void addMemberItemOwned(String itemName) {memberItemsOwned.add(itemName);}
}
