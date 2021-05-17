package BotMessage;

import Member.Member;

import java.util.Comparator;

public class LeaderboardSorter implements Comparator<Member> {
    @Override
    public int compare(Member m1, Member m2) {
        Integer m1Points = m1.getMemberPoints();
        Integer m2Points = m2.getMemberPoints();

        int iComp = (m1Points.compareTo(m2Points));
        return iComp;
    }
}
