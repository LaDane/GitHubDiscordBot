package Member;

public class MemberLog {
    private String memberLogDate;
    private int memberLogCommits;
    private int memberLogLinesAdded;
    private int memberLogLinesRemoved;
    private int memberLogPointsGiven;
    private int memberLogPointsWon;
    private int memberLogPointsLost;
    private int memberLogPointsSpent;

    public MemberLog(String memberLogDate, int memberLogCommits,
                     int memberLogLinesAdded, int memberLogLinesRemoved,
                     int memberLogPointsGiven, int memberLogPointsWon,
                     int memberLogPointsLost, int memberLogPointsSpent) {
        this.memberLogDate = memberLogDate;
        this.memberLogCommits = memberLogCommits;
        this.memberLogLinesAdded = memberLogLinesAdded;
        this.memberLogLinesRemoved = memberLogLinesRemoved;
        this.memberLogPointsGiven = memberLogPointsGiven;
        this.memberLogPointsWon = memberLogPointsWon;
        this.memberLogPointsLost = memberLogPointsLost;
        this.memberLogPointsSpent = memberLogPointsSpent;
    }

    public String getMemberLogDate() {return memberLogDate;}
    public int getMemberLogCommits() {return memberLogCommits;}
    public int getMemberLogLinesAdded() {return memberLogLinesAdded;}
    public int getMemberLogLinesRemoved() {return memberLogLinesRemoved;}
    public int getMemberLogPointsGiven() {return memberLogPointsGiven;}
    public int getMemberLogPointsWon() {return memberLogPointsWon;}
    public int getMemberLogPointsLost() {return memberLogPointsLost;}
    public int getMemberLogPointsSpent() {return memberLogPointsSpent;}

    public void setMemberLogCommits(int commits) {memberLogCommits = commits;}
    public void setMemberLogLinesAdded(int lines) {memberLogLinesAdded = lines;}
    public void setMemberLogLinesRemoved(int lines) {memberLogLinesRemoved = lines;}
    public void setMemberLogPointsGiven(int points) {memberLogPointsGiven = points;}
    public void setMemberLogPointsWon(int points) {memberLogPointsWon = points;}
    public void setMemberLogPointsLost(int points) {memberLogPointsLost = points;}
    public void setMemberLogPointsSpent(int points) {memberLogPointsSpent = points;}
}
