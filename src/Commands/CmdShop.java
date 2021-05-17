package Commands;

import BotChannel.BotChannel;
import Core.Config;
import Member.Member;
import Message.EditMessage;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CmdShop {
    public static void cmdShop(String memberID, String channelID) {
        BotChannel leaderBoardChannel = null;
        for (BotChannel channel : Config.allChannels.getAllChannels()) {
            if (channel.getChannelName().equals("leaderboard"))
                leaderBoardChannel = channel;
        }
        String leaderBoardChannelID = leaderBoardChannel.getChannelID();
        boolean hasAccount = false;
        for (Member d : Config.members.getMembers()) {
            if (d.getMemberDiscordID().equals(memberID)) {
                hasAccount = true;
            }
        }
        if (hasAccount) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.decode("#0099ff"))
                    .setTitle("Welcome to the shop")
                    .addField("You can use the react buttons to buy a theme color", "\u200B", false)
                    .addField(":brown_square:", "1.000 Points (:poop:)", true)
                    .addField(":yellow_square:", "10.000 Points (Piss)", true)
                    .addField(":orange_square:", "25.000 Points (ORANGE)", true)
                    .addField(":blue_square:", "50.000 Points (Blue)", true)
                    .addField(":green_square:", "75.000 Points (Green man)", true)
                    .addField(":red_square:", "125.000 Points (EL ROJO)", true)
                    .addField(":purple_square:", "250.000 Points (PURPLE DRANK :eggplant:)", true)
                    .addField(":white_large_square:", "500.000 Points (WHITE ASS :gorilla:)", true)
                    .addField(":black_large_square:", "1.000.000 Points (OG BIG BALLER :tophat:)", true);
            //Config.guild.getTextChannelById(channelID).sendMessage(embed.build()).queue();
            Config.guild.getTextChannelById(leaderBoardChannelID).sendMessage(embed.build()).queue((message) -> {
                Long shopMessageId = message.getIdLong();
              // EditMessage.editMessage(shopMessageId,leaderBoardChannelID);
                // use messageId here
            });
            embed.clear();
            //EditMessage.editMessage();
        } else {
            Cmd.sendErrorEmbed("User not found", null, channelID);
        }
    }

    public static void productBuy(Member member, String color, int price){
        int pointsAvailable = member.getMemberPoints();
        if (pointsAvailable>=price){
            System.out.println("YOu have money enough :D ");
        }else{
            System.out.println("Get out of here poor shit!");
        }
    }
}
