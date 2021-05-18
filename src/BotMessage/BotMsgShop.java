package BotMessage;

import Core.Config;
import Member.Member;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.awt.*;

public class BotMsgShop {
    public static EmbedBuilder shopEmbed() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.decode("#0099ff"))
                .setTitle("Welcome to the shop!")
                .addField("You can use the react buttons to buy a theme color", "\u200B", false)
                .addField(":brown_square:", "100 Points (:poop:)", true)
                .addField(":yellow_square:", "1.000 Points (Gold)", true)
                .addField(":orange_square:", "2.500 Points (Amber)", true)
                .addField(":blue_square:", "5.000 Points (Azure)", true)
                .addField(":green_square:", "7.500 Points (Emerald)", true)
                .addField(":red_square:", "12.500 Points (Ruby)", true)
                .addField(":purple_square:", "25.000 Points (Amethyst)", true)
                .addField(":white_large_square:", "50.000 Points (Quartz)", true)
                .addField(":black_large_square:", "1.000.000 Points (OG BIG BALLER :tophat:)", true);
        return embed;
    }

    public static void buyProduct(Member member, String color, int price){
        EmbedBuilder embed = new EmbedBuilder();

        int pointsAvailable = member.getMemberPoints();
        if (pointsAvailable >= price){
            member.setMemberColor(color);
            member.setMemberPoints(pointsAvailable - price);
            member.updateMemberLogs(0,0,0,0,0,0, price);

            embed
                .setColor(Color.decode(color))
                .setTitle(member.getMemberGithubName() + " has successfully bought the color for " + price + " points!");
            Config.botLogs.updateBotLogs(0,0,0,0,0,0, price,0,0);
            Config.members.serializeMembersSimple();

            member.editMemberEmbed();

        } else {
            embed
                .setColor(Color.red)
                .setTitle("You do not have enough points to buy the color. You need " + (price - member.getMemberPoints()) + " more points!");
        }

        String leaderBoardChannelID = Config.allChannels.getLeaderboardChannel().getChannelID();
        Config.guild.getTextChannelById(leaderBoardChannelID).sendMessage(embed.build()).queue((message) -> {
            String messageID = message.getId();
            try {
                Thread.sleep(5 * 1000L);
                TextChannel tChannel = Config.guild.getTextChannelById(leaderBoardChannelID);

                if (tChannel != null) {
                    tChannel.retrieveMessageById(messageID).queue((msg) -> {
                        msg.delete().queue();
                    }, (failure) -> {
                        if (failure instanceof ErrorResponseException) {
                            System.out.println("ERROR: Could not delete shop buy feedback message message!");
                        }
                    });
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            embed.clear();
        });
    }
}
